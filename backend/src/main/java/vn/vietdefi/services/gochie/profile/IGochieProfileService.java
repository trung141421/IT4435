package vn.vietdefi.services.gochie.profile;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public interface IGochieProfileService {
    public JsonObject updatePersonalProfile(JsonObject data);
    public JsonObject updateShoppingPreferences(long userId,JsonArray data);
    public JsonObject playerCheckShopInfo (long userid, long shop_id);
    public JsonObject playerCheckTransactionHistory (long userid);
    public JsonObject managerCheckShopInfo (long userid);
}
