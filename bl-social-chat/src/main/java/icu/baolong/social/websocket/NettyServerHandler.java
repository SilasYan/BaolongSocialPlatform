package icu.baolong.social.websocket;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty 服务处理器
 *
 * @author Silas Yan 2025-05-20 21:20
 */
@Slf4j
@Sharable // 该注解表示该类的实例可以被多个 Channel 安全地共享
public class NettyServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	/**
	 * 当有用户事件（如连接空闲、自定义事件等）触发时会被调用
	 *
	 * @param ctx 代表当前 Channel 的上下文，可通过它操作 Channel
	 * @param evt 表示触发的事件对象
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
			log.info("握手成功");
		} else if (evt instanceof IdleStateEvent idleStateEvent) {
			if (idleStateEvent.state() == IdleState.READER_IDLE) {
				log.info("读空闲");
				// 关闭连接
				ctx.channel().close();
				// TODO 这里要控制用户下线
			} else if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
				log.info("写空闲");
			} else if (idleStateEvent.state() == IdleState.ALL_IDLE) {
				log.info("读写空闲");
			}
		}
	}

	/**
	 * 用于处理接收到的消息
	 *
	 * @param ctx 通道上下文，用于与客户端通信
	 * @param msg 接收到的 WebSocket 文本消息帧
	 * @throws Exception e
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		String text = msg.text();
		log.info("接收到的消息：{}", text);

	}
}
