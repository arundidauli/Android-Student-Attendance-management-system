package com.techastrum.attendance.activities.model;

public class Attendance {
    private String id;
    private String student_name;
    private String student_photo;
    private String student_father_name;
    private String student_class;
    private String student_roll_no;
    private String attendance_date;
    private String attendance_time;
    private boolean isPresent;


    public String getStudent_father_name() {
        return student_father_name;
    }

    public void setStudent_father_name(String student_father_name) {
        this.student_father_name = student_father_name;
    }

    public String getStudent_photo() {
        return student_photo;
    }

    public void setStudent_photo(String student_photo) {
        this.student_photo = student_photo;
    }

    public Attendance() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_class() {
        return student_class;
    }

    public void setStudent_class(String student_class) {
        this.student_class = student_class;
    }

    public String getStudent_roll_no() {
        return student_roll_no;
    }

    public void setStudent_roll_no(String student_roll_no) {
        this.student_roll_no = student_roll_no;
    }

    public String getAttendance_date() {
        return attendance_date;
    }

    public void setAttendance_date(String attendance_date) {
        this.attendance_date = attendance_date;
    }

    public String getAttendance_time() {
        return attendance_time;
    }

    public void setAttendance_time(String attendance_time) {
        this.attendance_time = attendance_time;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }
}
