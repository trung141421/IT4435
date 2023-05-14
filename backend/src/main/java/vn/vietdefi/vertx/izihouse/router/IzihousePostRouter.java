package vn.vietdefi.vertx.izihouse.router;

import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.json.GsonUtil;
import vn.vietdefi.util.log.DebugLogger;

public class IzihousePostRouter {
    public static void createHousePost(RoutingContext rc){
        try{
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            long userid = Long.parseLong(rc.request().getHeader("userid"));
            json.addProperty("userid", userid);
            JsonObject response = VdefServices.iIzihousePostService.createHousePost(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void createRoomPost(RoutingContext rc){
        try{
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            long userid = Long.parseLong(rc.request().getHeader("userid"));
            json.addProperty("userid", userid);
            JsonObject response = VdefServices.iIzihousePostService.createRoomPost(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void getPostInfo(RoutingContext rc){
        try{
            JsonObject json = new JsonObject();
            long post_id = Long.parseLong(rc.request().getParam("post_id"));
            json.addProperty("post_id", post_id);
            JsonObject response = VdefServices.iIzihousePostService.getPostInfo(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void getAllPost(RoutingContext rc){
        try{
            JsonObject json = new JsonObject();
            long offset = Long.parseLong(rc.request().getParam("offset"));
            json.addProperty("offset", offset);
            JsonObject response = VdefServices.iIzihousePostService.getAllPost(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void hidePost(RoutingContext rc){
        try{
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.iIzihousePostService.hidePost(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void getHidePost(RoutingContext rc){
        try{
            JsonObject json = new JsonObject();
            long userid = Long.parseLong(rc.request().getHeader("userid"));
            json.addProperty("userid", userid);
            JsonObject response = VdefServices.iIzihousePostService.getHidePost(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void unhidePost(RoutingContext rc){
        try{
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.iIzihousePostService.unhidePost(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void deletePost(RoutingContext rc){
        try{
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.iIzihousePostService.deletePost(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void savePost(RoutingContext rc){
        try{
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            long userid = Long.parseLong(rc.request().getHeader("userid"));
            json.addProperty("userid", userid);
            JsonObject response = VdefServices.iIzihousePostService.savePost(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void getSavePost(RoutingContext rc){
        try{
            JsonObject json = new JsonObject();
            long userid = Long.parseLong(rc.request().getHeader("userid"));
            json.addProperty("userid", userid);
            JsonObject response = VdefServices.iIzihousePostService.getSavePost(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void unSavePost(RoutingContext rc){
        try{
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            long userid = Long.parseLong(rc.request().getHeader("userid"));
            json.addProperty("userid", userid);
            JsonObject response = VdefServices.iIzihousePostService.unSavePost(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void getUserPost(RoutingContext rc){
        try{
            JsonObject json = new JsonObject();
            long userid = Long.parseLong(rc.request().getHeader("userid"));
            long offset = Long.parseLong(rc.request().getParam("offset"));
            json.addProperty("userid", userid);
            json.addProperty("offset", offset);
            JsonObject response = VdefServices.iIzihousePostService.getUserPost(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void getHouseListToPost(RoutingContext rc){
        try{
            JsonObject json = new JsonObject();
            long userid = Long.parseLong(rc.request().getHeader("userid"));
            long offset = Long.parseLong(rc.request().getParam("offset"));
            json.addProperty("userid", userid);
            json.addProperty("offset", offset);
            JsonObject response = VdefServices.iIzihousePostService.getHouseListToPost(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void getRoomListToPost(RoutingContext rc){
        try{
            JsonObject json = new JsonObject();
            long house_id = Long.parseLong(rc.request().getParam("house_id"));
            long offset = Long.parseLong(rc.request().getParam("offset"));
            json.addProperty("house_id", house_id);
            json.addProperty("offset", offset);
            JsonObject response = VdefServices.iIzihousePostService.getRoomListToPost(json);
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
