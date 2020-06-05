package com.techastrum.attendance.activities.model;

public class Category {
    private String id,image_url,category_name,url;

    public Category(String id, String image_url, String category_name, String url) {
        this.id = id;
        this.image_url = image_url;
        this.category_name = category_name;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
