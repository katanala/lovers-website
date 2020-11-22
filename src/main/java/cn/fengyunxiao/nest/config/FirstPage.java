package cn.fengyunxiao.nest.config;

import cn.fengyunxiao.nest.entity.Link;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 首页显示的信息，放到 servletContext 里进行缓存
 */
public class FirstPage {
    private String titlePrimary;
    private String titleSecondary;
    private String timeLove;
    private String contactQQ;
    private String copyRight;
    private String adminMessage;
    private String keywords;
    private String description;
    private String timeStampHtml;
    private String cdnBootstrapCss;
    private String cdnBootstrapJs;
    private String cdnMuiCss;
    private String cdnMuiJs;
    private String cdnJqueryJs;
    private String cdnXssJs;
    private String cdnLazyloadJs;
    private String cdnChartJs;
    private int currencyYear;
    private int lovePeriodYears;
    private int lovePeriodMonths;
    private int lovePeriodDays;
    private int loveTotalDays;
    private List<Link> links;

    public FirstPage(final List<Link> links) {
        this.titlePrimary = Config.firstTitlePrimary;
        this.titleSecondary = Config.firstTitleSecondary;
        this.timeLove = Config.firstTimeLove;
        this.contactQQ = Config.firstContactQQ;
        this.copyRight = Config.firstCopyRight;
        this.adminMessage = Config.firstAdminMessage;
        this.keywords = Config.firstKeywords;
        this.description = Config.firstKeywords;
        this.timeStampHtml = Config.firstTimeStampHtml;
        this.cdnBootstrapCss = Config.cdnBootstrapCss;
        this.cdnBootstrapJs = Config.cdnBootstrapJs;
        this.cdnMuiCss = Config.cdnMuiCss;
        this.cdnMuiJs = Config.cdnMuiJs;
        this.cdnJqueryJs = Config.cdnJqueryJs;
        this.cdnXssJs = Config.cdnXssJs;
        this.cdnLazyloadJs = Config.cdnLazyloadJs;
        this.cdnChartJs = Config.cdnChartJs;
        final LocalDate today = LocalDate.now();
        final LocalDate original = LocalDate.of(Config.firstLoveYear, Config.firstLoveMonth, Config.firstLoveDay);
        final Period period = Period.between(original, today);
        this.lovePeriodYears = period.getYears();
        this.lovePeriodMonths = period.getMonths();
        this.lovePeriodDays = period.getDays();
        this.loveTotalDays = (int)ChronoUnit.DAYS.between(original, today);
        this.currencyYear = today.getYear();
        this.links = links;
    }

    public String getCdnMuiCss() {
        return this.cdnMuiCss;
    }

    public void setCdnMuiCss(final String cdnMuiCss) {
        this.cdnMuiCss = cdnMuiCss;
    }

    public String getCdnMuiJs() {
        return this.cdnMuiJs;
    }

    public void setCdnMuiJs(final String cdnMuiJs) {
        this.cdnMuiJs = cdnMuiJs;
    }

    public String getCdnBootstrapCss() {
        return this.cdnBootstrapCss;
    }

    public void setCdnBootstrapCss(final String cdnBootstrapCss) {
        this.cdnBootstrapCss = cdnBootstrapCss;
    }

    public String getCdnBootstrapJs() {
        return this.cdnBootstrapJs;
    }

    public void setCdnBootstrapJs(final String cdnBootstrapJs) {
        this.cdnBootstrapJs = cdnBootstrapJs;
    }

    public String getCdnJqueryJs() {
        return this.cdnJqueryJs;
    }

    public void setCdnJqueryJs(final String cdnJqueryJs) {
        this.cdnJqueryJs = cdnJqueryJs;
    }

    public String getTimeStampHtml() {
        return this.timeStampHtml;
    }

    public void setTimeStampHtml(final String timeStampHtml) {
        this.timeStampHtml = timeStampHtml;
    }

    public int getCurrencyYear() {
        return this.currencyYear;
    }

    public void setCurrencyYear(final int currencyYear) {
        this.currencyYear = currencyYear;
    }

    public int getLovePeriodYears() {
        return this.lovePeriodYears;
    }

    public void setLovePeriodYears(final int lovePeriodYears) {
        this.lovePeriodYears = lovePeriodYears;
    }

    public int getLovePeriodMonths() {
        return this.lovePeriodMonths;
    }

    public void setLovePeriodMonths(final int lovePeriodMonths) {
        this.lovePeriodMonths = lovePeriodMonths;
    }

    public int getLovePeriodDays() {
        return this.lovePeriodDays;
    }

    public void setLovePeriodDays(final int lovePeriodDays) {
        this.lovePeriodDays = lovePeriodDays;
    }

    public int getLoveTotalDays() {
        return this.loveTotalDays;
    }

    public void setLoveTotalDays(final int loveTotalDays) {
        this.loveTotalDays = loveTotalDays;
    }

    public String getTitlePrimary() {
        return this.titlePrimary;
    }

    public void setTitlePrimary(final String titlePrimary) {
        this.titlePrimary = titlePrimary;
    }

    public String getTitleSecondary() {
        return this.titleSecondary;
    }

    public void setTitleSecondary(final String titleSecondary) {
        this.titleSecondary = titleSecondary;
    }

    public String getTimeLove() {
        return this.timeLove;
    }

    public void setTimeLove(final String timeLove) {
        this.timeLove = timeLove;
    }

    public String getContactQQ() {
        return this.contactQQ;
    }

    public void setContactQQ(final String contactQQ) {
        this.contactQQ = contactQQ;
    }

    public String getCopyRight() {
        return this.copyRight;
    }

    public void setCopyRight(final String copyRight) {
        this.copyRight = copyRight;
    }

    public String getAdminMessage() {
        return this.adminMessage;
    }

    public void setAdminMessage(final String adminMessage) {
        this.adminMessage = adminMessage;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(final String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public List<Link> getLinks() {
        return this.links;
    }

    public void setLinks(final List<Link> links) {
        this.links = links;
    }

    public String getCdnLazyloadJs() {
        return this.cdnLazyloadJs;
    }

    public void setCdnLazyloadJs(final String cdnLazyloadJs) {
        this.cdnLazyloadJs = cdnLazyloadJs;
    }

    public String getCdnChartJs() {
        return this.cdnChartJs;
    }

    public void setCdnChartJs(final String cdnChartJs) {
        this.cdnChartJs = cdnChartJs;
    }

    public String getCdnXssJs() {
        return this.cdnXssJs;
    }

    public void setCdnXssJs(final String cdnXssJs) {
        this.cdnXssJs = cdnXssJs;
    }
}
