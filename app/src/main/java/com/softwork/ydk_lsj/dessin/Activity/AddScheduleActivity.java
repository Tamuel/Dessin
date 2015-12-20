package com.softwork.ydk_lsj.dessin.Activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.softwork.ydk_lsj.dessin.DataProvider.DBProvider;
import com.softwork.ydk_lsj.dessin.DataProvider.DataProvider;
import com.softwork.ydk_lsj.dessin.R;

import java.util.ArrayList;

public class AddScheduleActivity extends Activity {
    private ArrayList<String> yearArray, monthArray, dayArray;
    private Spinner startYearSpinner, startMonthSpinner, startDaySpinner;
    private Spinner endYearSpinner, endMonthSpinner, endDaySpinner;
    private Spinner subStartYearSpinner, subStartMonthSpinner, subStartDaySpinner;
    private Spinner subEndYearSpinner, subEndMonthSpinner, subEndDaySpinner;
    private ArrayAdapter<String> yearAdapter, monthAdapter, dayAdapter;
    private String startyear, startmonth, startday;
    private String endyear, endmonth, endday;
    private String substartyear, substartmonth, substartday;
    private String subendyear, subendmonth, subendday;
    private LinearLayout subLinear; //계속해서 추가되는 세부일정들이 들어가는

    private ArrayList<LinearLayout> subScheduleLayouts;

    private EditText scheduleTitle;
    private EditText scheduleInformation;

    private ArrayList<String> subScheduleTitle;
    private ArrayList<String> subScheduleInfo;
    private ArrayList<String> subScheduleStartYear;
    private ArrayList<String> subScheduleStartMonth;
    private ArrayList<String> subScheduleStartDay;
    private ArrayList<String> subScheduleEndYear;
    private ArrayList<String> subScheduleEndMonth;
    private ArrayList<String> subScheduleEndDay;

    private enum SpinnerName {
        STARTSPINNER,
        ENDSPINNER,
        SUBSTARTSPINNER,
        SUBENDSPINNER
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        subScheduleLayouts = new ArrayList<LinearLayout>();
        subScheduleTitle = new ArrayList<String>();
        subScheduleInfo = new ArrayList<String>();

        subScheduleStartYear = new ArrayList<String>();
        subScheduleStartMonth = new ArrayList<String>();
        subScheduleStartDay = new ArrayList<String>();

        subScheduleEndYear = new ArrayList<String>();
        subScheduleEndMonth = new ArrayList<String>();
        subScheduleEndDay = new ArrayList<String>();


        scheduleTitle = (EditText)findViewById(R.id.title_edit);
        scheduleInformation = (EditText)findViewById(R.id.info_edit);

        //spinner에 들어갈 년/월/일의 arrayList 설정
        yearArray = new ArrayList<String>();
        monthArray = new ArrayList<String> ();
        dayArray = new ArrayList<String> ();
        setDateArray();

        //startSpinner 설정
        startYearSpinner = (Spinner)findViewById(R.id.start_year_spinner);
        startMonthSpinner = (Spinner)findViewById(R.id.start_month_spinner);
        startDaySpinner = (Spinner)findViewById(R.id.start_day_spinner);
        setSpinner(startYearSpinner, startMonthSpinner, startDaySpinner, SpinnerName.STARTSPINNER);

        startYearSpinner.setSelection(yearArray.indexOf(String.valueOf(DataProvider.getInstance().getSelectedYear())));
        startMonthSpinner.setSelection(monthArray.indexOf(String.valueOf(DataProvider.getInstance().getSelectedMonth())));
        startDaySpinner.setSelection(dayArray.indexOf(String.valueOf(DataProvider.getInstance().getSelectedDay())));

        //endSpinner 설정
        endYearSpinner = (Spinner)findViewById(R.id.end_year_spinner);
        endMonthSpinner = (Spinner)findViewById(R.id.end_month_spinner);
        endDaySpinner = (Spinner)findViewById(R.id.end_day_spinner);
        setSpinner(endYearSpinner, endMonthSpinner, endDaySpinner, SpinnerName.ENDSPINNER);

        endYearSpinner.setSelection(yearArray.indexOf(String.valueOf(DataProvider.getInstance().getSelectedYear())));
        endMonthSpinner.setSelection(monthArray.indexOf(String.valueOf(DataProvider.getInstance().getSelectedMonth())));
        endDaySpinner.setSelection(dayArray.indexOf(String.valueOf(DataProvider.getInstance().getSelectedDay())));

        //subStartSpinner 설정
        subStartYearSpinner = (Spinner)findViewById(R.id.sub_start_year_spinner);
        subStartMonthSpinner = (Spinner)findViewById(R.id.sub_start_month_spinner);
        subStartDaySpinner = (Spinner)findViewById(R.id.sub_start_day_spinner);
        setSpinner(subStartYearSpinner, subStartMonthSpinner, subStartDaySpinner, SpinnerName.SUBSTARTSPINNER);

        subStartYearSpinner.setSelection(yearArray.indexOf(String.valueOf(DataProvider.getInstance().getSelectedYear())));
        subStartMonthSpinner.setSelection(monthArray.indexOf(String.valueOf(DataProvider.getInstance().getSelectedMonth())));
        subStartDaySpinner.setSelection(dayArray.indexOf(String.valueOf(DataProvider.getInstance().getSelectedDay())));

        //subEndSpinner 설정
        subEndYearSpinner = (Spinner)findViewById(R.id.sub_end_year_spinner);
        subEndMonthSpinner = (Spinner)findViewById(R.id.sub_end_month_spinner);
        subEndDaySpinner = (Spinner)findViewById(R.id.sub_end_day_spinner);
        setSpinner(subEndYearSpinner, subEndMonthSpinner, subEndDaySpinner, SpinnerName.SUBENDSPINNER);

        subEndYearSpinner.setSelection(yearArray.indexOf(String.valueOf(DataProvider.getInstance().getSelectedYear())));
        subEndMonthSpinner.setSelection(monthArray.indexOf(String.valueOf(DataProvider.getInstance().getSelectedMonth())));
        subEndDaySpinner.setSelection(dayArray.indexOf(String.valueOf(DataProvider.getInstance().getSelectedDay())));

        //LinearLayout 설정
        subLinear = (LinearLayout)findViewById(R.id.sub_schedule_linear);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setDateArray() {
        yearArray.clear();
        monthArray.clear();
        dayArray.clear();

        for(int i=DataProvider.getInstance().getSelectedYear()-5; i<DataProvider.getInstance().getSelectedYear()+10; i++)
            yearArray.add(String.valueOf(i));
        for(int i=1; i<=12; i++)
            monthArray.add(String.valueOf(i));
    }

    //Spinner 설정 메소드
    public void setSpinner(Spinner yearSpinner, Spinner monthSpinner, Spinner daySpinner, final SpinnerName sname) {
        yearAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, yearArray);
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String year = parent.getItemAtPosition(position).toString();
                switch (sname) {
                    case STARTSPINNER:
                        startyear = year;
                    case ENDSPINNER:
                        endyear = year;
                    case SUBSTARTSPINNER:
                        substartyear = year;
                    case SUBENDSPINNER:
                        subendyear = year;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        monthAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, monthArray);
        monthSpinner.setAdapter(monthAdapter);
        final Spinner _daySpinner = daySpinner;
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String month = parent.getItemAtPosition(position).toString();

                switch (sname) {
                    case STARTSPINNER:
                        startmonth = month;
                    case ENDSPINNER:
                        endmonth = month;
                    case SUBSTARTSPINNER:
                        substartmonth = month;
                    case SUBENDSPINNER:
                        subendmonth = month;
                }
                //매 월마다 날짜가 다르기 때문에 월별로 설정
                dayArray.clear();
                if (month.equals("1") || month.equals( "3") || month.equals("5") || month.equals("7")
                        || month.equals("8") || month.equals("10") || month.equals("12")) {
                    for (int i = 1; i <= 31; i++)
                        dayArray.add(String.valueOf(i));
                }
                else if (month.equals("4") || month.equals("6") || month.equals("9") || month.equals("11")) {
                    for (int i = 1; i <= 30; i++)
                        dayArray.add(String.valueOf(i));
                }
                else {
                    for (int i = 1; i <= 28; i++)
                        dayArray.add(String.valueOf(i));
                }

                dayAdapter = new ArrayAdapter<String>(AddScheduleActivity.this,
                        R.layout.support_simple_spinner_dropdown_item, dayArray);
                _daySpinner.setAdapter(dayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dayAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, dayArray);
        daySpinner.setAdapter(dayAdapter);
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String day = parent.getItemAtPosition(position).toString();

                switch(sname) {
                    case STARTSPINNER:
                        startday = day;
                    case ENDSPINNER:
                        endday = day;
                    case SUBSTARTSPINNER:
                        substartday = day;
                    case SUBENDSPINNER:
                        subendday = day;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onClickSubScheduleButton(View v) {
        EditText subTitle = (EditText)findViewById(R.id.sub_title_edit);
        EditText subInfo = (EditText)findViewById(R.id.sub_info_edit);

        TextView subDate = new TextView(this);
        TextView subTitleText = new TextView(this);
        TextView subInfoText = new TextView(this);
        Button deleteButton = new Button(this);

        LinearLayout.LayoutParams layoutSubDateParams = new LinearLayout.LayoutParams(
                800,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0);
        layoutSubDateParams.gravity = Gravity.CENTER_VERTICAL;
        subDate.setLayoutParams(layoutSubDateParams);

        subTitleText.setWidth(500);
        subInfoText.setWidth(800);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                (int) getResources().getDimension(R.dimen.schedule_bar_narrow_height),
                (int) getResources().getDimension(R.dimen.schedule_bar_narrow_height),
                0);

        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        deleteButton.setLayoutParams(layoutParams);
        deleteButton.setBackgroundResource(R.drawable.cyon_simple_button);
        deleteButton.setPadding(0, 0, 0, 0);
        deleteButton.setGravity(Gravity.CENTER);
        deleteButton.setText("-");
        deleteButton.setTextColor(getResources().getColor(R.color.hotPink));

        final LinearLayout outerLinearLayout = new LinearLayout(this);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout tempView = outerLinearLayout;
                int index = subScheduleLayouts.indexOf(tempView);
                subScheduleTitle.remove(index);
                subScheduleInfo.remove(index);

                subScheduleStartYear.remove(index);
                subScheduleStartMonth.remove(index);
                subScheduleStartDay.remove(index);

                subScheduleEndYear.remove(index);
                subScheduleEndMonth.remove(index);
                subScheduleEndDay.remove(index);

                subLinear.removeView(subScheduleLayouts.get(index));
                subScheduleLayouts.remove(index);
            }
        });

        outerLinearLayout.setOrientation(LinearLayout.VERTICAL);
        outerLinearLayout.setBackgroundResource(R.drawable.underbar_layout_background);

        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) getResources().getDimension(R.dimen.schedule_bar_narrow_height) + 20,
                0);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(linearLayoutParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        subDate.setText(
                substartyear + "년 " + substartmonth + "월 " + substartday + "일 ~ " +
                        subendyear + "년 " + subendmonth + "월 " + subendday + "일"
        );
        subTitleText.setText(subTitle.getText().toString());
        subInfoText.setText(subInfo.getText().toString());

        subScheduleTitle.add(subTitle.getText().toString());
        subScheduleInfo.add(subInfo.getText().toString());

        subScheduleStartYear.add(substartyear);
        subScheduleStartMonth.add(substartmonth);
        subScheduleStartDay.add(substartday);

        subScheduleEndYear.add(subendyear);
        subScheduleEndMonth.add(subendmonth);
        subScheduleEndDay.add(subendday);

        linearLayout.addView(subDate);
        linearLayout.addView(deleteButton);
        outerLinearLayout.addView(linearLayout);
        outerLinearLayout.addView(subTitleText);
        outerLinearLayout.addView(subInfoText);

        subScheduleLayouts.add(outerLinearLayout);

        subLinear.addView(outerLinearLayout);
    }


    /**
     * When add schedule
     * to DB
     * @param v
     */
    public void onClickAddScheduleButton(View v) {
        ContentResolver cr = getContentResolver();
        ContentValues newSchedule = new ContentValues();

        /** Add new schedule */
        String startDate = startYearSpinner.getSelectedItem().toString() + "-" +
                startMonthSpinner.getSelectedItem().toString() + "-" +
                startDaySpinner.getSelectedItem().toString();
        String endDate = endYearSpinner.getSelectedItem().toString() + "-" +
                endMonthSpinner.getSelectedItem().toString() + "-" +
                endDaySpinner.getSelectedItem().toString();

        Log.i("NEW SCHEDULE : ", scheduleTitle.getText().toString() + " " + scheduleInformation.getText().toString() + " " + startDate + " " + endDate);
        newSchedule.put(DBProvider.SCHEDULE_TITLE,
                scheduleTitle.getText().toString());
        newSchedule.put(DBProvider.SCHEDULE_INFORMATION,
                scheduleInformation.getText().toString());
        newSchedule.put(DBProvider.SCHEDULE_START_DATE,
                startDate);
        newSchedule.put(DBProvider.SCHEDULE_END_DATE,
                endDate);

        Uri newScheduleUri = cr.insert(DBProvider.SCHEDULE_TABLE_CONTENT_URI, newSchedule);
        Log.i("NEW SCHEDULE URI!!!!", newScheduleUri.toString() + " " + newScheduleUri.getPathSegments().get(1));

        int newScheduleID = Integer.parseInt(newScheduleUri.getPathSegments().get(1));

        /** Add new additional schedules */
        for(int i = 0; i < subScheduleTitle.size(); i++) {
            ContentValues newSubSchedule = new ContentValues();
            String addStartDate = subScheduleStartYear.get(i) + "-" +
                    subScheduleStartMonth.get(i) + "-" +
                    subScheduleStartDay.get(i);
            String addEndDate = subScheduleEndYear.get(i) + "-" +
                    subScheduleEndMonth.get(i) + "-" +
                    subScheduleEndDay.get(i);

            Log.i("NEW ADD SCHEDULE : ", subScheduleTitle.get(i) + " " + subScheduleInfo.get(i) + " " + addStartDate + " " + addEndDate);
            newSubSchedule.put(DBProvider.ADDITIONAL_SCHEDULE_TITLE,
                    subScheduleTitle.get(i));
            newSubSchedule.put(DBProvider.ADDITIONAL_SCHEDULE_INFORMATION,
                    subScheduleInfo.get(i));
            newSubSchedule.put(DBProvider.ADDITIONAL_SCHEDULE_START_DATE,
                    addStartDate);
            newSubSchedule.put(DBProvider.ADDITIONAL_SCHEDULE_END_DATE,
                    addEndDate);
            newSubSchedule.put(DBProvider.SCHEDULE_TABLE_FK,
                    newScheduleID);

            Uri newAddScheduleUri = cr.insert(DBProvider.ADDITIONAL_SCHEDULE_TABLE_CONTENT_URI, newSubSchedule);
        }

        finish();
    }
}