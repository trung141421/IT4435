package vn.vietdefi.services.vdef.auth;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;
import vn.vietdefi.util.string.StringUtil;

public class AuthService implements IAuthService{
    public JsonObject register(JsonObject json){
        try{
            String username = json.get("username").getAsString();
            String password = json.get("password").getAsString();
            return register(username, password);
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    private JsonObject register(String username, String password){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT id FROM user WHERE username = ?";

            boolean isExisted = bridge.queryExist(query, username);

            if(isExisted){
                return BaseResponse.createFullMessageResponse(10, "user_exist");
            }

            String token = StringUtil.generateRandomStringNumberCharacter(32);
            query = "INSERT INTO user (username, password, token, role, status, createTime) VALUE (?,?,?,?,?,?)";

            String hashedPassword = StringUtil.sha256(password);

            long createTime = System.currentTimeMillis();
            bridge.update(query, username, hashedPassword, token, 0, 0, createTime);
            return login(username, password);
        }catch (Exception e){
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    public JsonObject login(JsonObject json){
        try{
            String username = json.get("username").getAsString();
            String password = json.get("password").getAsString();
            return login(username, password);
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    private JsonObject login(String username, String password){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            String query = "SELECT * FROM user WHERE username = ?";

            JsonObject data = bridge.queryOne(query, username);

            if(data == null){
                return BaseResponse.createFullMessageResponse(10, "invalid_username");
            }
            String storedPassword = data.get("password").getAsString();
            String hashedPassword = StringUtil.sha256(password);
            if(!storedPassword.equals(hashedPassword)){
                return BaseResponse.createFullMessageResponse(11, "invalid_password");
            }

            data.remove("password");
            return BaseResponse.createFullMessageResponse(0, "success", data);
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    public JsonObject logout(long userId)  {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "UPDATE user SET token=? WHERE id=?";
            String token = StringUtil.generateRandomStringNumberCharacter(32);
            bridge.update(query, token, userId);
            return BaseResponse.createFullMessageResponse(0, "success");
        }catch (Exception e){
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject getUserInfo(long userId) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM user WHERE id = ?";
            JsonObject data = bridge.queryOne(query,userId);
            data.remove("password");
            return BaseResponse.createFullMessageResponse(0, "success", data);
        }catch(Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    public JsonObject authorize(long userId, String token) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM user WHERE token=? AND id = ?";
            JsonObject data = bridge.queryOne(query, token,userId);
            if(data == null){
                return BaseResponse.createFullMessageResponse(10, "invalid_token");
            }
            int status = data.get("status").getAsInt();
            if(status != 0){
                return BaseResponse.createFullMessageResponse(11, "account_locked", data);
            }
            data.remove("password");
            return BaseResponse.createFullMessageResponse(0, "success", data);
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    public JsonObject changePassword(JsonObject json){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String password = json.get("password").getAsString();
            String new_password = json.get("new_password").getAsString();
            String hash_password = StringUtil.sha256(password);
            String hash_new_password = StringUtil.sha256(new_password);
            long userid = json.get("userid").getAsLong();

            //check correct password
            String query = "SELECT password FROM user WHERE id = ?";
            JsonObject data = bridge.queryOne(query, userid);
            if(!data.get("password").getAsString().equals(hash_password)){
                return BaseResponse.createFullMessageResponse(2,"wrong_password");
            }

            //change password
            query = "UPDATE user SET password = ? WHERE id = ?";
            bridge.update(query, hash_new_password, userid);
            return BaseResponse.createFullMessageResponse(0,"success");
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject changeusername(JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String new_username = json.get("new_username").getAsString();
            String password = json.get("password").getAsString();
            String hash_password = StringUtil.sha256(password);
            long userid = json.get("userid").getAsLong();

            //check correct password
            String query = "SELECT password FROM user WHERE id = ?";
            JsonObject data = bridge.queryOne(query, userid);
            if(!data.get("password").getAsString().equals(hash_password)){
                return BaseResponse.createFullMessageResponse(2,"wrong_password");
            }

            //check username existed
            query = "SELECT id FROM user WHERE username = ?";
            boolean isExist = bridge.queryExist(query, new_username);
            if(isExist){
                return BaseResponse.createFullMessageResponse(3,"username_existed");
            }

            //change username
            query = "UPDATE user SET username = ? WHERE id = ?";
            bridge.update(query, new_username, userid);
            return BaseResponse.createFullMessageResponse(0,"success");
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    public JsonObject forgetPasswordSendUsername(String username){
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT id FROM user WHERE username = ? ";
            JsonObject data = bridge.queryOne(query, username);
            if (data == null) {
                return BaseResponse.createFullMessageResponse(2, "username_not_found");
            } else {
                long userId = data.get("id").getAsLong();
                JsonObject telegramInfo = VdefServices.telegramService.getTelegramInfo(userId);
                if (telegramInfo != null) {
                    data.addProperty("telegram_fullname", telegramInfo.get("telegram_info").getAsString());
                }
                JsonObject messengerInfo = VdefServices.messengerService.checkLinkMessenger(userId);
                if (BaseResponse.isSuccessFullMessage(messengerInfo)) {
                    if(messengerInfo.get("data").isJsonNull() ) data.addProperty("messenger_fullname", (String) null);
                    else {
                        JsonObject fb_info = messengerInfo.get("data").getAsJsonObject().get("fb_info").getAsJsonObject();
                        data.addProperty("messenger_fullname", fb_info.get("name").getAsString());
                    }
                }
                if( (!data.has("telegram_fullname")) && (!data.has("messenger_fullname")) ) {
                    return BaseResponse.createFullMessageResponse(12, "link_messenger_telegram_first", data);
                }else if(!data.has("telegram_fullname")){
                    return BaseResponse.createFullMessageResponse(11, "link_telegram_first", data);
                }else if(!data.has("messenger_fullname")){
                    return BaseResponse.createFullMessageResponse(10, "link_messenger_first", data);
                }else{
                    return BaseResponse.createFullMessageResponse(0, "success", data);
                }
            }
        }
            catch (Exception e){
                String stacktrace = ExceptionUtils.getStackTrace(e);
                DebugLogger.error(stacktrace);
                return BaseResponse.createFullMessageResponse(1, "system_error");
            }
    }

    @Override
    public JsonObject methodGetOtp(JsonObject json) {
        try{
            Long userId = json.get("userid").getAsLong();
            int method = json.get("method").getAsInt();
            if(method == 1){
                return VdefServices.otpService.sendMessengerOtp(userId);
            }
            else if(method == 2){
                VdefServices.telegramService.sendTelegramOTP(userId);
                return BaseResponse.createFullMessageResponse(0, "success");
            }else{
                return  BaseResponse.createFullMessageResponse(12, "wrong_method");
            }
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    public JsonObject createNewPassword(JsonObject data) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            Long userId = data.get("userid").getAsLong();
            String newPassword = data.get("new_password").getAsString();
            String hashNewPassword = StringUtil.sha256(newPassword);
            String query = "UPDATE user SET password = ? WHERE id = ?";
            bridge.queryOne(query, hashNewPassword, userId);
            return BaseResponse.createFullMessageResponse(0,"success");
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
}
