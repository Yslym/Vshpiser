package com.mycompany.app;

import java.util.HashMap;
import java.util.Set;

import org.java_websocket.client.WebSocketClient;


public interface ApplicationInterface {

    public int notify(ServerStanderMessage msg);
    public int success(ServerStanderMessage msg);
    public int fail(ServerStanderMessage msg);
    public void ApplicationMain(HashMap<String,String>args);
    public String getTargetNickName();
    public void ApplicationLoop(WebSocketClient ws);
    public void appleaList(Set<String>lists);
} 
    
