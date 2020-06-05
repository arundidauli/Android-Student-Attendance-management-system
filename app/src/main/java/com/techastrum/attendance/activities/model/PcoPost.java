package com.techastrum.attendance.activities.model;

public class PcoPost {
    private String post_id;
    private String image_url;
    private String post_title;
    private String pst_details;
    private String post_location;
    private String post_category;
    private String post_date;
    private String views;
    private String Post_type;
    private String Video_Url;
    private String District_fid;
    private String category_fid;
    private String contact;
    private String post_others;
    private String Is_approved;

    public PcoPost(String post_id, String image_url, String post_title, String pst_details, String post_location, String post_category, String post_date, String views, String post_type, String video_Url, String district_fid, String category_fid, String contact, String post_others, String is_approved) {
        this.post_id = post_id;
        this.image_url = image_url;
        this.post_title = post_title;
        this.pst_details = pst_details;
        this.post_location = post_location;
        this.post_category = post_category;
        this.post_date = post_date;
        this.views = views;
        Post_type = post_type;
        Video_Url = video_Url;
        District_fid = district_fid;
        this.category_fid = category_fid;
        this.contact = contact;
        this.post_others = post_others;
        Is_approved = is_approved;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPst_details() {
        return pst_details;
    }

    public void setPst_details(String pst_details) {
        this.pst_details = pst_details;
    }

    public String getPost_location() {
        return post_location;
    }

    public void setPost_location(String post_location) {
        this.post_location = post_location;
    }

    public String getPost_category() {
        return post_category;
    }

    public void setPost_category(String post_category) {
        this.post_category = post_category;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getPost_type() {
        return Post_type;
    }

    public void setPost_type(String post_type) {
        Post_type = post_type;
    }

    public String getVideo_Url() {
        return Video_Url;
    }

    public void setVideo_Url(String video_Url) {
        Video_Url = video_Url;
    }

    public String getDistrict_fid() {
        return District_fid;
    }

    public void setDistrict_fid(String district_fid) {
        District_fid = district_fid;
    }

    public String getCategory_fid() {
        return category_fid;
    }

    public void setCategory_fid(String category_fid) {
        this.category_fid = category_fid;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPost_others() {
        return post_others;
    }

    public void setPost_others(String post_others) {
        this.post_others = post_others;
    }

    public String getIs_approved() {
        return Is_approved;
    }

    public void setIs_approved(String is_approved) {
        Is_approved = is_approved;
    }
}
