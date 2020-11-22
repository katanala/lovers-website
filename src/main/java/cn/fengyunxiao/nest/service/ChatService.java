package cn.fengyunxiao.nest.service;

import cn.fengyunxiao.nest.config.JsonResult;
import cn.fengyunxiao.nest.dao.ChatDao;
import cn.fengyunxiao.nest.entity.Chat;
import cn.fengyunxiao.nest.util.Jackson;
import cn.fengyunxiao.nest.util.RobotUtil;
import cn.fengyunxiao.nest.util.TokenUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.websocket.Session;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ChatService {
    private ChatDao chatDao;
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    @Autowired private StringRedisTemplate stringRedisTemplate;

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
        if (message.getContent() == null || message.getContent().trim().equals("")) {
            return null;
        }
        if (message.getContent().length() > 512) {
            message.setContent(message.getContent().substring(0, 512));
        }
        if (message.getMale() == null) {
            return null;
        }

        message.setPubtime(new Timestamp(System.currentTimeMillis()));
        chatDao.insertChat(message);
        return message;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public JsonResult<String> updateChat(Chat chat, String sign) {
        JsonResult<String> result = new JsonResult<>();
        if (chat == null || chat.getCid() == null || chat.getCid() < 1) {
            return result.error(-1, "编号丢了");
        }
        if (chat.getContent() == null || chat.getContent().equals("")) {
            return result.error(-1, "内容丢了");
        }
        if (chat.getContent().length() > 512) {
            chat.setContent(chat.getContent().substring(0, 512));
        }
        if (sign == null || !TokenUtil.isRightSign(sign)) {
            return result.error(-1, "sign错误");
        }
        chatDao.updateChat(chat);
        return result.ok(null, "修改成功");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Chat insertRobotMessage(String content) {
        String text = RobotUtil.getRobotMessage(content);
        if (text == null) {
            text = RobotUtil.getLiliRobotMessage(content);
        }

        if (text == null) {
            logger.error("从api接口获取机器人回复失败。");
            return null;
        }
        if (text.length() > 512) {
            text = text.substring(0, 512);
        }

        Chat robotMessage = new Chat();
        robotMessage.setContent(text);
        robotMessage.setMale((byte) 3);
        robotMessage.setPubtime(new Timestamp(System.currentTimeMillis()));

        chatDao.insertChat(robotMessage);
        return robotMessage;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
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

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Chat> list(Integer cid, Integer number) {
        if (number == null || number > 32 || number < 1) {
            number = 32;
        }

        if (cid == null || cid <= 0) {
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

    public boolean isSafeSession(Session session) {
        if (session == null) {
            return false;
        }

        String key = "chat_id:" + session.getId();

        // 如果该 ip 规定时间内访问过，不记录
        String s1 = Long.toString(System.currentTimeMillis());
        Boolean a = stringRedisTemplate.boundValueOps(key).setIfAbsent(s1, 3, TimeUnit.SECONDS);
        if (a == null) {
            return false;
        }
        return a;
    }

}
