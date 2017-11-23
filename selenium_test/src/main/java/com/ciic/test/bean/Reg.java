package com.ciic.test.bean;

public class Reg {
    private String def;
    private String name;
    private String num;
    private String reg;
    private KeyValue path;

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
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

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public KeyValue getPath() {
        return path;
    }

    public void setPath(KeyValue path) {
        this.path = path;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"def\":\"")
                .append(def).append('\"');
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"num\":\"")
                .append(num).append('\"');
        sb.append(",\"Reg\":\"")
                .append(reg).append('\"');
        sb.append(",\"path\":")
                .append(path);
        sb.append('}');
        return sb.toString();
    }
}
