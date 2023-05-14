package vn.vietdefi.vertx.mystikos.router;

import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.json.GsonUtil;

public class MtTarotRouter {
    public static void dailyCard(RoutingContext rc){
        try{
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);

            JsonObject response = VdefServices.mtTarotService.dailyCard(json);
            rc.response().end(response.toString());
        }catch (Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
}
