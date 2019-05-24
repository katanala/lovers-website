package cn.fengyunxiao.nest.controller;

import cn.fengyunxiao.nest.config.Config;
import cn.fengyunxiao.nest.entity.Blog;
import cn.fengyunxiao.nest.service.BlogService;
import cn.fengyunxiao.nest.service.IpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.sql.Timestamp;
import java.util.List;

@Controller
public class ViewController {
    private IpService ipService;
    private BlogService blogService;

    private static final Logger logger = LoggerFactory.getLogger(ViewController.class);

    @Autowired
    public void setIpService(IpService ipService) {
        this.ipService = ipService;
    }
    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    @RequestMapping({"/index.html","/", ""})
    public String index() { return "index"; }

    @RequestMapping("/chat")
    public String chat() { return "pages/chat"; }

    @RequestMapping("/falls")
    public String falls(ModelMap map) {
        map.put("prefix", Config.OSS_URL_PREFIX);
        return "pages/falls";
    }

    @RequestMapping("/timeline")
    public String timeline() { return "pages/timeline"; }

    @GetMapping("/ip")
    public String ip(ModelMap map) {
        map.put("ipCount", Config.ipCount);
        map.put("allpage", ipService.getAllPage());
        return "pages/ip";
    }

    @RequestMapping("/chart")
    public String chart() { return "pages/chart"; }

    @RequestMapping("/blogs")
    public String blogs(ModelMap map, String key, Integer page) {
        if (page == null || page < 1) {
            page = 1;
        }
        if (key!=null && key.trim().isEmpty()) {
            key = null;
        }

        List<Blog> blogs = blogService.search(key, page);
        List<Blog> rands = blogService.rand(5);
        int total = blogService.count(key, blogs.size());

        int per = Config.PAGE_NUMBER;
        int allpage =  total % per == 0 ? total / per : total / per + 1;
        if (allpage < 1) { allpage = 1; }

        map.put("key", key);
        map.put("page", page);
        map.put("allpage", allpage);
        map.put("blogs", blogs);
        map.put("rands", rands);
        return "pages/blogs";
    }

    @RequestMapping("/blog/{bid}")
    public String blog(ModelMap map,@PathVariable("bid") Integer bid) {
        Blog blog = blogService.getBlogEsc(bid);
        if (blog == null) {
            blog = new Blog();
            blog.setBid(0);
            blog.setTitle("没有该标题");
            blog.setContent("没有该内容");
            blog.setKeyword("没有关键字");
            blog.setUrl("");
            blog.setRank((byte)(0));
            blog.setModtime(new Timestamp(System.currentTimeMillis()));
        }

        List<Blog> rands = blogService.rand(5);
        map.put("bid", bid);
        map.put("blog", blog);
        map.put("rands", rands);
        return "pages/blog";
    }

}
