package vn.vietdefi.vertx.izihouse.router;

import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.json.GsonUtil;
import vn.vietdefi.util.log.DebugLogger;

public class IzihouseProfileRouter {
    public static void getProfile(RoutingContext rc) {
        try{
            int userid = Integer.parseInt(rc.request().getHeader("userid"));
            JsonObject json = new JsonObject();
            json.addProperty("userid", userid);
            JsonObject response = VdefServices.izihouseProfileService.getProfile(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void createProfile(RoutingContext rc){
        try {
            int userid = Integer.parseInt(rc.request().getHeader("userid"));
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            json.addProperty("userid", userid);
            JsonObject response = VdefServices.izihouseProfileService.createProfile(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void updateProfile(RoutingContext rc){
        try {
            int userid = Integer.parseInt(rc.request().getHeader("userid"));
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            json.addProperty("userid", userid);
            JsonObject response = VdefServices.izihouseProfileService.updateProfile(json);
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
