package vn.vietdefi.services.mystikos.profile;

import com.google.gson.JsonObject;

public interface IMtProfileService {
    JsonObject getProfile(long userId);

    JsonObject createProfile(long userId);

    JsonObject updateProfile(JsonObject json);
    JsonObject chooseCardBack(long userId, long cardBackId);
}
