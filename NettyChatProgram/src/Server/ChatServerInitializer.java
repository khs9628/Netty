package Server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;
 
public class ChatServerInitializer extends ChannelInitializer<SocketChannel> {
 
    private final SslContext sslCtx;
    
    public ChatServerInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }
    
    @Override
    protected void initChannel(SocketChannel arg0) throws Exception {
        ChannelPipeline pipeline = arg0.pipeline();
        
        //pipeline.addLast(sslCtx.newHandler(arg0.alloc())); 보안을 강화.
        // 문자열을 주고 받기 위해서 필요 10000 - 받을 최대 바이트 수
        pipeline.addLast(new DelimiterBasedFrameDecoder(10000, Delimiters.lineDelimiter()));
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        
        pipeline.addLast(new ChatServerHandler());
 
    }
 
}
