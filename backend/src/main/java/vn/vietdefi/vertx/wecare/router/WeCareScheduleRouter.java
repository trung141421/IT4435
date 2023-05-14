package vn.vietdefi.vertx.wecare.router;

import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.json.GsonUtil;
import vn.vietdefi.util.log.DebugLogger;

public class WeCareScheduleRouter {

    public static void acceptSchedule(RoutingContext rc) {
        try {
            long userId = Long.parseLong(rc.request().getHeader("userId"));
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.wecareScheduleService.acceptSchedule(userId, json);
            rc.response().end(response.toString());
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1, "system_error");
            rc.response().end(response.toString());
        }
    }

    public static void declineSchedule(RoutingContext rc) {
        try {
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.wecareScheduleService.declineSchedule(json);
            rc.response().end(response.toString());
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1, "system_error");
            rc.response().end(response.toString());
        }
    }

    public static void changeStatusSchedule(RoutingContext rc) {
        try {
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.wecareScheduleService.changeStatusSchedule(json);
            rc.response().end(response.toString());
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1, "system_error");
            rc.response().end(response.toString());
        }
    }
    public static void bookSchedule(RoutingContext rc) {
        try {
            long userId = Long.parseLong(rc.request().getHeader("userId"));
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.wecareScheduleService.bookSchedule(userId,json);
            rc.response().end(response.toString());
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1, "system_error");
            rc.response().end(response.toString());
        }
    }

    public static void bookScheduleFast(RoutingContext rc) {
        try {
            long userId = Long.parseLong(rc.request().getHeader("userId"));
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.wecareScheduleService.bookScheduleFast(userId,json);
            rc.response().end(response.toString());
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1, "system_error");
            rc.response().end(response.toString());
        }
    }

    public static void getCustomerBookingSchedule(RoutingContext rc) {
        try {
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.wecareScheduleService.getCustomerBookingSchedule(json);
            rc.response().end(response.toString());
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1, "system_error");
            rc.response().end(response.toString());
        }
    }

    public static void getProviderBookingSchedule(RoutingContext rc) {
        try {
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.wecareScheduleService.getProviderBookingSchedule(json);
            rc.response().end(response.toString());
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1, "system_error");
            rc.response().end(response.toString());
        }
    }

    public static void createPersonalSchedule(RoutingContext rc) {
        try {
            long userId = Long.parseLong(rc.request().getHeader("userId"));
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            json.addProperty("userId", userId);
            JsonObject response = VdefServices.wecareScheduleService.createPersonalSchedule(json);
            rc.response().end(response.toString());
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1, "system_error");
            rc.response().end(response.toString());
        }
    }
    public static void getPersonalSchedule(RoutingContext rc) {
        try {
            long userId = Long.parseLong(rc.request().getHeader("userId"));
            JsonObject response = VdefServices.wecareScheduleService.getPersonalSchedule(userId);
            rc.response().end(response.toString());
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1, "system_error");
            rc.response().end(response.toString());
        }
    }
    public static void updatePersonalSchedule(RoutingContext rc) {
        try {
            String body = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(body);
            JsonObject response = VdefServices.wecareScheduleService.updatePersonalSchedule(json);
            rc.response().end(response.toString());
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1, "system_error");
            rc.response().end(response.toString());
        }
    }
}
