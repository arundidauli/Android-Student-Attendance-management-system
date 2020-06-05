package com.techastrum.attendance.activities.model;

public class Gallery {
    private String id;
    private String image;
    private String url;

    public Gallery() {

    }

    public Gallery(String id, String image, String url) {
        this.id = id;
        this.image = image;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
