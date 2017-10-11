//package com.ciic.test.tools;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.DecoderResult;
//import io.netty.handler.codec.http.*;
//import lee.study.proxyee.NettyHttpProxyServer;
//import lee.study.proxyee.intercept.HttpProxyIntercept;
//import lee.study.proxyee.intercept.ProxyInterceptFactory;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * Created by lixuecheng on 2017/7/25.
// */
//public class RunProxy {
//   static Map<String,String>  map=new ConcurrentHashMap<>();
////    public static void main(String[] args) throws InterruptedException {
//////        new NettyHttpProxyServer().start(8102);
//////        //拦截处理
////
////        new Thread(new Runnable() {
////            @Override
////            public void run() {
////                new run1().run();
////            }
////        }).start();
////
////        while (true){
////            Thread.sleep(1000);
////            System.out.println(map.size()+map.toString());
////        }
////
////
////
////    }
//
//    void run(){
//        new  NettyHttpProxyServer().initProxyInterceptFactory(new ProxyInterceptFactory() {
//            @Override
//            public HttpProxyIntercept build() {
//                return new HttpProxyIntercept() {
//
//                    @Override
//                    public boolean beforeRequest(ChannelHandlerContext ctx, HttpRequest httpRequest) {
//
//                        //  System.out.println("----------"+httpRequest.uri());
//                        // list.add(httpRequest.uri());
////                        System.out.println(channel.id().asShortText()+"_"+httpRequest.uri());
////                        System.out.println("-----------------------");
//                        map.put(ctx.channel().id().asShortText(),httpRequest.uri());
//                        if(httpRequest.uri().endsWith("woff")){
//
//                            HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, NettyHttpProxyServer.SUCCESS1);
//                          //  response.headers().set("Content-Type","application/x-www-form-urlencoded");
//
//
//                           ctx.writeAndFlush(response);
//
//                            ctx.channel().pipeline().remove("httpCodec");
//                            ctx.close();
//                            map.remove(ctx.channel().id().asShortText());
//                            return  true;
//                        }
//
//
//
//                        //  System.out.println(111111111111111l);
//                        return false;
//                    }
//
//                    @Override
//                    public boolean beforeRequest(Channel channel, HttpContent httpContent) {
//
//                        // System.out.println(2222222222222222l);
//
//                        return false;
//                    }
//
//                    @Override
//                    public boolean afterResponse(Channel channel, HttpResponse httpResponse) {
//                        //修改响应头
//                        // httpResponse.headers().set("Intercept","111");
//                        //   System.out.println(333333333333333333l);
//
//
//                        return false;
//                    }
//
//                    @Override
//                    public boolean afterResponse(Channel channel, HttpContent httpContent) {
//                        //  System.out.println(44444444444444444l);
//
//                        if(map.containsKey(channel.id().asShortText())){
//                            map.remove(channel.id().asShortText());
//                        }
//
//                        return false;
//                    }
//
//                    @Override
//                    public Map<String, String> getMap() {
//                        return map;
//                    }
//
//
//
//
//                };
//            }
//        }).start(8102);
//    }
//
//    public static Map<String, String> getMap() {
//        return map;
//    }
//}
