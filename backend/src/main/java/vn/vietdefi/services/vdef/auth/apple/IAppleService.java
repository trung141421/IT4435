package vn.vietdefi.services.vdef.auth.apple;

import com.google.gson.JsonObject;

public interface IAppleService {
    JsonObject getState();
    JsonObject client_register(String data);
    JsonObject authorize(String code);
    JsonObject refreshToken(JsonObject json);
    JsonObject loginApple(JsonObject json);
    JsonObject registerApple(JsonObject json);
}
