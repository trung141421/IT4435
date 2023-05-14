package vn.vietdefi.services.vdef.auth.google;

import com.google.gson.JsonObject;

public interface IGoogleService {
    JsonObject login(JsonObject jsonReq);
    JsonObject registerGoogle(String googleId);
    JsonObject getUserInfo(String access_token);
}
