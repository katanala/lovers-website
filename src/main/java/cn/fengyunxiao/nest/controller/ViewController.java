package cn.fengyunxiao.nest.controller;

import cn.fengyunxiao.nest.config.Config;
import cn.fengyunxiao.nest.entity.Blog;
import cn.fengyunxiao.nest.service.BlogService;
import cn.fengyunxiao.nest.service.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ViewController {
    @Autowired
    private IpService ipService;
    @Autowired
    private BlogService blogService;

    @RequestMapping({ "/index.html", "/", "", "/index", "index.htm", "index.php" })
    public String index(final HttpServletRequest request, final ModelMap map) {
        this.ipService.addIp(request);
        map.put("ipCount", Config.ipCount);
        map.put("allpage", this.ipService.getAllPage());
        map.put("perpage", 5);
        return "index";
    }

    @RequestMapping({ "/chat" })
    public String chat() {
        return "pages/chat";
    }

    @RequestMapping({ "/falls" })
    public String falls(final ModelMap map) {
        map.put("prefix", Config.ossUrlPrefix);
        return "pages/falls";
    }

    @RequestMapping({ "/falls2" })
    public String falls2(final ModelMap map) {
        map.put("prefix", Config.ossUrlPrefix);
        return "pages/falls2";
    }

    @RequestMapping({ "/timeline" })
    public String timeline() {
        return "pages/timeline";
    }

    @RequestMapping({ "/chart" })
    public String chart() {
        return "pages/chart";
    }

    @RequestMapping({ "/blogs" })
    public String blogs(final ModelMap map, String key, Integer page) {
        if (page == null || page < 1) {
            page = 1;
        }
        if (key != null && key.trim().isEmpty()) {
            key = null;
        }
        final List<Blog> blogs = this.blogService.search(key, page);
        final List<Blog> rands = this.blogService.rand(5);
        final int total = this.blogService.count(key, blogs.size());
        final int per = Config.pageNumber;
        int allpage = (total % per == 0) ? (total / per) : (total / per + 1);
        if (allpage < 1) {
            allpage = 1;
        }
        map.put("key", key);
        map.put("page", page);
        map.put("allpage", allpage);
        map.put("blogs", blogs);
        map.put("rands", rands);
        return "pages/blogs";
    }

    @RequestMapping({ "/blog/{bid}" })
    public String blog(final ModelMap map, @PathVariable("bid") Integer bid) {
        if (bid == null || bid < 1) {
            bid = 1;
        }
        final Blog blog = this.blogService.getBlogEsc(bid);
        final List<Blog> rands = this.blogService.rand(5);
        map.put("bid", bid);
        map.put("blog", blog);
        map.put("rands", rands);
        return "pages/blog";
    }

}
