package vn.vietdefi.services.izihouse.profile;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;

public class IzihouseNotiService implements IIzihouseNotiService{

    @Override
    public JsonObject createNoti(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "INSERT INTO iz_notification (user_id, type, post_id, title, content, created_time) VALUES (?,?,?,?,?,?)";
            long user_id = json.get("user_id").getAsLong();
            int type = json.get("type").getAsInt();
            long post_id = json.get("post_id").getAsLong();
            String title = json.get("title").getAsString();
            String content = json.get("content").getAsString();
            long created_time = System.currentTimeMillis();
            bridge.update(query, user_id, type, post_id, title, content, created_time);
            return BaseResponse.createFullMessageResponse(0,"success");
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject getNoti(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM iz_notification WHERE user_id = ?";
            long user_id = json.get("user_id").getAsLong();
            JsonArray data = bridge.query(query, user_id);
            return BaseResponse.createFullMessageResponse(0, "success", data);
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }
}
