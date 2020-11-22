package cn.fengyunxiao.nest.entity;

public class Link {
    private String title;
    private String href;
    private Integer rank;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() { return href; }

    public void setHref(String href) { this.href = href; }

    public Integer getRank() { return rank; }

    public void setRank(Integer rank) { this.rank = rank; }
}
