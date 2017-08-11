package com.ciic.test.bean;

/**
 * Created by lixuecheng on 2017/7/4.
 */
public class user {
    private  String name;
    private  String email;
    private  String password;
    private  String other;
    private  String isused;
    private  String lastlogintime;
    private String id;
    private String ismanager;

    public String getIsmanager() {
        return ismanager;
    }

    public void setIsmanager(String ismanager) {
        this.ismanager = ismanager;
    }

    public String getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(String lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"email\":\"")
                .append(email).append('\"');
        sb.append(",\"password\":\"")
                .append(password).append('\"');
        sb.append(",\"other\":\"")
                .append(other).append('\"');
        sb.append(",\"isused\":\"")
                .append(isused).append('\"');
        sb.append(",\"lastlogintime\":\"")
                .append(lastlogintime).append('\"');
        sb.append(",\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"ismanager\":\"")
                .append(ismanager).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getIsused() {
        return isused;
    }

    public void setIsused(String isused) {
        this.isused = isused;
    }
}
