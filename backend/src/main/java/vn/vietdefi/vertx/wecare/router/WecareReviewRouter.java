package vn.vietdefi.vertx.wecare.router;

import com.google.gson.JsonObject;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.json.GsonUtil;
import vn.vietdefi.util.log.DebugLogger;

public class WecareReviewRouter {
    public static void reviewCustomer(RoutingContext rc) {
        try {
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.wecareReviewService.reviewCustomer(json);
            rc.response().end(response.toString());
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1, "system_error");
            rc.response().end(response.toString());
        }
    }

    public static void reviewService(RoutingContext rc) {
        try {
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.wecareReviewService.reviewService(json);
            rc.response().end(response.toString());
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1, "system_error");
            rc.response().end(response.toString());
        }
    }

    public static void getCustomerReview(RoutingContext rc) {
        try {
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.wecareReviewService.getCustomerReview(json);
            rc.response().end(response.toString());
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1, "system_error");
            rc.response().end(response.toString());
        }
    }

    public static void getServiceReview(RoutingContext rc) {
        try{
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.wecareReviewService.getServiceReview(json);
            rc.response().end(response.toString());
        }catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1, "system_error");
            rc.response().end(response.toString());
        }
    }
}
