package cn.fengyunxiao.nest.config;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

/**
 *  相爱时间，每天更新一次
 */
public class TimeData {
    // 相爱第几年，几月，几日
    private int periodYears;
    private int periodMonths;
    private int periodDays;
    // 相爱天数
    private int allDays;
    // 今年是哪年，copyright 使用
    private int year;

    public TimeData() {
        LocalDate today = LocalDate.now();
        LocalDate original = LocalDate.of(Config.TIME_LOVE_YEAR, Config.TIME_LOVE_MONTH, Config.TIME_LOVE_DAY);
        Period period = Period.between(original, today);

        periodYears = period.getYears();
        periodMonths = period.getMonths();
        periodDays = period.getDays();
        allDays = (int) ChronoUnit.DAYS.between(original, today);
        year = today.getYear();
    }

    public int getPeriodYears() {
        return periodYears;
    }

    public void setPeriodYears(int periodYears) {
        this.periodYears = periodYears;
    }

    public int getPeriodMonths() {
        return periodMonths;
    }

    public void setPeriodMonths(int periodMonths) {
        this.periodMonths = periodMonths;
    }

    public int getPeriodDays() {
        return periodDays;
    }

    public void setPeriodDays(int periodDays) {
        this.periodDays = periodDays;
    }

    public int getAllDays() {
        return allDays;
    }

    public void setAllDays(int allDays) {
        this.allDays = allDays;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
