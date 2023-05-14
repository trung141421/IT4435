package vn.vietdefi.services.vdef.notification;

import com.google.gson.JsonObject;

public interface INotificationService {
    JsonObject subscribeNotification(long userId, JsonObject data);
    JsonObject createNotification(JsonObject data);
    JsonObject sendNotification(JsonObject data);

}
