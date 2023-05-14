package vn.vietdefi.services.vdef.auth.facebook.util;

import com.google.gson.JsonObject;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.User;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;
import vn.vietdefi.util.string.StringUtil;

public class FacebookService implements IFacebookService {
    @Override
    public JsonObject login(JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String accessToken = json.get("access_token").getAsString();
            String query = "SELECT userId FROM fb_user WHERE fb_token=? ";
            Long userId = bridge.queryLong(query, accessToken);
            if (userId == null) {
                User me;
                try {
                    FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_15_0);
                    me = fbClient.fetchObject("me", User.class);
                } catch (Exception e) {
                    String stacktrace = ExceptionUtils.getStackTrace(e);
                    DebugLogger.error(stacktrace);
                    return BaseResponse.createFullMessageResponse(3, "invalid_token");
                }
                long fbId = Long.parseLong(me.getId());
                query = "SELECT userId FROM fb_user WHERE fbId = ?";
                userId = bridge.queryLong(query, fbId);
                if (userId == null) {
                    return registerFacebook(fbId, accessToken);
                } else {
                    query = "UPDATE fb_user SET fb_token = ? WHERE fbId = ?";
                    bridge.update(query, accessToken, fbId);
                }
                addFbInfo(me);
            }
            JsonObject response = VdefServices.authService.getUserInfo(userId);
            return response;
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject registerFacebook(long fbId, String accessToken) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            JsonObject json = new JsonObject();
            String password = StringUtil.generateRandomStringNumberCharacter(16);
            String username = new StringBuilder("fb.").append(fbId).toString();
            json.addProperty("username", username);
            json.addProperty("password", password);
            JsonObject response = VdefServices.authService.register(json);
            if (!BaseResponse.isSuccessFullMessage(response)) {
                return response;
            }
            JsonObject data = response.get("data").getAsJsonObject();
            long userId = data.get("id").getAsLong();
            String query = "INSERT INTO fb_user(fbId,userId,fb_token,fb_token_expired) VALUES(?,?,?,?)";
            bridge.update(query, fbId, userId, accessToken, 0);
            return response;
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public void addFbInfo(User me) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            JsonObject data = new JsonObject();
            data.addProperty("name",me.getName());
            data.addProperty("email",me.getEmail());
            data.addProperty("birthday",me.getBirthday());
            String query = "UPDATE fb_user SET fb_info = ? WHERE fbId = ?";
            bridge.update(query,data,me.getId());
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
        }
    }

    @Override
    public JsonObject getFbInfo(long userId) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT fb_info FROM fb_user WHERE userId = ?";
            JsonObject info = bridge.queryOne(query,userId);
            return BaseResponse.createFullMessageResponse(0,"success",info);
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
}
