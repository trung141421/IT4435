package vn.vietdefi.services.gochie.auth;

import com.google.gson.JsonObject;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;

public class GochieAuthService implements  IGochieAuthService{
    public JsonObject checkExistUsername(String username){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM user WHERE username = ?";
            boolean exist = bridge.queryExist(query, username);
            if(exist){
                return BaseResponse.createFullMessageResponse(10, "username_existed");
            }
            return BaseResponse.createFullMessageResponse(0, "success");
        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject authorize(long userId, String token) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            JsonObject response = VdefServices.authService.authorize(userId, token);
            if (BaseResponse.isSuccessFullMessage(response)) {
                String query = "SELECT * FROM gochie_profile WHERE userId = ?";
                JsonObject data = bridge.queryOne(query, userId);
                return BaseResponse.createFullMessageResponse(0, "success", data);
            }
            else return BaseResponse.createFullMessageResponse(0, "unauthorized");
        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    @Override
    public JsonObject getProfile(long userId) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            String query = "SELECT * FROM gochie_profile WHERE userId = ? ";
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
            String query = "INSERT INTO gochie_profile(userId,role) value (?,0)";
            bridge.update(query, userId);
            query = "INSERT INTO gochie_wallet VALUES (?,0,0,0)";
            bridge.update(query,userId);
            query = "SELECT * FROM gochie_profile WHERE userId = ?";
            JsonObject data = bridge.queryOne(query, userId);
            return BaseResponse.createFullMessageResponse(0, "success",data);
        }catch(Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    public JsonObject createSalesProfile(JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long userId = json.get("userId").getAsLong();
            String query = "INSERT INTO gochie_sales_manager_profile(userId, role,status) VALUES(?, ?,?) ";
            bridge.update(query, userId, 1,0);
            query = "UPDATE gochie_profile SET role = 2 WHERE userId = ?";
            bridge.update(query,userId);
            return BaseResponse.createFullMessageResponse(0,"success");
        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    public JsonObject createManagerProfile(JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long userId = json.get("userId").getAsLong();
            String query = "INSERT INTO gochie_sales_manager_profile(userId, role,status) VALUES(?, ?,?) ";
            bridge.update(query, userId, 3,1);
            query = "UPDATE gochie_profile SET role = 3 WHERE userId = ?";
            bridge.update(query,userId);
            return BaseResponse.createFullMessageResponse(0,"success");
        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

}
