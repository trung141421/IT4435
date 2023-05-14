package vn.vietdefi.services.godoo.gift;

import com.google.gson.JsonObject;
import io.vertx.core.json.Json;

public interface IGodooGiftService {
    JsonObject createGiftTemplate(JsonObject data);
    JsonObject buyGift(JsonObject data);
    JsonObject giveSwipe(JsonObject data);
    JsonObject giveChat(JsonObject data);
    JsonObject getBucket(JsonObject data);
    JsonObject receiveGift(JsonObject data);
    JsonObject getGivenGift(JsonObject data);
    JsonObject getBeGivenGift(JsonObject data);


}
