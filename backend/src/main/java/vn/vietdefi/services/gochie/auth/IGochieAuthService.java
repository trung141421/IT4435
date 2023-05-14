package vn.vietdefi.services.gochie.auth;

import com.google.gson.JsonObject;

public interface IGochieAuthService {
    JsonObject checkExistUsername(String username);
    JsonObject authorize(long userId, String token);
    JsonObject createProfile(long userId);
    JsonObject getProfile(long userId);
    JsonObject createSalesProfile(JsonObject json);
    JsonObject createManagerProfile(JsonObject json);

}
