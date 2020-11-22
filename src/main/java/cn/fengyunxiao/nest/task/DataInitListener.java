package cn.fengyunxiao.nest.task;

import cn.fengyunxiao.nest.config.Config;
import cn.fengyunxiao.nest.config.FirstPage;
import cn.fengyunxiao.nest.entity.Link;
import cn.fengyunxiao.nest.service.InstallService;
import cn.fengyunxiao.nest.service.IpService;
import cn.fengyunxiao.nest.service.LinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import javax.servlet.ServletContext;
import java.util.List;

@Component
public class DataInitListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(DataInitListener.class);
    private IpService ipService;
    private InstallService installService;
    private LinkService linkService;

    @Autowired
    public void setIpService(IpService ipService) {
        this.ipService = ipService;
    }
    @Autowired
    public void setInstallService(InstallService installService) { this.installService = installService; }
    @Autowired
    public void setLinkService(LinkService linkService) { this.linkService = linkService; }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        Config.ipCount = ipService.total();

        WebApplicationContext webApplicationContext = (WebApplicationContext) contextRefreshedEvent.getApplicationContext();
        ServletContext servletContext = webApplicationContext.getServletContext();
        if (servletContext == null) {
            logger.error("error: servletContext is null");
            return;
        }

        installService.checkMysqlTime();

        installService.initConfig();
        logger.info("inited: config");

        List<Link> links = linkService.getLink();
        servletContext.setAttribute(Config.servletFirstpage, new FirstPage(links));
        logger.info("inited: servletContext");
    }
}
