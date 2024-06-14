package com.example.projectsem;

import com.google.firebase.events.Publisher;

public class Model {
    String tplace,tdesc,tamnt,tpic,tacti,pId,pIid,tmeals,tuser,tuname,tmobile,tnumperson,tcheckin;
    public Model(){

    }

    public Model(String tplace, String tdesc, String tamnt, String tpic, String tacti, String pId,String tmeals,String tuser,String tuname,String tmobile,String tnumperson,String tcheckin) {
        Publisher publisher;
        this.pId=pId;
        this.tuser=tuser;
        this.tmeals=tmeals;
        this.pIid=pIid;
        this.tplace = tplace;
        this.tdesc = tdesc;
        this.tamnt = tamnt;
        this.tpic = tpic;
        this.tacti = tacti;
        this.tuname=tuname;
        this.tmobile=tmobile;
        this.tnumperson=tnumperson;
        this.tcheckin=tcheckin;
       // this.amountt=amountt;
    }

    public String getTuname() {
        return tuname;
    }

    public void setTuname(String tuname) {
        this.tuname = tuname;
    }

    public String getTmobile() {
        return tmobile;
    }

    public void setTmobile(String tmobile) {
        this.tmobile = tmobile;
    }

    public String getTnumperson() {
        return tnumperson;
    }

    public void setTnumperson(String tnumperson) {
        this.tnumperson = tnumperson;
    }

    public String getTcheckin() {
        return tcheckin;
    }

    public void setTcheckin(String tcheckin) {
        this.tcheckin = tcheckin;
    }

    public String getTuser() {
        return tuser;
    }

    public void setTuser(String tuser) {
        this.tuser = tuser;
    }

    public String getTmeals() {
        return tmeals;
    }

    public void setTmeals(String tmeals) {
        this.tmeals = tmeals;
    }

    public String getpIid() {
        return pIid;
    }

    public void setpIid(String pIid) {
        this.pIid = pIid;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getTplace() {
        return tplace;
    }

    public void setTplace(String tplace) {
        this.tplace = tplace;
    }

    public String getTdesc() {
        return tdesc;
    }

    public void setTdesc(String tdesc) {
        this.tdesc = tdesc;
    }

    public String getTamnt() {
        return tamnt;
    }

    public void setTamnt(String tamnt) {
        this.tamnt = tamnt;
    }

    public String getTpic() {
        return tpic;
    }

    public void setTpic(String tpic) {
        this.tpic = tpic;
    }

    public String getTacti() {
        return tacti;
    }

    public void setTacti(String tacti) {
        this.tacti = tacti;
    }

//    public String getAmountt() {
//        return amountt;
//    }
//
//    public void setAmountt(String amountt) {
//        this.amountt = amountt;
//    }
}
