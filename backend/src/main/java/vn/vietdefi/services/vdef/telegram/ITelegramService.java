package vn.vietdefi.services.vdef.telegram;

import com.google.gson.JsonObject;

public interface ITelegramService {
    JsonObject requestLinkAccount(JsonObject json);
    JsonObject activeTelegramAccount(long telegramId, String activeCode,String fullName);
    JsonObject sendMessage(final long telegramdId, final String message);
    JsonObject sendMessageToAdmin(final String message);
    JsonObject sendLogMessage(final String message);
    JsonObject updatePhoneNumber(long telegramId, String phone);
    void sendNotificationService(long userId, JsonObject data);
    JsonObject sendTelegramOTP(long userId);
    long getTelegramId(long userid);
    JsonObject checkLinkTelegram(long userId);
    JsonObject getTelegramInfo(long userId);
}
