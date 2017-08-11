package com.ciic.test.bean;

/**
 * Created by lixuecheng on 2017/8/3.
 */
public class Expected {
    private String sid;
    private String type;
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"sid\":\"")
                .append(sid).append('\"');
        sb.append(",\"type\":\"")
                .append(type).append('\"');
        sb.append(",\"a\":\"")
                .append(a).append('\"');
        sb.append(",\"b\":\"")
                .append(b).append('\"');
        sb.append(",\"c\":\"")
                .append(c).append('\"');
        sb.append(",\"d\":\"")
                .append(d).append('\"');
        sb.append(",\"e\":\"")
                .append(e).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }
}
