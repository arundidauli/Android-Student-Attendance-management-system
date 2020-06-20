package com.techastrum.attendance.activities.model;

public class Student {
    private String id;
    private String student_name;
    private String student_class;
    private String student_roll_no;
    private String student_image;
    private String student_father_name;
    private String student_activities;
    private String student_position;

    public String getStudent_activities() {
        return student_activities;
    }

    public void setStudent_activities(String student_activities) {
        this.student_activities = student_activities;
    }

    public String getStudent_position() {
        return student_position;
    }

    public void setStudent_position(String student_position) {
        this.student_position = student_position;
    }

    public Student() {
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

    public String getStudent_image() {
        return student_image;
    }

    public void setStudent_image(String student_image) {
        this.student_image = student_image;
    }

    public String getStudent_father_name() {
        return student_father_name;
    }

    public void setStudent_father_name(String student_father_name) {
        this.student_father_name = student_father_name;
    }
}
