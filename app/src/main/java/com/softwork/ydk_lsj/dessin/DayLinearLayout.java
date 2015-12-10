package com.softwork.ydk_lsj.dessin;

import android.content.Context;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.LinearLayout;

import com.softwork.ydk_lsj.dessin.DataProvider.DataProvider;

/**
 * Make Day Linear Layout
 * Created by DongKyu on 2015-12-10.
 */
public class DayLinearLayout extends LinearLayout  {

    public DayLinearLayout(Context context) {
        super(context);

        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                (int) getResources().getDimension(R.dimen.day_bar_height),
                0);
        this.setLayoutParams(layoutParams);
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setBackgroundResource(R.drawable.light_underbar_layout_background);

        makeDayButtons();
    }

    /**
     * 날짜 버튼 만들기
     */
    public void makeDayButtons() {
        int day = 0;
        int today = DataProvider.getInstance().getDay();

        for(int i = 0; i < DataProvider.getInstance().getDayOfMonth(); i++) {
            day = i + 1;
            Button newDayButton = new Button(getContext());
            LayoutParams dayButtonParams = new LayoutParams(
                    (int) getResources().getDimension(R.dimen.day_button_size),
                    (int) getResources().getDimension(R.dimen.day_button_size),
                    0);
            int border = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15f, getResources().getDisplayMetrics());
            int border2 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, getResources().getDisplayMetrics());
            dayButtonParams.setMargins(border, border2, border, border2);
//            newDayButton.setGravity();
            newDayButton.setPadding(0, 0, 0, 0);
            newDayButton.setLayoutParams(dayButtonParams);
            String dayOfWeek = "";
            switch (DataProvider.getInstance().getDayOfWeek(day)) {
                case 1:
                case 7:
                    dayOfWeek = "S";
                    break;

                case 2:
                    dayOfWeek = "M";
                    break;

                case 3:
                    dayOfWeek = "T";
                    break;

                case 4:
                    dayOfWeek = "W";
                    break;

                case 5:
                    dayOfWeek = "T";
                    break;

                case 6:
                    dayOfWeek = "F";
                    break;
            }
            newDayButton.setText(day + "");
            newDayButton.setBackgroundResource(R.drawable.day_simple_button);
            if(today == day) {
                newDayButton.setTextColor(getResources().getColor(R.color.white));
                newDayButton.setBackgroundResource(R.drawable.today_simple_button);
                newDayButton.setText(dayOfWeek);
            } else if(DataProvider.getInstance().getDayOfWeek(day) == 1)
                newDayButton.setTextColor(getResources().getColor(R.color.hotPink));
            else
                newDayButton.setTextColor(getResources().getColor(R.color.gray));
            newDayButton.setTextSize((int) (getResources().getDimension(R.dimen.day_button_text_size) / getResources().getDisplayMetrics().density));

            this.addView(newDayButton);
        }
    }
}
