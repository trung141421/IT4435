package vn.vietdefi.vertx.wecare.router;

import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.json.GsonUtil;
import vn.vietdefi.util.log.DebugLogger;

public class WecareProfileRouter {
    public static void getProfile(RoutingContext rc) {
        try {
            long userId = Long.parseLong(rc.request().getHeader("userId"));
            JsonObject response = VdefServices.wecareProfileService.getProfile(userId);
            rc.response().end(response.toString());
        } catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void createProfile(RoutingContext rc) {
        try {
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            long userId = Long.parseLong(rc.request().getHeader("userId"));
            JsonObject response = VdefServices.wecareProfileService.createProfile(userId,json);
            rc.response().end(response.toString());
        } catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void updateProfile(RoutingContext rc) {
        try {
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            long userId = Long.parseLong(rc.request().getHeader("userId"));
            JsonObject response = VdefServices.wecareProfileService.updateProfile(userId,json);
            rc.response().end(response.toString());
        } catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void getProvider(RoutingContext rc) {
        try {
            long userId = Long.parseLong(rc.request().getHeader("userId"));
            JsonObject response = VdefServices.wecareProfileService.getProvider(userId);
            rc.response().end(response.toString());
        } catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void createProvider(RoutingContext rc) {
        try {
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            long userId = Long.parseLong(rc.request().getHeader("userId"));
            JsonObject response = VdefServices.wecareProfileService.createProvider(userId,json);
            rc.response().end(response.toString());
        } catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void setRoleProvider(RoutingContext rc) {
        try {
            Long userId = Long.parseLong(rc.request().getHeader("userId"));
            JsonObject response = VdefServices.wecareProfileService.setRoleProvider(userId);
        }catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1, "system_error");
            rc.response().end(response.toString());
        }
    }


    public static void updateProvider(RoutingContext rc) {
        try {
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            long userId = Long.parseLong(rc.request().getHeader("userId"));
            JsonObject response = VdefServices.wecareProfileService.updateProvider(userId,json);
            rc.response().end(response.toString());
        } catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
}
