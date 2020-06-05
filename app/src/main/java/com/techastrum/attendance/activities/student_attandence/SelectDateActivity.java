package com.techastrum.attendance.activities.student_attandence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.Util.Constants;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SelectDateActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    DatePickerDialog datePickerDialog ;
    TimePickerDialog timePickerDialog ;
    int Year, Month, Day, Hour, Minute;
    Calendar calendar ;
    private Context context;
    private TextView txt_select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);
        context = SelectDateActivity.this;
        txt_select=findViewById(R.id.txt_select);
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Hour = calendar.get(Calendar.HOUR_OF_DAY);
        findViewById(R.id.back_btn).setOnClickListener(v -> {
            super.onBackPressed();
        });

        SelectDate();



        findViewById(R.id.txt_select).setOnClickListener(v -> {
            SelectDate();
        });

        findViewById(R.id.fab).setOnClickListener(v -> {
            if (getIntent()!=null){
                if (getIntent().getStringExtra("view_attendance").equals("yes")){
                    Intent intent = new Intent(getApplicationContext(), ViewAttendanceActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Constants.DATE,txt_select.getText().toString());
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getApplicationContext(), SelectTimeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Constants.DATE,txt_select.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }

    private void SelectDate(){
        datePickerDialog = DatePickerDialog.newInstance(SelectDateActivity.this, Year, Month, Day);

        datePickerDialog.setThemeDark(false);
        datePickerDialog.showYearPickerFirst(false);
        datePickerDialog.setTitle("Date Picker");
        // Setting Min Date to today date
        Calendar min_date_c = Calendar.getInstance();
        datePickerDialog.setMinDate(min_date_c);
        // Setting Max Date to next 2 years
               /* Calendar max_date_c = Calendar.getInstance();
                max_date_c.set(Calendar.YEAR, Year);
                datePickerDialog.setMaxDate(max_date_c);*/



               /* //Disable all SUNDAYS and SATURDAYS between Min and Max Dates
                for (Calendar loopdate = min_date_c; min_date_c.before(max_date_c); min_date_c.add(Calendar.DATE, 1), loopdate = min_date_c) {
                    int dayOfWeek = loopdate.get(Calendar.DAY_OF_WEEK);
                    if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
                        Calendar[] disabledDays =  new Calendar[1];
                        disabledDays[0] = loopdate;
                        datePickerDialog.setDisabledDays(disabledDays);
                    }
                }*/



        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialogInterface) {

                Toast.makeText(SelectDateActivity.this, "Datepicker Canceled", Toast.LENGTH_SHORT).show();
            }
        });

        datePickerDialog.show(getFragmentManager(), "DatePickerDialog");


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = Day+"/"+(Month+1)+"/"+Year;
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        txt_select.setText(sdf.format(calendar.getTime()));
    }
}