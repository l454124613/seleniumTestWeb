package com.ciic.test.rest;

/**
 * Created by lixuecheng on 2017/10/20.
 */
public class RestResultResponse {
    private String result;

//    @Override
//    public String toString() {
//        final StringBuilder sb = new StringBuilder("{");
//        sb.append("\"status\":\"")
//                .append(result).append('\"');
//        sb.append('}');
//        return sb.toString();
//    }

    public RestResultResponse(boolean isok, String msg, String action) {

        if(isok){
            result="操作成功";
        }else {
            throw new com.ciic.test.exception.DelectFailedException(action,msg);

        }

    }

}
