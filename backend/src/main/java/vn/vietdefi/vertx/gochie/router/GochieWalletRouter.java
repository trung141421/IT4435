package vn.vietdefi.vertx.gochie.router;

import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.json.GsonUtil;

public class GochieWalletRouter {
    public static void getWallet(RoutingContext rc){
        try{
            long userId = Long.parseLong(rc.request().getHeader("userId"));

            JsonObject response = VdefServices.gochieWalletService.getWallet(userId);
            rc.response().end(response.toString());
        }catch (Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void changeDiamondToCoin(RoutingContext rc){
        try{
            long userId = Long.parseLong(rc.request().getHeader("userId"));

            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);

            long diamond = json.get("diamond").getAsLong();
            long coin = json.get("coin").getAsLong();
            JsonObject response = VdefServices.gochieWalletService.changeDiamondToCoin(userId,diamond,coin);
            rc.response().end(response.toString());
        }catch (Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void addDiamond(RoutingContext rc){
        try{
            long userId = Long.parseLong(rc.request().getHeader("userId"));

            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            long diamond = json.get("diamond").getAsLong();

            JsonObject response = VdefServices.gochieWalletService.addDiamond(userId,diamond);
            rc.response().end(response.toString());
        }catch (Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void addPoint(RoutingContext rc){
        try{
            long userId = Long.parseLong(rc.request().getHeader("userId"));

            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            long point = json.get("point").getAsLong();

            JsonObject response = VdefServices.gochieWalletService.addPoint(userId,point);
            rc.response().end(response.toString());
        }catch (Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
}
