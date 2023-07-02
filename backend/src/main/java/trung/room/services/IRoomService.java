package trung.room.services;

import com.google.gson.JsonObject;

public interface IRoomService {
    JsonObject getRoom();
    JsonObject createRoom(String name, String address, String description, int price);
    JsonObject deleteRoom(int room_id);
    JsonObject editRoom(int room_id, String name, String address, String description, int price, String renter_name);
}
