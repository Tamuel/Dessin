package com.softwork.ydk_lsj.dessin.DataProvider;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by DongKyu on 2015-12-10.
 */
public class DataProvider {
    private static DataProvider instance;
    private GregorianCalendar date;

    private int year;
    private int month;
    private int day;

    public enum Day {
        SUN(1),
        MON(2),
        TUE(3),
        WED(4),
        THR(5),
        FRI(6),
        SAT(7);

        private int value;

        private Day(int v) {
            value = v;
        }

        public int getInt() {
            return value;
        }
    }

    private DataProvider() {
        date = new GregorianCalendar();
        year = getCurrentYear();
        month = getCurrentMonth();
        day = getCurrentDay();
    }

    public static DataProvider getInstance() {
        if(instance == null) {
            instance = new DataProvider();
        }

        return instance;
    }

    public int getDayOfWeek(int day) {
        Calendar cal= Calendar.getInstance ();
        cal.set(Calendar.YEAR, getCurrentYear());
        cal.set(Calendar.MONTH, getCurrentMonth() - 1);
        cal.set(Calendar.DATE, day);
        return cal.get(date.DAY_OF_WEEK);
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public int getCurrentDay() {
        return date.get(date.DATE);
    }

    public int getCurrentYear() {
        return date.get(date.YEAR);
    }

    public int getCurrentMonth() {
        return date.get(date.MONTH) + 1;
    }

    public int getCurrentDayOfMonth() {
        return date.getActualMaximum(date.DAY_OF_MONTH);
    }

    public int getSelectedYear() {
        return year;
    }

    public int getSelectedMonth() {
        return month;
    }

    public int getSelectedDay() {
        return day;
    }

    public int getSelectedDayOfMonth() {
        GregorianCalendar tempDate = new GregorianCalendar();;
        tempDate.set(getSelectedYear(), getSelectedMonth() - 1, getSelectedDay());
        return tempDate.getActualMaximum(date.DAY_OF_MONTH);
    }

    public void setSelectedYear(int year) {
        this.year = year;
    }

    public void setSelectedMonth(int month) {
        this.month = month;
    }

    public void setSelectedDay(int day) {
        this.day = day;
    }
}
