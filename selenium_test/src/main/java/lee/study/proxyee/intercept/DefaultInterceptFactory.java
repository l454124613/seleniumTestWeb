package lee.study.proxyee.intercept;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultInterceptFactory implements ProxyInterceptFactory {
    @Override
    public HttpProxyIntercept build() {
        return new HttpProxyIntercept() {

            @Override
            public boolean beforeRequest(ChannelHandlerContext ctx, HttpRequest httpRequest) {
                return false;
            }

            @Override
            public boolean beforeRequest(Channel channel, HttpContent httpContent) {
                return false;
            }

            @Override
            public boolean afterResponse(Channel channel, HttpResponse httpResponse) {
                return false;
            }

            @Override
            public boolean afterResponse(Channel channel, HttpContent httpContent) {
                return false;
            }

            @Override
            public Map<String, String> getMap() {
                return new HashMap<>();
            }


        };
    }
}
