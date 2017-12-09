package com.hhcl.cropadvisory;

/**
 * Created by Karthik Kumar K on 28-11-2017.
 */

public class DELModel {
    private String no_id;
    private String star;
    private boolean isselected;
    private String tt_name,msg,url,imgtype,imgfull,payload,tstamp;

    public DELModel(String title, String message, String imageUrl, String imagetype, String notifyid, String dsfcsf, String timestamp, String s) {
        this.tt_name = title;
        this.msg = message;
        this.url=imageUrl;
        this.imgtype=imagetype;
        this.imgfull=notifyid;
        this.payload=dsfcsf;
        this.tstamp=timestamp;
        this.star=s;
    }

    public DELModel() {

    }

    public String getNo_id() {
        return no_id;
    }

    public void setNo_id(String no_id) {
        this.no_id = no_id;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public boolean isIsselected() {
        return isselected;
    }

    public void setIsselected(boolean isselected) {
        this.isselected = isselected;
    }

    public String getTt_name() {
        return tt_name;
    }

    public void setTt_name(String tt_name) {
        this.tt_name = tt_name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgtype() {
        return imgtype;
    }

    public void setImgtype(String imgtype) {
        this.imgtype = imgtype;
    }

    public String getImgfull() {
        return imgfull;
    }

    public void setImgfull(String imgfull) {
        this.imgfull = imgfull;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getTstamp() {
        return tstamp;
    }

    public void setTstamp(String tstamp) {
        this.tstamp = tstamp;
    }
}
