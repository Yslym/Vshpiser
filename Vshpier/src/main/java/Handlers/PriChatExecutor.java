package Handlers;


import com.vshiper.vshpier.ChatServer;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class PriChatExecutor {
    public static void executor(io.netty.channel.Channel channel , Command cmd){
            System.out.println("[+] command -> pri");
            String herName = cmd.getHerName();
            Channel channel2 = ChatServer.Users.get(herName);
            if(channel2 == null){
                channel.writeAndFlush(new TextWebSocketFrame(ServerResponse.fail("user-none").toJSONString()));
                return;
            }
            // check if online
            if(!channel2.isActive()){
                    // write to mysql as offline msg
                channel.writeAndFlush(new TextWebSocketFrame(ServerResponse.fail("Offline").toJSONString()));
                ChatServer.Users.remove(herName);
                channel2.disconnect();
                return;
            }
            System.out.println("[+] PRICHAT "+cmd.getNickName()+" -> "+cmd.getHerName());
            String notify = ServerStanderMessage.notify(cmd.getNickName(), cmd.getMsgContent());
            System.out.println(notify);
            channel2.writeAndFlush(new TextWebSocketFrame(notify));

            channel.writeAndFlush(new TextWebSocketFrame(ServerResponse.success().toJSONString()));
            return;
    }
    
}
