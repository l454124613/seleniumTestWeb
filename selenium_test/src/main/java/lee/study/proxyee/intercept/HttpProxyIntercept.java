package lee.study.proxyee.intercept;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

import java.util.List;
import java.util.Map;

public interface HttpProxyIntercept {
    boolean beforeRequest(ChannelHandlerContext ctx, HttpRequest httpRequest);

    boolean beforeRequest(Channel channel, HttpContent httpContent);

    boolean afterResponse(Channel channel, HttpResponse httpResponse);

    boolean afterResponse(Channel channel, HttpContent httpContent);

    Map<String,String> getMap();


}
