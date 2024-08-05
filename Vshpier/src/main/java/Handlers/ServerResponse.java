package Handlers;

import com.alibaba.fastjson.JSONObject;
/**
 *  Used in Server -> Users
 * 
*/
public class ServerResponse {
    public static JSONObject success(){
        JSONObject json = new JSONObject();
        json.put("code","OK");
        json.put("msg","null");
        json.put("herName","null");
        return json;
    }
    public static JSONObject fail(){
        JSONObject json = new JSONObject();
        json.put("code", "Err");
        json.put("msg","null");
        json.put("herName","Server");

        return json;
    }
    public static JSONObject fail(String msg){
        JSONObject json = new JSONObject();
        json.put("code","Err");
        json.put("msg",msg);
        json.put("herName","Server");
        return json;
    }
}