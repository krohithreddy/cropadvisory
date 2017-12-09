package com.hhcl.cropadvisory;

/**
 * Created by Karthik Kumar K on 18-11-2017.
 */

public class HomeModel {
    String name;
    String version;
    int id_;
    int image;

    public HomeModel(String name, String version, int id_, int image) {

        this.name = name;
        this.version = version;
        this.id_ = id_;
        this.image=image;
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
