package cn.fengyunxiao.nest.config;

import java.util.HashMap;
import java.util.Map;

public interface ConfigI {
    // ip缓存，8分钟只记录一次
    Map<String, Long> RECORD_IP_ADDR = new HashMap<>();

    // 聊天防刷，每个 session_id 3秒聊一次5020
    Map<String, Long> RECORD_CHAT_ID = new HashMap<>();

    // 6小时内留言的Ip 及 个数
    Map<String, Integer> LETTER_TODAY_IP = new HashMap<>();

    // 不记录访问的 ip，通常为内网 Ip
    String RECORD_IP_IGNORE = "0:0:0:0:0:0:0:0 0:0:0:0:0:0:0:1 127.0.0.1 0.0.0.0";

    // 莉莉聊天机器人接口 http://www.itpk.cn/
    String LILI_API_KEY = "";
    String LILI_API_SECRET = "";
    // 图灵聊天机器人接口 http://www.tuling123.com
    String TULING_KEY = "";

    // 6小时内每个ip最多留言几次。防灌水。
    int LETTER_TODAY_IP_MAX = 3;

    // 分页时，每页数量
    int PAGE_NUMBER = 10;

    // 邮件接收者
    String[] ADMIN_MAIL = {"2736629976@qq.com", "1350717815@qq.com"};

    // 相爱时间：年，月，日
    int TIME_LOVE_YEAR = 2015;
    int TIME_LOVE_MONTH = 11;
    int TIME_LOVE_DAY = 11;

    // ServletContext 中的名字
    String SERVLET_FIRST_PAGE = "firstPage";

    // 后台登录信息
    String TOKEN_URL = "";
    String TOKEN_PHONE = "";
    String TOKEN_DO_LOGIN = "";
    String TOKEN_GOOGLE_KEY = "";

    // 上传的图片（将压缩后的保存到服务器，然后上传到 oss，便于快速访问）
    String IMAGE_LOCAL_PATH = "/home/image/";
    float IMAGE_MAX_SIZE = 600;
    float IMAGE_ZIP_QUALITY = 0.5f;
    String IMAGE_SUFFIX = ".jpg,.png,.jpeg,.png,.gif";

    // 阿里 OSS 配置，这里使用 https 协议
    // 上传时 http://oss-cn-shanghai.aliyuncs.com/
    // 使用时 http://fengyunxiao.oss-cn-shanghai.aliyuncs.com/
    // 自定义 http://static.fengyunxiao.cn/
    String OSS_PROTOCOL = "https://";
    String OSS_BUCKET_NAME = "fengyunxiao";
    String OSS_ENDPOINT = "oss-cn-shanghai.aliyuncs.com/";
    String OSS_ACCESS_KEY_ID = "";
    String OSS_ACCESS_KEY_SECRET = "";
    // 自定义访问域名，不实用 oss 的域名进行访问
    String OSS_URL_PREFIX = OSS_PROTOCOL+"static.fengyunxiao.cn/";
    // 将 博客中的图片 和 相册中的图片 分开目录保存
    String OSS_BLOG_FOLDER = "nest/blog/";
    String OSS_PHOTOS_FOLDER = "nest/falls/";

    // 爬虫设置
    String CRAWLER_LOCAL_FOLER = "/home/crawler/";
    int    CRAWLER_MaxPagesToFetch = 2000;
    int    CRAWLER_MaxDepthOfCrawling = 2;

    // 首页主标题
    String FIRST_PAGE_TITLE_PRIMARY = "Our Small Nest";
    String FIRST_PAGE_TITLE_SECONDARY = "晨哥 &nbsp; ❤ &nbsp; 芸霄";
    String FIRST_PAGE_TIME_LOVE = "2015-11-11";
    String FIRST_PAGE_CONTACT_QQ = "2736629976";
    String FIRST_PAGE_COPY_RIGHT = "CopyRight © 2015-";
    String FIRST_PAGE_ADMIN_MESSAGE = "欢迎访问，欢迎观看，欢迎留言";
    String FIRST_PAGE_KEYWORDS = "我们的小窝,冯芸霄,张圣晨";
    String FIRST_PAGE_TIME_STAMP_HTML = "1";

    // js，css 优先使用 cdn
    String CDN_BOOTSTRAP_CSS = "//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/css/bootstrap.min.css";
    String CDN_BOOTSTRAP_JS = "//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.min.js";
    String CDN_MUI_CSS = "//cdnjs.cloudflare.com/ajax/libs/mui/3.7.1/css/mui.min.css";
    String CDN_MUI_JS = "//cdnjs.cloudflare.com/ajax/libs/mui/3.7.1/js/mui.min.js";
    String CDN_JQUERY_JS = "//cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js";

}
