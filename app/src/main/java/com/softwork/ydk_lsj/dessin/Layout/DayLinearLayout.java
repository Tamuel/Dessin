package com.softwork.ydk_lsj.dessin.Layout;

import android.content.Context;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.LinearLayout;

import com.softwork.ydk_lsj.dessin.DataProvider.DataProvider;
import com.softwork.ydk_lsj.dessin.R;

import java.util.ArrayList;

/**
 * Make Day Linear Layout
 * Created by DongKyu on 2015-12-10.
 */
public class DayLinearLayout extends LinearLayout  {

    private ArrayList<Button> dayButtons;

    public DayLinearLayout(Context context) {
        super(context);

        dayButtons = new ArrayList<Button>();

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
        int today = DataProvider.getInstance().getCurrentDay();

        for(int i = 0; i < DataProvider.getInstance().getSelectedDayOfMonth(); i++) {
            day = i + 1;
            Button newDayButton = new Button(getContext());
            LayoutParams dayButtonParams = new LayoutParams(
                    (int) getResources().getDimension(R.dimen.day_button_size),
                    (int) getResources().getDimension(R.dimen.day_button_size),
                    0);
            int border = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15f, getResources().getDisplayMetrics());
            int border2 = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, getResources().getDisplayMetrics());
            dayButtonParams.setMargins(border, border2, border, border2);

            newDayButton.setPadding(0, 0, 0, 0);
            newDayButton.setLayoutParams(dayButtonParams);

            newDayButton.setText(day + "");
            newDayButton.setBackgroundResource(R.drawable.day_simple_button);

            if(DataProvider.getInstance().getSelectedYear() == DataProvider.getInstance().getCurrentYear() &&
                    DataProvider.getInstance().getSelectedMonth() == DataProvider.getInstance().getCurrentMonth() &&
                    DataProvider.getInstance().getCurrentDay() == day) {
                newDayButton.setTextColor(getResources().getColor(R.color.white));
                newDayButton.setBackgroundResource(R.drawable.today_simple_button);
            } else if(DataProvider.getInstance().getSelectedDayOfWeek(day) == 1)
                newDayButton.setTextColor(getResources().getColor(R.color.hotPink));
            else
                newDayButton.setTextColor(getResources().getColor(R.color.gray));

            newDayButton.setTextSize((int) (getResources().getDimension(R.dimen.day_button_text_size) / getResources().getDisplayMetrics().density));

            dayButtons.add(newDayButton);
            this.addView(newDayButton);
        }
    }

    public void setButtonsToDay() {
        for(int i = 0; i < dayButtons.size(); i++)
            dayButtons.get(i).setText((i + 1) + "");
    }

    public void setButtonsToDayOfWeek() {
        String dayOfWeek = "";
        for(int i = 0; i < dayButtons.size(); i++) {
            if(DataProvider.getInstance().getDayOfWeek((i + 1)) != 1) {
                dayOfWeek = getResources().getStringArray(R.array.day)[DataProvider.getInstance().getDayOfWeek((i + 1)) - 1];
                dayButtons.get(i).setText(dayOfWeek);
            }
        }
    }
}
