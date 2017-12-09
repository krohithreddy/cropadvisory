package com.hhcl.cropadvisory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Karthik Kumar K on 21-11-2017.
 */

public class DBModel implements Serializable{
    private String no_id;
    private String star;
    private boolean isselected;
   private String tt_name,msg,url,imgtype,imgfull,payload,tstamp,deletestatus;
    private ArrayList<String> allItemsInSection;
    private String headerTitle;

    private HModel Hmodel;

    public DBModel(String ravi, String s) {
    }

    public void setHmodel(HModel hmodel) {
        Hmodel = hmodel;
    }

    public HModel getHmodel() {
        return Hmodel;
    }

    public String getCategoryId() {
        return Hmodel.getId();
    }
    public String getDate() {
        return Hmodel.getDate();
    }

    //    public DBModel(String i, String name, String messg, String imurl, String typeimg, String imgfull, String loadpay, String stampt,String star, String s1) {
//    this.no_id=i;
//    this.tt_name=name;
//    this.msg=messg;
//    this.url=imurl;
//    this.imgtype=typeimg;
//    this.imgfull=imgfull;
//    this.payload=loadpay;
//    this.tstamp=stampt;
//    this.star=star;
//    this.deletestatus=s1;
//    }

    public DBModel() {

    }

    public DBModel(String karthik, String s, String s1, String scsccac, String sasdsaa, String dsfcsf, String s2) {
        this.tt_name = karthik;
        this.msg = s;
        this.url=s1;
        this.imgtype=scsccac;
        this.imgfull=sasdsaa;
        this.payload=dsfcsf;
        this.tstamp=s2;
    }

    public DBModel(String title, String message, String imageUrl, String imagetype, String notifyid, String dsfcsf, String timestamp, String s) {
        this.tt_name = title;
        this.msg = message;
        this.url=imageUrl;
        this.imgtype=imagetype;
        this.imgfull=notifyid;
        this.payload=dsfcsf;
        this.tstamp=timestamp;
        this.star=s;
    }



    public DBModel(String i, String title, String message, String imageUrl, String imagetype, String notifyid, String dsfcsf, String timestamp, String s, String s1) {
        this.no_id=i;
        this.tt_name=title;
        this.msg=message;
        this.url=imageUrl;
        this.imgtype=imagetype;
        this.imgfull=notifyid;
        this.payload=dsfcsf;
        this.tstamp=timestamp;
        this.star=s;
        this.deletestatus=s1;
    }

    public DBModel(String title, String message, String imageUrl, String imagetype, String notifyid, String dsfcsf, String timestamp, String starstatus, String s) {
        this.tt_name = title;
        this.msg = message;
        this.url=imageUrl;
        this.imgtype=imagetype;
        this.imgfull=notifyid;
        this.payload=dsfcsf;
        this.tstamp=timestamp;
        this.star=starstatus;
        this.deletestatus=s;
    }

    public DBModel(String s) {
this.deletestatus=s;
    }


    public String getDeletestatus() {
        return deletestatus;
    }

    public void setDeletestatus(String deletestatus) {
        this.deletestatus = deletestatus;
    }

    public boolean isIsselected() {
        return isselected;
    }

    public void setIsselected(boolean isselected) {
        this.isselected = isselected;
    }

    public String getNo_id() {
        return no_id;
    }

    public void setNo_id(String no_id) {

    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
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


    public ArrayList<String> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<String> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }
}
