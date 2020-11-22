package cn.fengyunxiao.nest.service;

import cn.fengyunxiao.nest.config.Config;
import cn.fengyunxiao.nest.config.JsonResult;
import cn.fengyunxiao.nest.dao.LetterDao;
import cn.fengyunxiao.nest.dao.ReplyDao;
import cn.fengyunxiao.nest.entity.Letter;
import cn.fengyunxiao.nest.entity.LetterAndReply;
import cn.fengyunxiao.nest.entity.Reply;
import cn.fengyunxiao.nest.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class LetterService {
    @Autowired
    private LetterDao letterDao;
    @Autowired
    private ReplyDao replyDao;
    @Autowired
    private MailService mailService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(LetterService.class);

    @Cacheable(value = { "letter_reply" }, unless = "#isRand")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<LetterAndReply> listLetterAndReply(final Integer lid, final Integer number, final Boolean isRand) {
        LetterService.logger.info("sql_letter:lid:{},num:{},rand:{}", lid, number, isRand);
        final List<Letter> letters = this.listLetter(lid, number, isRand);
        final List<Integer> lids = new ArrayList<Integer>();
        final List<LetterAndReply> letterAndReplies = new ArrayList<LetterAndReply>();
        for (final Letter letter : letters) {
            lids.add(letter.getLid());
        }
        List<Reply> replies = new ArrayList<Reply>();
        if (lids.size() > 0) {
            replies = this.replyDao.listByLid(lids);
        }
        for (final Letter letter2 : letters) {
            final LetterAndReply letterAndReply = new LetterAndReply();
            letterAndReply.setLetter(letter2);
            for (final Reply reply : replies) {
                if (reply.getLid() == (int)letter2.getLid()) {
                    letterAndReply.getReplies().add(reply);
                }
            }
            letterAndReplies.add(letterAndReply);
        }
        return letterAndReplies;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Letter> listLetter(Integer lid, Integer number, final Boolean isRand) {
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
        if (isRand != null && isRand) {
            return this.letterDao.listByRand(number);
        }
        return this.letterDao.listLetter(lid, number);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Reply> listReply(Integer replyid, Integer number) {
        if (replyid == null || replyid <= 0) {
            replyid = Integer.MAX_VALUE;
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
        return this.replyDao.listReply(replyid, number);
    }

    @CacheEvict(value = { "letter_reply" }, condition = "#result.code == 0", allEntries = true)
    @Transactional(propagation = Propagation.REQUIRED)
    public JsonResult<String> insertLetter(String ip, String nickname, String content, String email) {
        final JsonResult<String> result = new JsonResult<String>();
        if (ip == null) {
            ip = "";
        }
        if (ip.length() > 150) {
            ip = ip.substring(0, 150);
        }
        if (!this.isSafe(ip)) {
            return result.error(-1, "ip limited");
        }
        if (email == null) {
            email = "";
        }
        if (email.length() > 50) {
            email = email.substring(0, 50);
        }
        if (nickname == null || nickname.equals("")) {
            return result.error(-1, "nickname empty");
        }
        if (nickname.length() > 16) {
            nickname = nickname.substring(0, 16);
        }
        if (content == null || content.equals("")) {
            return result.error(-1, "content empty");
        }
        if (content.length() > 255) {
            content = content.substring(0, 255);
        }
        final Letter letter = new Letter();
        letter.setNickname(nickname);
        letter.setContent(content);
        letter.setZan(0);
        letter.setIp(ip);
        letter.setEmail(email);
        letter.setPubtime(new Timestamp(System.currentTimeMillis()));
        this.letterDao.insertLetter(letter);
        if (!ip.equalsIgnoreCase("localhost") && !ip.equalsIgnoreCase("127.0.0.1")) {
            final String text = "nickname:" + nickname + ";content:" + content;
            this.mailService.sendMail("nest letter:", text);
        }
        return result.ok(null, "ok");
    }

    @CacheEvict(value = { "letter_reply" }, condition = "#result.code == 0", allEntries = true)
    @Transactional(propagation = Propagation.REQUIRED)
    public JsonResult<String> insertReply(String ip, String nickname, String content, final Integer lid) {
        final JsonResult<String> result = new JsonResult<String>();
        if (ip == null) {
            ip = "";
        }
        if (ip.length() > 150) {
            ip = ip.substring(0, 150);
        }
        if (!this.isSafe(ip)) {
            return result.error(-1, "ip limited");
        }
        if (lid == null || lid < 1) {
            return result.error(-1, "lid error");
        }
        if (nickname == null || nickname.equals("")) {
            return result.error(-1, "nickname error");
        }
        if (nickname.length() > 16) {
            nickname = nickname.substring(0, 16);
        }
        if (content == null || content.equals("")) {
            return result.error(-1, "content error");
        }
        if (content.length() > 255) {
            content = content.substring(0, 255);
        }
        final Reply reply = new Reply();
        reply.setContent(content);
        reply.setNickname(nickname);
        reply.setIp(ip);
        reply.setLid(lid);
        reply.setCurtime(new Timestamp(System.currentTimeMillis()));
        this.replyDao.insert(reply);
        if (!ip.equalsIgnoreCase("localhost") && !ip.equalsIgnoreCase("127.0.0.1")) {
            final String text = "nickname:" + nickname + ",content:" + content;
            this.mailService.sendMail("nest reply:", text);
        }
        return result.ok(null, "ok");
    }

    @CacheEvict(value = { "letter_reply" }, condition = "#result.code == 0", allEntries = true)
    @Transactional(propagation = Propagation.REQUIRED)
    public JsonResult<String> updateLetter(final Letter letter, final String sign) {
        final JsonResult<String> result = new JsonResult<String>();
        if (letter == null) {
            return result.error(-1, "letter empty");
        }
        if (letter.getLid() == null || letter.getLid() < 1) {
            return result.error(-1, "lid error:" + letter.getLid());
        }
        if (letter.getNickname() == null || letter.getNickname().equals("")) {
            return result.error(-1, "nickname error：" + letter.getNickname());
        }
        if (letter.getNickname().length() > 16) {
            letter.setNickname(letter.getNickname().substring(0, 16));
        }
        if (letter.getContent() == null || letter.getContent().equals("")) {
            return result.error(-1, "content error:" + letter.getContent());
        }
        if (letter.getContent().length() > 255) {
            letter.setContent(letter.getContent().substring(0, 255));
        }
        if (sign == null || !TokenUtil.isRightSign(sign)) {
            return result.error(-1, "sign error");
        }
        if (this.letterDao.updateLetter(letter) < 1) {
            return result.error(-1, "sql error");
        }
        return result.ok(null, "ok");
    }

    @CacheEvict(value = { "letter_reply" }, condition = "#result.code == 0", allEntries = true)
    @Transactional(propagation = Propagation.REQUIRED)
    public JsonResult<String> updateReply(final Reply reply, final String sign) {
        final JsonResult<String> result = new JsonResult<String>();
        if (reply == null) {
            return result.error(-1, "reply empty");
        }
        if (reply.getReplyid() == null || reply.getReplyid() < 1) {
            return result.error(-1, "rid error:" + reply.getReplyid());
        }
        if (reply.getNickname() == null || reply.getNickname().equals("")) {
            return result.error(-1, "nickname error:" + reply.getNickname());
        }
        if (reply.getNickname().length() > 16) {
            reply.setNickname(reply.getNickname().substring(0, 16));
        }
        if (reply.getContent() == null || reply.getContent().equals("")) {
            return result.error(-1, "content error:" + reply.getContent());
        }
        if (reply.getContent().length() > 255) {
            reply.setContent(reply.getContent().substring(0, 255));
        }
        if (sign == null || !TokenUtil.isRightSign(sign)) {
            return result.error(-1, "sign error");
        }
        if (this.replyDao.updateReply(reply) < 1) {
            return result.error(-1, "sql error");
        }
        return result.ok(null, "ok");
    }

    @CacheEvict(value = { "letter_reply" }, allEntries = true)
    @Transactional(propagation = Propagation.REQUIRED)
    public void zanLetter(final int lid) {
        this.letterDao.zanLetter(lid);
    }

    public synchronized boolean isSafe(final String ip) {
        final String key = "ip_message:" + ip;
        final BoundValueOperations<String, String> operations = this.stringRedisTemplate.boundValueOps(key);
        final Long increment = operations.increment();
        if (increment == null || increment <= Config.letterMaxNum) {
            operations.expire(21600L, TimeUnit.SECONDS);
            return true;
        }
        return false;
    }
}
