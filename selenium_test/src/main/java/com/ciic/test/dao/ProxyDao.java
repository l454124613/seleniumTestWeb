package com.ciic.test.dao;

import com.ciic.test.bean.tmp;
import com.ciic.test.service.Proxy;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import lee.study.proxyee.NettyHttpProxyServer;
import lee.study.proxyee.intercept.HttpProxyIntercept;
import lee.study.proxyee.intercept.ProxyInterceptFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lixuecheng on 2017/10/11.
 */
@Service
public class ProxyDao implements Proxy{

    Map<String,String> map =new ConcurrentHashMap<>();
@Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public void run()  {
    new Thread(new Runnable() {
    @Override
    public void run() {
        new NettyHttpProxyServer().initProxyInterceptFactory(new ProxyInterceptFactory() {
            @Override
            public HttpProxyIntercept build() {
                return new HttpProxyIntercept() {

                    @Override
                    public boolean beforeRequest(ChannelHandlerContext ctx, HttpRequest httpRequest) {

                        //  System.out.println("----------"+httpRequest.uri());
                        // list.add(httpRequest.uri());
//                        System.out.println(channel.id().asShortText()+"_"+httpRequest.uri());
//                        System.out.println("-----------------------");
                        map.put(ctx.channel().id().asShortText(),httpRequest.uri());
                        List<tmp> lt=jdbcTemplate.query("select url value from excepturl where  isused=1",new BeanPropertyRowMapper<>(tmp.class));
if(lt.size()>0){
    for (int i = 0; i <lt.size() ; i++) {
        if(lt.get(i).getValue().equals(httpRequest.uri())){
            HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, NettyHttpProxyServer.SUCCESS1);
            ctx.writeAndFlush(response);

            ctx.channel().pipeline().remove("httpCodec");
            ctx.close();
            map.remove(ctx.channel().id().asShortText());
            return  true;
        }

    }
}





                        //  System.out.println(111111111111111l);
                        return false;
                    }

                    @Override
                    public boolean beforeRequest(Channel channel, HttpContent httpContent) {

                        // System.out.println(2222222222222222l);

                        return false;
                    }

                    @Override
                    public boolean afterResponse(Channel channel, HttpResponse httpResponse) {
                        //修改响应头
                        // httpResponse.headers().set("Intercept","111");
                        //   System.out.println(333333333333333333l);


                        return false;
                    }

                    @Override
                    public boolean afterResponse(Channel channel, HttpContent httpContent) {
                        //  System.out.println(44444444444444444l);

                        if(map.containsKey(channel.id().asShortText())){
                            map.remove(channel.id().asShortText());
                        }

                        return false;
                    }

                    @Override
                    public Map<String, String> getMap() {
                        return map;
                    }




                };
            }
        }).start(8102);

    }
}).start();

    }

    @Override
    public void stop() {


    }

    @Override
    public Map<String, String> getMap() {
        return map;
    }
}
