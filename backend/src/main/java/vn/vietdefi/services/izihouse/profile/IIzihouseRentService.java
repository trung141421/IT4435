package vn.vietdefi.services.izihouse.profile;

import com.google.gson.JsonObject;

public interface IIzihouseRentService {
    JsonObject createRequest(JsonObject json);

    JsonObject getRequestOfRenter(JsonObject json);

    JsonObject deleteRequest(JsonObject json);

    JsonObject getRequestOfHouse(JsonObject json);

    JsonObject getRequestOfRoom(JsonObject json);

    JsonObject acceptRequest(JsonObject json);

    JsonObject cancelRental(JsonObject json);
}
