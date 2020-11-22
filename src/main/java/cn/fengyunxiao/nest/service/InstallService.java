package cn.fengyunxiao.nest.service;

import cn.fengyunxiao.nest.config.Config;
import cn.fengyunxiao.nest.config.FirstPage;
import cn.fengyunxiao.nest.config.JsonResult;
import cn.fengyunxiao.nest.dao.InstallDao;
import cn.fengyunxiao.nest.dao.LinkDao;
import cn.fengyunxiao.nest.entity.Install;
import cn.fengyunxiao.nest.entity.Link;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.*;

@Service
public class InstallService {
    private final static Logger logger = LoggerFactory.getLogger(InstallService.class);

    @Autowired private InstallDao installDao;
    @Autowired private LinkDao linkDao;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void checkMysqlTime() {
        Calendar calendar = Calendar.getInstance();
        Timestamp mysqlTime = installDao.getCurrentTime();

        if (Math.abs(calendar.getTimeInMillis() -  mysqlTime.getTime()) > 5000) {
            logger.error("error: java & mysql time not same");
        }

        logger.info("java  time:{}-{}-{} {}:{}:{}, timearea: {}", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND), ZoneId.systemDefault());

        calendar.setTimeInMillis( mysqlTime.getTime() );
        logger.info("mysql time:{}-{}-{} {}:{}:{}", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND));
    }

    // 启动时，从数据库读取配置进行初始化
    public void initConfig() {
        Map<String, String> mapInstall = mapInstall();

        Field[] declaredFields = Config.class.getDeclaredFields();
        for (Field field : declaredFields) {
            if (mapInstall.containsKey(field.getName())) {
                if (field.getType().equals(int.class)) {
                    int value = Integer.parseInt(mapInstall.get(field.getName()));
                    try {
                        field.setInt(null, value);
                    } catch (Exception e) {
                        logger.error("error config, key: " + field.getName() + ", value: " + value);
                    }
                } else if (field.getType().equals(String.class)) {
                    String value = mapInstall.get(field.getName());
                    try {
                        field.set(null, value);
                    } catch (Exception e) {
                        logger.error("error config, key: " + field.getName() + ", value: " + value);
                    }
                } else if (field.getType().equals(BigDecimal.class)) {
                    BigDecimal value = new BigDecimal(mapInstall.get(field.getName()));
                    try {
                        field.set(null, value);
                    } catch (Exception e) {
                        logger.error("error config, key: " + field.getName() + ", value: " + value);
                    }
                } else if (field.getType().equals(int[].class)) {
                    String string = mapInstall.get(field.getName());

                    if (string != null) {
                        String[] strings = string.split(",");
                        int[] temp = new int[strings.length];
                        for (int i = 0; i < strings.length; i++) {
                            temp[i] = Integer.parseInt(strings[i]);
                        }

                        try {
                            field.set(null, temp);
                        } catch (Exception e) {
                            logger.error("error config, key: " + field.getName() + ", value: " + Arrays.toString(strings));
                        }
                    }
                } else if (field.getType().equals(String[].class)) {
                    String string = mapInstall.get(field.getName());

                    if (string != null) {
                        String[] strings = string.split(",");
                        try {
                            field.set(null, strings);
                        } catch (Exception e) {
                            logger.error("error config, key: " + field.getName() + ", value: " + Arrays.toString(strings));
                        }
                    }
                } else if (field.getType().equals(float.class)) {
                    float value = Float.parseFloat(mapInstall.get(field.getName()));
                    try {
                        field.setFloat(null, value);
                    } catch (Exception e) {
                        logger.error("error config, key: " + field.getName() + ", value: " + value);
                    }
                }
            } else {
                logger.warn("error config, sql not exist：" + field.getName());
            }
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Install> listInstall() {
        return installDao.listInstall();
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Link> listLink() {
        return linkDao.listLink();
    }

    public Map<String, String> mapInstall() {
        List<Install> installs = listInstall();
        Map<String, String> map = new HashMap<>();
        for (Install install : installs) {
            try {
                map.put(install.getKey(), install.getValue());
            } catch (Exception e) {
                logger.error("配置错误，key：" + install.getKey() + "，value：" + install.getValue());
            }
        }
        return  map;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public JsonResult<String> deleteLink(String href, String tokens,
            HttpServletRequest request) {
        JsonResult<String> result = new JsonResult<>();

        if (tokens == null || !tokens.equals(Config.adminLoginToken)) {
            return result.error(-1, "请重新登录");
        }

        if (href == null) {
            return result.error(-1, "未获取到信息");
        }

        int deleteCount = linkDao.delete(href);

        initConfig();
        List<Link> links = linkDao.listLink();
        request.getServletContext().setAttribute(Config.servletFirstpage, new FirstPage(links));

        return result.ok("", "删除友链个数：" + deleteCount);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public JsonResult<String> updateLink(String href, String title,
             Integer rank, String tokens, HttpServletRequest request) {
        JsonResult<String> result = new JsonResult<>();

        if (tokens == null || !tokens.equals(Config.adminLoginToken)) {
            return result.error(-1, "请重新登录");
        }
        if (href == null) {
            return result.error(-1, "未获取到信息");
        }
        href = href.trim();
        if (rank == null) {
            rank = 0;
        }
        if (title == null) {
            title = "";
        }

        Link link = linkDao.getLink(href);

        if (link == null) {
            link = new Link();
            link.setTitle(title);
            link.setRank(rank);
            link.setHref(href);
            linkDao.insert(link);
        } else {
            if (!title.trim().equals("")) {
                link.setTitle(title);
            }
            if (rank != 0) {
                link.setRank(link.getRank() + rank);
            }
            linkDao.update(link);
        }


        initConfig();
        List<Link> links = linkDao.listLink();
        request.getServletContext().setAttribute(Config.servletFirstpage, new FirstPage(links));
        return result.ok("", "更新成功");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public JsonResult<String> insertOrUpdate(Install install, String tokens, HttpServletRequest request) {
        JsonResult<String> result = new JsonResult<>();

        if (tokens == null || !tokens.equals(Config.adminLoginToken)) {
            return result.error(-1, "请重新登录");
        }

        if (install == null) {
            return result.error(-1, "未获取到信息");
        }
        if (install.getKey() == null || install.getKey().length() > 100) {
            return result.error(-1, "未获取到key，或key长度超过100");
        }
        if (install.getValue() == null || install.getValue().length() > 900) {
            return result.error(-1, "未获取到值，或值长度超过900");
        }
        if (install.getIntro() == null) {
            install.setIntro("");
        }
        if (install.getIntro().length() > 900) {
            return result.error(-1, "说明长度超过900");
        }

        installDao.insertOrupdate(install);

        initConfig();

        List<Link> links = linkDao.listLink();
        request.getServletContext().setAttribute(Config.servletFirstpage, new FirstPage(links));
        return result.ok("", "插入或修改成功！");
    }

}
