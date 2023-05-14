package vn.vietdefi.services.godoo.logic;


public class GodooLogic {
    public static String getRelationshipId(long user1,long user2){
        if(user1 > user2){
            long tmp = user1;
            user1 = user2;
            user2 = tmp;
        }
        return new StringBuilder("").append(user1).append("_").append(user2).toString();
    }
}
