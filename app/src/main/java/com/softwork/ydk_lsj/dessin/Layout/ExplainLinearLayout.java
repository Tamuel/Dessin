package com.softwork.ydk_lsj.dessin.Layout;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by Seungjin on 2015-12-10.
 * 작성했던 schedule 내용을 상세히 보여주는 layout
 */
public class ExplainLinearLayout extends LinearLayout {
    TextView dateTextView;
    TextView titleTextView;

    //임시 데이터
    ArrayList<Integer> yearArrayList = new ArrayList<Integer> ();
    ArrayList<Integer> monthArrayList = new ArrayList<Integer> ();
    ArrayList<Integer> dayArrayList = new ArrayList<Integer> ();
    ArrayList<String> titleArrayList = new ArrayList<String>();

    public ExplainLinearLayout(Context context, GregorianCalendar date, Integer id, boolean check) {
        super(context);

        setData();

        LayoutParams layoutParams = new LayoutParams(
                500,
                LayoutParams.MATCH_PARENT,
                0);

        this.setLayoutParams(layoutParams);
        this.setOrientation(LinearLayout.VERTICAL);

        //check == true : 날짜를 클릭했을 시
        if(check) {
            for(int i=0; i<yearArrayList.size(); i++) {
                if(yearArrayList.get(i) == date.get(date.YEAR) && monthArrayList.get(i) == date.get(date.MONTH) &&
                        dayArrayList.get(i) == date.get(date.DATE)) {
                    dateTextView.setText(date.get(date.YEAR) + date.get(date.MONTH) + date.get(date.DATE));
                    titleTextView.setText(titleArrayList.get(i));
                    this.addView(dateTextView);
                    this.addView(titleTextView);
                }
            }
        }
        else {
            //id를 받아와서 수행
        }

        makeChangeButton();
    }

    public void makeChangeButton() {
        Log.i("수정 버튼 클릭", "changeButton");

        Button changeButton = new Button(getContext());
        LayoutParams changeParams = new LayoutParams(
                30,
                30,
                0);
        changeButton.setLayoutParams(changeParams);
        changeButton.setText("수정");

        this.addView(changeButton);
    }

    public void setData() {
        yearArrayList.add(2015);
        yearArrayList.add(2015);
        yearArrayList.add(2015);

        monthArrayList.add(10);
        monthArrayList.add(12);
        monthArrayList.add(12);

        dayArrayList.add(1);
        dayArrayList.add(18);
        dayArrayList.add(18);

        titleArrayList.add("데이터베이스 과제");
        titleArrayList.add("모바일앱프로그래밍 과제");
        titleArrayList.add("데이터통신 과제");
    }
}
