package vn.vietdefi.vertx.godoo.router;

import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.json.GsonUtil;
import vn.vietdefi.util.log.DebugLogger;

public class GodooGiftRouter {
    public static void createGiftTemplate(RoutingContext rc){
        try {
            String body = rc.body().asString();
            JsonObject data = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.godooGiftService.createGiftTemplate(data);
            rc.response().end(response.toString());
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void buyGift(RoutingContext rc){
        try {
            Long userId = Long.parseLong(rc.request().getHeader("userid"));
            String body = rc.body().asString();
            JsonObject data = GsonUtil.toJsonObject(body);
            data.addProperty("userid",userId);
            JsonObject response = VdefServices.godooGiftService.buyGift(data);
            rc.response().end(response.toString());
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void giveSwipe(RoutingContext rc){
        try{
            String body = rc.body().asString();
            JsonObject data = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.godooGiftService.giveSwipe(data);
            rc.response().end(response.toString());
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void giveChat(RoutingContext rc){
        try{
            String body = rc.body().asString();
            JsonObject data = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.godooGiftService.giveChat(data);
            rc.response().end(response.toString());
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void getBucket(RoutingContext rc){
        try{
            Long userId = Long.parseLong(rc.request().getHeader("userid"));
            String body = rc.body().asString();
            JsonObject data = GsonUtil.toJsonObject(body);
            data.addProperty("userid",userId);
            JsonObject response = VdefServices.godooGiftService.getBucket(data);
            rc.response().end(response.toString());
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void receiveGift(RoutingContext rc){
        try{
            Long receiverId = Long.parseLong(rc.request().getHeader("userid"));
            String body = rc.body().asString();
            JsonObject data = GsonUtil.toJsonObject(body);
            data.addProperty("receiverid",receiverId);
            JsonObject response = VdefServices.godooGiftService.receiveGift(data);
            rc.response().end(response.toString());
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void getGivenGift(RoutingContext rc){
        try{
            Long senderId = Long.parseLong(rc.request().getHeader("userid"));
            String body = rc.body().asString();
            JsonObject data = GsonUtil.toJsonObject(body);
            data.addProperty("senderid",senderId);
            JsonObject response = VdefServices.godooGiftService.getGivenGift(data);
            rc.response().end(response.toString());
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void getBeGivenGift(RoutingContext rc){
        try{
            Long receiverId = Long.parseLong(rc.request().getHeader("userid"));
            String body = rc.body().asString();
            JsonObject data = GsonUtil.toJsonObject(body);
            data.addProperty("receiverid",receiverId);
            JsonObject response = VdefServices.godooGiftService.getBeGivenGift(data);
            rc.response().end(response.toString());
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
}
