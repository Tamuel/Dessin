package com.softwork.ydk_lsj.dessin;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.softwork.ydk_lsj.dessin.DataProvider.DBProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by DongKyu on 2015-12-19.
 */
public class ScheduleBar extends FrameLayout implements View.OnClickListener{
    /** Text view for show schedule title */
    private TextView scheduleTitleView;
    /** Text view for show additional schedule titles */
    private ArrayList<TextView> addScheduleTitleViews;

    /** For Schedule ID */
    private int scheduleID;
    /** For Schedule title */
    private String scheduleTitle;
    /** For Schedule information */
    private String scheduleInfo;
    /** For additional schedule titles */
    private ArrayList<String> addScheduleTitles;
    /** For additional schedule positions */
    private ArrayList<Integer> addSchedulePos;

    /** For schedule start date */
    private Date scheduleStartDate;
    /** For schedule end date */
    private Date scheduleEndDate;

    /** Current layout state is wide or not */
    private boolean isWide = false;
    /** Layout width */
    private int layoutWidth;
    /** Layout Position */
    private int layoutPosition;

    public ScheduleBar(Context context, int scheduleID, String title, String info, Date startDate, Date endDate) {
        super(context);
        addScheduleTitles = new ArrayList<String>();
        addSchedulePos = new ArrayList<Integer>();
        addScheduleTitleViews = new ArrayList<TextView>();

        this.scheduleID = scheduleID;
        scheduleTitle = title;
        scheduleInfo = info;

        scheduleStartDate = startDate;
        scheduleEndDate = endDate;

        /** Set layout params */
        int border = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15f, getResources().getDisplayMetrics());
        layoutWidth = (endDate.getDate() - startDate.getDate() + 1) * ((int)getResources().getDimension(R.dimen.day_button_size) + border * 2);
        layoutPosition = (startDate.getDate() - 1) * ((int)getResources().getDimension(R.dimen.day_button_size) + border *2);
        LayoutParams layoutParams = new LayoutParams(
                layoutWidth,
                (int) getResources().getDimension(R.dimen.schedule_bar_narrow_height),
                0);
        layoutParams.setMargins(layoutPosition, 0, 0, 0);

        setLayoutParams(layoutParams);
        setBackgroundResource(R.drawable.bar_layout);

        setOnClickListener(this);

        setTitleView();
        setAddScheduleTitleViews();
    }

    /**
     *  Set schedule title view
     * */
    public void setTitleView() {
        LayoutParams titleParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT,
                0);

        scheduleTitleView = new TextView(getContext());
        scheduleTitleView.setTextColor(Color.WHITE);
        scheduleTitleView.setLayoutParams(titleParams);
        scheduleTitleView.setPadding(
                (int) getResources().getDimension(R.dimen.schedule_bar_title_left_margin),
                0, 0, 0);
        scheduleTitleView.setGravity(Gravity.CENTER_VERTICAL);
        scheduleTitleView.setText(scheduleTitle);

        addView(scheduleTitleView);
    }

    /**
     * Set additional schedule title views
     */
    public void setAddScheduleTitleViews() {
        addSchedulePos.clear();
        addScheduleTitles.clear();

        ContentResolver cr = getContext().getContentResolver();
        Cursor cur;

        String[] selectionArgs = {scheduleID + ""};

        cur = cr.query(Uri.parse(DBProvider.ADDITIONAL_SCHEDULE_TABLE_CONTENT_URI + "/*"), null,
                DBProvider.SCHEDULE_TABLE_FK, selectionArgs,
                " ORDER BY " + DBProvider.ADDITIONAL_SCHEDULE_START_DATE + " asc");
        Log.i("GET COUNT : ", cur.getCount() + "");

        int layoutStartPosition = 0;
        int border = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15f, getResources().getDisplayMetrics());
        Date addStartDate = null;

        cur.moveToFirst();
        if(cur.getCount() != 0) {
            do {
                addScheduleTitles.add(cur.getString(cur.getColumnIndex(DBProvider.ADDITIONAL_SCHEDULE_TITLE)));
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                try {
                    addStartDate = format.parse(cur.getString(cur.getColumnIndex(DBProvider.ADDITIONAL_SCHEDULE_START_DATE)));
                } catch (Exception e) {}
                layoutStartPosition = (addStartDate.getDate() - scheduleStartDate.getDate()) *
                        ((int) getResources().getDimension(R.dimen.day_button_size) + border * 2);
                Log.i("ADD SCHEDULE : ", cur.getString(cur.getColumnIndex(DBProvider.ADDITIONAL_SCHEDULE_TITLE)) + " " + layoutStartPosition);

                addSchedulePos.add(layoutStartPosition);
            } while (cur.moveToNext());

            for (int i = 0; i < addScheduleTitles.size(); i++) {
                TextView tempView = new TextView(getContext());

                int addTitleWidth;

                if (i == addScheduleTitles.size() - 1)
                    addTitleWidth = layoutWidth - addSchedulePos.get(i);
                else
                    addTitleWidth = addSchedulePos.get(i + 1) - addSchedulePos.get(i);

                LayoutParams titleParams = new LayoutParams(
                        addTitleWidth,
                        LayoutParams.MATCH_PARENT,
                        0);
                titleParams.setMargins(
                        addSchedulePos.get(i),
                        0, 0, 0);

                tempView = new TextView(getContext());
                tempView.setSingleLine();
                tempView.setBackgroundResource(R.drawable.left_bar_layout);
                tempView.setTextColor(Color.WHITE);
                tempView.setLayoutParams(titleParams);
                tempView.setGravity(Gravity.CENTER_VERTICAL);
                tempView.setText(addScheduleTitles.get(i));
                tempView.setPadding(
                        (int) getResources().getDimension(R.dimen.schedule_bar_title_left_margin),
                        0, 0, 0);

                addScheduleTitleViews.add(tempView);
                addView(tempView);
            }
        }

        if(addSchedulePos.size() >= 1) {
            LayoutParams titleParams = new LayoutParams(
                    addSchedulePos.get(0),
                    LayoutParams.MATCH_PARENT,
                    0);
            scheduleTitleView.setLayoutParams(titleParams);
        }
        scheduleTitleView.setSingleLine();
    }

    /**
     * When click on this layout
     * @param v
     */
    @Override
    public void onClick(View v) {
        if(!isWide) { // When to wide
            scheduleTitleView.setSingleLine(false);
            for(TextView temp : addScheduleTitleViews) {
                temp.setGravity(Gravity.TOP);
                temp.setSingleLine(false);
            }
            LayoutParams layoutParams = new LayoutParams(
                    layoutWidth,
                    (int) getResources().getDimension(R.dimen.schedule_bar_wide_height),
                    0);
            layoutParams.setMargins(layoutPosition, 0, 0, 0);
            setLayoutParams(layoutParams);
        } else { // When to narrow
            scheduleTitleView.setSingleLine();
            for(TextView temp : addScheduleTitleViews) {
                temp.setGravity(Gravity.CENTER_VERTICAL);
                temp.setSingleLine();
            }
            LayoutParams layoutParams = new LayoutParams(
                    layoutWidth,
                    (int) getResources().getDimension(R.dimen.schedule_bar_narrow_height),
                    0);
            layoutParams.setMargins(layoutPosition, 0, 0, 0);
            setLayoutParams(layoutParams);
        }
        isWide = !isWide;
    }
}
