package vn.vietdefi.services.gochie.logic;

public class StringLogic {
    public static boolean isHasSpace(String s){
        int i = 0;
        for(i=0;i<s.length();i++)
        {
            char ch=s.charAt(i);
            if(ch == ' ')
                return false;
        }
        return  true;
    }
    public static boolean isHasTV(String s){
        int i = 0;
        for(i=0;i<s.length();i++)
        {
            char ch=s.charAt(i);
            if(! (ch >= '!' && ch <= '~') ) {
                return false;
            }
        }
        return  true;
    }
    public static boolean checkFormUsername(String username){
        if(username.trim().length()==0) return false;
        if(username.isEmpty()) return false;
        if(!isHasSpace(username)) return false;
        if(!isHasTV(username)) return false;
        return true;
    }
    public static boolean checkFormPassword(String password){
        if(password.length()<8) return false;
        if(password.trim().length()==0) return false;
        if(password.isEmpty()) return false;
        if(!isHasSpace(password))return false;
        if(!isHasTV(password)) return false;
        return true;
    }
}
