package cn.fengyunxiao.nest.service;

import cn.fengyunxiao.nest.config.Config;
import cn.fengyunxiao.nest.dao.IpDao;
import cn.fengyunxiao.nest.entity.Chart;
import cn.fengyunxiao.nest.entity.Ip;
import cn.fengyunxiao.nest.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class IpService {
    @Autowired private IpDao ipDao;
    @Autowired private StringRedisTemplate stringRedisTemplate;
    private static final Logger logger = LoggerFactory.getLogger(IpService.class);

    public int getAllPage() {
        int allpage = (Config.ipCount % 8 == 0) ? (Config.ipCount / 8) : (Config.ipCount / 8 + 1);
        if (allpage < 1) {
            allpage = 1;
        }
        return allpage;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public int total() {
        return this.ipDao.total();
    }

    @CachePut({ "charts" })
    public Map<String, Object> resetChart() {
        return this.getChartsFromSql();
    }

    @Cacheable({ "charts" })
    public Map<String, Object> getCharts() {
        return this.getChartsFromSql();
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public synchronized Map<String, Object> getChartsFromSql() {
        IpService.logger.info("sql_charts");
        final Map<String, Object> charts = new HashMap<String, Object>(8);
        final List<String> cityName = new ArrayList<String>();
        final List<String> ispName = new ArrayList<String>();
        final List<String> monthName = new ArrayList<String>();
        final List<String> daysName = new ArrayList<String>();
        final List<Integer> cityNum = new ArrayList<Integer>();
        final List<Integer> ispNum = new ArrayList<Integer>();
        final List<Integer> monthNum = new ArrayList<Integer>();
        final List<Integer> daysNum = new ArrayList<Integer>();
        final List<Chart> citys = this.ipDao.listTop5City();
        for (final Chart chart : citys) {
            cityName.add(chart.getName());
            cityNum.add(chart.getNum());
        }
        charts.put("cityName", cityName);
        charts.put("cityNum", cityNum);
        final List<Chart> isps = this.ipDao.listTop5Isp();
        for (final Chart chart2 : isps) {
            ispName.add(chart2.getName());
            ispNum.add(chart2.getNum());
        }
        charts.put("ispName", ispName);
        charts.put("ispNum", ispNum);
        final long halfYearAgo = System.currentTimeMillis() - 15811200000L;
        Timestamp timestamp = new Timestamp(halfYearAgo);
        final List<Chart> month5 = this.ipDao.listMonth5(timestamp);
        Collections.reverse(month5);
        for (final Chart chart3 : month5) {
            monthName.add(chart3.getName());
            monthNum.add(chart3.getNum());
        }
        charts.put("monthName", monthName);
        charts.put("monthNum", monthNum);
        final long day14age = System.currentTimeMillis() - 1209600000L;
        timestamp = new Timestamp(day14age);
        final List<Chart> day5 = this.ipDao.listDay5(timestamp);
        Collections.reverse(day5);
        for (final Chart chart4 : day5) {
            daysName.add(chart4.getName());
            daysNum.add(chart4.getNum());
        }
        charts.put("daysName", daysName);
        charts.put("daysNum", daysNum);
        return charts;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Ip> listIp(Integer page, Integer per) {
        if (page == null) {
            page = 1;
        }
        if (per == null) {
            per = 10;
        }
        if (per > 32) {
            per = 32;
        }
        if (per < 1) {
            per = 1;
        }
        if (page < 1) {
            page = 1;
        }
        return this.ipDao.listIp((page - 1) * per, per);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addIp(final HttpServletRequest request) {
        final String ip = IpUtil.getIp(request);
        if (Config.ipIgnore.contains(ip)) {
            return;
        }
        if (!this.isSafeIp(ip)) {
            return;
        }
        Ip realIp = IpUtil.getIpAddrByIpIp(ip);
        if (realIp == null) {
            realIp = IpUtil.getIpAddrByAli(ip);
            if (realIp == null) {
                IpService.logger.error("ipip ali not get info null");
                return;
            }
        }
        this.ipDao.insertIp(realIp);
        ++Config.ipCount;
    }

    public synchronized boolean isSafeIp(final String ip) {
        if (ip == null) {
            return false;
        }
        final String key = "ip_visit:" + ip;
        final String s1 = Long.toString(System.currentTimeMillis());
        final Boolean a = this.stringRedisTemplate.boundValueOps(key).setIfAbsent(s1, 300L, TimeUnit.SECONDS);
        if (a == null) {
            IpService.logger.error("setIfAbsent return:{}", a);
            return false;
        }
        return a;
    }

}
