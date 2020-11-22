package cn.fengyunxiao.nest.controller;

import cn.fengyunxiao.nest.config.JsonResult;
import cn.fengyunxiao.nest.entity.Blog;
import cn.fengyunxiao.nest.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired private BlogService blogService;
    @Autowired private HttpSession session;

    @RequestMapping("/get")
    @ResponseBody
    public JsonResult<Blog> get(Integer bid, HttpServletRequest request) {
        return blogService.get(bid, request);
    }

    @RequestMapping("/update")
    @ResponseBody
    public JsonResult<String> update(Blog blog) {
        return blogService.update(blog, (String)session.getAttribute("admin"));
    }
}
