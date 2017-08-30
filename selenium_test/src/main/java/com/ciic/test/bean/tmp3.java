package com.ciic.test.bean;

/**
 * Created by lixuecheng on 2017/8/30.
 */
public class tmp3 {
    private String value1;
    private String value2;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"value1\":\"")
                .append(value1).append('\"');
        sb.append(",\"value2\":\"")
                .append(value2).append('\"');
        sb.append(",\"value3\":\"")
                .append(value3).append('\"');
        sb.append('}');
        return sb.toString();
    }

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

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    private String value3;
}
