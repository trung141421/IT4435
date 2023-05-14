package vn.vietdefi.vertx.izihouse.router;


import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.json.GsonUtil;
import vn.vietdefi.util.log.DebugLogger;


public class IzihouseHouseRouter {
    public static void createHouse(RoutingContext rc) {
        try{
            int userid = Integer.parseInt(rc.request().getHeader("userid"));
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            json.addProperty("userid", userid);
            JsonObject response = VdefServices.izihouseHouseService.createHouse(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void createRoom(RoutingContext rc){
        try{
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.izihouseHouseService.createRoom(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void getHouseInfo(RoutingContext rc){
        try{
            int house_id = Integer.parseInt(rc.request().getParam("house_id"));
            JsonObject json = new JsonObject();
            json.addProperty("house_id", house_id);
            JsonObject response = VdefServices.izihouseHouseService.getHouseInfo(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void getRoomInfo(RoutingContext rc){
        try{
            int room_id = Integer.parseInt(rc.request().getParam("room_id"));
            JsonObject json = new JsonObject();
            json.addProperty("room_id", room_id);
            JsonObject response = VdefServices.izihouseHouseService.getRoomInfo(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void getHouseOfUser(RoutingContext rc){
        try{
            int userid = Integer.parseInt(rc.request().getHeader("userid"));
            int offset = Integer.parseInt(rc.request().getParam("offset"));
            JsonObject json = new JsonObject();
            json.addProperty("userid", userid);
            json.addProperty("offset", offset);
            JsonObject response = VdefServices.izihouseHouseService.getHousesOfUser(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void getRoomOfHouse(RoutingContext rc){
        try{
            int houseid = Integer.parseInt(rc.request().getParam("house_id"));
            int offset = Integer.parseInt(rc.request().getParam("offset"));
            JsonObject json = new JsonObject();
            json.addProperty("house_id", houseid);
            json.addProperty("offset", offset);
            JsonObject response = VdefServices.izihouseHouseService.getRoomsOfHouse(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void updateHouseInfo(RoutingContext rc){
        try{
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.izihouseHouseService.updateHouseInfo(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void updateRoomInfo(RoutingContext rc){
        try{
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.izihouseHouseService.updateRoomInfo(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void deleteHouse(RoutingContext rc){
        try{
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.izihouseHouseService.deleteHouse(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void deleteRoom(RoutingContext rc){
        try{
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.izihouseHouseService.deleteRoom(json);
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
