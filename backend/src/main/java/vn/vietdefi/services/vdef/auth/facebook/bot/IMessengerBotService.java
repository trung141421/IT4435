package vn.vietdefi.services.vdef.auth.facebook.bot;

import com.google.gson.JsonObject;

public interface IMessengerBotService {
    JsonObject receiveMessage(long userId, String message );
    JsonObject responseMessage(long senderId, String message);
    void addMessengerInfo(long senderId);
}
