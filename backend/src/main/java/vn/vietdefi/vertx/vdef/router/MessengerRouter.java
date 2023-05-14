package vn.vietdefi.vertx.vdef.router;

import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.json.GsonUtil;

public class MessengerRouter {
    public static void linkMessenger(RoutingContext rc){
        try{
            long userId = Long.parseLong(rc.request().getHeader("userId"));

            JsonObject data = new JsonObject();
            data.addProperty("userId",userId);
            JsonObject response = VdefServices.messengerService.linkMessenger(data);
            rc.response().end(response.toString());
        }catch(Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void response(RoutingContext rc){
        try{
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);

            Long senderId = json.get("sender_id").getAsLong();
            String reply = json.get("reply").getAsString();
            JsonObject response = VdefServices.messengerBotService.responseMessage(senderId,reply);
            rc.response().end(response.toString());
        }catch (Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void checkLinkMessenger(RoutingContext rc){
        try{
            long userId = Long.parseLong(rc.request().getHeader("userId"));
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);

            JsonObject response = VdefServices.messengerService.checkLinkMessenger(userId);
            rc.response().end(response.toString());
        }catch (Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
}
