package trung.room.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import trung.common.BaseResponse;
import trung.util.sql.HikariClients;
import trung.util.sql.SQLJavaBridge;
import trung.room.services.logic.HashLogic;
import trung.room.services.logic.ShiftCached;
import trung.room.services.logic.TimeLogic;
import java.util.Calendar;

public class RoomService implements IRoomService {
    public JsonObject getRoom(){
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM room";
            JsonArray roomList = bridge.query(query);
            return BaseResponse.createFullMessageResponse(0,"Success", roomList);
        }
        catch(Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"System_error!");
        }
    }

    @Override
    public JsonObject createRoom(String name, String address, String description, int price) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "INSERT INTO room (name, address, description, price) VALUES (?,?,?,?)";
            int data = bridge.update(query, name, address, description, price);
            return BaseResponse.createFullMessageResponse(0,"Success");
        }
        catch(Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"System_error!");
        }
    }

    @Override
    public JsonObject deleteRoom(int room_id) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "DELETE FROM room WHERE id = ?";
            int data = bridge.update(query, room_id);
            return BaseResponse.createFullMessageResponse(0,"Success");
        }
        catch(Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"System_error!");
        }
    }

    @Override
    public JsonObject editRoom(int room_id, String name, String address, String description, int price, String renter_name) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "UPDATE room SET name = ?, address = ?, description = ?, price = ?, renter_name = ? WHERE id = ?";
            int data = bridge.update(query, name, address, description, price, renter_name, room_id);
            return BaseResponse.createFullMessageResponse(0,"Success");
        }
        catch(Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"System_error!");
        }
    }

}
