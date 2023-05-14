package vn.vietdefi.services.wecare.profile;

import com.google.gson.JsonObject;
import org.json.JSONObject;

public interface IWecareProfileService {
    JsonObject getProfile (long userId);
    JsonObject createProfile (long userId,JsonObject json);
    JsonObject updateProfile (long userId,JsonObject json);

    JsonObject getProvider(long userId);
    JsonObject createProvider (long userId,JsonObject json);
    JsonObject setRoleProvider (long userId);
    JsonObject updateProvider (long userId,JsonObject json);

}
