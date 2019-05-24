package cn.fengyunxiao.nest.entity;

import java.sql.Timestamp;

public class Chat {
	private Integer   cid;
	private Byte      male;		// 性别，0宝宝，1我，2游客
	private String    content;
	private Timestamp pubtime;
	
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public Byte getMale() {
		return male;
	}
	public void setMale(Byte male) {
		this.male = male;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getPubtime() {
		return pubtime;
	}
	public void setPubtime(Timestamp pubtime) {
		this.pubtime = pubtime;
	}

	@Override
	public String toString() {
		return "Chat{" +
				"cid=" + cid +
				'}';
	}
}
