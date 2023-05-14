package vn.vietdefi.vertx.vdef.router;

import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.json.GsonUtil;
import vn.vietdefi.util.log.DebugLogger;

public class FacebookAuthRouter {
    public static void login(RoutingContext rc) {
        try {
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);

            JsonObject response = VdefServices.facebookService.login(json);
            rc.response().end(response.toString());
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject res = BaseResponse.createFullMessageResponse(1, "system_error");
            rc.response().end(res.toString());
        }
    }

    public static void getFbInfo(RoutingContext rc) {
        try {
            long userId = Long.parseLong(rc.request().getHeader("userId"));

            JsonObject response = VdefServices.facebookService.getFbInfo(userId);
            rc.response().end(response.toString());
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject res = BaseResponse.createFullMessageResponse(1, "system_error");
            rc.response().end(res.toString());
        }
    }
}
