package lee.study.proxyee.intercept;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

public class HttpProxyIntercept {

  protected HttpRequest httpRequest;
  protected HttpResponse httpResponse;

  /**
   * 拦截代理服务器到目标服务器的请求头
   */
  public void beforeRequest(ChannelHandlerContext chc, HttpRequest httpRequest,
                            HttpProxyInterceptPipeline pipeline) throws Exception {
    this.httpRequest = httpRequest;
    pipeline.beforeRequest(chc,httpRequest);
  }

  /**
   * 拦截代理服务器到目标服务器的请求体
   */
  public void beforeRequest(ChannelHandlerContext chc, HttpContent httpContent,
      HttpProxyInterceptPipeline pipeline) throws Exception {
    pipeline.beforeRequest(chc,httpContent);
  }

  /**
   * 拦截代理服务器到客户端的响应头
   */
  public void afterResponse(Channel clientChannel, Channel proxyChannel,
      HttpResponse httpResponse, HttpProxyInterceptPipeline pipeline) throws Exception {
    this.httpResponse = httpResponse;
    pipeline.afterResponse(clientChannel,proxyChannel,httpResponse);
  }


  /**
   * 拦截代理服务器到客户端的响应体
   */
  public void afterResponse(Channel clientChannel, Channel proxyChannel,
      HttpContent httpContent, HttpProxyInterceptPipeline pipeline) throws Exception {
    pipeline.afterResponse(clientChannel,proxyChannel,httpContent);
  }
}
