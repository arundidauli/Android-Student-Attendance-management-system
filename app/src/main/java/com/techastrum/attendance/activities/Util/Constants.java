package com.techastrum.attendance.activities.Util;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Constants {
    public static int DELAY_TIME=2000;
    public static int SPLASH_TIME=3000;
    public static String Full_Name="Full_Name";
    public static String Email="Email";
    public static String Profile_Pic="pic";
    public static String Phone_No="Phone_No";
    public static String Password="Password";
    public static String Please_wait="Please wait...";
    public static String IsLogin="is_user_login";
    public static final int REQUEST_IMAGE = 100;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 101;
    public static String User_Id = "user_id";
    public static String Post_Id = "post_id";
    public static String Post_Image = "post_image";
    public static String Post_Title = "post_title";
    public static String Post_details = "post_details";
    public static String Post_date = "post_date";
    public static String Post_category = "post_category";
    public static String Post_location = "post_district";
    public static String Post_contact = "contact";
    public static String Post_view = "views";
    public static String Dist = "dist";
    public static String CODE = "code";
    public static String isAdmin = "false";
    public static String IsTeacher = "true";
    public static String DATE = "date";
    public static String TIME = "time";

    public static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static void showTime(final Context context, final TextView textView) {

        final Calendar myCalendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String am_pm = "";
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                if (myCalendar.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = "AM";
                else if (myCalendar.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = "PM";
                String strHrsToShow = (myCalendar.get(Calendar.HOUR) == 0) ? "12" : myCalendar.get(Calendar.HOUR) + "";
                //UIHelper.showLongToastInCenter(context, strHrsToShow + ":" + myCalendar.get(Calendar.MINUTE) + " " + am_pm);
                textView.setText(strHrsToShow + ":" + myCalendar.get(Calendar.MINUTE) + " " + am_pm);
            }
        };
        new TimePickerDialog(context, mTimeSetListener, myCalendar.get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE), false).show();
    }

    public static void showDate(final Context context, final TextView textView) {
        Calendar myCalendar = Calendar.getInstance();
        myCalendar.add(Calendar.DATE, 1);
        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                // view.setMinDate(newDate.getTime()-(newDate.getTime()%(24*60*60*1000)));
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
                textView.setText(sdf.format(myCalendar.getTime()));

            }

        };
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context, datePickerListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}
