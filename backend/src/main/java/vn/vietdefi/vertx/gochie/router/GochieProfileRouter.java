package vn.vietdefi.vertx.gochie.router;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import vn.vietdefi.util.json.GsonUtil;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.util.log.DebugLogger;

public class GochieProfileRouter {
    public static void updatePersonalInfo(RoutingContext rc){
        try{
            long userId = Long.parseLong(rc.request().getHeader("userId"));
            String token = rc.request().getHeader("token");
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            json.addProperty("userId",userId);
            json.addProperty("token",token);
            JsonObject response = VdefServices.gochieProfileService.updatePersonalProfile(json);
            rc.response().end(response.toString());
        }catch(Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
            rc.response().end(response.toString());
        }
    }
    public static void updateShoppingPreferences(RoutingContext rc){
        try{
            long userId = Long.parseLong(rc.request().getHeader("userId"));

            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            System.out.println(json);
            JsonArray shopping_preferences = json.get("shopping_preferences").getAsJsonArray();
            JsonObject response = VdefServices.gochieProfileService.updateShoppingPreferences(userId,shopping_preferences);
            rc.response().end(response.toString());
        }catch(Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
            rc.response().end(response.toString());
        }
    }
    public static void playerCheckShopInfo(RoutingContext rc){
        try{
            long userId = Long.parseLong(rc.request().getHeader("userId"));
            long shop_id = Long.parseLong(rc.request().getParam("shopid"));

            JsonObject response = VdefServices.gochieProfileService.playerCheckShopInfo(userId,shop_id);
            rc.response().end(response.toString());
        }catch(Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
            rc.response().end(response.toString());
        }
    }
    public static void playerCheckTransactionHistory(RoutingContext rc){
        try{
            long userId = Long.parseLong(rc.request().getHeader("userId"));

            JsonObject response = VdefServices.gochieProfileService.playerCheckTransactionHistory(userId);
            rc.response().end(response.toString());
        }catch(Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
            rc.response().end(response.toString());
        }
    }
    public static void managerCheckShopInfo(RoutingContext rc){
        try{
            long userId = Long.parseLong(rc.request().getHeader("userId"));

            JsonObject response = VdefServices.gochieProfileService.managerCheckShopInfo(userId);
            rc.response().end(response.toString());
        }catch(Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
            rc.response().end(response.toString());
        }
    }
}
