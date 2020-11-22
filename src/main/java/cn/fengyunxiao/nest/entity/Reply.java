package cn.fengyunxiao.nest.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Reply implements Serializable {
    private Integer replyid;
    private Integer lid;
    private String  nickname;
    private String  content;
    private String  ip;
    private Timestamp curtime;

    public Integer getReplyid() {
        return replyid;
    }

    public void setReplyid(Integer replyid) {
        this.replyid = replyid;
    }

    public Integer getLid() {
        return lid;
    }

    public void setLid(Integer lid) {
        this.lid = lid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Timestamp getCurtime() {
        return curtime;
    }

    public void setCurtime(Timestamp curtime) {
        this.curtime = curtime;
    }
}
