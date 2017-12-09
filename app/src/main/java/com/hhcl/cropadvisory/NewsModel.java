package com.hhcl.cropadvisory;

/**
 * Created by Karthik Kumar K on 17-11-2017.
 */

class NewsModel {
    String name;
    String date;
    String version;
    int id_;
    int image;

    public NewsModel(String name, String version, int id_, int image, String date) {
        this.name = name;
        this.version = version;
        this.id_ = id_;
        this.date=date;
        this.image=image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getId_() {
        return id_;
    }

    public void setId_(int id_) {
        this.id_ = id_;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
