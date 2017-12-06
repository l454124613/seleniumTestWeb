package com.ciic.test.bean;

/**
 * Created by lixuecheng on 2017/11/29.
 */
public class Http4res {
    private String requestHeaders;
    private String requestdata;
    private String responseCode;
    private String responseDataAsString;
    private String responseHeaders;
    private String responseMessage;
    private String sentBytes;
    private String bytesAsLong;
    private String urlAsString;
    private String restype;

    public String getRestype() {
        return restype;
    }

    public void setRestype(String restype) {
        this.restype = restype;
    }

    public String getResid() {
        return resid;
    }

    public void setResid(String resid) {
        this.resid = resid;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"requestHeaders\":\"")
                .append(requestHeaders).append('\"');
        sb.append(",\"requestdata\":\"")
                .append(requestdata).append('\"');
        sb.append(",\"responseCode\":\"")
                .append(responseCode).append('\"');
        sb.append(",\"responseDataAsString\":\"")
                .append(responseDataAsString).append('\"');
        sb.append(",\"responseHeaders\":\"")
                .append(responseHeaders).append('\"');
        sb.append(",\"responseMessage\":\"")
                .append(responseMessage).append('\"');
        sb.append(",\"sentBytes\":\"")
                .append(sentBytes).append('\"');
        sb.append(",\"bytesAsLong\":\"")
                .append(bytesAsLong).append('\"');
        sb.append(",\"urlAsString\":\"")
                .append(urlAsString).append('\"');
        sb.append(",\"restype\":\"")
                .append(restype).append('\"');
        sb.append(",\"resid\":\"")
                .append(resid).append('\"');
        sb.append(",\"res\":\"")
                .append(res).append('\"');
        sb.append(",\"isok\":")
                .append(isok);
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append('}');
        return sb.toString();
    }

    private String resid;

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    private String res;

    public boolean isIsok() {
        return isok;
    }

    public void setIsok(boolean isok) {
        this.isok = isok;
    }

    private boolean isok;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public String getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(String requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public String getRequestdata() {
        return requestdata;
    }

    public void setRequestdata(String requestdata) {
        this.requestdata = requestdata;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDataAsString() {
        return responseDataAsString;
    }

    public void setResponseDataAsString(String responseDataAsString) {
        this.responseDataAsString = responseDataAsString;
    }

    public String getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(String responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getSentBytes() {
        return sentBytes;
    }

    public void setSentBytes(String sentBytes) {
        this.sentBytes = sentBytes;
    }

    public String getBytesAsLong() {
        return bytesAsLong;
    }

    public void setBytesAsLong(String bytesAsLong) {
        this.bytesAsLong = bytesAsLong;
    }

    public String getUrlAsString() {
        return urlAsString;
    }

    public void setUrlAsString(String urlAsString) {
        this.urlAsString = urlAsString;
    }

}
