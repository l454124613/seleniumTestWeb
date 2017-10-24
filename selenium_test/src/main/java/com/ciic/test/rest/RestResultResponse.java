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
        sb.append("\"result\":\"")
                .append(result).append('\"');
        sb.append(",\"msg\":\"")
                .append(msg).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;



    public RestResultResponse(boolean isok,  String action,String msg) {

        if(isok){
            result="操作成功";
            this.msg=msg;
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
