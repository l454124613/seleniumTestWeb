package com.ciic.test.bean;

/**
 * Created by lixuecheng on 2017/7/25.
 */
public class Step {
    private String id;
    private String type;
    private String catid;
    private String catname;
    private String value;
    private String eid;
    private String ename;
    private String expid;
    private String step;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"type\":\"")
                .append(type).append('\"');
        sb.append(",\"catid\":\"")
                .append(catid).append('\"');
        sb.append(",\"catname\":\"")
                .append(catname).append('\"');
        sb.append(",\"value\":\"")
                .append(value).append('\"');
        sb.append(",\"eid\":\"")
                .append(eid).append('\"');
        sb.append(",\"ename\":\"")
                .append(ename).append('\"');
        sb.append(",\"expid\":\"")
                .append(expid).append('\"');
        sb.append(",\"step\":\"")
                .append(step).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getExpid() {
        return expid;
    }

    public void setExpid(String expid) {
        this.expid = expid;
    }
}
