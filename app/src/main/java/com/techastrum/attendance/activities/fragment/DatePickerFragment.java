package com.techastrum.attendance.activities.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.techastrum.attendance.R;
import com.techastrum.attendance.activities.Util.Constants;
import com.techastrum.attendance.activities.Util.Prefs;
import com.techastrum.attendance.activities.student_attandence.SelectDateActivity;
import com.techastrum.attendance.activities.student_attandence.SelectTimeActivity;
import com.techastrum.attendance.activities.student_attandence.ViewAttendanceActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class DatePickerFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    DatePickerDialog datePickerDialog ;
    TimePickerDialog timePickerDialog ;
    int Year, Month, Day, Hour, Minute;
    Calendar calendar ;
    private Context context;
    private TextView txt_select;
    private TextView mdate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public DatePickerFragment(TextView mdate) {
        this.mdate = mdate;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_date, container, false);
        context = getActivity();
        txt_select=view.findViewById(R.id.txt_select);
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Hour = calendar.get(Calendar.HOUR_OF_DAY);

        SelectDate();



        view.findViewById(R.id.txt_select).setOnClickListener(v -> {
            SelectDate();
        });

        view.findViewById(R.id.fab).setOnClickListener(v -> {
            getActivity().onBackPressed();
            mdate.setText(txt_select.getText().toString());
        });

        return view;
    }

    private void Ride_Cancel() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure want to cancel ?");
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Confirm",
                (dialog, id) -> {
                    dialog.cancel();
                });
        builder.setNegativeButton(
                "Cancel",
                (dialog, id) -> dialog.cancel());

        AlertDialog alert = builder.create();
        alert.show();

    }

    private void SelectDate(){
        datePickerDialog = DatePickerDialog.newInstance(this, Year, Month, Day);

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

                Toast.makeText(context, "Datepicker Canceled", Toast.LENGTH_SHORT).show();
            }
        });

        datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");


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
        mdate.setText(sdf.format(calendar.getTime()));
    }
}
