package vn.vietdefi.services.mystikos.profile;

import com.google.gson.JsonObject;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;

public class MtProfileService implements IMtProfileService{

    @Override
    public JsonObject getProfile(long userId) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            String query = "SELECT * FROM mt_profile WHERE userId = ? ";
            JsonObject data = bridge.queryOne(query, userId);
            if(data == null) return createProfile(userId);

            return  BaseResponse.createFullMessageResponse(0,"success",data);
        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    public JsonObject createProfile(long userId){
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "INSERT INTO mt_profile(userId) value (?)";
            bridge.update(query, userId);
            query = "INSERT INTO mt_wallet VALUES (?,0,0)";
            bridge.update(query,userId);
            query = "SELECT * FROM mt_profile WHERE userId = ?";
            JsonObject data = bridge.queryOne(query, userId);
            return BaseResponse.createFullMessageResponse(0, "success",data);
        }catch(Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject updateProfile(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long userId = json.get("userId").getAsLong();

            String username = json.get("username").getAsString();
            JsonObject info = json.get("info").getAsJsonObject();

            String query = "UPDATE mt_profile SET username = ?, info = ? WHERE userId = ?";
            bridge.update(query,username,info,userId);
            return BaseResponse.createFullMessageResponse(0,"success");
        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject chooseCardBack(long userId,long cardBackId) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            JsonObject info = new JsonObject();
            info.addProperty("card_back_id",cardBackId);
            String query = "UPDATE mt_profile SET info = ? WHERE userId = ?";
            bridge.update(query,info,userId);
            return  BaseResponse.createFullMessageResponse(0,"success");
        }catch(Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    public JsonObject checkInvitedId(JsonObject json){
        try{
            return  null;
        }catch(Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }
}
