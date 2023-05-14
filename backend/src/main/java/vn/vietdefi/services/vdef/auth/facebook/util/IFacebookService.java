package vn.vietdefi.services.vdef.auth.facebook.util;

import com.google.gson.JsonObject;
import com.restfb.types.User;

public interface IFacebookService {
    JsonObject login(JsonObject json);
    JsonObject registerFacebook(long fbId,String accessToken);
    void addFbInfo(User me);
    JsonObject getFbInfo(long userId);
}
