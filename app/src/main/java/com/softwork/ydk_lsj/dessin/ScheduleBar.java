package com.softwork.ydk_lsj.dessin;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by DongKyu on 2015-12-19.
 */
public class ScheduleBar extends FrameLayout implements View.OnClickListener{
    /** Text view for show schedule title */
    private TextView scheduleTitleView;
    /** Text view for show additional schedule titles */
    private ArrayList<TextView> addScheduleTitleViews;

    /** For Schedule title */
    private String scheduleTitle;
    /** For additional schedule titles */
    private ArrayList<String> addScheduleTitles;

    /** Current layout state is wide or not */
    private boolean isWide = false;
    /** Layout width */
    private int layoutWidth;
    /** Layout Position */
    private int layoutPosition;

    public ScheduleBar(Context context) {
        super(context);
        addScheduleTitles = new ArrayList<String>();
        addScheduleTitleViews = new ArrayList<TextView>();

        /** Set layout params */
        // TODO - Should get layout width and position from DB
        layoutWidth = 1000;
        layoutPosition = 500;
        LayoutParams layoutParams = new LayoutParams(
                layoutWidth,
                (int) getResources().getDimension(R.dimen.schedule_bar_narrow_height),
                0);
        // TODO - Should get position from DB by date
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
        scheduleTitle = "놀기";

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
        // TODO - Should get titles from DB
        addScheduleTitles.add("더 놀기");
        addScheduleTitles.add("더더더더 놀기");
        addScheduleTitles.add("더더더 놀기");

        // TODO - Should get additional schedule's day - schedule's day
        ArrayList<Integer> addSchedulePos = new ArrayList<Integer>();
        addSchedulePos.add(200);
        addSchedulePos.add(500);
        addSchedulePos.add(700);

        for(int i = 0; i < addScheduleTitles.size(); i++) {
            TextView tempView = new TextView(getContext());

            int addTitleWidth;

            if(i == addScheduleTitles.size() - 1)
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

    /**
     * When click on this layout
     * @param v
     */
    @Override
    public void onClick(View v) {
        if(!isWide) { // When to wide
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
