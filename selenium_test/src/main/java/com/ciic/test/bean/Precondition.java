package com.ciic.test.bean;

/**
 * Created by lixuecheng on 2017/8/7.
 */
public class Precondition {
    private String id;
    private String type;
    private String a;
    private String b;
    private String c;
    private String cid;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"type\":\"")
                .append(type).append('\"');
        sb.append(",\"a\":\"")
                .append(a).append('\"');
        sb.append(",\"b\":\"")
                .append(b).append('\"');
        sb.append(",\"c\":\"")
                .append(c).append('\"');
        sb.append(",\"cid\":\"")
                .append(cid).append('\"');
        sb.append('}');
        return sb.toString();
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

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
