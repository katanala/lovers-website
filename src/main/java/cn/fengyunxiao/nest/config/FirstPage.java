package cn.fengyunxiao.nest.config;

/**
 * 首页显示的信息，进行缓存
 */
public class FirstPage {
	private String titlePrimary = Config.FIRST_PAGE_TITLE_PRIMARY;
	private String titleSecondary = Config.FIRST_PAGE_TITLE_SECONDARY;
	private String timeLove = Config.FIRST_PAGE_TIME_LOVE;
	private String contactQQ = Config.FIRST_PAGE_CONTACT_QQ;
	private String copyRight = Config.FIRST_PAGE_COPY_RIGHT;
	private String adminMessage = Config.FIRST_PAGE_ADMIN_MESSAGE;
	private String keywords = Config.FIRST_PAGE_KEYWORDS;
	private String description = Config.FIRST_PAGE_KEYWORDS;

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
