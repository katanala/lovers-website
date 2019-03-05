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
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class CrawlerDytt extends WebCrawler {
    public static BlogDao blogDao;
    private static final Logger logger = LoggerFactory.getLogger(CrawlerDytt.class);

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp4|zip|gz))$");

    private Remark remark = null;

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches()
                && href.startsWith("https://www.dytt8.net");
    }

    @Override
    public void visit(Page page) {
        if (remark == null) {
            Options options = new Options();
            // 代码块的 ``` 用 3个表示
            options.fencedCodeBlocks = Options.FencedCodeBlocks.ENABLED_BACKTICK;
            options.fencedCodeBlocksWidth = 3;

            options.tables = Options.Tables.MULTI_MARKDOWN;
            // url 的引用和连接一起，不分开
            options.inlineLinks = true;
            remark = new Remark(options);
        }

        String url = page.getWebURL().getURL();
        if (url.contains("/html/tv/") || url.contains("index.html") || url.contains("/html/game/")) {
            logger.info("tv页面、游戏页面或目录页面");
            return;
        }

        if (blogDao == null) {
            logger.info("blogdao = null");
            return;
        }

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = "";
            try {
                html = new String(page.getContentData(), page.getContentCharset());
            } catch (UnsupportedEncodingException e) {
                logger.info("编码转换错误!");
                return;
            }

            Document document = Jsoup.parse(html);
            Elements titleDom = document.select("div[class=title_all] h1 font");
            Element zoom = document.getElementById("Zoom");

            if (titleDom==null || !titleDom.hasText() || zoom==null || !zoom.hasText()) {
                logger.info("无标题等内容");
                return;
            }
            Elements download_url = zoom.select("a");
            if (download_url==null || !download_url.hasText()) {
                logger.info("下载链接");
                return;
            }

            zoom.select("script").remove();
            zoom.select("center").remove();
            zoom.select("font[color=red]").remove();
            zoom.select("strong").remove();
            zoom.select("span p a").remove();
            zoom.select("span table").remove();

            String title = titleDom.text();
            String content = remark.convert(zoom.html());
            Timestamp pubtime = null;
            int i = content.indexOf("上映日期　");
            String time = "";
            if (i>0) {
                time = content.substring(i+5, i+15);
            }
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = simpleDateFormat.parse(time);
                pubtime = new Timestamp(date.getTime());
                if (System.currentTimeMillis() - date.getTime() > 94608000000L) {
                    logger.info("忽略：3年前电影："+pubtime.toString());
                    return;
                }
            } catch (Exception e) {
                logger.info("时间转换失败:"+time);
                return;
            }

            logger.info("=====================================");
            logger.info("time:"+pubtime.toString());
            logger.info("url:" + url);
            logger.info("title:"+title);

            List<String> hrefs = download_url.eachAttr("href");
            for (String href : hrefs) {
                // 排除无用链接
                if (href.equals("http://www.ygdy8.net/") || href.equals("/js/thunder.htm")
                        || href.equals("http://www.dytt8.net/")) { continue; }

                // 磁力链接下载地址，去掉后面的无效内容
                // ftp 中带@ 特殊符号，会被Markdown错误解析，需要在代码块里才能显示原来的内容
                if (href.startsWith("magnet:") && href.contains("&")) {
                    String[] split = href.split("&");
                    content = content +"\n\n点击或复制磁力链接下载：\n["+split[0]+"](" + split[0] + ")";
                } else if (href.startsWith("ftp:")) {
                    content = content + "\n\n复制ftp地址下载：\n`" + href + "`";
                } else {
                    content = content +"\n\n点击或复制下载地址：\n["+href+"](" + href + ")";
                }
            }

            if (content.length()>9000) {
                logger.info("内容太长，忽略");
                return;
            }

            Integer bid = blogDao.getBidByUrl(url);
            if (bid == null) {
                Blog blog = new Blog();
                blog.setKeyword("");
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
