package Handlers;

import com.vshiper.vshpier.ChatServer;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class ConnectionExecutor{
    public static void executor(io.netty.channel.Channel channel , String nickName ){
        System.out.println("[+] command -> connect");
        if(nickName.length() <= 0){
            channel.writeAndFlush(new TextWebSocketFrame(ServerResponse.fail("small").toJSONString()));
            channel.disconnect();
            return;
        }
        if(ChatServer.Users.containsKey(nickName)){
            if(ChatServer.Users.get(nickName).isActive()){
            System.out.println("[+] Hacker Guessing");
            channel.writeAndFlush(new TextWebSocketFrame(ServerResponse.fail("hacked").toJSONString()));
            channel.disconnect();
            return;
            }
            ChatServer.Users.replace(nickName, channel);
            channel.writeAndFlush(new TextWebSocketFrame(ServerResponse.success().toJSONString()));
            return ;
        }
        ChatServer.Users.put(nickName, channel);
        // need pull offline msg from mysql;
        channel.writeAndFlush(new TextWebSocketFrame(ServerResponse.success().toJSONString()));
        channel.pipeline().addLast(new ChannelHandler() {
            @Override
            public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
                   return;
            }

            @Override
            public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
                    ChatServer.Users.remove(nickName);
            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
               
                    ChatServer.Users.remove(nickName);
            }
            
        });
        return;
    }
}