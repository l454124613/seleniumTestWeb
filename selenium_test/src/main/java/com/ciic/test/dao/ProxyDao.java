package com.ciic.test.dao;

import com.ciic.test.bean.tmp;
import com.ciic.test.service.Proxy;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import lee.study.proxyee.intercept.CertDownIntercept;
import lee.study.proxyee.intercept.HttpProxyIntercept;
import lee.study.proxyee.intercept.HttpProxyInterceptInitializer;
import lee.study.proxyee.intercept.HttpProxyInterceptPipeline;

import lee.study.proxyee.server.HttpProxyServer;
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
        new HttpProxyServer()
//  .proxyConfig(new ProxyConfig(ProxyType.SOCKS5, "127.0.0.1", 1085))  //使用socks5二级代理
                .proxyInterceptInitializer(new HttpProxyInterceptInitializer() {
                    @Override
                    public void init(HttpProxyInterceptPipeline pipeline) {
                        pipeline.addLast(new CertDownIntercept());  //开启网页下载证书功能
                        pipeline.addLast(new HttpProxyIntercept() {
                            @Override
                            public void beforeRequest(ChannelHandlerContext chc, HttpRequest httpRequest,
                                                      HttpProxyInterceptPipeline pipeline) throws Exception {
                                //替换UA，伪装成手机浏览器
                              //  httpRequest.headers().set(HttpHeaderNames.USER_AGENT,
                                   //     "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
                                //转到下一个拦截器处理
                                map.put(chc.channel().id().asShortText(),httpRequest.uri());
                                List<tmp> lt=jdbcTemplate.query("select url value from excepturl where  isused=1",new BeanPropertyRowMapper<>(tmp.class));
                                if(lt.size()>0){
                                    for (int i = 0; i <lt.size() ; i++) {
                                        String tm= httpRequest.uri();
                                        String ta=lt.get(i).getValue();
                                        if(ta.contains("(")&&ta.contains(")")){
                                            int t1=ta.indexOf("(");
                                            int t2=ta.lastIndexOf(")");
                                            String tb=ta.substring(t1,t2+1);
                                            String re11=ta.replace(tb,"tmppmt112233qq");
                                            ta= re11.replace(".","\\.").replace("?","\\?").replace("+","\\+");
                                            tb=tb.substring(1,t2-t1);
                                            ta= ta.replace("tmppmt112233qq",tb);


                                        }else {
                                            ta=ta.replace(".","\\.").replace("?","\\?").replace("+","\\+");
                                        }
                                        if(httpRequest.uri().matches(ta)){
                                            HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpProxyServer.f404);
                                            chc.writeAndFlush(response);

                                            chc.pipeline().remove("httpCodec");


                                            map.remove(chc.channel().id().asShortText());
                                            chc.channel().close();
                                            chc.close();
                                            return  ;
                                        }

                                    }
                                }
                                pipeline.beforeRequest(chc, httpRequest);
                            }

                            @Override
                            public void afterResponse(Channel clientChannel, Channel proxyChannel,
                                                      HttpResponse httpResponse,
                                                      HttpProxyInterceptPipeline pipeline) throws Exception {
                                //拦截响应，添加一个响应头
                                //httpResponse.headers().add("intercept", "test");
                                if(map.containsKey(proxyChannel.id().asShortText())){
                                    map.remove(proxyChannel.id().asShortText());
                                }
                                pipeline.afterResponse(clientChannel, proxyChannel, httpResponse);
                            }
                        });
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

