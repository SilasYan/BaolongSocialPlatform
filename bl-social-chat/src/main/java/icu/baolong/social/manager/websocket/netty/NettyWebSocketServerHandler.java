package icu.baolong.social.manager.websocket.netty;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import icu.baolong.social.manager.websocket.domain.base.WSReq;
import icu.baolong.social.manager.websocket.domain.enums.WSReqTypeEnum;
import icu.baolong.social.manager.websocket.service.WebSocketService;
import icu.baolong.social.manager.websocket.utils.NettyUtils;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty WebSocket 服务处理器
 *
 * @author Silas Yan 2025-05-20 21:20
 */
@Slf4j
@Sharable // 该注解表示该类的实例可以被多个 Channel 安全地共享
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	private WebSocketService webSocketService;

	/**
	 * 当客户端与服务器的连接建立并激活时
	 *
	 * @param ctx 通道上下文，用于与客户端通信
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		this.online(ctx);
	}

	/**
	 * 当客户端与服务器的连接断开时, 移除当前连接的Channel
	 *
	 * @param ctx 通道上下文，用于与客户端通信
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		this.offline(ctx);
	}

	/**
	 * 处理客户端连接断开时的清理工作
	 *
	 * @param ctx 通道上下文，用于与客户端通信
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		this.offline(ctx);
	}

	/**
	 * 当有用户事件（如连接空闲、自定义事件等）触发时会被调用
	 *
	 * @param ctx 通道上下文，用于与客户端通信
	 * @param evt 触发的事件对象
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
			String token = NettyUtils.getAttr(ctx.channel(), NettyUtils.TOKEN);
			if (StrUtil.isNotBlank(token)) {
				this.webSocketService.handelHandshakeAuthorize(ctx.channel(), token);
				log.info("握手成功, token: {}", token);
			} else {
				log.info("握手成功, 未携带token");
			}
		} else if (evt instanceof IdleStateEvent idleStateEvent) {
			if (idleStateEvent.state() == IdleState.READER_IDLE) {
				// 读空闲
				this.offline(ctx);
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
		WSReq socketReq = JSONUtil.toBean(msg.text(), WSReq.class);
		log.info("收到消息：{}", JSONUtil.toJsonStr(socketReq));
		WSReqTypeEnum wsReqTypeEnum = WSReqTypeEnum.of(socketReq.getType());
		switch (wsReqTypeEnum) {
			case LOGIN_WX_QR_CODE:
				this.webSocketService.handleLoginWxQrCode(ctx.channel());
				break;
			case HEARTBEAT:
				break;
			case AUTHORIZE:
				break;
			default:
				break;
		}
	}

	/**
	 * 上线
	 *
	 * @param ctx 通道上下文，用于与客户端通信
	 */
	private void online(ChannelHandlerContext ctx) {
		this.webSocketService = SpringUtil.getBean(WebSocketService.class);
		this.webSocketService.handleOnline(ctx.channel());
		log.info("客户端上线：{}", ctx.channel().remoteAddress());
	}

	/**
	 * 下线
	 *
	 * @param ctx 通道上下文，用于与客户端通信
	 */
	private void offline(ChannelHandlerContext ctx) {
		this.webSocketService.handleOffline(ctx.channel());
		ctx.channel().close();
		log.info("客户端下线：{}", ctx.channel().remoteAddress());
	}

}
