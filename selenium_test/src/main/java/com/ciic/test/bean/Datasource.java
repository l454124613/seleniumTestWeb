package com.ciic.test.bean;

/**
 * Created by lixuecheng on 2017/8/1.
 */
public class Datasource {
    private String id;
    private String name;
    private String des;
    private String type;
    private String link;
    private String tid;
    private String user;
    private String pass;
    private String dataname;

    public String getDataname() {
        return dataname;
    }

    public void setDataname(String dataname) {
        this.dataname = dataname;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"des\":\"")
                .append(des).append('\"');
        sb.append(",\"type\":\"")
                .append(type).append('\"');
        sb.append(",\"link\":\"")
                .append(link).append('\"');
        sb.append(",\"tid\":\"")
                .append(tid).append('\"');
        sb.append(",\"user\":\"")
                .append(user).append('\"');
        sb.append(",\"dataname\":\"")
                .append(dataname).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
}
