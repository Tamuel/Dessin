package com.softwork.ydk_lsj.dessin.DataProvider;

import android.app.Application;

import com.softwork.ydk_lsj.dessin.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by DongKyu on 2015-12-10.
 */
public class DataProvider {
    private static DataProvider instance;
    private GregorianCalendar date;

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
        date = new GregorianCalendar( );
    }

    public static DataProvider getInstance() {
        if(instance == null) {
            instance = new DataProvider();
        }

        return instance;
    }

    public int getDayOfWeek(int day) {
        Calendar cal= Calendar.getInstance ();
        cal.set(Calendar.YEAR, getYear());
        cal.set(Calendar.MONTH, getMonth() - 1);
        cal.set(Calendar.DATE, day);
        return cal.get(date.DAY_OF_WEEK);
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public int getDay() {
        return date.get(date.DATE);
    }

    public int getYear() {
        return date.get(date.YEAR);
    }

    public int getMonth() {
        return date.get(date.MONTH) + 1;
    }

    public int getDayOfMonth() {
        return date.getActualMaximum(date.DAY_OF_MONTH);
    }
}
