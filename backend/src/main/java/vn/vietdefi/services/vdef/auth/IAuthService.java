package vn.vietdefi.services.vdef.auth;

import com.google.gson.JsonObject;
import io.vertx.core.json.Json;

public interface IAuthService {
    JsonObject register(JsonObject json);
    JsonObject login(JsonObject json);
    JsonObject authorize(long userId, String token);
    JsonObject logout(long userId);
    JsonObject getUserInfo(long userId);
    JsonObject changePassword(JsonObject json);
    JsonObject changeusername(JsonObject json);
    JsonObject forgetPasswordSendUsername(String username);
    JsonObject methodGetOtp(JsonObject json);
    JsonObject createNewPassword(JsonObject json);

}
