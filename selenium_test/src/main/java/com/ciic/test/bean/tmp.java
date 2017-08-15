package com.ciic.test.bean;

/**
 * Created by lixuecheng on 2017/7/14.
 */
public class tmp {
    private String value;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"value\":\"")
                .append(value).append('\"');
        sb.append(",\"value2\":\"")
                .append(value2).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    private String value2;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
