package com.softwork.ydk_lsj.dessin;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import com.softwork.ydk_lsj.dessin.DataProvider.DataProvider;

import java.util.Date;

/**
 * Make Day Linear Layout
 * Created by DongKyu on 2015-12-10.
 */
public class DayLinearLayout extends LinearLayout  {

    public DayLinearLayout(Context context) {
        super(context);

        Date date = new Date();
        date.getTime();
        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                (int) getResources().getDimension(R.dimen.day_bar_height),
                0);
        layoutParams.weight = 1;
        this.setLayoutParams(layoutParams);
        this.setOrientation(LinearLayout.HORIZONTAL);

        makeDayButtons();
    }

    /**
     * 날짜 버튼 만들기
     */
    public void makeDayButtons() {
        Log.i("마지막 날짜", DataProvider.getInstance().getMonth() + " " + DataProvider.getInstance().getDayOfMonth() + "일 ");
        for(int i = 0; i < DataProvider.getInstance().getDayOfMonth(); i++) {
            Button newDayButton = new Button(getContext());
            LayoutParams dayButtonParams = new LayoutParams(
                    (int) getResources().getDimension(R.dimen.day_button_width),
                    (int) getResources().getDimension(R.dimen.day_bar_height),
                    0);
            newDayButton.setLayoutParams(dayButtonParams);
            newDayButton.setText((i + 1) + "");
            newDayButton.setBackgroundColor(Color.WHITE);
            newDayButton.setTextColor(getResources().getColor(R.color.gray));//
            newDayButton.setTextSize((int)(getResources().getDimension(R.dimen.day_button_text_size) /  getResources().getDisplayMetrics().density));

            this.addView(newDayButton);
        }
    }
}
