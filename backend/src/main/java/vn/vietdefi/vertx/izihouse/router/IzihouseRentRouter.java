package vn.vietdefi.vertx.izihouse.router;

import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.json.GsonUtil;
import vn.vietdefi.util.log.DebugLogger;

public class IzihouseRentRouter {
    public static void createRequest(RoutingContext rc) {
        try{
            int userid = Integer.parseInt(rc.request().getHeader("userid"));
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            json.addProperty("userid", userid);
            JsonObject response = VdefServices.iIzihouseRentService.createRequest(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void getRequestOfRenter(RoutingContext rc) {
        try{
            int userid = Integer.parseInt(rc.request().getHeader("userid"));
            JsonObject json = new JsonObject();
            json.addProperty("userid", userid);
            JsonObject response = VdefServices.iIzihouseRentService.getRequestOfRenter(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void deleteRequest(RoutingContext rc){
        try{
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.iIzihouseRentService.deleteRequest(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void acceptRequest(RoutingContext rc){
        try{
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            long user_id = Long.parseLong(rc.request().getHeader("userid"));
            json.addProperty("user_id",user_id);
            JsonObject response = VdefServices.iIzihouseRentService.acceptRequest(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void getRequestOfHouse(RoutingContext rc){
        try{
            int house_id = Integer.parseInt(rc.request().getParam("house_id"));
            JsonObject json = new JsonObject();
            json.addProperty("house_id", house_id);
            JsonObject response = VdefServices.iIzihouseRentService.getRequestOfHouse(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void getRequestOfRoom(RoutingContext rc){
        try{
            int house_id = Integer.parseInt(rc.request().getParam("room_id"));
            JsonObject json = new JsonObject();
            json.addProperty("room_id", house_id);
            JsonObject response = VdefServices.iIzihouseRentService.getRequestOfRoom(json);
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
