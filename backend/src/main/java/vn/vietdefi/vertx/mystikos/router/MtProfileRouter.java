package vn.vietdefi.vertx.mystikos.router;

import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.json.GsonUtil;

public class MtProfileRouter {
    public static void getProfile(RoutingContext rc){
        try{
            long userId = Long.parseLong(rc.request().getHeader("userId"));
            JsonObject response = VdefServices.mtProfileService.getProfile(userId);
            rc.response().end(response.toString());

        }catch (Exception e) {
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse( 1, "system_error"  );
            rc.response().end(response.toString());
        }
    }
    public static void updateProfile(RoutingContext rc){
        try{
            long userId = Long.parseLong(rc.request().getHeader("userId"));

            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            json.addProperty("userId",userId);
            JsonObject response = VdefServices.mtProfileService.updateProfile(json);
            rc.response().end(response.toString());

        }catch (Exception e) {
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse( 1, "system_error"  );
            rc.response().end(response.toString());
        }
    }
    public static void chooseCardBack(RoutingContext rc){
        try{
            long userId = Long.parseLong(rc.request().getHeader("userId"));

            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            long cardBackId = json.get("cardback_id").getAsLong();
            JsonObject response = VdefServices.mtProfileService.chooseCardBack(userId,cardBackId);
            rc.response().end(response.toString());

        }catch (Exception e) {
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse( 1, "system_error"  );
            rc.response().end(response.toString());
        }
    }
}
