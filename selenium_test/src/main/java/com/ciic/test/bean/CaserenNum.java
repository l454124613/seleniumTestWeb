package com.ciic.test.bean;

/**
 * Created by lixuecheng on 2017/8/17.
 */
public class CaserenNum {
    private String cid ;
    private String res ;
    private String num ;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"cid\":\"")
                .append(cid).append('\"');
        sb.append(",\"res\":\"")
                .append(res).append('\"');
        sb.append(",\"num\":\"")
                .append(num).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
