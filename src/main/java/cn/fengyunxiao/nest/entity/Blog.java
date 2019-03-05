package cn.fengyunxiao.nest.entity;

import java.sql.Timestamp;

public class Blog {
    private Integer bid;
    private Byte    rank;
    private String  title;
    private String keyword;
    private String  content;
    private Timestamp modtime;
    private String url;

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public Byte getRank() {
        return rank;
    }

    public void setRank(Byte rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getModtime() {
        return modtime;
    }

    public void setModtime(Timestamp modtime) {
        this.modtime = modtime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

