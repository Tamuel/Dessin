package com.softwork.ydk_lsj.dessin;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softwork.ydk_lsj.dessin.DataProvider.DataProvider;

public class MainActivity extends Activity {
    private LinearLayout calendarDataLayout;
    private DayLinearLayout  dayLayout;
    private LinearLayout scheduleLayout;

    private TextView yearMonthTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarDataLayout = (LinearLayout)findViewById(R.id.calendar_data_layout);
        yearMonthTextView = (TextView)findViewById(R.id.year_month_text_view);

        yearMonthTextView.setText(
            DataProvider.getInstance().getYear() +
            "." +
            DataProvider.getInstance().getMonth()
        );

        dayLayout = new DayLinearLayout(this);
        calendarDataLayout.addView(dayLayout);
    }

}
