package com.nerbit.CryptoProxy.server;

import java.net.InetSocketAddress;
import java.nio.ByteOrder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;

public class ProxyServer {
	public static final int CTRL_PORT = 8021;
	public static final int FILE_PORT = 8020;
	
	public void strat(){
		EventLoopGroup boss = new NioEventLoopGroup(2);
		EventLoopGroup worker = new NioEventLoopGroup(20);
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(boss, worker)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 1024)
					.childHandler(new ChannelInitializer<Channel>() {
						@Override
						protected void initChannel(Channel ch) throws Exception {
							/* cast localAddress from SocketAddress 
							 * back to InetSocketAdress type,
							 * then decide whether it is a CTRL channel 
							 * or FILE channel
							 */
							InetSocketAddress sa = (InetSocketAddress) ch.localAddress();
							int port = sa.getPort();
							if(port == CTRL_PORT){
								/* set the handlers handling the CTRL messages*/
								ch.pipeline().addLast(new LineBasedFrameDecoder(1024, true, true));
								
							}else if(port == FILE_PORT){
								/* set the handlers handling the FILE messages*/
								
							}
							
							ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4));
							new LengthFieldBasedFrameDecoder(ByteOrder.BIG_ENDIAN, 1024, 0, 4, 0, 0, true);
						}	
					});
			
			ChannelFuture CtrlChannelFuture = bootstrap.bind(CTRL_PORT).sync();
			ChannelFuture fileChannelFuture = bootstrap.bind(FILE_PORT).sync();
			
			CtrlChannelFuture.channel().closeFuture();
			fileChannelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			boss.shutdownGracefully();
			worker.shutdownGracefully();
		}
		
	}
}
