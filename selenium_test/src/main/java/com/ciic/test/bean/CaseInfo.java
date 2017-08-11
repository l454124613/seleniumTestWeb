package com.ciic.test.bean;

/**
 * Created by lixuecheng on 2017/7/21.
 */
public class CaseInfo {
    private String id;
    private String name;
    private String des;
    private  String important;
    private String tid;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"des\":\"")
                .append(des).append('\"');
        sb.append(",\"important\":\"")
                .append(important).append('\"');
        sb.append(",\"tid\":\"")
                .append(tid).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
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

    public String getImportant() {
        return important;
    }

    public void setImportant(String important) {
        this.important = important;
    }

}
