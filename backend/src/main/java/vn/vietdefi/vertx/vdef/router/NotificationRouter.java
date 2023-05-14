package vn.vietdefi.vertx.vdef.router;

import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.json.GsonUtil;
import vn.vietdefi.util.log.DebugLogger;

public class NotificationRouter {
    public static void createNotification(RoutingContext rc) {
        try{
                String data = rc.body().asString();
                JsonObject json = GsonUtil.toJsonObject(data);
                JsonObject response = VdefServices.gochieNotifier.createNotification(json);
                rc.response().end(response.toString());
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void sendNotification(RoutingContext rc){
        try{
                String data = rc.body().asString();
                JsonObject json = GsonUtil.toJsonObject(data);
                JsonObject response = VdefServices.gochieNotifier.sendNotification(json);
                rc.response().end(response.toString());
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }

    }
    public static void subscribeNotification(RoutingContext rc){
        try{
            Long userId = Long.parseLong(rc.request().getHeader("userid"));
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            json.addProperty("userId",userId);
            JsonObject response = VdefServices.gochieNotifier.subscribeNotification(userId,json);
            rc.response().end(response.toString());
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

}
