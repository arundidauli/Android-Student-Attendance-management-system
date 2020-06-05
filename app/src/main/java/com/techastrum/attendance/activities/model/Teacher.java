package com.techastrum.attendance.activities.model;

public class Teacher {
    private String id;
    private String teacher_name;
    private String teacher_class;
    private String teacher_photo;
    private String teacher_id;
    private String teacher_pass;


    public Teacher() {
    }


    public Teacher(String id, String teacher_name, String teacher_class, String teacher_id, String teacher_pass) {
        this.id = id;
        this.teacher_name = teacher_name;
        this.teacher_class = teacher_class;
        this.teacher_id = teacher_id;
        this.teacher_pass = teacher_pass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getTeacher_class() {
        return teacher_class;
    }

    public void setTeacher_class(String teacher_class) {
        this.teacher_class = teacher_class;
    }

    public String getTeacher_photo() {
        return teacher_photo;
    }

    public void setTeacher_photo(String teacher_photo) {
        this.teacher_photo = teacher_photo;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getTeacher_pass() {
        return teacher_pass;
    }

    public void setTeacher_pass(String teacher_pass) {
        this.teacher_pass = teacher_pass;
    }
}
