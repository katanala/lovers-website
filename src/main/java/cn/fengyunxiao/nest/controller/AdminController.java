package cn.fengyunxiao.nest.controller;

import cn.fengyunxiao.nest.config.Config;
import cn.fengyunxiao.nest.config.JsonResult;
import cn.fengyunxiao.nest.entity.Blog;
import cn.fengyunxiao.nest.entity.Image;
import cn.fengyunxiao.nest.entity.Install;
import cn.fengyunxiao.nest.entity.Link;
import cn.fengyunxiao.nest.service.AdminService;
import cn.fengyunxiao.nest.service.BlogService;
import cn.fengyunxiao.nest.service.InstallService;
import cn.fengyunxiao.nest.util.GoogleAuthenticator;
import cn.fengyunxiao.nest.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/adminLove")
public class AdminController {
    @Autowired private AdminService adminService;
    @Autowired private InstallService installService;
    @Autowired private HttpSession session;
    @Autowired private BlogService blogService;
    private final static Logger log = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping({"/index.html", "/", ""})
    public String index(HttpServletRequest request) {
        String servletName = request.getServerName();
        // 本地测试，不需要登陆
        if (servletName.equals("127.0.0.1") || servletName.equals("localhost")) {
            log.info("不需要登录，servletName：{}", servletName);
            session.setAttribute("admin", Config.adminLoginToken);
            return "admin/index";
        }

        if (!Config.adminLoginToken.equals(session.getAttribute("admin"))) {
            return "redirect:/";
        }
        return "admin/index";
    }

    @RequestMapping("/login")
    public String login(ModelMap map, String token, HttpServletRequest request) {
        // 本地测试，不需要登陆
        String servletName = request.getServerName();
        if (servletName.equals("127.0.0.1") || servletName.equals("localhost")) {
            log.info("不需要登录，servletName：{}", servletName);
            session.setAttribute("admin", Config.adminLoginToken);
            return "admin/index";
        }
        if (token == null || !token.equals(Config.adminToken)) {
            return "redirect:/";
        }
        map.put("tokenLogin", Config.adminLoginToken);
        return "admin/login";
    }

    @RequestMapping("/logout")
    public String logout() {
        session.removeAttribute("admin");
        return "redirect:/";
    }

    @RequestMapping("/image")
    public String image(ModelMap map, Integer page) {
        if (!Config.adminLoginToken.equals(session.getAttribute("admin"))) {
            return "redirect:/";
        }

        if (page == null || page < 1) {
            page = 1;
        }

        int total = adminService.countImage();
        int number = 12;

        int allpage = total % number == 0 ? total / number : total / number + 1;
        if (allpage < 1) {
            allpage = 1;
        }
        List<Image> images = adminService.listImage(page, number);

        map.put("total", total);
        map.put("allpage", allpage);
        map.put("curpage", page);
        map.put("images", images);
        map.put("ossUrl", Config.ossUrlPrefix);
        map.put("pageKind", "image");
        return "admin/image";
    }

    @RequestMapping("/blogs")
    public String blogs(String key, Integer page, ModelMap map) {
        if (!Config.adminLoginToken.equals(session.getAttribute("admin"))) {
            return "redirect:/";
        }
        if (page == null || page < 1) {
            page = 1;
        }
        List<Blog> blogs = blogService.search(key, page);
        int total = blogService.count(key, blogs.size());
        int number = 12;
        int allpage = total % number == 0 ? total / number : total / number + 1;
        if (allpage < 1) {
            allpage = 1;
        }

        map.put("key", key);
        map.put("page", page);
        map.put("allpage", allpage);
        map.put("blogs", blogs);
        map.put("pageKind", "blogs");
        return "admin/blogs";
    }

    @RequestMapping("/image2")
    public String image2(ModelMap map) {
        if (!Config.adminLoginToken.equals(session.getAttribute("admin"))) {
            return "redirect:/";
        }
        String[] _threes = {"nest/photos/3d/images/p1.jpg",
                "nest/photos/3d/images/p2.jpg",
                "nest/photos/3d/images/p3.jpg",
                "nest/photos/3d/images/p4.jpg",
                "nest/photos/3d/images/p5.jpg",
                "nest/photos/3d/images/p6.jpg",
                "nest/photos/3d/images/p7.jpg",
                "nest/photos/3d/images/p8.jpg",};

        String[] _ipresenters = {
                "nest/photos/ipresenter/img/photos/p1.jpg",
                "nest/photos/ipresenter/img/photos/p2.jpg",
                "nest/photos/ipresenter/img/photos/p3.jpg",
                "nest/photos/ipresenter/img/photos/p4.jpg"};

        String[] _ipresenterThumbs = {
                "nest/photos/ipresenter/img/thumbs/1.jpg",
                "nest/photos/ipresenter/img/thumbs/2.jpg",
                "nest/photos/ipresenter/img/thumbs/3.jpg",
                "nest/photos/ipresenter/img/thumbs/4.jpg",
        };

        String[] _rotates = {"nest/photos/rotate/img/p1.jpg",
                "nest/photos/rotate/img/p2.jpg",
                "nest/photos/rotate/img/p3.jpg",
                "nest/photos/rotate/img/p4.jpg",
                "nest/photos/rotate/img/p5.jpg",
                "nest/photos/rotate/img/p6.jpg"};

        String[] _walls = {"nest/photos/wall/photos/p1.jpg",
                "nest/photos/wall/photos/p2.jpg",
                "nest/photos/wall/photos/p3.jpg",
                "nest/photos/wall/photos/p4.jpg",
                "nest/photos/wall/photos/p5.jpg",
                "nest/photos/wall/photos/p6.jpg",
                "nest/photos/wall/photos/p7.jpg",
                "nest/photos/wall/photos/p8.jpg",
                "nest/photos/wall/photos/p9.jpg"};

        map.put("ossUrl", Config.ossUrlPrefix);
        map.put("threes", Arrays.asList(_threes));
        map.put("ipresenters", Arrays.asList(_ipresenters));
        map.put("ipresenterThumbs", Arrays.asList(_ipresenterThumbs));
        map.put("rotates", Arrays.asList(_rotates));
        map.put("walls", Arrays.asList(_walls));
        map.put("pageKind", "image2");
        return "admin/image2";
    }

    @RequestMapping("/install")
    public String install(ModelMap map) {
        if (!Config.adminLoginToken.equals(session.getAttribute("admin"))) {
            return "redirect:/";
        }
        List<Install> installs = installService.listInstall();
        map.put("installs", installs);
        map.put("pageKind", "install");
        return "admin/install";
    }

    @RequestMapping("/link")
    public String link(ModelMap map) {
        if (!Config.adminLoginToken.equals(session.getAttribute("admin"))) {
            return "redirect:/";
        }
        List<Link> links = installService.listLink();
        map.put("links", links);
        map.put("pageKind", "link");
        return "admin/link";
    }

    @RequestMapping("/doLogin")
    public String doLogin(String tokenLogin, String veri, String phone, Long google) {
        String veri1 = (String) session.getAttribute("veri");
        if (!Config.adminLoginToken.equals(tokenLogin) || !Config.adminPhone.equals(phone)
                || veri == null || !veri.equalsIgnoreCase(veri1) || google == null) {
            return "redirect:/";
        }

        GoogleAuthenticator ga = new GoogleAuthenticator();
        boolean r = ga.check_code(Config.adminGoogle, google, System.currentTimeMillis());
        if (!r) {
            return "redirect:/";
        }

        session.setAttribute("admin", Config.adminLoginToken);
        return "redirect:/adminLove";
    }

    @RequestMapping("/veri")
    public void veri(HttpServletRequest request, HttpServletResponse response) {
        try {
            ImageUtil.getVeri(request, response, "veri");
        } catch (IOException e) {
            log.info("生成验证码错误");
        }
    }

    // 根据 bid 编辑博客，bid 没有，创建新的博客
    @RequestMapping("/blog")
    public String blog(ModelMap map, Integer bid) {
        if (!Config.adminLoginToken.equals(session.getAttribute("admin"))) {
            return "redirect:/";
        }
        int bid2 = adminService.gainBlogId(bid);
        if (bid == null || bid < 0) {
            return "redirect:blog?bid=" + bid2;
        }
        map.put("bid", bid2);
        map.put("pageKind", "blog");
        return "admin/blog";
    }

    @RequestMapping("/uploadImage")
    @ResponseBody
    public Map<String, Object> uploadImage(@RequestParam("editormd-image-file") MultipartFile file
            , Integer bid) {
        return adminService.uploadImage(file, bid, session.getAttribute("admin"));
    }

    @RequestMapping({ "/getOssPolicy" })
    @ResponseBody
    public Map<String, String> getOssPolicy() {
        return this.adminService.getOssPolicy(this.session.getAttribute("admin"));
    }

    @RequestMapping({ "/updateInstall" })
    @ResponseBody
    public JsonResult<String> updateInstall(Install install, HttpServletRequest request) {
        String tokens = (String)request.getSession().getAttribute("admin");
        return installService.insertOrUpdate(install, tokens, request);
    }

    @RequestMapping({ "/updateLink" })
    @ResponseBody
    public JsonResult<String> updateLink(String href, String title,
                             Integer rank, HttpServletRequest request) {
        String tokens = (String)request.getSession().getAttribute("admin");
        return installService.updateLink(href, title, rank, tokens, request);
    }

    @RequestMapping({ "/deleteLink" })
    @ResponseBody
    public JsonResult<String> deleteLink(String href,
                             HttpServletRequest request) {
        String tokens = (String)request.getSession().getAttribute("admin");
        return installService.deleteLink(href, tokens, request);
    }

    @RequestMapping({ "/deleteBlog" })
    @ResponseBody
    public JsonResult<String> deleteBlog(Integer bid) {
        String tokens = (String)session.getAttribute("admin");
        return blogService.delete(bid, tokens);
    }

    @RequestMapping({ "/insertImage" })
    @ResponseBody
    public JsonResult<String> insertImage(Integer bid, String fileName) {
        return adminService.insertImage(bid, fileName, session.getAttribute("admin"));
    }

    @RequestMapping("/deleteImage")
    @ResponseBody
    public JsonResult<String> deleteImage(Integer iid) {
        return adminService.deleteImage(iid, session.getAttribute("admin"));
    }

}
