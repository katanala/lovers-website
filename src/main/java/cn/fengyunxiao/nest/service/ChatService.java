package cn.fengyunxiao.nest.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

import cn.fengyunxiao.nest.config.Config;
import cn.fengyunxiao.nest.util.Jackson;
import cn.fengyunxiao.nest.util.XssUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import cn.fengyunxiao.nest.dao.ChatDao;
import cn.fengyunxiao.nest.entity.Chat;
import cn.fengyunxiao.nest.util.RobotUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.websocket.Session;

@Service
public class ChatService {
	private ChatDao chatDao;
	private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

	@Autowired
	public void setChatDao(ChatDao chatDao) {
		this.chatDao = chatDao;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Chat insertMessage(String object) {
		Chat message = null;
		try {
			message = Jackson.toObject(object, Chat.class);
		} catch (IOException e) {
			logger.error("聊天string转化为object失败。");
			return null;
		}
		if (message.getContent()==null || message.getContent().trim().equals("")) {
			return null;
		}
		message.setContent(XssUtil.replaceHtmlToFull(message.getContent()));
		if (message.getContent().length()>512) {
			message.setContent( message.getContent().substring(0, 512) );
		}
		if (message.getMale()==null) {
			return null;
		}

		message.setPubtime(new Timestamp(System.currentTimeMillis()));
		chatDao.insertMessage(message);
		return message;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Chat insertRobotMessage(String content) {
		String text  = RobotUtil.getRobotMessage(content);
		if (text == null) {
			text  = RobotUtil.getLiliRobotMessage(content);
		}

		if (text == null) {
			logger.error("从api接口获取机器人回复失败。");
			return null;
		}
		text = XssUtil.replaceHtmlToFull(text);
		if (text.length() > 512) {
			text = text.substring(0, 512);
		}

		Chat robotMessage = new Chat();
		robotMessage.setContent(text);
		robotMessage.setMale((byte) 3);
		robotMessage.setPubtime(new Timestamp(System.currentTimeMillis()));

		chatDao.insertMessage(robotMessage);
		return robotMessage;
	}

	@Transactional(propagation= Propagation.SUPPORTS,readOnly=true)
	public String getRecentMessage(String max) {
		int maxtemp = 0;
		
		try {
			maxtemp = Integer.parseInt(max);
		} catch (Exception e) {
			maxtemp = Integer.MAX_VALUE;
		}
		if (maxtemp <= 0) {
			maxtemp = Integer.MAX_VALUE;
		}
		
		List<Chat> messages = chatDao.list(maxtemp, 32);
		Collections.reverse(messages);
		String json;
		try {
			json = Jackson.toJson(messages);
		} catch (JsonProcessingException e) {
			logger.error("获取历史聊天记录失败。");
			json = "[]";
		}
		return json;
	}

	@Transactional(propagation= Propagation.SUPPORTS,readOnly=true)
	public List<Chat> list(int cid, int number) {
		if (number > 32) {
			number = 32;
		}
		
		if (cid <= 0) {
			cid = Integer.MAX_VALUE;
		}

		return chatDao.list(cid, number);
	}

	public boolean isNumber(String str) {
		if (str == null || str.equals("")) {
			return false;
		}
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public synchronized boolean isSafe(Session session) {
		// 去掉超过3秒未说话的，不是灌水的
		long nowTime = System.currentTimeMillis();
		Iterator<Map.Entry<String, Long>> iterator = Config.RECORD_CHAT_ID.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Long> next = iterator.next();
			Long oldtime = next.getValue();
			if (nowTime - oldtime > 3000) {
				iterator.remove();
			}
		}

		// 检测当前的id
		Long oldtime = Config.RECORD_CHAT_ID.get(session.getId());
		// 如果没有记录过Ip，表示没有操作过，放行。否则是1s内操作过，阻止。
		if (oldtime == null) {
			Config.RECORD_CHAT_ID.put(session.getId(), System.currentTimeMillis());
			return true;
		} else {
			return false;
		}
	}
}
