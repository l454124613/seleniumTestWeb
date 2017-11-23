package com.ciic.test.bean;

public class Base {
    private String encoding;
    private String host;
    private String path;
    private String port;
    private String time4req;
    private String time4res;
    private KeyValue http;
    private KeyValue method;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"encoding\":\"")
                .append(encoding).append('\"');
        sb.append(",\"host\":\"")
                .append(host).append('\"');
        sb.append(",\"path\":\"")
                .append(path).append('\"');
        sb.append(",\"port\":\"")
                .append(port).append('\"');
        sb.append(",\"time4req\":\"")
                .append(time4req).append('\"');
        sb.append(",\"time4res\":\"")
                .append(time4res).append('\"');
        sb.append(",\"http\":")
                .append(http);
        sb.append(",\"method\":")
                .append(method);
        sb.append('}');
        return sb.toString();
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getTime4req() {
        return time4req;
    }

    public void setTime4req(String time4req) {
        this.time4req = time4req;
    }

    public String getTime4res() {
        return time4res;
    }

    public void setTime4res(String time4res) {
        this.time4res = time4res;
    }

    public KeyValue getHttp() {
        return http;
    }

    public void setHttp(KeyValue http) {
        this.http = http;
    }

    public KeyValue getMethod() {
        return method;
    }

    public void setMethod(KeyValue method) {
        this.method = method;
    }
}
