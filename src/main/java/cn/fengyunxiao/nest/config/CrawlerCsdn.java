package cn.fengyunxiao.nest.config;

import cn.fengyunxiao.nest.dao.BlogDao;
import cn.fengyunxiao.nest.entity.Blog;
import com.overzealous.remark.Options;
import com.overzealous.remark.Remark;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class CrawlerCsdn extends WebCrawler {
    public static BlogDao blogDao;
    private static final Logger logger = LoggerFactory.getLogger(CrawlerCsdn.class);

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp4|zip|gz))$");

    private Remark remark = null;

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches()
                && href.startsWith("https://blog.csdn.net/");
    }

    @Override
    public void visit(Page page) {
        if (remark == null) {
            Options options = new Options();
            options.tables = Options.Tables.MULTI_MARKDOWN;
            options.fencedCodeBlocks = Options.FencedCodeBlocks.ENABLED_BACKTICK;
            // 代码块的 ``` 用 3个表示
            options.fencedCodeBlocksWidth = 3;
            // url 的引用和连接一起，不分开
            options.inlineLinks = true;
            remark = new Remark(options);
        }

        String url = page.getWebURL().getURL();
        if (!url.contains("/article/details/")) {
            logger.info("非博客内容页面");
            return;
        }

        if (blogDao == null) {
            logger.info("blogdao = null");
            return;
        }

        if (page.getParseData() instanceof HtmlParseData) {
            logger.info("\n");

            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();
            // Set<WebURL> links = htmlParseData.getOutgoingUrls();

            Document document = Jsoup.parse(html);
            Elements titleDom = document.select("h1[class=title-article]");
            Element contentDom = document.getElementById("article_content");
            Elements readCountDom = document.select("span[class=read-count]");
            Elements timeDom = document.select("div[class=article-bar-top] span[class=time]");

            if (titleDom == null || !titleDom.hasText()) {
                logger.info("titleDom 没有："+titleDom);
                logger.info(url);
                return;
            }
            if (contentDom == null || !contentDom.hasText()) {
                logger.info("contentDom 没有："+contentDom);
                return;
            }
            if (readCountDom == null || !readCountDom.hasText()) {
                logger.info("readCountDom 没有："+readCountDom);
                return;
            }
            if (timeDom == null || !timeDom.hasText()) {
                logger.info("timeDom 没有："+timeDom);
                return;
            }

            String title = titleDom.text();
            String html_content = contentDom.html();
            int readCount;
            String keys = "";
            String kind = "";
            String time = timeDom.text();
            String text = readCountDom.text();
            Timestamp pubtime;

            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date date = simpleDateFormat.parse(time);
                pubtime = new Timestamp(date.getTime());

                if (System.currentTimeMillis() - date.getTime() > 252288000000L) {
                    logger.info("忽略：8年前博客："+pubtime.toString());
                    return;
                }
            } catch (Exception e) {
                logger.info("时间转换失败:"+time);
                return;
            }

            try {
                String[] split = text.split("：");
                if (split.length != 2) {
                    logger.info("阅读量转换失败:"+text);
                    return;
                }
                readCount = Integer.parseInt(split[1]);
            } catch (Exception e) {
                logger.info("阅读量转换失败:"+text);
                return;
            }
            if (readCount < 3000) {
                logger.info("忽略：阅读量低于 3000");
                return;
            }

            if (html_content.contains("未经博主允许不得转载")) {
                logger.info("忽略：包含版权");
                return;
            }

            if (html_content.contains("https://img-blog.csdnimg.cn/")
                    || html_content.contains("https://img-blog.csdn.net/")
                    || html_content.contains("https://images2018.cnblogs.com/")
                    || html_content.contains("https://img-my.csdn.net/")) {
                logger.info("忽略：包含csdn图片");
                return;
            }

            if (html_content.contains("<table") && !html_content.contains("<thead")) {
                logger.info("忽略：包含 table 不包含 thead");
                return;
            }

            if (html_content.contains("<div class=\"dp-highlighter") && html_content.contains("<div class=\"bar\">")) {
                logger.info("忽略：包含不兼容代码");
                return;
            }

            Elements tags = document.select("a[class=tag-link]");
            Elements kinds = document.select("div[class=article-bar-top] div.tags-box.space a.tag-link");
            if (kinds != null && kinds.hasText()) {
                kind = kinds.text();
            }

            if (tags != null && tags.hasText()) {
                keys = tags.eachText().toString()
                        .replace("[","")
                        .replace("]", "")
                        .replace(" ", "");
            }
            if (keys.equals("")) {
                if (kind != null && !kind.equals("")) {
                    keys = kind;
                }
            }

            String content = remark.convert(html_content);

            if (keys.length() > 200) {
                keys = keys.substring(0, 200);
            }
            if (title.length() > 200) {
                title = title.substring(0, 200);
            }
            if (content.length() > 9000) {
                logger.info("长度超过9000，不记录");
                return;
            }

            logger.info("url:" + url);
            logger.info("原时间："+time+"，转换后："+pubtime.toString());
            logger.info("title:"+title);
            logger.info("kind:"+kind);
            logger.info("length："+content.length());
            logger.info("keys:"+keys);
//            logger.info("content:"+content);

            Integer bid = blogDao.getBidByUrl(url);
            if (bid == null) {
                Blog blog = new Blog();
                blog.setKeyword(keys);
                blog.setTitle(title);
                blog.setModtime(pubtime);
                blog.setRank((byte)1);
                blog.setContent(content);
                blog.setUrl(url);
                blogDao.insert(blog);
            } else {
                logger.info("该 url 已记录");
            }
        }
    }
}
