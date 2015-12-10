package com.softwork.ydk_lsj.dessin.DataProvider;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by DongKyu on 2015-12-10.
 */
public class DataProvider {
    private static DataProvider instance;
    private GregorianCalendar date;

    private DataProvider() {
        date = new GregorianCalendar( );
    }

    public static DataProvider getInstance() {
        if(instance == null) {
            instance = new DataProvider();
        }

        return instance;
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
