package com.mycompany.app;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

import org.java_websocket.client.WebSocketClient;

import com.alibaba.fastjson.JSON;

public class CApplicationImp implements ApplicationInterface{
    private  String nickName;
    HashMap<String,Vector<String>> msgmq;
    public static String comPrefix = " >";
    @Override
    public int notify(ServerStanderMessage msg) {
        //System.out.println("["+msg.getHerName()+"]"+msg.getMsg());
        System.out.println("\n"+JSON.toJSONString(msg));
        System.out.print(comPrefix);
        LocalTime localTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        StringBuilder stringb = new StringBuilder(localTime.format(formatter));
        stringb.append(" ");
        String string = stringb.toString();
    
        //String string = localTime.toString()+" ";
        if(msgmq.containsKey(msg.getHerName())){
            Vector<String> vector = msgmq.get(msg.getHerName());

            vector.add(string+msg.getMsg());
            msgmq.put(msg.getHerName(), vector);
        }
        else{
            Vector<String> vector = new Vector<>();
            vector.add(string+msg.getMsg());
            msgmq.put(msg.getHerName(),vector);
        }
    
        return 0;
    }

    @Override
    public int success(ServerStanderMessage msg) {
        return 0;   
    }
       

    @Override
    public int fail(ServerStanderMessage msg) {
        System.out.println("\nWARNING -> "+msg.getMsg() + "\n");
        System.out.print("\n");
        System.out.print(comPrefix);
        return 0;
    }

    @Override
    public void ApplicationMain(HashMap<String,String> args) { // initiliaze the core configuration
        this.nickName = args.get("nickname");
        this.msgmq = new HashMap<>();
    }

    @Override
    public String getTargetNickName() {
       return this.nickName;
    }

    @Override
    public void ApplicationLoop(WebSocketClient ws) {
        Scanner sc = new Scanner(System.in);
        String line;
        StringBuffer prefix = new StringBuffer(nickName);
        prefix.append(" >");
        String string = prefix.toString();
        comPrefix = string;
        while(true){
            System.out.print(string);
            line = sc.nextLine();
            Vector<String> v = parseCommandLine(line);
            String command = v.get(0);
            
            if(command.equals("send")){
                if(v.size()!=3){
                    System.out.println("Error Command \nsend NAME CONTENT");
                    continue;
                }
                String herName = v.get(1);
                String content = v.get(2);
                ws.send(App.prichat(herName, content));     
                continue;
            }
            else if(command.equals("psend")){
                if(v.size()!=2){
                    System.out.println("Error Command \nsend NAME CONTENT");
                    continue;
                }
                String content = v.get(1);
                ws.send(App.pubchat("null", content));     
                continue;
            }
            else if(command.equals("pull")){
               pull();
            }
            else if(command.equals("exit")){
                break;
            }
            else if(command.equals("help")){
                help();
            }
            else if(command.equals("clear")){
                msgmq.clear();
            }
            else if(command.equals("wipe")){
                wipe();

            }
            else if(command.equals("list")){
                list(ws);;
             
            }
            else{
                System.out.println("Unknow command");
            }
        }
        sc.close();
    }
    public static Vector<String> parseCommandLine(String line){
        Vector<String> v = new Vector<>();
        int len = line.length();
        int slow = 0;
        int count = 0;
        for(int i=0;i<len;i++){
            if(line.charAt(i) == ' '){
                v.add(line.substring(slow, i));
                slow = i+1;count +=1;
                if(count == 2){
                    break;
                }
                
            }
        }
        v.add(line.substring(slow,len));
        return v;
    }
    private static void help(){
        System.out.println("send  -> [Usage ] send NAME CONTENT\npull -> Just show your Message quene\nclear -> Clear All Message\nExit -> exit the program");
        System.out.println("wipe -> [Usage] wipe  -> pull all message and quickly clear it");
        System.out.println("psend -> [Usage] psend CONTENT -> send message to public \n");
        String example = "Example\n\n"  
        + "send foo hihi -> foo will receive the message hihi\n\n"  
        + "psend hihi -> every one online will receive the message hihi\n\n"  
        + "pull -> you will check the messages you have received\n\n"  
        + "clear  -> clear all messages\n\n"  
        + "wipe   -> have a glance of the message and clear it\n\n"  
        + "list -> check the online members\n\n";
        System.out.println(example);
          
       
        
    }
    private  void pull(){
        if (msgmq.isEmpty()){
            System.out.println("Empty");
            return;
        }
        for(String key : msgmq.keySet()){
            Vector<String> vector = msgmq.get(key);
            for(String s:vector){
                System.out.println("[ "+key+" ]"+" -> "+s);
            }
        }
        return;
    }
    private void wipe(){
        pull();
        msgmq.clear();
    }
    private String list (WebSocketClient wc){
        Command command = new Command();
        command.setMsgCode(1004);
        command.setHerName("null");
        command.setMsgContent("null");
        command.setNickName(nickName);
        String jsonString = JSON.toJSONString(command);
        wc.send(JSON.toJSONString(command));
        return jsonString;
    }

    @Override
    public void appleaList(Set<String> lists) {
        System.out.println("\n ==== ONLINE");
        int idx = 0;
        for(String s : lists){
            System.out.println("\t\t ["+idx+" ]"+" -> "+s);
            idx+=1;
        }
        System.out.print("\n");
        System.out.print(comPrefix);
    }
}
