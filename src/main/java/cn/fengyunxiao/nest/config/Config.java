package cn.fengyunxiao.nest.config;

import cn.fengyunxiao.nest.entity.Letter;
import java.util.List;
import java.util.Map;

/**
 *  不变的配置在 ConfigI 里，前缀默认是 public static final
 *
 *   该类放可变配置（需读取的配置）
 */
public class Config implements ConfigI {
    // 记录总的ip个数，listener 初始化，每插入一个ip，该值+1
    public static Integer ipCount = 0;

    // 图表数据缓存，启动服务后初始化，6个小时更新一次
    public static Map<String, Object> charts = null;

    // 首页前四个留言缓存，（插入新留言或点赞时清空）
    public static List<Letter> lastLetter4 = null;
}
