package vn.vietdefi.services.gochie.profile;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;

public class GochieProfileService implements IGochieProfileService{
    @Override
    public JsonObject updatePersonalProfile(JsonObject data) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long userId = data.get("userId").getAsLong();
            String fullname = data.get("fullname").getAsString();
            String avatar = data.get("avatar").getAsString();
            int gender = data.get("gender").getAsInt();
            String dob = data.get("date_of_birth").getAsString();
            int role = data.get("role").getAsInt();

            String query = "UPDATE gochie_profile SET  fullname = ?, avatar = ?, gender=?, date_of_birth = ? , role = ? WHERE userId=?";
            bridge.update(query,fullname,avatar,gender,dob,role,userId);

            return BaseResponse.createFullMessageResponse(0, "success");

        }catch(Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject updateShoppingPreferences(long userId, JsonArray data) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            String query = "UPDATE gochie_profile SET  shopping_preferences = ? WHERE userId=?";
            bridge.update(query,data,userId);

            return BaseResponse.createFullMessageResponse(0, "success");

        }catch(Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }


    @Override
    public JsonObject playerCheckShopInfo(long userId, long shop_id) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            String query = "SELECT * FROM shop WHERE id = ?";
            JsonObject data = bridge.queryOne(query,shop_id);

            return  BaseResponse.createFullMessageResponse(0,"success",data);
        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject playerCheckTransactionHistory(long userId) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            String query = "SELECT * FROM transaction_history WHERE id = ?";
            boolean isExist = bridge.queryExist(query,userId);
            if(!isExist){
                return BaseResponse.createFullMessageResponse(2,"transaction_history_empty");
            }
            query = "SELECT * FROM transaction_history WHERE id = ?";
            JsonObject data = bridge.queryOne(query,userId);
            return  BaseResponse.createFullMessageResponse(0,"success",data);
        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject managerCheckShopInfo(long userId) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            String query = "SELECT * FROM gochie_sales_manager_account JOIN shop ON shop_id = shop.id WHERE gochie_sales_manager_account.id = ? ";
            JsonObject data = bridge.queryOne(query,userId);

            return BaseResponse.createFullMessageResponse(0,"success",data);
        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
}

