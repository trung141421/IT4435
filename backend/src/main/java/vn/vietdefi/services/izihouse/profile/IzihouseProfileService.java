package vn.vietdefi.services.izihouse.profile;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;

public class IzihouseProfileService implements IIzihouseProfileService{
    @Override
    public JsonObject getProfile(JsonObject json) {
        try{
            int userid = json.get("userid").getAsInt();
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM iz_profile WHERE id = ?";
            JsonObject data = bridge.queryOne(query, userid);
            return BaseResponse.createFullMessageResponse(0, "success", data);
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject createProfile(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String phone = json.get("phone_number").getAsString();
            String name = json.get("name").getAsString();
            int userid = json.get("userid").getAsInt();
            String avatar = json.get("avatar").getAsString();
            JsonObject details = json.get("details").getAsJsonObject();
            String query = "INSERT INTO iz_profile VALUES (?,?,?,?,?)";
            bridge.update(query, userid, phone, name, avatar, details);
            return BaseResponse.createFullMessageResponse(0,"success");
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    public JsonObject updateProfile(JsonObject json){//update profile -> update request
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String phone = json.get("phone_number").getAsString();
            String name = json.get("name").getAsString();
            String avatar = json.get("avatar").getAsString();
            JsonObject details = json.get("details").getAsJsonObject();
            int id = json.get("userid").getAsInt();
            String query = "UPDATE iz_profile SET phone_number = ?, name = ?, avatar = ?, details = ? WHERE id = ?";
            bridge.update(query, phone, name, avatar, details, id);
            return BaseResponse.createFullMessageResponse(0,"success");
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }


}
