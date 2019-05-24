package cn.fengyunxiao.nest.entity;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Ip {
	private Integer iid;
	private String ip;
	private String region;
	private String city;
	private String isp;
	private Timestamp curtime;
	
	public Integer getIid() {
		return iid;
	}
	public void setIid(Integer iid) {
		this.iid = iid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getIsp() {
		return isp;
	}
	public void setIsp(String isp) {
		this.isp = isp;
	}
	public Timestamp getCurtime() {
		return curtime;
	}
	public void setCurtime(Timestamp curtime) {
		this.curtime = curtime;
	}

	@Override
	public String toString() {
		return "Ip{" +
				"iid=" + iid +
				", ip='" + ip + '\'' +
				", region='" + region + '\'' +
				", city='" + city + '\'' +
				", isp='" + isp + '\'' +
				", curtime=" + curtime +
				'}';
	}
}
