package com.ciic.test.bean;

/**
 * Created by lixuecheng on 2017/8/9.
 */
public class Series {
    private String id;
    private String cids;
    private String status;
    private String tid;
    private String type;
    private String sttime;
    private String etime;
    private String series;
    private String ordertime;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"cids\":\"")
                .append(cids).append('\"');
        sb.append(",\"status\":\"")
                .append(status).append('\"');
        sb.append(",\"tid\":\"")
                .append(tid).append('\"');
        sb.append(",\"type\":\"")
                .append(type).append('\"');
        sb.append(",\"sttime\":\"")
                .append(sttime).append('\"');
        sb.append(",\"etime\":\"")
                .append(etime).append('\"');
        sb.append(",\"series\":\"")
                .append(series).append('\"');
        sb.append(",\"ordertime\":\"")
                .append(ordertime).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCids() {
        return cids;
    }

    public void setCids(String cids) {
        this.cids = cids;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSttime() {
        return sttime;
    }

    public void setSttime(String sttime) {
        this.sttime = sttime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }
}
