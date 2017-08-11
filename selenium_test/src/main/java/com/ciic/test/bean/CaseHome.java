package com.ciic.test.bean;

/**
 * Created by lixuecheng on 2017/8/11.
 */
public class CaseHome {
    private  String id;
    private  String name;
    private  String des;
    private  String cids;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"des\":\"")
                .append(des).append('\"');
        sb.append(",\"cids\":\"")
                .append(cids).append('\"');
        sb.append('}');
        return sb.toString();
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

    public String getCids() {
        return cids;
    }

    public void setCids(String cids) {
        this.cids = cids;
    }
}
