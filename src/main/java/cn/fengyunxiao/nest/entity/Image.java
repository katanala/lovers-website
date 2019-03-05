package cn.fengyunxiao.nest.entity;

import java.sql.Timestamp;

public class Image {
	private Integer  iid;
	private Integer  bid; // <0 bid，-1小相册
	private String   name;
	private Timestamp pubtime;

	public Integer getIid() {
		return iid;
	}
	public void setIid(Integer iid) {
		this.iid = iid;
	}
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getPubtime() {
		return pubtime;
	}
	public void setPubtime(Timestamp pubtime) {
		this.pubtime = pubtime;
	}
}
