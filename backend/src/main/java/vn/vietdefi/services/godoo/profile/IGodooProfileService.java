package vn.vietdefi.services.godoo.profile;

import com.google.gson.JsonObject;

public interface IGodooProfileService {
    JsonObject compulsoryInfo(JsonObject data);
    JsonObject getProfile(long userId);
    JsonObject updateAdditionalInfo(JsonObject data);
}
