package com.ciic.test.bean;

/**
 * Created by lixuecheng on 2017/8/16.
 */
public class CaseresList {
    private  String id;
    private  String cid;
    private  String res;
    private  String runnum;
    private  String allnum;
    private  String cname;
    private  String cdes;
    private  String status;


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"cid\":\"")
                .append(cid).append('\"');
        sb.append(",\"res\":\"")
                .append(res).append('\"');
        sb.append(",\"runnum\":\"")
                .append(runnum).append('\"');
        sb.append(",\"allnum\":\"")
                .append(allnum).append('\"');
        sb.append(",\"cname\":\"")
                .append(cname).append('\"');
        sb.append(",\"cdes\":\"")
                .append(cdes).append('\"');
        sb.append(",\"status\":\"")
                .append(status).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getRunnum() {
        return runnum;
    }

    public void setRunnum(String runnum) {
        this.runnum = runnum;
    }

    public String getAllnum() {
        return allnum;
    }

    public void setAllnum(String allnum) {
        this.allnum = allnum;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCdes() {
        return cdes;
    }

    public void setCdes(String cdes) {
        this.cdes = cdes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
