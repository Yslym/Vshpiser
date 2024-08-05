package Handlers;
/**
 *  used in User -> Server
*/
public class Command {
    private String nickName;

    private int msgCode;
    
    private String msgContent;

    private String herName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(int msgCode) {
        this.msgCode = msgCode;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getHerName() {
        return herName;
    }

    public void setHerName(String herName) {
        this.herName = herName;
    }
}
