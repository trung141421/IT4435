package vn.vietdefi.vertx.vdef.router;

import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.log.DebugLogger;


public class TelegramRouter {

    public static void requestLinkAccount(RoutingContext rc) {
        try {
            long userid = Long.parseLong(rc.request().getHeader("userid"));
            String username = rc.request().getHeader("username");
            JsonObject data = new JsonObject();
            data.addProperty("userid", userid);
            data.addProperty("username", username);
            JsonObject response = VdefServices.telegramService.requestLinkAccount(data);
            rc.response().end(response.toString());
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void sendTelegramOTP(RoutingContext rc) {
        try {
            long userId = Long.parseLong(rc.request().getHeader("userId"));

            JsonObject response = VdefServices.telegramService.sendTelegramOTP(userId);
            rc.response().end(response.toString());

        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void checkLinkTelegram(RoutingContext rc){
        try {
            long userId = Long.parseLong(rc.request().getHeader("userId"));
            JsonObject response = VdefServices.telegramService.checkLinkTelegram(userId);
            rc.response().end(response.toString());

        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
}
