package cn.fengyunxiao.nest.service;

import java.sql.Timestamp;
import java.util.List;
import cn.fengyunxiao.nest.dao.LetterDao;
import cn.fengyunxiao.nest.config.Config;
import cn.fengyunxiao.nest.config.JsonResult;
import cn.fengyunxiao.nest.entity.Letter;
import cn.fengyunxiao.nest.util.XssUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LetterService {
	private LetterDao letterDao;
	private MailService mailService;
    private static final Logger logger = LoggerFactory.getLogger(LetterService.class);

	@Autowired
	public void setLetterDao(LetterDao letterDao) {
		this.letterDao = letterDao;
	}
	@Autowired
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Letter> listLetter(Integer lid, Integer number, Boolean isRand) {
		if (lid == null || lid <= 0) {
			lid = Integer.MAX_VALUE;
		}
		if (number == null) {
			number = 4;
		}
		if (number > 32) {
			number = 32;
		}
		if (number < 1) {
			number = 1;
		}
		if (isRand == null) {
			isRand = false;
		}

		if (isRand) {
			return letterDao.listByRand(number);
		} else {
			// 最后四条留言需要在首页显示，使用频率较高，优先使用缓存，插入新留言或点赞时清除缓存
			if (lid==Integer.MAX_VALUE && number==4) {
				if (Config.lastLetter4 != null) {
					return Config.lastLetter4;
				}
				Config.lastLetter4 = letterDao.listLetter(lid, number);
				return Config.lastLetter4;
			}

			return letterDao.listLetter(lid, number);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public JsonResult<String> insertLetter(String ip, String nickname, String content) {
		JsonResult<String> result = new JsonResult<>();

		// 留言防刷，一个ip，指定时间内最多留言几个
		if (Config.LETTER_TODAY_IP.containsKey(ip)) {
			Integer total = Config.LETTER_TODAY_IP.get(ip);

			if (total >= Config.LETTER_TODAY_IP_MAX) {
				return result.error(-1, "该 ip 今日留言次数太多，请择日再留言。");
			}

			Config.LETTER_TODAY_IP.put(ip, ++total);
		} else {
			Config.LETTER_TODAY_IP.put(ip, 1);
		}

		if (ip == null) {
			ip = "";
		}
		if (ip.length() > 150) {
			ip = ip.substring(0, 150);
		}

		if (nickname == null || nickname.equals("")) {
			return result.error(-1, "昵称丢了");
		}
		nickname = XssUtil.replaceHtmlToFull(nickname);
		if (nickname.length() > 16) {
			nickname = nickname.substring(0, 16);
		}
		if (content == null || content.equals("")) {
			return result.error(-1, "脚印丢了");
		}
		content = XssUtil.replaceHtmlToFull(content);
		if (content.length()>255) {
			content = content.substring(0, 255);
		}

		Letter letter = new Letter();
		letter.setNickname(nickname);
		letter.setContent(content);
		letter.setZan(0);
		letter.setIp(ip);
		letter.setPubtime(new Timestamp(System.currentTimeMillis()));
		letterDao.insertLetter(letter);

		Config.lastLetter4 = null;
		// 新线程：发送邮件通知
		String text = "昵称："+nickname+"；留言内容："+content;
		mailService.sendMail("我们的小窝留言", text);
		return result.ok(null, "留言成功");
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void zanLetter(int lid) {
		letterDao.zanLetter(lid);
		Config.lastLetter4 = null;
	}
}
