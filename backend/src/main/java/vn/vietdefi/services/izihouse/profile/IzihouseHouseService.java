package vn.vietdefi.services.izihouse.profile;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;

public class IzihouseHouseService implements IIzihouseHouseService{
    @Override
    public JsonObject createHouse(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            int type = json.get("type").getAsInt();
            String name = json.get("name").getAsString();
            String address = json.get("address").getAsString();
            int status = json.get("status").getAsInt();
            float area = json.get("area").getAsFloat();
            String description = json.get("description").getAsString();
            String image = json.get("image").getAsString();
            int homeowner_id = json.get("userid").getAsInt();
            String renter_name = json.get("renter_name").getAsString();
            String renter_phone_number = json.get("renter_phone_number").getAsString();
            int price = json.get("price").getAsInt();
            long created_time = System.currentTimeMillis();

            //check duplicate house_name
            String query = "SELECT * FROM iz_house WHERE name = ? AND homeowner_id = ? AND status <> -1";
            JsonObject data = bridge.queryOne(query, name, homeowner_id);
            if(data != null){
                return BaseResponse.createFullMessageResponse(2,"house_name_already_exists");
            }

            //create house
            if(type == 1){
                query = "INSERT INTO iz_house (type, name, address, status, area, description, image, homeowner_id, renter_name, renter_phone_number, price, num_of_rooms, num_of_rooms_rented, num_of_rooms_posted, created_time, is_posted) VALUES (?,?,?,?,?,?,?,?,?,?,?,0,0,0,?,0)";
            }
            else {
                query = "INSERT INTO iz_house (type, name, address, status, area, description, image, homeowner_id, renter_name, renter_phone_number, price, created_time, is_posted) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,0)";
            }
            bridge.update(query, type, name, address, status, area, description, image, homeowner_id, renter_name, renter_phone_number, price, created_time);

            //response house_id
            query = "SELECT id FROM iz_house WHERE name = ? AND homeowner_id = ? AND status <> -1";
            JsonObject house_id = bridge.queryOne(query, name, homeowner_id);
            return BaseResponse.createFullMessageResponse(0, "success", house_id);
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject createRoom(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            int house_id = json.get("house_id").getAsInt();
            String name = json.get("name").getAsString();
            int price = json.get("price").getAsInt();
            int status = json.get("status").getAsInt();
            float area = json.get("area").getAsFloat();
            String description = json.get("description").getAsString();
            String image = json.get("image").getAsString();
            String renter_name = json.get("renter_name").getAsString();
            String renter_phone_number = json.get("renter_phone_number").getAsString();
            long created_time = System.currentTimeMillis();

            //check duplicate room_name
            String query = "SELECT * FROM iz_room WHERE house_id = ? AND name = ? AND status <> -1";
            JsonObject data = bridge.queryOne(query, house_id, name);
            if(data != null){
                return BaseResponse.createFullMessageResponse(2,"room_name_already_exists");
            }

            //create room
            query = "INSERT INTO iz_room (house_id, name, price, status, area, description, image, renter_name, renter_phone_number, is_posted, created_time) VALUES (?,?,?,?,?,?,?,?,?,0,?)";
            bridge.update(query, house_id, name, price, status, area, description, image, renter_name, renter_phone_number, created_time);

            //update house
            query = "UPDATE iz_house SET num_of_rooms = num_of_rooms + 1 WHERE id = ?";
            bridge.update(query, house_id);
            if(status == 1){
                query = "UPDATE iz_house SET num_of_rooms_rented = num_of_rooms_rented + 1 WHERE id =?";
                bridge.update(query, house_id);
            }

            return BaseResponse.createFullMessageResponse(0,"success");
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject getHouseInfo(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM iz_house WHERE id = ?";
            int id = json.get("house_id").getAsInt();
            JsonObject data = bridge.queryOne(query, id);
            return BaseResponse.createFullMessageResponse(0,"success", data);
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject getRoomInfo(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM iz_room WHERE id = ?";
            int id = json.get("room_id").getAsInt();
            JsonObject data = bridge.queryOne(query, id);
            return BaseResponse.createFullMessageResponse(0,"success", data);
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject getHousesOfUser(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM iz_house WHERE homeowner_id = ? AND status <> -1 ORDER BY name LIMIT 30 OFFSET ?";
            int homeowner = json.get("userid").getAsInt();
            int offset = json.get("offset").getAsInt();
            JsonArray data = bridge.query(query, homeowner, offset);
            return BaseResponse.createFullMessageResponse(0, "success", data);
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject getRoomsOfHouse(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM iz_room WHERE house_id = ? AND status <> -1 ORDER BY name LIMIT 30 OFFSET ?";
            int house_id = json.get("house_id").getAsInt();
            int offset = json.get("offset").getAsInt();
            JsonArray data = bridge.query(query, house_id, offset);
            return BaseResponse.createFullMessageResponse(0,"success", data);
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject updateHouseInfo(JsonObject json) {
        try{
            //update house
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "UPDATE iz_house SET name = ?, address = ?, description = ?, area = ?, image = ?, status = ?, renter_name = ?, renter_phone_number = ?, price = ? WHERE id = ?";
            String name = json.get("name").getAsString();
            String address = json.get("address").getAsString();
            String description = json.get("description").getAsString();
            Float area = json.get("area").getAsFloat();
            String image = json.get("image").getAsString();
            int status = json.get("status").getAsInt();
            String renter_name = json.get("renter_name").getAsString();
            String renter_phone_number = json.get("renter_phone_number").getAsString();
            int price = json.get("price").getAsInt();
            long id = json.get("house_id").getAsLong();
            bridge.update(query, name, address, description, area, image, status, renter_name, renter_phone_number, price, id);

            //delete post
            IzihouseLogic.deleteAllPostOfHouse(id);
            return BaseResponse.createFullMessageResponse(0,"success");
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject updateRoomInfo(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            String name = json.get("name").getAsString();
            int price = json.get("price").getAsInt();
            float area = json.get("area").getAsFloat();
            String description = json.get("description").getAsString();
            String image = json.get("image").getAsString();
            int status = json.get("status").getAsInt();
            String renter_name = json.get("renter_name").getAsString();
            String renter_phone_number = json.get("renter_phone_number").getAsString();
            long id = json.get("room_id").getAsLong();
            long house_id = json.get("house_id").getAsLong();

            //update house
            String query = "SELECT status FROM iz_room WHERE id = ?";
            JsonObject data = bridge.queryOne(query, id);
            if(data.get("status").getAsInt() != status){
                if(status == 1){
                    query = "UPDATE iz_house SET num_of_rooms_rented = num_of_rooms_rented + 1 WHERE id = ?";
                    bridge.update(query, house_id);
                }else {
                    query = "UPDATE iz_house SET num_of_rooms_rented = num_of_rooms_rented - 1 WHERE id = ?";
                    bridge.update(query, house_id);
                }
            }

            //update room
            query = "UPDATE iz_room SET name = ?, price = ?, area = ?, description = ?, image = ?, status = ?, renter_name = ?, renter_phone_number = ? WHERE id = ?";
            bridge.update(query, name, price, area, description, image, status, renter_name, renter_phone_number, id);

            //delete post
            IzihouseLogic.deleteAllPostOfRoom(id);

            return BaseResponse.createFullMessageResponse(0,"success");
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject deleteHouse(JsonObject json) {
        try{
            //delete house
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "UPDATE iz_house SET status = -1 WHERE id = ?";
            long id = json.get("house_id").getAsLong();
            bridge.update(query, id);

            //delete room
            IzihouseLogic.deleteAllRoomOfHouse(id);

            //delete post
            IzihouseLogic.deleteAllPostOfHouse(id);

            return BaseResponse.createFullMessageResponse(0,"success");
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject deleteRoom(JsonObject json) {
        try{
            //delete room
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "UPDATE iz_room SET status = -1 WHERE id = ?";
            long id = json.get("room_id").getAsLong();
            bridge.update(query, id);

            //update house
            int status = json.get("status").getAsInt();
            int is_posted = json.get("is_posted").getAsInt();
            long house_id = json.get("house_id").getAsLong();
            if(status == 1){
                query = "UPDATE iz_house SET num_of_rooms_rented = num_of_rooms_rented - 1 WHERE id = ?";
                bridge.update(query, house_id);
            }

            if(is_posted == 1){
                query = "UPDATE iz_house SET num_of_rooms_posted = num_of_rooms_posted - 1 WHERE id = ?";
                bridge.update(query, house_id);
            }


            query = "UPDATE iz_house SET num_of_rooms = num_of_rooms - 1 WHERE id = ?";
            bridge.update(query, house_id);



            //delete post
            IzihouseLogic.deleteAllPostOfRoom(id);
            return BaseResponse.createFullMessageResponse(0,"success");
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }
}
