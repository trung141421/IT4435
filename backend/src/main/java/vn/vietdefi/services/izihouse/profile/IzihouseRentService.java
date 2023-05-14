package vn.vietdefi.services.izihouse.profile;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.jsonwebtoken.Header;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;

public class IzihouseRentService implements IIzihouseRentService{
    @Override
    public JsonObject createRequest(JsonObject json) {
        try{
            long post_id = json.get("id").getAsLong();
            long user_id = json.get("userid").getAsLong();
            int type = json.get("type").getAsInt();
            long house_id = json.get("house_id").getAsLong();
            Object room_id = null;
            if(type == 1){
                room_id = json.get("room_id").getAsLong();
            }
            String title = json.get("title").getAsString();
            String address = json.get("address").getAsString();
            int price = json.get("price").getAsInt();
            float area = json.get("area").getAsFloat();
            String description = json.get("description").getAsString();
            String image = json.get("image").getAsString();
            int status = json.get("status").getAsInt();
            long created_time = json.get("created_time").getAsLong();
            long request_created_time = System.currentTimeMillis();
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            //get name and phone number of user
            String query = "SELECT name, phone_number FROM iz_profile WHERE id = ?";
            JsonObject data = bridge.queryOne(query, user_id);
            String user_name = data.get("name").getAsString();
            String user_phone_number = data.get("phone_number").getAsString();

            //update post
            query = "UPDATE iz_post SET is_registered = 1 WHERE id = ?";
            bridge.update(query, post_id);

            //create notification
            query = "SELECT user_id FROM iz_post WHERE id = ?";
            data = bridge.queryOne(query, post_id);
            long homeowner_id = data.get("user_id").getAsLong();

            query = "INSERT INTO iz_notification (user_id, type, post_id, name, phone_number, content, created_time) VALUES (?,?,?,?,?,?,?)";
            bridge.update(query, homeowner_id, 0, post_id, user_name, user_phone_number, title, System.currentTimeMillis());

            //create rent request
            if(type != 1){
                query = "INSERT INTO iz_request (post_id, user_id, user_name, user_phone_number, type, house_id, title, address, price, area, description, image, status, created_time,  request_created_time, request_status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,0)";
                bridge.update(query, post_id, user_id, user_name, user_phone_number, type, house_id, title, address, price, area, description, image, status, created_time, request_created_time);
                return BaseResponse.createFullMessageResponse(0,"success");
            }
            query = "INSERT INTO iz_request (post_id, user_id, user_name, user_phone_number, type, house_id, room_id, title, address, price, area, description, image, status, created_time,  request_created_time, request_status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,0)";
            bridge.update(query, post_id, user_id, user_name, user_phone_number, type, house_id, room_id, title, address, price, area, description, image, status, created_time, request_created_time);



            return BaseResponse.createFullMessageResponse(0,"success");
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject getRequestOfRenter(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM iz_request WHERE user_id = ?";
            long user_id = json.get("userid").getAsLong();
            JsonArray data = bridge.query(query, user_id);
            return BaseResponse.createFullMessageResponse(0,"success", data);
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject deleteRequest(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long id = json.get("request_id").getAsLong();

            //update post
            String query = "SELECT post_id FROM iz_request WHERE id = ?";
            JsonObject data = bridge.queryOne(query, id);
            long post_id = data.get("post_id").getAsLong();

            //delete request
            query = "DELETE FROM iz_request WHERE id = ?";
            bridge.update(query, id);

            query = "SELECT * FROM iz_request WHERE post_id = ?";
            boolean check_request = bridge.queryExist(query, post_id);

            if (!check_request){
                query = "UPDATE iz_post SET is_registered = 0 WHERE id = ?";
                bridge.update(query, post_id);
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
    public JsonObject getRequestOfHouse(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM iz_request WHERE house_id = ?";
            long house_id = json.get("house_id").getAsLong();
            JsonArray data = bridge.query(query, house_id);
            return BaseResponse.createFullMessageResponse(0,"success",data);
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject getRequestOfRoom(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM iz_request WHERE room_id = ?";
            long room_id = json.get("room_id").getAsLong();
            JsonArray data = bridge.query(query, room_id);
            return BaseResponse.createFullMessageResponse(0,"success",data);
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject acceptRequest(JsonObject json) {
        try{
            //accept request
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "UPDATE iz_request SET request_status = 1 WHERE id = ?";
            long request_id = json.get("request_id").getAsLong();
            long user_id = json.get("user_id").getAsLong();
            bridge.update(query, request_id);

            //create notification
            query = "SELECT user_id, post_id FROM iz_request WHERE id = ?";
            JsonObject data = bridge.queryOne(query, request_id);
            long renter_id = data.get("user_id").getAsLong();
            long post_id = data.get("post_id").getAsLong();

            query = "SELECT name, phone_number FROM iz_profile WHERE id = ?";
            data = bridge.queryOne(query, user_id);
            String homeowner_name = data.get("name").getAsString();
            String homeowner_phone_number = data.get("phone_number").getAsString();

            query = "SELECT title FROM iz_post WHERE id = ?";
            data = bridge.queryOne(query, post_id);
            String title = data.get("title").getAsString();

            query = "INSERT INTO iz_notification (user_id, type, post_id, name, phone_number, content, created_time) VALUES (?,?,?,?,?,?,?,?)";
            bridge.update(query, renter_id, 0, post_id, homeowner_name, homeowner_phone_number, title, System.currentTimeMillis());


            return BaseResponse.createFullMessageResponse(0,"success");
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject cancelRental(JsonObject json) {
        try{
            return null;
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }
}
