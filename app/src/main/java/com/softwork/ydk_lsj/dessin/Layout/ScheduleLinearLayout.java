package com.softwork.ydk_lsj.dessin.Layout;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.softwork.ydk_lsj.dessin.DataProvider.DBProvider;
import com.softwork.ydk_lsj.dessin.DataProvider.DataProvider;
import com.softwork.ydk_lsj.dessin.R;
import com.softwork.ydk_lsj.dessin.ScheduleBar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Make Schedule Layouts and Bars
 * Created by DongKyu on 2015-12-10.
 */
public class ScheduleLinearLayout extends LinearLayout {
    private ArrayList<FrameLayout> layouts;

    public ScheduleLinearLayout(Context context) {
        super(context);

        layouts = new ArrayList<FrameLayout>();

        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
                0);
        this.setLayoutParams(layoutParams);
        this.setOrientation(LinearLayout.VERTICAL);

        makeLayoutsAndBars();
    }

    public void makeLayoutsAndBars() {
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60f, getResources().getDisplayMetrics());
        int layoutWidth = DataProvider.getInstance().getSelectedDayOfMonth() * size;

        ContentResolver cr = getContext().getContentResolver();
        Cursor cur;

        String sql = " WHERE (" + DBProvider.SCHEDULE_START_DATE + " >= '" +
                DataProvider.getInstance().getSelectedYear() + "-" +
                DataProvider.getInstance().getSelectedMonth() + "-" +
                "1' AND " + DBProvider.SCHEDULE_START_DATE + " <= '" +
                DataProvider.getInstance().getSelectedYear() + "-" +
                DataProvider.getInstance().getSelectedMonth() + "-" +
                + DataProvider.getInstance().getSelectedDayOfMonth() + "') OR " +
                "(" + DBProvider.SCHEDULE_END_DATE + " >= '" +
                DataProvider.getInstance().getSelectedYear() + "-" +
                DataProvider.getInstance().getSelectedMonth() + "-" +
                "1' AND " + DBProvider.SCHEDULE_END_DATE + " <= '" +
                DataProvider.getInstance().getSelectedYear() + "-" +
                DataProvider.getInstance().getSelectedMonth() + "-" +
                + DataProvider.getInstance().getSelectedDayOfMonth() + "')" +
                " ORDER BY " + DBProvider.SCHEDULE_ID + " asc";

        cur = cr.query(DBProvider.SCHEDULE_TABLE_CONTENT_URI, null, null, null, sql);
        Log.i("SQL", sql);

        if (cur.getCount() != 0) {
            cur.moveToFirst();
            do {
                FrameLayout newLayout = new FrameLayout(getContext());
                LayoutParams dayButtonParams = new LayoutParams(
                        layoutWidth,
                        LayoutParams.WRAP_CONTENT,
                        0);

                String startDate = cur.getString(cur.getColumnIndex(DBProvider.SCHEDULE_START_DATE));
                String endDate = cur.getString(cur.getColumnIndex(DBProvider.SCHEDULE_END_DATE));
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                Date startD = null, endD = null;
                try {
                    startD = format.parse(startDate);
                    endD = format.parse(endDate);
                } catch (Exception e) {}

                newLayout.addView(new ScheduleBar(
                        getContext(),
                        cur.getInt(cur.getColumnIndex(DBProvider.SCHEDULE_ID)),
                        cur.getString(cur.getColumnIndex(DBProvider.SCHEDULE_TITLE)),
                        cur.getString(cur.getColumnIndex(DBProvider.SCHEDULE_INFORMATION)),
                        startD,
                        endD
                ));

                newLayout.setLayoutParams(dayButtonParams);
                newLayout.setBackgroundResource(R.drawable.light_underbar_layout_background);

                layouts.add(newLayout);
                this.addView(newLayout);
            } while (cur.moveToNext());
        }
        cur.close();
    }

    public ArrayList<FrameLayout> getLayouts() {
        return layouts;
    }

    public void setLayouts(ArrayList<FrameLayout> layouts) {
        this.layouts = layouts;
    }
}
