package Handlers;

import java.util.Collection;

import com.alibaba.fastjson.JSON;
import com.vshiper.vshpier.ChatServer;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
public class PubChatExecutor {
    public static void executor(io.netty.channel.Channel channel,Command command){
            System.out.println("[+] command -> pub");
            ServerStanderMessage serverStanderMessage = new ServerStanderMessage();
            serverStanderMessage.setCode("notify");
            serverStanderMessage.setHerName(command.getNickName());
            serverStanderMessage.setMsg(command.getMsgContent());
            
            String json = JSON.toJSONString(serverStanderMessage);
            Collection<Channel> channels = ChatServer.Users.values();
            for (io.netty.channel.Channel c : channels){
                c.writeAndFlush(new TextWebSocketFrame(json));
            }
    }
    
}
