package cn.fengyunxiao.nest.task;

import cn.fengyunxiao.nest.service.IpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class TimeTask {
    private static final Logger logger = LoggerFactory.getLogger(TimeTask.class);
    //private ServletContext servletContext;
    @Autowired private IpService ipService;

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

}
