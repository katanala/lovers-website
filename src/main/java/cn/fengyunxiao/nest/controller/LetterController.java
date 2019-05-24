package cn.fengyunxiao.nest.controller;

import cn.fengyunxiao.nest.config.JsonResult;
import cn.fengyunxiao.nest.entity.Letter;
import cn.fengyunxiao.nest.service.LetterService;
import cn.fengyunxiao.nest.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/letter")
public class LetterController {
    private LetterService letterService;

    @Autowired
    public void setLetterService(LetterService letterService) {
        this.letterService = letterService;
    }

    /**
     *  倒叙 或 随机 列出 letter
     */
    @PostMapping("/listLetter")
    @ResponseBody
    public List<Letter> listLetter(Integer lid, Integer number, Boolean isRand) {
        List<Letter> letters = letterService.listLetter(lid, number, isRand);
        return letters;
    }

    /**
     *  插入一个新的留言
     */
    @PostMapping("/insertLetter")
    @ResponseBody
    public JsonResult<String> insertLetter(HttpServletRequest request, String nickname, String content) {
        String ip = IpUtil.getIp(request);
        return letterService.insertLetter(ip, nickname, content);
    }

    /**
     *  赞留言
     */
    @PostMapping("/zanLetter")
    @ResponseBody
    public String zanLetter(Integer lid) {
        letterService.zanLetter(lid);
        return "ok";
    }
}
