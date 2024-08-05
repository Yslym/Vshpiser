package com.vshiper.vshpier;

import java.util.HashMap;

import Handlers.MyWebSocketHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class ChatServer {
     public static  final HashMap<String,Channel> Users =  new HashMap<>();
     public static  final HashMap<String,String> ServerConfig = new HashMap<>();

     public static void run(){
        ChatServer.ServerInitiliaze();
        System.out.println("[+] Initiliaze the ThreadPool");
        EventLoopGroup boss     = new NioEventLoopGroup();
        EventLoopGroup worker   = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        System.out.println("[+] BootStrap the Server Configuration");
        bootstrap.group(boss,worker)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel arg0) throws Exception {  
                    ChannelPipeline pipeline = arg0.pipeline(); 
                    pipeline.addLast(new HttpServerCodec())
                    .addLast(new ChunkedWriteHandler())
                    .addLast(new HttpObjectAggregator(Integer.parseInt(ChatServer.ServerConfig.get("max-members"))))
                    .addLast(new WebSocketServerProtocolHandler("/"))
                    .addLast(new MyWebSocketHandler())
            
                    ;
                }
            });
            System.out.println("[+] Server Binding on port :");
            bootstrap.bind(Integer.parseInt(ChatServer.ServerConfig.get("port")));
            System.out.println("[+] Server Binding on port :"+ChatServer.ServerConfig.get("port"));

    }
    protected static void addLast(IdleStateHandler idleStateHandler) {

    }
    
    private static void ServerInitiliaze(){
        ChatServer.ServerConfig.put("port","8080");
        ChatServer.ServerConfig.put("max-members","1024");
    }
}
