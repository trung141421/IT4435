package vn.vietdefi.vertx.godoo.router;

import com.google.gson.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.json.GsonUtil;
import vn.vietdefi.util.log.DebugLogger;

public class GodooMatchRouter {
    public static void like(RoutingContext rc){
        try {
            Long userId = Long.parseLong(rc.request().getHeader("userid"));
            String body = rc.body().asString();
            JsonObject data = GsonUtil.toJsonObject(body);
            data.addProperty("user_id_1",userId);
            JsonObject response = VdefServices.godooMatchService.like(data);
            rc.response().end(response.toString());
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void setlove(RoutingContext rc){
        try {
            Long userId = Long.parseLong(rc.request().getHeader("userid"));
            String body = rc.body().asString();
            JsonObject data = GsonUtil.toJsonObject(body);
            data.addProperty("user_id_1",userId);
            JsonObject response = VdefServices.godooMatchService.setLove(data);
            rc.response().end(response.toString());
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void getYouLiked(RoutingContext rc){
        try {
            Long userId = Long.parseLong(rc.request().getHeader("userid"));
            JsonObject response = VdefServices.godooMatchService.getYouLiked(userId);
            rc.response().end(response.toString());
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void getLikedYou(RoutingContext rc){
        try {
            Long userId = Long.parseLong(rc.request().getHeader("userid"));
            JsonObject response = VdefServices.godooMatchService.getLikedYou(userId);
            rc.response().end(response.toString());
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void endMatch(RoutingContext rc){
        try {
            Long userId = Long.parseLong(rc.request().getHeader("userid"));
            String body = rc.body().asString();
            JsonObject data = GsonUtil.toJsonObject(body);
            data.addProperty("user_id_1",userId);
            JsonObject response = VdefServices.godooMatchService.endMatch(data);
            rc.response().end(response.toString());
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void setSchedule(RoutingContext rc){
        try {
            Long userId = Long.parseLong(rc.request().getHeader("userid"));
            String body = rc.body().asString();
            JsonObject data = GsonUtil.toJsonObject(body);
            data.addProperty("user_id_1",userId);
            JsonObject response = VdefServices.godooMatchService.setSchedule(data);
            rc.response().end(response.toString());
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void changeSchedule(RoutingContext rc){
        try {
            String body = rc.body().asString();
            JsonObject data = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.godooMatchService.changeSchedule(data);
            rc.response().end(response.toString());
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void hide(RoutingContext rc){
        try{
            Long userId = Long.parseLong(rc.request().getHeader("userid"));
            String body = rc.body().asString();
            JsonObject data = GsonUtil.toJsonObject(body);
            data.addProperty("user_id_1",userId);
            JsonObject response = VdefServices.godooMatchService.hide(data);
            rc.response().end(response.toString());
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void report(RoutingContext rc){
        try{
            Long userId = Long.parseLong(rc.request().getHeader("userid"));
            String body = rc.body().asString();
            JsonObject data = GsonUtil.toJsonObject(body);
            data.addProperty("user_id_1",userId);
            JsonObject response = VdefServices.godooMatchService.report(data);
            rc.response().end(response.toString());
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
}
