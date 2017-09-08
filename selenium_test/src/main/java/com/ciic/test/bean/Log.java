package com.ciic.test.bean;

/**
 * Created by lixuecheng on 2017/9/8.
 */
public class Log {
    private String id;
    private String user;
    private String name;
    private String log;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"user\":\"")
                .append(user).append('\"');
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"log\":\"")
                .append(log).append('\"');
        sb.append(",\"status\":\"")
                .append(status).append('\"');
        sb.append(",\"time\":\"")
                .append(time).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;
}
