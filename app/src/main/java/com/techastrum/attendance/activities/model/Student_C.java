package com.techastrum.attendance.activities.model;

public class Student_C {
    private String id;
    private String class_name;

    public Student_C() {
    }

    public Student_C(String id, String class_name) {
        this.id = id;
        this.class_name = class_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }
}
