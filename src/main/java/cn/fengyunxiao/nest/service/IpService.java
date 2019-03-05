package cn.fengyunxiao.nest.service;

import java.sql.Timestamp;
import java.util.*;

import cn.fengyunxiao.nest.config.Config;
import cn.fengyunxiao.nest.entity.Chart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.fengyunxiao.nest.dao.IpDao;
import cn.fengyunxiao.nest.entity.Ip;
import cn.fengyunxiao.nest.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IpService {
	private IpDao ipDao;
    private static final Logger logger = LoggerFactory.getLogger(IpService.class);

	@Autowired
	public void setIpDao(IpDao ipDao) {
		this.ipDao = ipDao;
	}

	public int getAllPage() {
		int allpage = Config.ipCount%10==0 ? Config.ipCount/10 : Config.ipCount/10+1;
		if (allpage < 1) { allpage = 1; }
		return allpage;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int total() {
		return ipDao.total();
	}

	/**
	 *  获取统计数据，优先使用缓存，task 中定时清除缓存
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public synchronized Map<String, Object> chartStatic() {
		Map<String, Object> charts = Config.charts;
		if (charts == null) {
			charts = new HashMap<>();
		} else {
			return charts;
		}

		List<String> cityName = new ArrayList<>();
		List<String> ispName = new ArrayList<>();
		List<String> monthName = new ArrayList<>();
		List<String> daysName = new ArrayList<>();

		List<Integer> cityNum = new ArrayList<>();
		List<Integer> ispNum = new ArrayList<>();
		List<Integer> monthNum = new ArrayList<>();
		List<Integer> daysNum = new ArrayList<>();

		List<Chart> citys = ipDao.listTop5City();
		for (Chart chart : citys) {
			cityName.add(chart.getName());
			cityNum.add(chart.getNum());
		}
		charts.put("cityName", cityName);
		charts.put("cityNum", cityNum);

		List<Chart> isps = ipDao.listTop5Isp();
		for (Chart chart : isps) {
			ispName.add(chart.getName());
			ispNum.add(chart.getNum());
		}
		charts.put("ispName", ispName);
		charts.put("ispNum", ispNum);

		// 统计前 183 天的中，前6个月的，提高效率
		long halfYearAgo = System.currentTimeMillis() - 15811200000L;
		Timestamp timestamp = new Timestamp(halfYearAgo);
		List<Chart> month5 = ipDao.listMonth5(timestamp);
		Collections.reverse(month5);
		for (Chart chart : month5) {
			monthName.add(chart.getName());
			monthNum.add(chart.getNum());
		}
		charts.put("monthName", monthName);
		charts.put("monthNum", monthNum);

		// 统计前 14 天中，前5天的，提高效率
		long day14age = System.currentTimeMillis() - 1209600000;
		timestamp = new Timestamp(day14age);
		List<Chart> day5 = ipDao.listDay5(timestamp);
		Collections.reverse(day5);
		for (Chart chart : day5) {
			daysName.add(chart.getName());
			daysNum.add(chart.getNum());
		}
		charts.put("daysName", daysName);
		charts.put("daysNum", daysNum);

		// 进行缓存
		Config.charts = charts;
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

		return ipDao.listIp((page-1)*per, per);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addIp(String ip) {
		// 排除本地 ip
		if (Config.RECORD_IP_IGNORE.contains(ip)) {
			// logger.info("不记录该ip："+ip);
			return;
		}

		if (!IpUtil.isSafeIp(ip)) {
			return;
		}

		Ip realIp = IpUtil.getIpAddrByIpIp(ip);
		if (realIp == null) {
			realIp = IpUtil.getIpAddrByAli(ip);
			if (realIp == null) {
				logger.error("ipip ali 未获取到ip信息：null");
				return;
			}
		}

		ipDao.insertIp(realIp);
		Config.ipCount++;
	}

}
