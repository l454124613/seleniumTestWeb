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
    private String type;
    private String label;
    private String ispass;

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
        sb.append(",\"type\":\"")
                .append(type).append('\"');
        sb.append(",\"label\":\"")
                .append(label).append('\"');
        sb.append(",\"ispass\":\"")
                .append(ispass).append('\"');
        sb.append(",\"vid\":\"")
                .append(vid).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    private String vid;

    public String getIspass() {
        return ispass;
    }

    public void setIspass(String ispass) {
        this.ispass = ispass;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
