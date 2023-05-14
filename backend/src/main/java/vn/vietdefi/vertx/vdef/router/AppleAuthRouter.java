package vn.vietdefi.vertx.vdef.router;

import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.services.vdef.auth.apple.AppleService;
import vn.vietdefi.util.json.GsonUtil;
import vn.vietdefi.util.log.DebugLogger;

public class AppleAuthRouter {
    public static void login(RoutingContext rc) {
        try{
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.appleService.loginApple(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void register(RoutingContext rc){
        try{
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.appleService.registerApple(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void getState(RoutingContext rc){
        try{
            JsonObject response = VdefServices.appleService.getState();
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void client_register(RoutingContext rc){
        try{
            String data = rc.body().asString();
            JsonObject response = VdefServices.appleService.client_register(data);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void loginApple(RoutingContext rc){
        try{
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.appleService.loginApple(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void authorize(RoutingContext rc) {
        try{
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            String code = json.get("code").getAsString();
            JsonObject response = VdefServices.appleService.authorize(code);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void refreshToken(RoutingContext rc) {
        try{
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.appleService.refreshToken(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
}
