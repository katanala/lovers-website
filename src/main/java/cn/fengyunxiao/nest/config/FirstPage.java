package cn.fengyunxiao.nest.config;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

/**
 * 首页显示的信息，放到 servletContext 里进行缓存
 */
public class FirstPage {
	// 网站显示的信息
	private String titlePrimary = Config.FIRST_PAGE_TITLE_PRIMARY;
	private String titleSecondary = Config.FIRST_PAGE_TITLE_SECONDARY;
	private String timeLove = Config.FIRST_PAGE_TIME_LOVE;
	private String contactQQ = Config.FIRST_PAGE_CONTACT_QQ;
	private String copyRight = Config.FIRST_PAGE_COPY_RIGHT;
	private String adminMessage = Config.FIRST_PAGE_ADMIN_MESSAGE;
	private String keywords = Config.FIRST_PAGE_KEYWORDS;
	private String description = Config.FIRST_PAGE_KEYWORDS;
	// js,css 缓存时间戳
	private String timeStampHtml = Config.FIRST_PAGE_TIME_STAMP_HTML;
	// cdn 地址
	private String cdnBootstrapCss = Config.CDN_BOOTSTRAP_CSS;
	private String cdnBootstrapJs = Config.CDN_BOOTSTRAP_JS;
	private String cdnMuiCss = Config.CDN_MUI_CSS;
	private String cdnMuiJs = Config.CDN_MUI_JS;
	private String cdnJqueryJs = Config.CDN_JQUERY_JS;

	// 时间信息
	private int    currencyYear;        // 今年是哪年，copyright中更新使用
	private int    lovePeriodYears;    // 相爱了 多少年，多少月，多少日
	private int    lovePeriodMonths;
	private int    lovePeriodDays;
	private int    loveTotalDays;       // 相爱总天数

	public FirstPage() {
		LocalDate today = LocalDate.now();
		LocalDate original = LocalDate.of(Config.TIME_LOVE_YEAR, Config.TIME_LOVE_MONTH, Config.TIME_LOVE_DAY);
		Period period = Period.between(original, today);

		lovePeriodYears = period.getYears();
		lovePeriodMonths = period.getMonths();
		lovePeriodDays = period.getDays();
		loveTotalDays = (int) ChronoUnit.DAYS.between(original, today);
		currencyYear = today.getYear();
	}

	public String getCdnMuiCss() {
		return cdnMuiCss;
	}

	public void setCdnMuiCss(String cdnMuiCss) {
		this.cdnMuiCss = cdnMuiCss;
	}

	public String getCdnMuiJs() {
		return cdnMuiJs;
	}

	public void setCdnMuiJs(String cdnMuiJs) {
		this.cdnMuiJs = cdnMuiJs;
	}

	public String getCdnBootstrapCss() {
		return cdnBootstrapCss;
	}

	public void setCdnBootstrapCss(String cdnBootstrapCss) {
		this.cdnBootstrapCss = cdnBootstrapCss;
	}

	public String getCdnBootstrapJs() {
		return cdnBootstrapJs;
	}

	public void setCdnBootstrapJs(String cdnBootstrapJs) {
		this.cdnBootstrapJs = cdnBootstrapJs;
	}

	public String getCdnJqueryJs() {
		return cdnJqueryJs;
	}

	public void setCdnJqueryJs(String cdnJqueryJs) {
		this.cdnJqueryJs = cdnJqueryJs;
	}

	public String getTimeStampHtml() {
		return timeStampHtml;
	}

	public void setTimeStampHtml(String timeStampHtml) {
		this.timeStampHtml = timeStampHtml;
	}

	public int getCurrencyYear() {
		return currencyYear;
	}

	public void setCurrencyYear(int currencyYear) {
		this.currencyYear = currencyYear;
	}

	public int getLovePeriodYears() {
		return lovePeriodYears;
	}

	public void setLovePeriodYears(int lovePeriodYears) {
		this.lovePeriodYears = lovePeriodYears;
	}

	public int getLovePeriodMonths() {
		return lovePeriodMonths;
	}

	public void setLovePeriodMonths(int lovePeriodMonths) {
		this.lovePeriodMonths = lovePeriodMonths;
	}

	public int getLovePeriodDays() {
		return lovePeriodDays;
	}

	public void setLovePeriodDays(int lovePeriodDays) {
		this.lovePeriodDays = lovePeriodDays;
	}

	public int getLoveTotalDays() {
		return loveTotalDays;
	}

	public void setLoveTotalDays(int loveTotalDays) {
		this.loveTotalDays = loveTotalDays;
	}

	public String getTitlePrimary() {
		return titlePrimary;
	}

	public void setTitlePrimary(String titlePrimary) {
		this.titlePrimary = titlePrimary;
	}

	public String getTitleSecondary() {
		return titleSecondary;
	}

	public void setTitleSecondary(String titleSecondary) {
		this.titleSecondary = titleSecondary;
	}

	public String getTimeLove() {
		return timeLove;
	}

	public void setTimeLove(String timeLove) {
		this.timeLove = timeLove;
	}

	public String getContactQQ() {
		return contactQQ;
	}

	public void setContactQQ(String contactQQ) {
		this.contactQQ = contactQQ;
	}

	public String getCopyRight() {
		return copyRight;
	}

	public void setCopyRight(String copyRight) {
		this.copyRight = copyRight;
	}

	public String getAdminMessage() {
		return adminMessage;
	}

	public void setAdminMessage(String adminMessage) {
		this.adminMessage = adminMessage;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
