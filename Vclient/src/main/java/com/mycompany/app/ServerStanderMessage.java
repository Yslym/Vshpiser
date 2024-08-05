package com.mycompany.app;



import com.alibaba.fastjson.JSON;

/**
 * 
 * Used  in notify user2user
 * 
 * 
 */
public class ServerStanderMessage {
    
    private String code;    // Ok Err
    private String msg;     // content 
    private String herName; // HerName
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getHerName() {
        return herName;
    }
    public void setHerName(String herName) {
        this.herName = herName;
    }    
    public static String success(){
         ServerStanderMessage msg = new ServerStanderMessage();
         msg.setCode("OK");
         msg.setMsg("success");
         msg.setHerName("Server");
         return JSON.toJSONString(msg);
    }
    public static String notify(String herName , String content){
        ServerStanderMessage msg = new ServerStanderMessage();
        msg.setCode("notify");
        msg.setMsg(content);
        msg.setHerName(herName);
        return JSON.toJSONString(msg);
    }

}

