package com.techastrum.attendance.activities.model;

public class StudentClass {
    private String id,category_name;
    int image_url;

    public StudentClass(String id, String category_name, int image_url) {
        this.id = id;
        this.category_name = category_name;
        this.image_url = image_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getImage_url() {
        return image_url;
    }

    public void setImage_url(int image_url) {
        this.image_url = image_url;
    }
}
