package com.ciic.test.bean;

/**
 * Created by lixuecheng on 2017/8/8.
 */
public class Caseres {
    private  String cid;
    private  String sid;
    private  String pic;
    private  String word;
    private  String res;
    private  String restext;
    private  String time;
    private  String listid;


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"cid\":\"")
                .append(cid).append('\"');
        sb.append(",\"sid\":\"")
                .append(sid).append('\"');
        sb.append(",\"pic\":\"")
                .append(pic).append('\"');
        sb.append(",\"word\":\"")
                .append(word).append('\"');
        sb.append(",\"res\":\"")
                .append(res).append('\"');
        sb.append(",\"restext\":\"")
                .append(restext).append('\"');
        sb.append(",\"time\":\"")
                .append(time).append('\"');
        sb.append(",\"listid\":\"")
                .append(listid).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getRestext() {
        return restext;
    }

    public void setRestext(String restext) {
        this.restext = restext;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getListid() {
        return listid;
    }

    public void setListid(String listid) {
        this.listid = listid;
    }
}
