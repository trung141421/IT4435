package vn.vietdefi.vertx.vdef.router;

import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.json.GsonUtil;

public class FacebookWebhookRouter {
    public static void webhookPost(RoutingContext rc){
        try{
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            String page = json.get("object").getAsString();
            JsonObject response;
            if(!page.equals("page")) {
                response = BaseResponse.createFullMessageResponse(2, "object_error");
                rc.response().end(response.toString());
            }
            response = VdefServices.facebookWebhook.webhookResponse(json);
            rc.response().end(response.toString());
        }catch (Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void webhookGet(RoutingContext rc){
        try{
            String mode = rc.request().getParam("hub.mode");
            String token = rc.request().getParam("hub.verify_token");
            String challenge = rc.request().getParam("hub.challenge");

            if(mode.equals("subscribe")&&token.equals("hai_token")){
                rc.response().end(challenge);
            }
        }catch(Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
}
