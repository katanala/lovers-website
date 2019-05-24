package cn.fengyunxiao.nest.controller;

import cn.fengyunxiao.nest.config.Config;
import cn.fengyunxiao.nest.config.CrawlerCsdn;
import cn.fengyunxiao.nest.config.CrawlerDytt;
import cn.fengyunxiao.nest.entity.Image;
import cn.fengyunxiao.nest.service.AdminService;
import cn.fengyunxiao.nest.util.GoogleAuthenticator;
import cn.fengyunxiao.nest.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/adminLove")
public class AdminController {
    private AdminService adminService;
    private HttpSession session;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }
    @Autowired
    public void setSession(HttpSession session) { this.session = session; }

    @RequestMapping({"/index.html","/", ""})
    public String index(HttpServletRequest request) {
        String servletName = request.getServerName();
        logger.info("servletName："+servletName);
        // 本地测试，不需要登陆
        if (servletName.equals("127.0.0.1") || servletName.equals("localhost")) {
            session.setAttribute("admin", Config.TOKEN_DO_LOGIN);
            return "admin/index";
        }

        if (!Config.TOKEN_DO_LOGIN.equals(session.getAttribute("admin"))) {
            return "redirect:/";
        }
        return "admin/index";
    }

    @RequestMapping("/login")
    public String login(ModelMap map, String token) {
        if (token == null || !token.equals(Config.TOKEN_URL)) {
            return "redirect:/";
        }
        map.put("tokenLogin", Config.TOKEN_DO_LOGIN);
        return "admin/login";
    }

    @RequestMapping("/logout")
    public String logout() {
        session.removeAttribute("admin");
        return "redirect:/";
    }

    @RequestMapping("/image")
    public String image(ModelMap map, Integer page) {
        if (!Config.TOKEN_DO_LOGIN.equals(session.getAttribute("admin"))) {
            return "redirect:/";
        }

        if (page == null || page<1) {
            page = 1;
        }

        int total = adminService.countImage();
        int number = Config.PAGE_NUMBER;

        int allpage = total % number == 0 ? total/number : total/number+1;
        if (allpage < 1) { allpage = 1; }
        List<Image> images = adminService.listImage(page, number);

        map.put("total", total);
        map.put("allpage", allpage);
        map.put("curpage", page);
        map.put("images", images);
        map.put("ossUrl", Config.OSS_URL_PREFIX);
        return "admin/image";
    }

    @RequestMapping("/doLogin")
    public String doLogin(String tokenLogin, String veri, String phone, Long google) {
        String veri1 = (String)session.getAttribute("veri");
        if (!Config.TOKEN_DO_LOGIN.equals(tokenLogin) || !Config.TOKEN_PHONE.equals(phone)
            || veri == null || !veri.equalsIgnoreCase(veri1) || google == null) {
            return "redirect:/";
        }

        GoogleAuthenticator ga = new GoogleAuthenticator();
        boolean r = ga.check_code(Config.TOKEN_GOOGLE_KEY, google, System.currentTimeMillis());
        if (!r) {
            return "redirect:/";
        }

        session.setAttribute("admin", Config.TOKEN_DO_LOGIN);
        return "redirect:/adminLove";
    }

    @RequestMapping("/veri")
    public void veri(HttpServletRequest request, HttpServletResponse response) {
        try {
            ImageUtil.getVeri(request, response, "veri");
        } catch (IOException e) {
            logger.info("生成验证码错误");
        }
    }

    // 根据 bid 编辑博客，bid 没有，创建新的博客
    @RequestMapping("/blog")
    public String blog(ModelMap map, Integer bid) {
        if (!Config.TOKEN_DO_LOGIN.equals(session.getAttribute("admin"))) {
            return "redirect:/";
        }
        int bid2 = adminService.gainBlogId(bid);
        if (bid == null || bid<0) {
            return "redirect:blog?bid="+bid2;
        }
        map.put("bid", bid2);
        return "admin/blog";
    }

    @RequestMapping("/blogs")
    public String blogs() {
        return "admin/blogs";
    }

    @RequestMapping("/uploadImage")
    @ResponseBody
    public Map<String, Object> uploadImage(@RequestParam("editormd-image-file") MultipartFile file, Integer bid) {
        return adminService.uploadImage(file, bid, session.getAttribute("admin"));
    }

    @RequestMapping("/deleteImage")
    @ResponseBody
    public String deleteImage(Integer iid) {
        return adminService.deleteImage(iid, session.getAttribute("admin"));
    }

    @RequestMapping("/crawler")
    @ResponseBody
    public String crawler(String kind) {
        if (!Config.TOKEN_DO_LOGIN.equals(session.getAttribute("admin"))) {
            return "请先登录";
        }

        if (kind == null) {
            return "类型错误";
        } else if (kind.equals("csdn")) {
            adminService.crawler("https://blog.csdn.net/", CrawlerCsdn.class);
        } else if (kind.equals("dytt")) {
            adminService.crawler("http://www.ygdy8.net/", CrawlerDytt.class);
        } else {
            return "类型错误";
        }

        return "正在爬取中，请查看日志";
    }
}
