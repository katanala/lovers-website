package cn.fengyunxiao.nest.task;

import cn.fengyunxiao.nest.config.Config;
import cn.fengyunxiao.nest.config.FirstPage;
import cn.fengyunxiao.nest.entity.Link;
import cn.fengyunxiao.nest.service.IpService;
import cn.fengyunxiao.nest.service.LinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.servlet.ServletContext;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class TimeTask {
    private static final Logger logger = LoggerFactory.getLogger(TimeTask.class);
    @Autowired private IpService ipService;
    @Autowired private LinkService linkService;
    @Autowired private ServletContext servletContext;

    @Async
    @Scheduled(fixedDelay = 3600000L)
    public void do1hour() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ipService.resetChart();
            }
        }, 3000L);
    }

    @Async
    @Scheduled(fixedDelay = 86400000L)
    public void do1Day() {
        if (servletContext != null) {
            List<Link> links = this.linkService.getLink();
            servletContext.setAttribute(Config.servletFirstpage, new FirstPage(links));
        }
    }
}
