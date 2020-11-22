package cn.fengyunxiao.nest.controller;

import cn.fengyunxiao.nest.config.JsonResult;
import cn.fengyunxiao.nest.entity.Chat;
import cn.fengyunxiao.nest.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {
    @Autowired private ChatService chatService;

    @PostMapping("/listChat")
    @ResponseBody
    public List<Chat> listChat(Integer cid, Integer number) {
        return chatService.list(cid, number);
    }

    @PostMapping("/updateChat")
    @ResponseBody
    public JsonResult<String> updateChat(Chat chat, String sign) {
        return chatService.updateChat(chat, sign);
    }

}
