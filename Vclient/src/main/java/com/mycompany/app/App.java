package com.mycompany.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import org.java_websocket.client.WebSocketClient;
import com.alibaba.fastjson.JSON;
import com.mycompany.MyClient;
import java.lang.Thread;
/**
 * Hello world!
 *
 */
public class App 
{
   
    // public static final String nick = "gyk";
    public static final HashMap<String,String> clientConfigure = new HashMap<>();
    public static void main( String[] args ) throws URISyntaxException  // start the project
, IOException
    {
      
         // CApp interface
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("[i wanna your name]: ");
        String name = reader.readLine();  
        clientConfigure.put("nickname",name);
        System.out.println("[i need your server ip]: ");
        String ip = reader.readLine();
        if(ip.length()<7){
            ip = "0.0.0.0";
        }
        clientConfigure.put("ip", ip);
        System.out.println("VshiperClient say: ~Hello :)");
        WebSocketClient client = new MyClient(new URI("ws://"+ip+":8080/"),new CApplicationImp(),clientConfigure); // start the base
        try{
            client.connect();
            System.out.println("...Loading");
            Thread.sleep(1000);
        }catch(Exception e){
            client.close();
            System.out.println("Connect Error");
        }
    String json = register(clientConfigure.get("nickname"));
    client.send(json);
    
    ((MyClient) client).ApplicationLoop();
    System.out.println("VshiperClient say: ~Bye :)");
    client.close();
    }

    private static String register(String nickname){
        Command command = new Command();
        command.setMsgCode(1001);
        command.setMsgContent("null");
        command.setNickName(nickname);
        String json = JSON.toJSONString(command); 
        return json;
    }
    public static String prichat(String herName ,String content){
        Command command = new Command();
        command.setMsgCode(1003);
        command.setHerName(herName);
        command.setNickName(clientConfigure.get("nickname"));
        command.setMsgContent(content);
        return JSON.toJSONString(command);
    }
    public static String pubchat(String herName,String content){
        Command command = new Command();
        command.setMsgCode(1002);
        command.setHerName(herName);
        command.setNickName(clientConfigure.get("nickname"));
        command.setMsgContent(content);
        return JSON.toJSONString(command);
    }
}
