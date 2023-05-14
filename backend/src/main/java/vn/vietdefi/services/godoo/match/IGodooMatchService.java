package vn.vietdefi.services.godoo.match;

import com.google.gson.JsonObject;

public interface IGodooMatchService {
    JsonObject like(JsonObject data);
    JsonObject setLove(JsonObject data);
    JsonObject getYouLiked(long userId);
    JsonObject getLikedYou(long userId);
    JsonObject endMatch(JsonObject data);
    JsonObject setSchedule(JsonObject data);
    JsonObject changeSchedule(JsonObject data);
    JsonObject hide(JsonObject data);
    JsonObject report(JsonObject data);


}
