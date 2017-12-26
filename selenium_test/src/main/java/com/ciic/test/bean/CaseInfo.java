package com.ciic.test.bean;

/**
 * Created by lixuecheng on 2017/7/21.
 */
public class CaseInfo {
    private String id;
    private String name;
    private String des;
    private  String important;
    private  String status;

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
        sb.append(",\"status\":\"")
                .append(status).append('\"');
        sb.append(",\"cvid\":\"")
                .append(cvid).append('\"');
        sb.append(",\"type\":\"")
                .append(type).append('\"');
        sb.append(",\"label\":\"")
                .append(label).append('\"');
        sb.append(",\"vid\":\"")
                .append(vid).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public String getCvid() {
        return cvid;
    }

    public void setCvid(String cvid) {
        this.cvid = cvid;
    }

    private String cvid;


    private String type;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String label;


    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    private String vid;


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
