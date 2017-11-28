package com.ciic.test.bean;

/**
 * Created by lixuecheng on 2017/11/23.
 */
public class Http4j {
    private String body;
    private String header;
    private String isshow;
    private String name;
    private String num;
    private Reg    Reg;
    private Ass    Ass;
    private Base   Base;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"body\":\"")
                .append(body).append('\"');
        sb.append(",\"header\":\"")
                .append(header).append('\"');
        sb.append(",\"isshow\":\"")
                .append(isshow).append('\"');
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"num\":\"")
                .append(num).append('\"');
        sb.append(",\"reg\":")
                .append(Reg);
        sb.append(",\"ass\":")
                .append(Ass);
        sb.append(",\"base\":")
                .append(Base);
        sb.append('}');
        return sb.toString();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getIsshow() {
        return isshow;
    }

    public void setIsshow(String isshow) {
        this.isshow = isshow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Reg getReg() {
        return Reg;
    }

    public void setReg(Reg Reg) {
        this.Reg = Reg;
    }

    public Ass getAss() {
        return Ass;
    }

    public void setAss(Ass Ass) {
        this.Ass = Ass;
    }

    public Base getBase() {
        return Base;
    }

    public void setBase(Base Base) {
        this.Base = Base;
    }

}
