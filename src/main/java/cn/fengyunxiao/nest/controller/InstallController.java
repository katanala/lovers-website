package cn.fengyunxiao.nest.controller;

import cn.fengyunxiao.nest.config.Config;
import cn.fengyunxiao.nest.config.FirstPage;
import cn.fengyunxiao.nest.config.JsonResult;
import cn.fengyunxiao.nest.entity.Link;
import cn.fengyunxiao.nest.service.InstallService;
import cn.fengyunxiao.nest.service.LinkService;
import cn.fengyunxiao.nest.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping({ "/install" })
public class InstallController {
    @Autowired
    private InstallService installService;
    @Autowired
    private LinkService linkService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping({ "/reset_Config_Servlet_Redis" })
    @ResponseBody
    public JsonResult<String> resetConfigAndServlet(HttpServletRequest request, String sign) {
        final JsonResult<String> result = new JsonResult<String>();
        if (sign == null || !TokenUtil.isRightSign(sign)) {
            return result.error(-1, "sign error");
        }
        this.installService.initConfig();
        List<Link> links = this.linkService.getLink();
        request.getServletContext().setAttribute(Config.servletFirstpage, new FirstPage(links));
        this.stringRedisTemplate.delete("*");
        return result.ok("", "ok");
    }

}
