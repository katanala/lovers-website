package cn.fengyunxiao.nest.controller;

import cn.fengyunxiao.nest.config.Config;
import cn.fengyunxiao.nest.config.CrawlerCsdn;
import cn.fengyunxiao.nest.config.CrawlerDytt;
import cn.fengyunxiao.nest.dao.BlogDao;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CrawlerController {
    private BlogDao blogDao;

    @Autowired
    public void setBlogDao(BlogDao blogDao) {
        this.blogDao = blogDao;
    }

    @RequestMapping("/csdn")
    @ResponseBody
    public void csdn() {
        crawler("https://blog.csdn.net", CrawlerCsdn.class);
    }

    @RequestMapping("/dytt")
    @ResponseBody
    public void dytt() {
        crawler("https://www.dytt8.net", CrawlerDytt.class);
    }

    public void crawler(String startUrl, Class t) {
        // 线程数，多线程更快，但容易被限制 Ip
        int numberOfCrawlers = 1;

        CrawlConfig config = new CrawlConfig();
        // 保存位置
        config.setCrawlStorageFolder(Config.CRAWLER_LOCAL_FOLER);
        // 开启 ssl
        config.setIncludeHttpsPages(true);
        // 最大抓几个
        config.setMaxPagesToFetch(Config.CRAWLER_MaxPagesToFetch);
        // 递归深度
        config.setMaxDepthOfCrawling(Config.CRAWLER_MaxDepthOfCrawling);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = null;
        try {
            controller = new CrawlController(config, pageFetcher, robotstxtServer);
        } catch (Exception e) {
            return;
        }

        controller.addSeed(startUrl);
        controller.start(t, numberOfCrawlers);
    }
}
