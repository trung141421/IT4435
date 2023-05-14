package vn.vietdefi.services.vdef.auth.facebook.messenger;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;
import vn.vietdefi.util.string.StringUtil;

public class MessengerService implements IMessengerService{
    @Override
    public JsonObject sendActiveCode(long userId) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            String query = "SELECT active_code, active_code_expired FROM messenger_user WHERE userId = ?";
            JsonObject info = bridge.queryOne(query,userId);
            String activeCode = info.get("active_code").getAsString();
            long active_code_expired = info.get("active_code_expired").getAsLong();

            JsonObject data = new JsonObject();
            data.addProperty("active_code",activeCode);
            data.addProperty("active_code_expired",active_code_expired);
            data.addProperty("link_page","https://www.facebook.com/profile.php?id=100067070558829");

            return BaseResponse.createFullMessageResponse(0,"success",data);

        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject linkMessenger(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long userId = json.get("userId").getAsLong();
            String query = "SELECT active_code_expired FROM messenger_user WHERE userid = ?";
            Long activeCodeExpired = bridge.queryLong(query,userId);

            String active_code = StringUtil.generateRandomStringNumberCharacter(6);

            if(activeCodeExpired == null){
                query = "SELECT username from user WHERE id = ?";
                JsonObject data = bridge.queryOne(query,userId);
                String username = data.get("username").getAsString();

                query = "INSERT INTO messenger_user(userId,username,active_code,active_code_expired) VALUES(?,?,?,?)";
                bridge.update(query,userId,username,active_code,System.currentTimeMillis()/1000+300);
            }
            else if(activeCodeExpired<System.currentTimeMillis()/1000){
                query = "UPDATE messenger_user SET active_code = ?, active_code_expired = ?";
                bridge.update(query,active_code,System.currentTimeMillis()/1000+300);
            }
            return sendActiveCode(userId);

        }catch(Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject checkLinkMessenger(long userId) {
        try{
            JsonObject info = getMessengerInfo(userId);
            return info;
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject getMessengerInfo(long userId) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT messenger_info FROM messenger_user WHERE userId = ? ";
            JsonObject info = bridge.queryOne(query,userId);
            return BaseResponse.createFullMessageResponse(0,"success",info);

        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

}
