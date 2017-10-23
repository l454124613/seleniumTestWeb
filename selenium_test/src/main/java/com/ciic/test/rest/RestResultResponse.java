package com.ciic.test.rest;

/**
 * Created by lixuecheng on 2017/10/20.
 */
public class RestResultResponse {
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private String result;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"status\":\"")
                .append(result).append('\"');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestResultResponse that = (RestResultResponse) o;

        return result.equals(that.result);
    }

    @Override
    public int hashCode() {
        return result.hashCode();
    }

    public RestResultResponse(boolean isok,  String action,String msg) {

        if(isok){
            result="操作成功";
        }else {
            throw  new IllegalArgumentException("操作失败");

        }

    }
//    public RestResultResponse(boolean isok){
//        if(isok){
//            result="操作成功";
//        }else {
//            throw  new IllegalArgumentException("操作失败");
//        }
//
//    }

}
