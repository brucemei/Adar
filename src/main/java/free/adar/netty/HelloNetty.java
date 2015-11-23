package free.adar.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;


/**
 * Netty version: 3.5.9.Final
 */
public class HelloNetty {

	public static void main(String[] args) {
		ServerBootstrap bootstrap = new ServerBootstrap(
			new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(), 
				Executors.newCachedThreadPool()
			)
		);
		
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("decoder", new StringDecoder());
				pipeline.addLast("encoder", new StringEncoder());
				pipeline.addLast("handler", new SimpleChannelHandler () {

					@Override
					public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
						e.getChannel().write("Echo: " + e.getMessage());
						e.getChannel().close();
					}
										
				});
				
				return pipeline;
			}
		});
		
		bootstrap.bind(new InetSocketAddress(10080));
	}
}
