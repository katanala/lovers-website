package cn.fengyunxiao.nest.task;

import cn.fengyunxiao.nest.config.*;
import cn.fengyunxiao.nest.dao.BlogDao;
import cn.fengyunxiao.nest.service.IpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import javax.servlet.ServletContext;

@Component
public class DataInitListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(DataInitListener.class);
    private IpService ipService;
    private BlogDao blogDao;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Autowired
    public void setIpService(IpService ipService) {
        this.ipService = ipService;
    }
    @Autowired
    public void setBlogDao(BlogDao blogDao) { this.blogDao = blogDao; }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // 给爬虫的 blogdao 赋值
        CrawlerCsdn.blogDao = blogDao;
        CrawlerDytt.blogDao = blogDao;

        Config.ipCount = ipService.total();
        logger.info("初始化完毕：ipCount");

        WebApplicationContext webApplicationContext = (WebApplicationContext)contextRefreshedEvent.getApplicationContext();
        ServletContext servletContext = webApplicationContext.getServletContext();
        if (servletContext == null) {
            logger.error("未获取到： servletContext");
            return;
        }

        servletContext.setAttribute(Config.SERVLET_TIME_STAMP_HTML, "1");
        servletContext.setAttribute(Config.SERVLET_TIME_STAMP_IMAG, "1");
        servletContext.setAttribute(Config.SERVLET_FIRST_PAGE, new FirstPage());
        servletContext.setAttribute(Config.SERVLET_TIME_DATA, new TimeData());
        logger.info("初始化完毕：servletContext");


        if (mailFrom != null) {
            Config.MAIL_FROM = mailFrom;
            logger.info("初始化完毕：mailFrom："+mailFrom);
        } else {
            logger.info("初始化失败：mailFrom");
        }

    }
}
