package com.techastrum.attendance.activities.student_attandence;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.TimePicker;

import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.Util.Constants;
import com.techastrum.attendance.activities.Util.RangeTimePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SelectTimeActivity extends AppCompatActivity {
    private static String TAG = SelectTimeActivity.class.getSimpleName();
    private String m_Time;
    private Context context;
    private TextView txt_select;
    private String M_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_time);
        context=SelectTimeActivity.this;
        txt_select=findViewById(R.id.txt_select);


        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String CurrentDate = df.format(c);


        if (getIntent()!=null){
            M_date=getIntent().getStringExtra(Constants.DATE);
        }
        if (CurrentDate.equals(M_date)){
            MSelectTime();
            findViewById(R.id.txt_select).setOnClickListener(v -> {
                MSelectTime();
            });
        }else {
            MSelectTime1();
            findViewById(R.id.txt_select).setOnClickListener(v -> {
                MSelectTime1();
            });
        }

        findViewById(R.id.fab).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TakeAttendanceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constants.DATE,M_date);
            intent.putExtra(Constants.TIME,txt_select.getText().toString());
            startActivity(intent);
        });
    }

    private void MSelectTime() {
        // Constant.showTime(context, mtime);
        final Calendar mcurrentTime = Calendar.getInstance();
        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute = mcurrentTime.get(Calendar.MINUTE);
        final RangeTimePickerDialog mTimePicker;
        mTimePicker = new RangeTimePickerDialog(SelectTimeActivity.this, new android.app.TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String rtime = selectedHour+":"+selectedMinute+":00";
                DateFormat f1 = new SimpleDateFormat("HH:mm:ss"); //HH for hour of the day (0 - 23)
                Date d = null;
                try {
                    d = f1.parse(rtime);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DateFormat f2 = new SimpleDateFormat("hh:mm a");
                Log.e(TAG, selectedHour+":"+selectedMinute);
                Log.e(TAG, f2.format(d));
                txt_select.setText(f2.format(d));
                m_Time=(f2.format(d));

            }
        }, hour, minute, false);//true = 24 hour time
        mTimePicker.setMin(hour, minute);
        mTimePicker.show();
    }
    private void MSelectTime1() {
        // Constant.showTime(context, mtime);
        final Calendar mcurrentTime = Calendar.getInstance();
        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute = mcurrentTime.get(Calendar.MINUTE);
        final RangeTimePickerDialog mTimePicker;
        mTimePicker = new RangeTimePickerDialog(SelectTimeActivity.this, new android.app.TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String rtime = selectedHour+":"+selectedMinute+":00";
                DateFormat f1 = new SimpleDateFormat("HH:mm:ss"); //HH for hour of the day (0 - 23)
                Date d = null;
                try {
                    d = f1.parse(rtime);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DateFormat f2 = new SimpleDateFormat("hh:mm a");
                Log.e(TAG, selectedHour+":"+selectedMinute);
                Log.e(TAG, f2.format(d));
                txt_select.setText(f2.format(d));
                m_Time=(f2.format(d));

            }
        }, hour, minute, false);//true = 24 hour time
        //mTimePicker.setMin(hour, minute);
        mTimePicker.show();
    }
}