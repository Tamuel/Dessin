package com.softwork.ydk_lsj.dessin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.softwork.ydk_lsj.dessin.DataProvider.DataProvider;

import java.util.ArrayList;

public class AddScheduleActivity extends AppCompatActivity {
    private ArrayList<String> yearArray, monthArray, dayArray;
    private Spinner startYearSpinner, startMonthSpinner, startDaySpinner;
    private Spinner endYearSpinner, endMonthSpinner, endDaySpinner;
    private Spinner addYearSpinner, addMonthSpinner, addDaySpinner;
    private ArrayAdapter<String> yearAdapter, monthAdapter, dayAdapter;
    private String startyear, startmonth, startday;
    private String endyear, endmonth, endday

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        //spinner에 들어갈 년/월/일의 arrayList 설정
        yearArray = new ArrayList<String>();
        monthArray = new ArrayList<String> ();
        dayArray = new ArrayList<String> ();
        setDateArray();

        //startSpinner 설정
        startYearSpinner = (Spinner)findViewById(R.id.start_year_spinner);
        startMonthSpinner = (Spinner)findViewById(R.id.start_month_spinner);
        startDaySpinner = (Spinner)findViewById(R.id.start_day_spinner);
        setSpinner(startYearSpinner, startMonthSpinner, startDaySpinner, startyear, startmonth, startday);

        //endSpinner 설정
        endYearSpinner = (Spinner)findViewById(R.id.end_year_spinner);
        endMonthSpinner = (Spinner)findViewById(R.id.end_month_spinner);
        endDaySpinner = (Spinner)findViewById(R.id.end_day_spinner);
        setSpinner(endYearSpinner, endMonthSpinner, endDaySpinner, endyear, endmonth, endday);


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
        for(int i=DataProvider.getInstance().getYear()-5; i<DataProvider.getInstance().getYear()+10; i++)
            yearArray.add(String.valueOf(i));
        for(int i=1; i<=12; i++)
            monthArray.add(String.valueOf(i));
    }

    //Spinner 설정 메소드
    public void setSpinner(Spinner yearSpinner, Spinner monthSpinner, Spinner daySpinner) {
        yearAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, yearArray);
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startyear = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        monthAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, monthArray);
        monthSpinner.setAdapter(monthAdapter);
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startmonth = parent.getItemAtPosition(position).toString();
                //매 월마다 날짜가 다르기 때문에 월별로 설정
                dayArray.clear();
                if (startmonth.equals("1") || startmonth.equals( "3") || startmonth.equals("5") || startmonth.equals("7")
                        || startmonth.equals("8") || startmonth.equals("10") || startmonth.equals("12"))
                    for (int i = 0; i < 31; i++)
                        dayArray.add(String.valueOf(i + 1));
                else if (startmonth.equals("4") || startmonth.equals("6") || startmonth.equals("9") || startmonth.equals("11"))
                    for (int i = 0; i < 30; i++)
                        dayArray.add(String.valueOf(i + 1));
                else
                    for (int i = 0; i < 28; i++)
                        dayArray.add(String.valueOf(i + 1));
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
                startday = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}