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
    private Spinner yearSpinner, monthSpinner, daySpinner;
    private ArrayAdapter<String> yearAdapter, monthAdapter, dayAdapter;
    private String year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        //spinner에 들어갈 년/월/일의 arrayList 설정
        yearArray = new ArrayList<String>();
        monthArray = new ArrayList<String> ();
        dayArray = new ArrayList<String> ();
        setDateArray();

        //spinner 설정
        yearSpinner = (Spinner)findViewById(R.id.year_spinner);
        monthSpinner = (Spinner)findViewById(R.id.month_spinner);
        daySpinner = (Spinner)findViewById(R.id.day_spinner);

        yearAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, yearArray);
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = parent.getItemAtPosition(position).toString();
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
                month = parent.getItemAtPosition(position).toString();
                //매 월마다 날짜가 다르기 때문에 월별로 설정
                dayArray.clear();
                if (month.equals("1") || month.equals( "3") || month.equals("5") || month.equals("7")
                        || month.equals("8") || month.equals("10") || month.equals("12"))
                    for (int i = 0; i < 31; i++)
                        dayArray.add(String.valueOf(i + 1));
                else if (month.equals("4") || month.equals("6") || month.equals("9") || month.equals("11"))
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
                day = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
}