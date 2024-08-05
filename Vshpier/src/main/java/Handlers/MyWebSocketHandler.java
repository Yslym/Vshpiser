package Handlers;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import com.alibaba.fastjson.*;


public class MyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("[+] Connection coming");
        try{
            Command object = JSON.parseObject(msg.text(),Command.class);
            switch (CommandType.match(object.getMsgCode())) {
                case CONNECT:
                    ConnectionExecutor.executor(ctx.channel(),object.getNickName());
             
                    break;
                case PUBCHAT:
                    PubChatExecutor.executor(ctx.channel(), object);
                    break;
                
                case PRICHAT:
                    PriChatExecutor.executor(ctx.channel(), object);
                    break;
                case LIST:
                    ListHandler.executor(ctx.channel());
                break;
                default:
                    Channel channel = ctx.channel();
                    channel.writeAndFlush(new TextWebSocketFrame(ServerResponse.success().toJSONString()));
                    channel.disconnect();
                    break;
            }
        }catch( Exception e){
            ctx.channel().writeAndFlush(new TextWebSocketFrame(ServerResponse.fail().toJSONString()));
            ctx.channel().disconnect();
            e.printStackTrace();
        }
    }
    
}
