package Handlers;

import java.util.HashMap;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vshiper.vshpier.ChatServer;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;


public class ListHandler {
    public static void executor(Channel channel){
        System.out.println("[+] command -> list");
        HashMap<String,Channel> users = ChatServer.Users;
        Set<String> keySet = users.keySet();
        JSONArray jobj = new JSONArray();
        for(String name : keySet){
            jobj.add(name);
        }
        String jsonString = JSONObject.toJSONString(jobj);
        ServerStanderMessage msg = new ServerStanderMessage();
        msg.setMsg(jsonString);
        msg.setCode("List");
        msg.setHerName("null");
    
        channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msg)));
        return ;
    }
    
}
