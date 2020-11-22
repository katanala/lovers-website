package cn.fengyunxiao.nest.controller;

import cn.fengyunxiao.nest.config.JsonResult;
import cn.fengyunxiao.nest.entity.Letter;
import cn.fengyunxiao.nest.entity.LetterAndReply;
import cn.fengyunxiao.nest.entity.Reply;
import cn.fengyunxiao.nest.service.LetterService;
import cn.fengyunxiao.nest.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/letter")
public class LetterController {
    @Autowired
    private LetterService letterService;

    @RequestMapping({ "/listLetter" })
    @ResponseBody
    public List<Letter> listLetter(final Integer lid, final Integer number, final Boolean isRand) {
        return this.letterService.listLetter(lid, number, isRand);
    }

    @RequestMapping({ "/listLetterAndReply" })
    @ResponseBody
    public List<LetterAndReply> listLetterAndReply(final Integer lid, final Integer number, final Boolean isRand) {
        return this.letterService.listLetterAndReply(lid, number, isRand);
    }

    @RequestMapping({ "/listReply" })
    @ResponseBody
    public List<Reply> listReply(final Integer replyid, final Integer number) {
        return this.letterService.listReply(replyid, number);
    }

    @RequestMapping({ "/insertLetter" })
    @ResponseBody
    public JsonResult<String> insertLetter(final HttpServletRequest request, final String nickname, final String content, final String email) {
        final String ip = IpUtil.getIp(request);
        return this.letterService.insertLetter(ip, nickname, content, email);
    }

    @RequestMapping({ "/insertReply" })
    @ResponseBody
    public JsonResult<String> insertReply(final HttpServletRequest request, final String nickname, final String content, final Integer lid) {
        final String ip = IpUtil.getIp(request);
        return this.letterService.insertReply(ip, nickname, content, lid);
    }

    @RequestMapping({ "/updateLetter" })
    @ResponseBody
    public JsonResult<String> updateLetter(final Letter letter, final String sign) {
        return this.letterService.updateLetter(letter, sign);
    }

    @RequestMapping({ "/updateReply" })
    @ResponseBody
    public JsonResult<String> updateReply(final Reply reply, final String sign) {
        return this.letterService.updateReply(reply, sign);
    }

    @RequestMapping({ "/zanLetter" })
    @ResponseBody
    public String zanLetter(final Integer lid) {
        this.letterService.zanLetter(lid);
        return "ok";
    }
}
