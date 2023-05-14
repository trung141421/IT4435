package vn.vietdefi.vertx.vdef.router;

import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.json.GsonUtil;
import vn.vietdefi.util.log.DebugLogger;

public class AuthRouter {
    public static void authorizeUser(RoutingContext rc) {
        try {
            long userid = Long.parseLong(rc.request().getHeader("userid"));
            String token = rc.request().getHeader("token");
            JsonObject response = VdefServices.authService.authorize(userid, token);
            if (BaseResponse.isSuccessFullMessage(response)) {
                JsonObject user = response.getAsJsonObject("data");
                rc.request().headers().add("username", user.get("username").getAsString());
                rc.request().headers().add("role", user.get("role").getAsString());
                rc.next();
            } else {
                response = BaseResponse.createFullMessageResponse(
                        2, "unauthorized"
                );
                rc.response().end(response.toString());
            }
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
            rc.response().end(response.toString());
        }
    }
    public static void authorizeSales(RoutingContext rc){
        try {
            long userid = Long.parseLong(rc.request().getHeader("userid"));
            String token = rc.request().getHeader("token");
            JsonObject response = VdefServices.authService.authorize(userid, token);
            if (BaseResponse.isSuccessFullMessage(response)) {
                JsonObject user = response.getAsJsonObject("data");
                int role = user.get("role").getAsInt();
                if (role == 1 || role == 2 || role == 3) {
                    rc.request().headers().add("username", user.get("username").getAsString());
                    rc.request().headers().add("role", user.get("role").getAsString());
                    rc.next();
                } else {
                    response = BaseResponse.createFullMessageResponse(
                            2, "unauthorized"
                    );
                    rc.response().end(response.toString());
                }
            } else {
                response = BaseResponse.createFullMessageResponse(
                        2, "unauthorized"
                );
                rc.response().end(response.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
            rc.response().end(response.toString());
        }
    }
    public static void authorizeManager(RoutingContext rc) {
        try {
            long userid = Long.parseLong(rc.request().getHeader("userid"));
            String token = rc.request().getHeader("token");
            JsonObject response = VdefServices.authService.authorize(userid, token);
            if (BaseResponse.isSuccessFullMessage(response)) {
                JsonObject user = response.getAsJsonObject("data");
                int role = user.get("role").getAsInt();
                if (role == 2 || role == 3) {
                    rc.request().headers().add("username", user.get("username").getAsString());
                    rc.request().headers().add("role", user.get("role").getAsString());
                    rc.next();
                } else {
                    response = BaseResponse.createFullMessageResponse(
                            2, "unauthorized"
                    );
                    rc.response().end(response.toString());
                }
            } else {
                response = BaseResponse.createFullMessageResponse(
                        2, "unauthorized"
                );
                rc.response().end(response.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
            rc.response().end(response.toString());
        }
    }
    public static void authorizeDirector(RoutingContext rc){
        try {
            long userid = Long.parseLong(rc.request().getHeader("userid"));
            String token = rc.request().getHeader("token");
            JsonObject response = VdefServices.authService.authorize(userid, token);
            if (BaseResponse.isSuccessFullMessage(response)) {
                JsonObject user = response.getAsJsonObject("data");
                int role = user.get("role").getAsInt();
                if (role == 3) {
                    rc.request().headers().add("username", user.get("username").getAsString());
                    rc.request().headers().add("role", user.get("role").getAsString());
                    rc.next();
                } else {
                    response = BaseResponse.createFullMessageResponse(
                            2, "unauthorized"
                    );
                    rc.response().end(response.toString());
                }
            } else {
                response = BaseResponse.createFullMessageResponse(
                        2, "unauthorized"
                );
                rc.response().end(response.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
            rc.response().end(response.toString());
        }
    }
    public static void authorizeAdmin(RoutingContext rc) {
        try {
            int userid = Integer.parseInt(rc.request().getHeader("userid"));
            String token = rc.request().getHeader("token");
            JsonObject response = VdefServices.authService.authorize(userid, token);
            if (BaseResponse.isSuccessFullMessage(response)) {
                JsonObject user = response.getAsJsonObject("data");
                int role = user.get("role").getAsInt();
                if (role == 1) {
                    rc.request().headers().add("username", user.get("username").getAsString());
                    rc.request().headers().add("role", user.get("role").getAsString());
                    rc.next();
                } else {
                    response = BaseResponse.createFullMessageResponse(
                            2, "unauthorized"
                    );
                    rc.response().end(response.toString());
                }
            } else {
                response = BaseResponse.createFullMessageResponse(
                        2, "unauthorized"
                );
                rc.response().end(response.toString());
            }
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
            rc.response().end(response.toString());
        }
    }

    public static void authorizeSuperAdmin(RoutingContext rc) {
        try {
            int userid = Integer.parseInt(rc.request().getHeader("userid"));
            String token = rc.request().getHeader("token");
            JsonObject response = VdefServices.authService.authorize(userid, token);
            if (BaseResponse.isSuccessFullMessage(response)) {
                JsonObject user = response.getAsJsonObject("data");
                int role = user.get("role").getAsInt();
                if (role == 2) {
                    rc.request().headers().add("username", user.get("username").getAsString());
                    rc.request().headers().add("role", user.get("role").getAsString());
                    rc.next();
                } else {
                    response = BaseResponse.createFullMessageResponse(
                            2, "unauthorized"
                    );
                    rc.response().end(response.toString());
                }
            } else {
                response = BaseResponse.createFullMessageResponse(
                        2, "unauthorized"
                );
                rc.response().end(response.toString());
            }
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
            rc.response().end(response.toString());
        }
    }
    public static void register(RoutingContext rc) {
        try {
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            JsonObject response = VdefServices.authService.register(json);
            rc.response().end(response.toString());
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
            rc.response().end(response.toString());
        }
    }
    public static void login(RoutingContext rc) {
        try {
            /// Lấy request
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            JsonObject response = VdefServices.authService.login(json);
            rc.response().end(response.toString());
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
        }
    }
    public static void logout(RoutingContext rc) {
        try {
            int userid = Integer.parseInt(rc.request().getHeader("userid"));
            JsonObject response = VdefServices.authService.logout(userid);
            rc.response().end(response.toString());
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
            rc.response().end(response.toString());
        }

    }

    public static void changePassword(RoutingContext rc) {
        try {
            int userid = Integer.parseInt(rc.request().getHeader("userid"));
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            json.addProperty("userid", userid);
            JsonObject response = VdefServices.authService.changePassword(json);
            rc.response().end(response.toString());
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
            rc.response().end(response.toString());
        }
    }

    public static void changeUsername(RoutingContext rc) {
        try {
            int userid = Integer.parseInt(rc.request().getHeader("userid"));
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            json.addProperty("userid", userid);
            JsonObject response = VdefServices.authService.changeusername(json);
            rc.response().end(response.toString());
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
            rc.response().end(response.toString());
        }
    }
    public static void forgetPasswordSendUsername(RoutingContext rc){
        try {
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            String username = json.get("username").getAsString();
            JsonObject response = VdefServices.authService.forgetPasswordSendUsername(username);
            rc.response().end(response.toString());
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
            rc.response().end(response.toString());
        }
    }
    public static void methodGetOtp(RoutingContext rc){
        try{
            Long userId = Long.parseLong(rc.request().getHeader("userid"));
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            json.addProperty("userid",userId);
            JsonObject response = VdefServices.authService.methodGetOtp(json);
            rc.response().end(response.toString());
        }catch (Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse( 1, "system_error"  );
            rc.response().end(response.toString());
        }
    }
    public static void createNewPassword(RoutingContext rc){
        try{
            Long userId = Long.parseLong(rc.request().getHeader("userid"));
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            json.addProperty("userid",userId);
            JsonObject response = VdefServices.authService.createNewPassword(json);
            rc.response().end(response.toString());
        }catch (Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse( 1, "system_error"  );
            rc.response().end(response.toString());
        }
    }
    
}
