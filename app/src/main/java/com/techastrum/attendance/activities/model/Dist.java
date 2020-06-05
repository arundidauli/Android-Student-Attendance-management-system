package com.techastrum.attendance.activities.model;

public class Dist {
    private String id;
    private String dist_name;

    public Dist(String id, String dist_name) {
        this.id = id;
        this.dist_name = dist_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDist_name() {
        return dist_name;
    }

    public void setDist_name(String dist_name) {
        this.dist_name = dist_name;
    }
}
