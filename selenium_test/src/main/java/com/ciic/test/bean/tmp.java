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
        sb.append('}');
        return sb.toString();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
