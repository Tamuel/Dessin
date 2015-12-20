package com.softwork.ydk_lsj.dessin.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.softwork.ydk_lsj.dessin.DataProvider.DataProvider;
import com.softwork.ydk_lsj.dessin.Layout.DayLinearLayout;
import com.softwork.ydk_lsj.dessin.Layout.ScheduleLinearLayout;
import com.softwork.ydk_lsj.dessin.R;

public class MainActivity extends Activity {
    private LinearLayout dayScheduleLayout;

    private ScrollView scheduleScrollView;

    private DayLinearLayout dayLayout;
    private ScheduleLinearLayout scheduleLayout;

    private TextView yearTextView;
    private TextView monthTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        yearTextView = (TextView)findViewById(R.id.year_text_view);
        yearTextView.setText(DataProvider.getInstance().getYear() + "");

        monthTextView = (TextView)findViewById(R.id.month_text_view);
        monthTextView.setText(DataProvider.getInstance().getMonth() + "");

        dayScheduleLayout = (LinearLayout)findViewById(R.id.day_schedule_linear_layout);

        dayLayout = new DayLinearLayout(this);
        dayScheduleLayout.addView(dayLayout);

        scheduleScrollView = new ScrollView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0);
        scheduleScrollView.setFillViewport(true);
        scheduleScrollView.setLayoutParams(layoutParams);

        scheduleLayout = new ScheduleLinearLayout(this);
        scheduleScrollView.addView(scheduleLayout);

        dayScheduleLayout.addView(scheduleScrollView);
    }

    public void onClickPlusButton(View v) {
        Intent intent = new Intent(MainActivity.this,
                AddScheduleActivity.class);
        startActivity(intent);
    }

    public void onDayToDateToggle(View v) {
        if(((ToggleButton)v).isChecked()) {
            dayLayout.setButtonsToDayOfWeek();
        } else {
            dayLayout.setButtonsToDay();
        }
    }

}
