package cn.fengyunxiao.nest.entity;

import java.io.Serializable;

public class Timeline implements Serializable {
    private Integer lineid;
    private Integer year;
    private String title;
    private String content1;
    private String content2;
    private String content3;

    public Integer getLineid() {
        return this.lineid;
    }

    public void setLineid(final Integer lineid) {
        this.lineid = lineid;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(final Integer year) {
        this.year = year;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getContent1() {
        return this.content1;
    }

    public void setContent1(final String content1) {
        this.content1 = content1;
    }

    public String getContent2() {
        return this.content2;
    }

    public void setContent2(final String content2) {
        this.content2 = content2;
    }

    public String getContent3() {
        return this.content3;
    }

    public void setContent3(final String content3) {
        this.content3 = content3;
    }
}
