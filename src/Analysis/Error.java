package Analysis;

/** 
 * 错误信息 
 * @author Gaius 
 * 
 */  
public class Error {  
    //行号  
    private Integer hh;  
    //错误信息  
    private String msg;  
    public Error(Integer hh, String msg) {  
        this.hh = hh;  
        this.msg = msg;  
    }  
    public Integer getHh() {  
        return hh;  
    }  
    public void setHh(Integer hh) {  
        this.hh = hh;  
    }  
    public String getMsg() {  
        return msg;  
    }  
    public void setMsg(String msg) {  
        this.msg = msg;  
    }  
}  