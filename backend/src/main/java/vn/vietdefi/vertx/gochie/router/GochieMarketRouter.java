package vn.vietdefi.vertx.gochie.router;

import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;

public class GochieMarketRouter {
    public static void getShopList(RoutingContext rc){
        try{
            long userId = Long.parseLong(rc.request().getHeader("userId"));

            JsonObject response = VdefServices.gochieMarketService.getShopList(userId);
            rc.response().end(response.toString());
        }catch(Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void getCategoryList(RoutingContext rc){
        try{
            long userId = Long.parseLong(rc.request().getHeader("userId"));

            JsonObject response = VdefServices.gochieMarketService.getCategoryList(userId);
            rc.response().end(response.toString());
        }catch(Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
}
