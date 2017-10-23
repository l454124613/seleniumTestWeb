package com.ciic.test.bean;

/**
 * Created by lixuecheng on 2017/8/30.
 */
public class tmp2 {
    private String value1;
    private String value2;

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"value1\":\"")
                .append(value1).append('\"');
        sb.append(",\"value2\":\"")
                .append(value2).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
