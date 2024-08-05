package Handlers;

public enum CommandType {
    CONNECT(1001),
    PUBCHAT(1002),
    PRICHAT(1003),
    LIST(1004),
    Error(-1)
    ;
    private CommandType(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
    public static CommandType match(int code){
            for(CommandType each:CommandType.values()){
                if (each.getCode() == code) {
                    return each;
                }
            }
            return CommandType.Error;
    }
    private int code;
}
