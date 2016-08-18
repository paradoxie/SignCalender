package com.test.signcalender;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.test.sign_calender.DPCManager;
import com.test.sign_calender.DPDecor;
import com.test.sign_calender.DatePicker;
import com.test.sign_calender.DatePicker2;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myCalendar();
    }

    private void myCalendar() {
//        gson = new Gson();

//        List<String> tmp = gson.fromJson(SignActivity.sign_time, new TypeToken<List<String>>() {
//        }.getType());
        List<String> tmp = new ArrayList<>();
        tmp.add("2016-8-2");
        tmp.add("2016-8-4");
        tmp.add("2016-08-21");
        tmp.add("2016-08-09");
        DPCManager.getInstance().setDecorBG(tmp);

        DatePicker2 picker = (DatePicker2) findViewById(R.id.main_dp);

        picker.setFestivalDisplay(true); //是否显示节日
        picker.setHolidayDisplay(true); //是否显示假期
        picker.setDeferredDisplay(true); //是否显示补休

        Calendar c = Calendar.getInstance();//首先要获取日历对象
        int mYear = c.get(Calendar.YEAR); // 获取当前年份
        final int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        picker.setDate(mYear, mMonth);
        picker.setDPDecor(new DPDecor() {
            @Override
            public void drawDecorBG(Canvas canvas, Rect rect, Paint paint) {
                paint.setColor(Color.RED);
                //                paint.setStyle(Paint.Style.STROKE);
                //                paint.setTextAlign(Paint.Align.CENTER);
                //                paint.setTextSize(16);
                paint.setAntiAlias(true);
                InputStream is = getResources().openRawResource(R.mipmap.ic_launcher);
                Bitmap mBitmap = BitmapFactory.decodeStream(is);
                canvas.drawBitmap(mBitmap, rect.centerX() - mBitmap.getWidth() / 2f, rect.centerY() - mBitmap.getHeight() / 2f, paint);
            }
        });
        picker.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(List<String> date) {
                String result = "";
                Iterator iterator = date.iterator();
                while (iterator.hasNext()) {
                    result += iterator.next();
                    if (iterator.hasNext()) {
                        result += "\n";
                    }
                }
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
            }
        });

    }
}
