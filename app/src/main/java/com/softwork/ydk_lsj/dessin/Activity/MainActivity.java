package com.softwork.ydk_lsj.dessin.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.softwork.ydk_lsj.dessin.DataProvider.DBProvider;
import com.softwork.ydk_lsj.dessin.DataProvider.DataProvider;
import com.softwork.ydk_lsj.dessin.Layout.DayLinearLayout;
import com.softwork.ydk_lsj.dessin.Layout.ScheduleLinearLayout;
import com.softwork.ydk_lsj.dessin.R;

public class MainActivity extends Activity {
    private LinearLayout dayScheduleLayout;

    private ScrollView scheduleScrollView;
    private HorizontalScrollView outerScheduleScrollView;

    private DayLinearLayout dayLayout;
    private ScheduleLinearLayout scheduleLayout;

    private Spinner yearSpinner;
    private Spinner monthSpinner;

    private Button addScheduleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addScheduleButton = (Button)findViewById(R.id.add_new_schedule_button);
        addScheduleButton.setPadding(0, 0, 0, 0);

        String[] yearArr = new String[15];
        for(int i = 0; i < yearArr.length; i++) {
            yearArr[i] = (DataProvider.getInstance().getCurrentYear() + i - 5) + "";
        }

        String[] monthArr = new String[12];
        for(int i = 0; i < monthArr.length; i++) {
            monthArr[i] = (i + 1) + "";
        }

        yearSpinner = (Spinner)findViewById(R.id.year_spinner);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(
                this,
                R.layout.year_spinner_dropdown_item,
                yearArr);
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DataProvider.getInstance().setSelectedYear(
                        DataProvider.getInstance().getCurrentYear() + position - 5
                );
                repaintSchedule();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        yearSpinner.setSelection(5);

        monthSpinner = (Spinner)findViewById(R.id.month_spinner);
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(
                this,
                R.layout.month_spinner_dropdown_item,
                monthArr);
        monthSpinner.setAdapter(monthAdapter);
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DataProvider.getInstance().setSelectedMonth(position + 1);
                repaintSchedule();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        monthSpinner.setSelection(DataProvider.getInstance().getCurrentMonth() - 1);

        dayScheduleLayout = (LinearLayout)findViewById(R.id.day_schedule_linear_layout);

        dayLayout = new DayLinearLayout(this);
        dayScheduleLayout.addView(dayLayout);

        outerScheduleScrollView = (HorizontalScrollView)findViewById(R.id.schedule_outer_scroll_view);
        outerScheduleScrollView.post(new Runnable() {
            public void run() {
                int border = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15f, getResources().getDisplayMetrics());
                outerScheduleScrollView.scrollTo((DataProvider.getInstance().getCurrentDay() - 5) * ((int) getResources().getDimension(R.dimen.day_button_size) + border * 2), 0);
            }
        });

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

    @Override
    protected void onResume() {
        super.onResume();
        repaintSchedule();
    }

    public void repaintSchedule() {
        dayScheduleLayout.removeAllViews();
        scheduleScrollView.removeAllViews();
        dayLayout.removeAllViews();
        scheduleLayout = new ScheduleLinearLayout(this);
        dayLayout = new DayLinearLayout(this);
        dayScheduleLayout.addView(dayLayout);
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
