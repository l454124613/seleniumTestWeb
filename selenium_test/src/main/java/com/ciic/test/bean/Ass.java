package com.ciic.test.bean;

public  class Ass {
    private String pa;
    private KeyValue path;
    private KeyValue type;
    private boolean isfan;
    private boolean isigst;
    private boolean isor;

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

    public boolean isIsor() {
        return isor;
    }

    public void setIsor(boolean isor) {
        this.isor = isor;
    }

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
        sb.append(",\"isor\":")
                .append(isor);
        sb.append('}');
        return sb.toString();
    }
}
