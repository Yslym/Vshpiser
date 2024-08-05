package com.mycompany;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.mycompany.app.ApplicationInterface;
import com.mycompany.app.ServerStanderMessage;

public class MyClient extends WebSocketClient{
    private ApplicationInterface target;
    private boolean debugFlag;
    private HashSet<String>onlineUsers ;
    public String getTargetNickName(){
        return target.getTargetNickName();
    }
    public MyClient(URI serverUri) {
        super(serverUri);
        this.debugFlag = true;
    }
    public MyClient(URI serverUri , ApplicationInterface applicationInterface,HashMap<String,String>args){ // start the base

        super(serverUri);
        this.target = applicationInterface;
        this.debugFlag = false;
        this.target.ApplicationMain(args); // start the core compoments
    }
    public void ApplicationLoop(){
        this.target.ApplicationLoop(this);
    }

    @Override
    public void onClose(int arg0, String arg1, boolean arg2) {
        if(this.debugFlag)
            System.out.println("[+] Close ");
        
    }

    @Override
    public void onError(Exception arg0) {
        if(this.debugFlag)
            System.out.println("[+] occure some Errors :"+arg0.toString());
     
    }

    @Override
    public void onMessage(String arg0) {
        ServerStanderMessage object = JSONObject.parseObject(arg0,ServerStanderMessage.class);
        if(this.debugFlag){
            onDebug(arg0, object);
            return;
        }
        String code = object.getCode();
     
        if(code.equals("notify")){
            target.notify(object);
            return;
        }
        if(code.equals("OK")){
            target.success(object);
            return;
        }
        if(code.equals("Err")){
            target.fail(object);
            return;
        }
        if(code.equals("List")){
            String msg = object.getMsg();
            onlineUsers = JSON.parseObject(msg,new TypeReference<HashSet<String>>(){});
            if(onlineUsers!=null){
                target.appleaList(onlineUsers);
            }
            else{
                Set<String>s = new HashSet<String>();
                s.add("none");
                target.appleaList(s);
            }
        }
        
       
    }


    @Override
    public void onOpen(ServerHandshake arg0) {
        System.out.println("[+] connect successful");
    }
    public static void onDebug(String arg0,ServerStanderMessage object){
        System.out.println(arg0);
        String code = object.getCode();
        if(code.equals("notify")){
            System.out.println("["+object.getHerName()+"]" +object.getMsg());
            return;
        }
        if(code.equals("OK")){
            return;
        }
        switch (object.getMsg()) {
            case "Offline":
                System.out.println("offline");    
                break;
            case "small":
                System.out.println("name is small");
            break;
            case "hacked":               
                System.out.println("naming has already existed");
            break;
            case "user-none":
                System.out.println("user is none");
            break;
            default:
                System.out.println(object.getMsg());
            break;
        }
    }
    
}
