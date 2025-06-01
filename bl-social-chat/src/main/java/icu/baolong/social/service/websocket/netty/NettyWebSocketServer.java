package icu.baolong.social.service.websocket.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.Future;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Netty WebSocket 服务
 *
 * @author Silas Yan 2025-05-20 21:16
 */
@Slf4j
@Service
public class NettyWebSocketServer {

	@Value("${baolong.social.websocket.port}")
	private Integer port;

	public static final NettyWebSocketServerHandler NETTY_SERVER_HANDLER = new NettyWebSocketServerHandler();

	// 创建线程池执行器
	private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
	private EventLoopGroup workerGroup = new NioEventLoopGroup(NettyRuntime.availableProcessors());

	@PostConstruct
	public void start() throws InterruptedException {
		run();
		log.info("[WS]WebSocket启动成功!!!");
	}

	@PreDestroy
	public void destroy() {
		Future<?> future = bossGroup.shutdownGracefully();
		Future<?> future1 = workerGroup.shutdownGracefully();
		future.syncUninterruptibly();
		future1.syncUninterruptibly();
		log.info("[WS]WebSocket关闭成功!!!");
	}

	/**
	 * 启动服务器
	 *
	 * @throws InterruptedException e
	 */
	public void run() throws InterruptedException {
		// 服务器启动引导对象
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 128)
				.option(ChannelOption.SO_KEEPALIVE, true)
				// 为 bossGroup 添加 日志处理器
				.handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						ChannelPipeline pipeline = socketChannel.pipeline();
						// 30秒客户端没有向服务器发送心跳则关闭连接
						// pipeline.addLast(new IdleStateHandler(30, 0, 0));
						// 因为使用HTTP协议，所以需要使用HTTP的编码器，解码器
						pipeline.addLast(new HttpServerCodec());
						// 以块方式写，添加 ChunkedWriter 处理器
						pipeline.addLast(new ChunkedWriteHandler());
						// HTTP 消息在传输过程中是分段的，HttpObjectAggregator就是将多个段聚合起来，组装成一个完整的HTTP消息
						pipeline.addLast(new HttpObjectAggregator(8192));
						// 用于处理 websocket 的头信息
						pipeline.addLast(new NettyHeadersHandler());
						// 用于指定 websocket 的路径
						pipeline.addLast(new WebSocketServerProtocolHandler("/"));
						// 自定义处理
						pipeline.addLast(NETTY_SERVER_HANDLER);
					}
				});
		// 启动服务器，监听端口，阻塞直到启动成功
		serverBootstrap.bind(port).sync();
	}

}
