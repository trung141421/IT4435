package trung.room.vertx.router;

import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import trung.util.json.GsonUtil;
import trung.common.BaseResponse;
import trung.room.services.TrungServices;

public class AuthRouter {

    public static void register(RoutingContext rc) {
        try {
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);

            String username = json.get("username").getAsString();
            String password = json.get("password").getAsString();

            JsonObject response = TrungServices.authService.register(username, password);
            rc.response().end(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
            rc.response().end(response.toString());
        }
    }
    public static void login(RoutingContext rc) {
        try {
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            String username = json.get("username").getAsString();
            String password = json.get("password").getAsString();

            JsonObject response = TrungServices.authService.login(username, password);
            rc.response().end(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
        }
    }
}
