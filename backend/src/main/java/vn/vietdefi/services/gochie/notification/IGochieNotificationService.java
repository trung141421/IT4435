package vn.vietdefi.services.gochie.notification;

import com.google.gson.JsonObject;

public interface IGochieNotificationService {
    JsonObject createTransactionHistory(JsonObject json);
    JsonObject getTransactionHistory(long userid);
    public  JsonObject create(JsonObject json);
    public JsonObject get(JsonObject json);
    public JsonObject send(JsonObject json);
}

