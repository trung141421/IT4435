package vn.vietdefi.services.vdef.auth.google;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.network.OkHttpUtil;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;
import vn.vietdefi.util.string.StringUtil;


public class GoogleService implements IGoogleService {
    public  JsonObject login(JsonObject json){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String token = json.get("token").getAsString();
            String query = "SELECT userId FROM vdef.gg_user WHERE gg_token = ?";
            Long userId = bridge.queryLong(query, token);

            if(userId == null) {
                String googleId;
                try {
                    googleId = getUserInfo(token).get("id").getAsString();
                }catch (Exception e){
                    String stacktrace = ExceptionUtils.getStackTrace(e);
                    DebugLogger.error(stacktrace);
                    return BaseResponse.createFullMessageResponse(3, "invalid_token");
                }
                query = "SELECT ggId FROM vdef.gg_user WHERE ggId = ?";
                String ggId = bridge.queryString(query, googleId);
                if(ggId!= null){
                    query = "SELECT userId FROM vdef.gg_user WHERE ggId = ?";
                    userId = bridge.queryLong(query, ggId);
                    query = "SELECT * FROM user WHERE id =?";
                    json = bridge.queryOne(query, userId);
                    json.remove("password");;
                    return BaseResponse.createFullMessageResponse(0, "success", json);
                }else {
                    json = VdefServices.googleService.registerGoogle(googleId);
                    userId = json.get("id").getAsLong();
                    json.remove("password");
                    String userInfo = getUserInfo(token).toString();
                    query = "INSERT INTO vdef.gg_user(ggId,userId,gg_token,gg_info) VALUE(?,?,?,?)";
                    bridge.update(query, googleId, userId, token, userInfo);
                    return BaseResponse.createFullMessageResponse(0, "success", json);
                    }
                }
            else{
                query = "SELECT * FROM user WHERE id =?";
                json = bridge.queryOne(query, userId);
                json.remove("password");;
                return BaseResponse.createFullMessageResponse(0, "success", json);
            }
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }
    public JsonObject registerGoogle(String googleId) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            googleId = new StringBuilder("gg.").append(googleId).toString();
            JsonObject json = new JsonObject();
            json.addProperty("username",googleId);
            json.addProperty("password",StringUtil.sha256(StringUtil.generateRandomStringNumberCharacter(12)));
            VdefServices.authService.register(json);

            String query = "SELECT * FROM user WHERE username =?";
            json = bridge.queryOne(query, googleId);
            return json;
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    public  JsonObject getUserInfo(String token){
        String link =
                new StringBuilder("https://www.googleapis.com/oauth2/v1/userinfo?access_token=")
                        .append(token).toString();
        return OkHttpUtil.get(link);
    }
}
