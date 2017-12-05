package com.ciic.test.bean;

import java.util.ArrayList;

public  class Ass {
    private String pa;
    private KeyValue path;
    private KeyValue type;
    private boolean isfan;
    private boolean isigst;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"pa\":\"")
                .append(pa).append('\"');
        sb.append(",\"path\":")
                .append(path);
        sb.append(",\"type\":")
                .append(type);
        sb.append(",\"isfan\":")
                .append(isfan);
        sb.append(",\"isigst\":")
                .append(isigst);
        sb.append('}');
        return sb.toString();
    }

    public String getPa() {

        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public KeyValue getPath() {
        return path;
    }

    public void setPath(KeyValue path) {
        this.path = path;
    }

    public KeyValue getType() {
        return type;
    }

    public void setType(KeyValue type) {
        this.type = type;
    }

    public boolean isIsfan() {
        return isfan;
    }

    public void setIsfan(boolean isfan) {
        this.isfan = isfan;
    }

    public boolean isIsigst() {
        return isigst;
    }

    public void setIsigst(boolean isigst) {
        this.isigst = isigst;
    }



}
