package vn.vietdefi.vertx.gochie.router;

import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.json.GsonUtil;


public class GochieAuthRouter {
    public static void checkExistUsername(RoutingContext rc){
        try{
            String data = rc.body().asString();
            JsonObject body = GsonUtil.toJsonObject(data);
            String username = body.get("username").getAsString();
            JsonObject response = VdefServices.gochieAuthService.checkExistUsername(username);
            rc.response().end(response.toString());
        }catch (Exception e){

        }
    }
    public static void authorizeSales(RoutingContext rc){
        try {
            long userId = Long.parseLong(rc.request().getHeader("userid"));
            String token = rc.request().getHeader("token");
            JsonObject response = VdefServices.gochieAuthService.authorize(userId, token);
            if (BaseResponse.isSuccessFullMessage(response)) {

                JsonObject user = response.getAsJsonObject("data");
                int role = user.get("role").getAsInt();
                if (role == 1 || role == 2 || role == 3) {
                    rc.request().headers().add("fullname", user.get("fullname").getAsString());
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
            long userId = Long.parseLong(rc.request().getHeader("userid"));
            String token = rc.request().getHeader("token");
            JsonObject response = VdefServices.gochieAuthService.authorize(userId, token);
            if (BaseResponse.isSuccessFullMessage(response)) {

                JsonObject user = response.getAsJsonObject("data");
                int role = user.get("role").getAsInt();
                if (role == 2 || role == 3) {
                    rc.request().headers().add("userId", user.get("userId").getAsString());
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
            long userId = Long.parseLong(rc.request().getHeader("userid"));
            String token = rc.request().getHeader("token");
            JsonObject response = VdefServices.gochieAuthService.authorize(userId, token);
            if (BaseResponse.isSuccessFullMessage(response)) {

                JsonObject user = response.getAsJsonObject("data");
                int role = user.get("role").getAsInt();
                System.out.println(role);
                if (role == 3) {
                    rc.request().headers().add("fullname", user.get("fullname").getAsString());
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
    public static void getProfile(RoutingContext rc){
        try{
            long userId = Long.parseLong(rc.request().getHeader("userId"));

            JsonObject response = VdefServices.gochieAuthService.getProfile(userId);
            rc.response().end(response.toString());
        }catch(Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
            rc.response().end(response.toString());
        }
    }
    public static void createProfile(RoutingContext rc){
        try{
            long userId = Long.parseLong(rc.request().getHeader("userId"));

            JsonObject response = VdefServices.gochieAuthService.createProfile(userId);
            rc.response().end(response.toString());

        }catch (Exception e) {
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse( 1, "system_error"  );
            rc.response().end(response.toString());
        }
    }
    public static void createSalesProfile(RoutingContext rc) {
        try {
            long userId = Long.parseLong(rc.request().getHeader("userId"));
            JsonObject json = new JsonObject();
            json.addProperty("userId",userId);
            JsonObject response = VdefServices.gochieAuthService.createSalesProfile(json);
            rc.response().end(response.toString());
        }catch (Exception e) {
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse( 1, "system_error"  );
            rc.response().end(response.toString());
        }
    }
    public static void createManagerProfile(RoutingContext rc) {
        try {
            long userId = Long.parseLong(rc.request().getHeader("userId"));
            JsonObject json = new JsonObject();
            json.addProperty("userId", userId);
            JsonObject response = VdefServices.gochieAuthService.createManagerProfile(json);
            rc.response().end(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1, "system_error");
            rc.response().end(response.toString());
        }
    }
}

