package trung.room.vertx.router;

import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import trung.common.BaseResponse;
import trung.room.services.TrungServices;
import trung.util.json.GsonUtil;

public class RoomRouter {
    public static void getRoom(RoutingContext rc) {
        try {
            JsonObject response = TrungServices.roomService.getRoom();
            rc.response().end(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
            rc.response().end(response.toString());
        }
    }

    public static void createRoom(RoutingContext rc) {
        try {
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);

            String name = json.get("name").getAsString();
            String address = json.get("address").getAsString();
            String description = json.get("description").getAsString();
            int price = json.get("price").getAsInt();

            JsonObject response = TrungServices.roomService.createRoom(name, address, description, price);
            rc.response().end(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
            rc.response().end(response.toString());
        }
    }

    public static void deleteRoom(RoutingContext rc) {
        try {
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);

            int room_id = json.get("room_id").getAsInt();

            JsonObject response = TrungServices.roomService.deleteRoom(room_id);
            rc.response().end(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
            rc.response().end(response.toString());
        }
    }

    public static void editRoom(RoutingContext rc) {
        try {
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            int room_id = json.get("room_id").getAsInt();
            String name = json.get("name").getAsString();
            String address = json.get("address").getAsString();
            String description = json.get("description").getAsString();
            int price = json.get("price").getAsInt();
            String renter_name = json.get("renter_name").getAsString();

            JsonObject response = TrungServices.roomService.editRoom(room_id, name, address, description, price, renter_name);
            rc.response().end(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
            rc.response().end(response.toString());
        }
    }
}
