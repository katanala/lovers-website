package cn.fengyunxiao.nest.config;

/**
 * 配置文件，对应 install 表，系统启动时，从install读取，然后设置
 */
public class Config implements ConfigI {
    // 记录总的ip个数，listener 初始化，每插入一个ip，该值+1
    public static Integer ipCount = 0;

    // 不记录访问的 ip，通常为内网 Ip
    public static String ipIgnore = "0:0:0:0:0:0:0:0 0:0:0:0:0:0:0:1 127.0.0.1 0.0.0.0";

    // 每天每个ip最多留言几次。防灌水。
    public static int letterMaxNum = 2;

    // 分页时，每页数量
    public static int pageNumber = 10;

    // 邮件接收者
    public static String[] adminMail = {"2736629976@qq.com", "1350717815@qq.com"};//

    // 后台登录信息
    public static String adminToken = "123456";
    public static String adminPhone = "admin";
    public static String adminLoginToken = "123456"; //  api token
    public static String adminGoogle = "ZZZZZZZZZZZZZZZZ";

    // 上传的图片（将压缩后的保存到服务器，然后上传到 oss，便于快速访问）
    public static String imageLocalPath = "/home/image/";
    public static float imageSizeMax = 600;
    public static float imageQuality = 0.5f;
    public static String imageSuffix = ".jpg,.png,.jpeg,.png,.gif";

    // 阿里 OSS 配置，这里使用 https 协议
    // 上传时域名 http://oss-cn-shanghai.aliyuncs.com/
    // 使用时域名 http://fengyunxiao.oss-cn-shanghai.aliyuncs.com/
    // 自定义域名 http://static.fengyunxiao.cn/
    public static String ossProtocol = "http://";
    public static String ossBucket = "fengyunxiao";
    public static String ossEndpoint = "oss-cn-shanghai.aliyuncs.com/";
    public static String ossAccessKeyId = "";
    public static String ossAccessKeySecret = "";
    // 自定义访问域名，不实用 oss 的域名进行访问
    public static String ossUrlPrefix = "https://static.fengyunxiao.cn/";
    // 将 博客中的图片 和 相册中的图片 分开目录保存
    public static String ossBlogFolder = "nest/blog/";
    public static String ossPhotoFolder = "nest/";

    // 莉莉聊天机器人接口 http://www.itpk.cn/
    public static String apiLiliKey = "";
    public static String apiLiliSecret = "";

    // 图灵聊天机器人接口 http://www.tuling123.com
    public static String apiTulingKey = "";

    // 相爱时间：年，月，日，计算出总的时间
    public static int firstLoveYear = 2015;
    public static int firstLoveMonth = 11;
    public static int firstLoveDay = 11;
    // 首页信息
    public static String firstTitlePrimary = "Our Small Nest";
    public static String firstTitleSecondary = "晨哥 &nbsp; ❤ &nbsp; 芸霄";
    public static String firstTimeLove = "2015-11-11";
    public static String firstContactQQ = "2736629976";
    public static String firstCopyRight = "CopyRight © 2015-";
    public static String firstAdminMessage = "欢迎访问，欢迎观看，欢迎留言";
    public static String firstKeywords = "我们的小窝,冯芸霄,张圣晨";
    public static String firstTimeStampHtml = "3";

    // js，css 优先使用 cdn
    public static String cdnBootstrapCss = "//cdn.bootcss.com/twitter-bootstrap/4.3.1/css/bootstrap.min.css";
    public static String cdnBootstrapJs = "//cdn.bootcss.com/twitter-bootstrap/4.3.1/js/bootstrap.min.js";
    public static String cdnMuiCss = "//cdn.bootcss.com/mui/3.7.1/css/mui.min.css";
    public static String cdnMuiJs = "//cdn.bootcss.com/mui/3.7.1/js/mui.min.js";
    public static String cdnJqueryJs = "//cdn.bootcss.com/jquery/3.4.1/jquery.min.js";
    public static String cdnXssJs = "//cdn.bootcss.com/js-xss/0.3.3/xss.min.js";
    public static String cdnLazyloadJs = "https://libs.cdnjs.net/jquery_lazyload/1.9.7/jquery.lazyload.min.js";
    public static String cdnChartJs = "https://libs.cdnjs.net/Chart.js/2.9.3/Chart.min.js";
}
