package vn.vietdefi.services.izihouse.profile;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;
public class IzihousePostService implements IIzihousePostService{
    @Override
    public JsonObject createHousePost(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            //created_post
            String query = "INSERT INTO iz_post (user_id, type, house_id, title, address, price, area, description, image, is_registered, status, created_time) VALUES (?,?,?,?,?,?,?,?,?,0,0,?)";
            long user_id = json.get("userid").getAsLong();
            int type = json.get("type").getAsInt();
            long house_id = json.get("house_id").getAsLong();
            String title = json.get("title").getAsString();
            String address = json.get("address").getAsString();
            int price = json.get("price").getAsInt();
            float area = json.get("area").getAsFloat();
            String description = json.get("description").getAsString();
            String image = json.get("image").getAsString();
            long created_time = System.currentTimeMillis();
            bridge.update(query,user_id, type, house_id, title, address, price, area, description, image, created_time);

            //update iz_house
            query = "UPDATE iz_house SET is_posted = 1 WHERE id = ?";
            bridge.update(query, house_id);
            return BaseResponse.createFullMessageResponse(0,"success");
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject createRoomPost(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            //created_post
            String query = "INSERT INTO iz_post (user_id, type, house_id, room_id, title, address, price, area, description, image, is_registered, status, created_time) VALUES (?,?,?,?,?,?,?,?,?,?,0,0,?)";
            long user_id = json.get("userid").getAsLong();
            int type = json.get("type").getAsInt();
            long house_id = json.get("house_id").getAsLong();
            long room_id = json.get("room_id").getAsLong();
            String title = json.get("title").getAsString();
            String address = json.get("address").getAsString();
            int price = json.get("price").getAsInt();
            float area = json.get("area").getAsFloat();
            String description = json.get("description").getAsString();
            String image = json.get("image").getAsString();
            long created_time = System.currentTimeMillis();
            bridge.update(query,user_id, type, house_id, room_id, title, address, price, area, description, image, created_time);

            //update iz_room
            query = "UPDATE iz_room SET is_posted = 1 WHERE id = ?";
            bridge.update(query, room_id);
            query = "UPDATE iz_house SET num_of_rooms_posted = num_of_rooms_posted + 1 WHERE id = ?";
            bridge.update(query, house_id);

            return BaseResponse.createFullMessageResponse(0,"success");
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject getPostInfo(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM iz_post WHERE id = ?";
            long id = json.get("post_id").getAsLong();
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
    public JsonObject getAllPost(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM iz_post WHERE status = 0 LIMIT 30 OFFSET ?";
            long offset = json.get("offset").getAsLong();
            JsonArray data = bridge.query(query, offset);
            return BaseResponse.createFullMessageResponse(0,"success", data);
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject hidePost(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            //hide post
            String query = "UPDATE iz_post SET status = 1 WHERE id = ?";
            long post_id = json.get("post_id").getAsLong();
            bridge.update(query, post_id);

            //update request
            query = "UPDATE iz_request SET status = 1 WHERE post_id = ?";
            bridge.update(query, post_id);

            //update saved post
            query = "UPDATE iz_saved_post SET status = 1 WHERE post_id = ?";
            bridge.update(query, post_id);

            return BaseResponse.createFullMessageResponse(0,"success");
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject getHidePost(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM iz_post WHERE user_id = ? AND status = 1";
            long user_id = json.get("userid").getAsLong();
            JsonArray data = bridge.query(query, user_id);
            return BaseResponse.createFullMessageResponse(0, "success", data);
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject unhidePost(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            //unhide post
            String query = "UPDATE iz_post SET status = 0 WHERE id = ?";
            long post_id = json.get("post_id").getAsLong();
            bridge.update(query, post_id);

            //update request
            query = "UPDATE iz_request SET status = 0 WHERE post_id = ?";
            bridge.update(query, post_id);

            //update saved post
            query = "UPDATE iz_saved_post SET status = 0 WHERE post_id = ?";
            bridge.update(query, post_id);
            return BaseResponse.createFullMessageResponse(0, "success");
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject deletePost(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long post_id = json.get("post_id").getAsLong();

            //update house and room
            String query = "SELECT house_id, room_id, type FROM iz_post WHERE id = ?";
            JsonObject data = bridge.queryOne(query, post_id);
            query = "UPDATE iz_house SET is_posted = 0 WHERE id = ?";
            long house_id = data.get("house_id").getAsLong();
            bridge.update(query, house_id);
            int type = data.get("type").getAsInt();
            if(type == 1){
                long room_id = data.get("room_id").getAsLong();
                query = "UPDATE iz_room SET is_posted = 0 WHERE id = ?";
                bridge.update(query, room_id);
            }

            //delete post
            query = "DELETE FROM iz_post WHERE id = ?";
            bridge.update(query, post_id);

            //delete saved post
            query = "DELETE FROM iz_saved_post WHERE post_id = ?";
            bridge.update(query, post_id);

            //delete request
            query = "DELETE FROM iz_request WHERE post_id = ?";
            bridge.update(query, post_id);

            return BaseResponse.createFullMessageResponse(0, "success");
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject savePost(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long post_id = json.get("id").getAsLong();
            long user_id = json.get("userid").getAsLong();

            String query = "SELECT id FROM iz_saved_post WHERE user_id = ? AND post_id = ?";
            boolean data = bridge.queryExist(query, user_id, post_id);
            if(data){
                return BaseResponse.createFullMessageResponse(2,"already_saved");
            }

            int type = json.get("type").getAsInt();
            long house_id = json.get("house_id").getAsLong();
            Object room_id = null;
            if(type == 1) {
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
            query = "INSERT INTO iz_saved_post (post_id, user_id, type, house_id, room_id, title, address, price, area, description, image, status, created_time) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            if(room_id == null){
                query = "INSERT INTO iz_saved_post (post_id, user_id, type, house_id, title, address, price, area, description, image, status, created_time) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                bridge.update(query, post_id, user_id, type, house_id, title, address, price, area, description, image, status, created_time);
                return BaseResponse.createFullMessageResponse(0, "success");
            }
            bridge.update(query, post_id, user_id, type, house_id, room_id, title, address, price, area, description, image, status, created_time);
            return BaseResponse.createFullMessageResponse(0, "success");
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject getSavePost(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM iz_saved_post WHERE user_id = ?";
            long userid = json.get("userid").getAsLong();
            JsonArray data = bridge.query(query, userid);
            return BaseResponse.createFullMessageResponse(0,"success", data);
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject unSavePost(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "DELETE FROM iz_saved_post WHERE post_id = ? AND user_id = ?";
            long post_id = json.get("post_id").getAsLong();
            long user_id = json.get("userid").getAsLong();
            bridge.update(query, post_id, user_id);
            return BaseResponse.createFullMessageResponse(0,"success");
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject getUserPost(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM iz_post WHERE user_id = ? LIMIT 30 OFFSET ?";
            long user_id = json.get("userid").getAsLong();
            long offset = json.get("offset").getAsLong();
            JsonArray data = bridge.query(query, user_id, offset);
            return BaseResponse.createFullMessageResponse(0,"success", data);
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject getHouseListToPost(JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM iz_house WHERE ((is_posted = 0 AND type = 0) OR (type = 1 AND num_of_rooms_rented + num_of_rooms_posted < num_of_rooms)) AND homeowner_id = ? AND status = 0 LIMIT 30 OFFSET ?";
            long homeowner_id = json.get("userid").getAsLong();
            long offset = json.get("offset").getAsLong();
            JsonArray data = bridge.query(query, homeowner_id, offset);
            return BaseResponse.createFullMessageResponse(0,"success",data);
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject getRoomListToPost(JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM iz_room WHERE is_posted = 0 AND house_id = ? AND status = 0 LIMIT 30 OFFSET ?";
            long house_id = json.get("house_id").getAsLong();
            long offset = json.get("offset").getAsLong();
            JsonArray data = bridge.query(query, house_id, offset);
            return BaseResponse.createFullMessageResponse(0,"success",data);
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }
}
