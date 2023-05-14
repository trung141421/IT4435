package vn.vietdefi.services.izihouse.profile;


import com.google.gson.JsonObject;

public interface IIzihouseHouseService {
    JsonObject createHouse(JsonObject json);

    JsonObject createRoom(JsonObject json);

    JsonObject getHouseInfo(JsonObject json);

    JsonObject getRoomInfo(JsonObject json);

    JsonObject getHousesOfUser(JsonObject json);

    JsonObject getRoomsOfHouse(JsonObject json);

    JsonObject updateHouseInfo(JsonObject json);

    JsonObject updateRoomInfo(JsonObject json);

    JsonObject deleteHouse(JsonObject json);

    JsonObject deleteRoom(JsonObject json);
}
