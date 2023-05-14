package vn.vietdefi.services.vdef.auth.facebook.messenger;

import com.google.gson.JsonObject;

public interface IMessengerService {
    JsonObject sendActiveCode(long userId);
    JsonObject linkMessenger(JsonObject json);
    JsonObject checkLinkMessenger(long userId);
    JsonObject getMessengerInfo(long userId);
}
