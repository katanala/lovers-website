package cn.fengyunxiao.nest.controller;

import cn.fengyunxiao.nest.config.JsonResult;
import cn.fengyunxiao.nest.dao.BlogDao;
import cn.fengyunxiao.nest.entity.Blog;
import cn.fengyunxiao.nest.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/blog")
public class BlogController {
    private BlogService blogService;
    private HttpSession session;

    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }
    @Autowired
    public void setSession(HttpSession session) { this.session = session; }

    @RequestMapping("/get")
    @ResponseBody
    public JsonResult<Blog> get(Integer bid) {
        return blogService.get(bid);
    }

    @RequestMapping("/update")
    @ResponseBody
    public JsonResult<String> update(Blog blog) {
        return blogService.update(blog, session.getAttribute("admin"));
    }
}
