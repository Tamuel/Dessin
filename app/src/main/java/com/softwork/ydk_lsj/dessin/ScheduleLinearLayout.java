package com.softwork.ydk_lsj.dessin;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.softwork.ydk_lsj.dessin.DataProvider.DataProvider;

import java.util.ArrayList;

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
        int layoutWidth = DataProvider.getInstance().getDayOfMonth() * size;
        for(int i = 0; i < 20; i++) {
            FrameLayout newLayout = new FrameLayout(getContext());
            LayoutParams dayButtonParams = new LayoutParams(
                    layoutWidth,
                    (int) getResources().getDimension(R.dimen.schedule_bar_height),
                    0);
            newLayout.setLayoutParams(dayButtonParams);
            newLayout.setBackgroundResource(R.drawable.light_underbar_layout_background);

            layouts.add(newLayout);
            this.addView(newLayout);
        }
    }
}
