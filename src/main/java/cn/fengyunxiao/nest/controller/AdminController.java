package cn.fengyunxiao.nest.controller;

import cn.fengyunxiao.nest.config.Config;
import cn.fengyunxiao.nest.service.AdminService;
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
    public String index() {
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
    public String image() {
        if (!Config.TOKEN_DO_LOGIN.equals(session.getAttribute("admin"))) {
            return "redirect:/";
        }
        return "admin/image";
    }

    @RequestMapping("/doLogin")
    public String doLogin(String tokenLogin, String veri, String phone) {
        String veri1 = (String)session.getAttribute("veri");
        if (!Config.TOKEN_DO_LOGIN.equals(tokenLogin) || !Config.TOKEN_PHONE.equals(phone)
            || veri == null || !veri.equalsIgnoreCase(veri1)) {
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
}
