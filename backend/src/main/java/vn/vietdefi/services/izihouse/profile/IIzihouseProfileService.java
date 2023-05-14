package vn.vietdefi.services.izihouse.profile;

import com.google.gson.JsonObject;

public interface IIzihouseProfileService {
    JsonObject getProfile(JsonObject json);

    JsonObject createProfile(JsonObject json);

    JsonObject updateProfile(JsonObject json);
}
