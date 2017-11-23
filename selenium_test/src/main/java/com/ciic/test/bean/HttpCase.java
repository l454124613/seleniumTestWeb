package com.ciic.test.bean;

/**
 * Created by lixuecheng on 2017/8/24.
 */
public class HttpCase {
    private String id;
    private String con;
    private String time;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"con\":\"")
                .append(con).append('\"');
        sb.append(",\"time\":\"")
                .append(time).append('\"');
        sb.append(",\"cid\":\"")
                .append(cid).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    private String cid;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
