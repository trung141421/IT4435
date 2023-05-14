package vn.vietdefi.vertx.godoo.router;

import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.json.GsonUtil;
import vn.vietdefi.util.log.DebugLogger;

public class GodooProfileRouter {
    public static void compulsoryInfo(RoutingContext rc) {
        try{
            Long userId = Long.parseLong(rc.request().getHeader("userid"));
            String body = rc.body().asString();
            JsonObject data = GsonUtil.toJsonObject(body);
            data.addProperty("userid",userId);
            JsonObject response = VdefServices.godooProfileService.compulsoryInfo(data);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void getProfile(RoutingContext rc) {
        try{
            Long userId = Long.parseLong(rc.request().getHeader("userid"));
            JsonObject response = VdefServices.godooProfileService.getProfile(userId);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void updateAdditionalInfo(RoutingContext rc){
        try{
            Long userId = Long.parseLong(rc.request().getHeader("userid"));
            String body = rc.body().asString();
            JsonObject data = GsonUtil.toJsonObject(body);
            data.addProperty("userid",userId);
            JsonObject response = VdefServices.godooProfileService.updateAdditionalInfo(data);
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
