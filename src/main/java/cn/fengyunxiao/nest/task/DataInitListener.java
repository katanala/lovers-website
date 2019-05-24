package cn.fengyunxiao.nest.task;

import cn.fengyunxiao.nest.config.*;
import cn.fengyunxiao.nest.dao.BlogDao;
import cn.fengyunxiao.nest.service.IpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import javax.servlet.ServletContext;
import java.time.LocalDateTime;

@Component
public class DataInitListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(DataInitListener.class);
    private IpService ipService;
    private BlogDao blogDao;

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

        WebApplicationContext webApplicationContext = (WebApplicationContext)contextRefreshedEvent.getApplicationContext();
        ServletContext servletContext = webApplicationContext.getServletContext();
        if (servletContext == null) {
            logger.error("未获取到： servletContext");
            return;
        }

        logger.info("请自行验证时间！currentTimeMillis：" + System.currentTimeMillis());
        LocalDateTime localDateTime = LocalDateTime.now();
        logger.info("请自行验证时间！LocalDateTime：" + localDateTime.getYear() + "-" + localDateTime.getMonthValue()
                + "-" + localDateTime.getDayOfMonth() + " " + localDateTime.getHour() + ":" + localDateTime.getMinute()
                + ":" + localDateTime.getSecond());

        servletContext.setAttribute(Config.SERVLET_FIRST_PAGE, new FirstPage());
        logger.info("初始化完毕：servletContext");
    }
}
