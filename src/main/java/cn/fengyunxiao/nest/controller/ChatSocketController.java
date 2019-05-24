package cn.fengyunxiao.nest.controller;

import cn.fengyunxiao.nest.entity.Chat;
import cn.fengyunxiao.nest.service.ChatService;
import cn.fengyunxiao.nest.util.Jackson;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint(value="/chatSocket")
public class ChatSocketController {
    // webSocket 会生成多个对象，而psring的单例模式只会注入一次，所以用 static
    private static ChatService chatService;
    private static final Logger logger = LoggerFactory.getLogger(ChatSocketController.class);
    private static final CopyOnWriteArraySet<Session> sessionSet = new CopyOnWriteArraySet<>();

    private Session session;

    @Autowired
    public void setChatService(ChatService chatService) { ChatSocketController.chatService = chatService; }

    public ChatSocketController() {
        logger.info("创建聊天用户");
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        sessionSet.add(session);
        sendToAll(sessionSet.size() + 1 + "" );
    }

    @OnClose
    public void onClose() {
        sessionSet.remove(session);
        sendToAll(sessionSet.size() + 1 + "" );
    }

    @OnMessage
    public void onMessage(String content, Session session) {
        if (content==null || content.trim().equals("") || content.length() < 1) {
            logger.info("终止聊天：聊天内容为空。");
            return;
        }

        // 获取历史纪录，如果是数字，否则是json串
        if (chatService == null) {
            logger.info("终止聊天：chatService 为空。");
            return;
        }
        if (chatService.isNumber(content)) {
            String maxTemp = chatService.getRecentMessage(content);
            session.getAsyncRemote().sendText(maxTemp);
            return;
        }

        // 防刷消息
        if (!chatService.isSafe(session)) {
            logger.error("终止聊天：防刷拦截。");
            return;
        }

        // 插入用户聊天数据，并返回json结果
        Chat message = chatService.insertMessage(content);
        if (message == null) {
            return;
        }
        String result = null;
        try {
            result = Jackson.toJson(message);
        } catch (JsonProcessingException e) {
            return;
        }
        sendToAll(result);

        // 如果是机器发的信息，不再进行机器人聊天了
        if (message.getMale()==null || message.getMale()==3) {
            return;
        }
        // 插入机器人聊天记录
        Chat robotMessage = chatService.insertRobotMessage(message.getContent());
        if (robotMessage == null) {
            return;
        }

        try {
            result = Jackson.toJson(robotMessage);
        } catch (JsonProcessingException e) {
            return;
        }
        sendToAll(result);
    }

    private void sendToAll(String content) {
        for (Session session : sessionSet) {
            if (session == null || !session.isOpen()) {
                logger.info("聊天用户 session 已失效，进行删除");
                sessionSet.remove(session);
                continue;
            }

            session.getAsyncRemote().sendText(content);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        // 发送异常，说明该用户中断了。
        if (session==null || !session.isOpen()) {
            sessionSet.remove(session);
        }
        logger.error("聊天出错："+error.getMessage());
    }

}
