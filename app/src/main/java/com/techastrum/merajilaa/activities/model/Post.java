package com.techastrum.merajilaa.activities.model;

public class Post {
    private String id ,user_id,post_id,post_image,post_title,post_detail,post_date,post_url,video_url,post_district,post_type,post_category;
    private boolean is_approve;

    public Post() {

    }

    public Post(String id, String user_id, String post_id, String post_image, String post_title, String post_detail, String post_date, String post_url, String video_url, String post_district, String post_type, String post_category, boolean is_approve) {
        this.id = id;
        this.user_id = user_id;
        this.post_id = post_id;
        this.post_image = post_image;
        this.post_title = post_title;
        this.post_detail = post_detail;
        this.post_date = post_date;
        this.post_url = post_url;
        this.video_url = video_url;
        this.post_district = post_district;
        this.post_type = post_type;
        this.post_category = post_category;
        this.is_approve = is_approve;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_detail() {
        return post_detail;
    }

    public void setPost_detail(String post_detail) {
        this.post_detail = post_detail;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getPost_url() {
        return post_url;
    }

    public void setPost_url(String post_url) {
        this.post_url = post_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getPost_district() {
        return post_district;
    }

    public void setPost_district(String post_district) {
        this.post_district = post_district;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public String getPost_category() {
        return post_category;
    }

    public void setPost_category(String post_category) {
        this.post_category = post_category;
    }

    public boolean isIs_approve() {
        return is_approve;
    }

    public void setIs_approve(boolean is_approve) {
        this.is_approve = is_approve;
    }
}
