package vn.vietdefi.services.mystikos.tarot;

import com.google.gson.JsonObject;

public interface IMtTarotService {
    JsonObject dailyCard(JsonObject json);
    JsonObject cardInfo(JsonObject json);
    JsonObject readingCard(JsonObject json);
}
