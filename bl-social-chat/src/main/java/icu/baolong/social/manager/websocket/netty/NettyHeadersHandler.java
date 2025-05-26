package icu.baolong.social.manager.websocket.netty;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.StrUtil;
import icu.baolong.social.manager.websocket.utils.NettyUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;

import java.net.InetSocketAddress;
import java.util.Optional;

/**
 * Netty握手处理器
 *
 * @author Silas Yan 2025-05-25 01:39
 */
public class NettyHeadersHandler extends ChannelInboundHandlerAdapter {

	/**
	 * 在 WebSocket 协议升级前, 获取到 token
	 *
	 * @param ctx 通道上下文，用于与客户端通信
	 * @param msg 请求消息
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof FullHttpRequest request) {
			UrlBuilder urlBuilder = UrlBuilder.ofHttp(request.uri());

			// 获取token参数
			String token = Optional.ofNullable(urlBuilder.getQuery())
					.map(k -> k.get("token"))
					.map(CharSequence::toString)
					.orElse("");
			// 设置token参数
			NettyUtils.setAttr(ctx.channel(), NettyUtils.TOKEN, token);

			// 获取请求路径并重新设置为uri
			request.setUri(urlBuilder.getPath().toString());

			// 获取IP地址
			HttpHeaders headers = request.headers();
			String ip = headers.get("X-Real-IP");
			if (StrUtil.isBlank(ip)) {
				// 说明在Nginx中获取不到, 所以可以直接获取远端地址的IP
				InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
				ip = address.getAddress().getHostAddress();
			}
			NettyUtils.setAttr(ctx.channel(), NettyUtils.IP, ip);

			ctx.pipeline().remove(this);
			ctx.fireChannelRead(request);
		} else {
			ctx.fireChannelRead(msg);
		}
	}
}
