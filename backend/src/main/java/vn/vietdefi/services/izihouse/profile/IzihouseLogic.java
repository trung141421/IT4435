package vn.vietdefi.services.izihouse.profile;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;

public class IzihouseLogic {
    public static void deleteAllRoomOfHouse(long house_id){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT id AS room_id FROM iz_room WHERE house_id = ?";
            JsonArray data = bridge.query(query, house_id);
            IzihouseHouseService ser = new IzihouseHouseService();
            for (JsonElement n: data) {
                ser.deleteRoom(n.getAsJsonObject());
            }
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
        }
    }

    public static void deleteAllPostOfHouse(long house_id){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT id AS post_id FROM iz_post WHERE house_id = ?";
            JsonArray data = bridge.query(query, house_id);
            IzihousePostService ser = new IzihousePostService();
            for (JsonElement n: data) {
                ser.deletePost(n.getAsJsonObject());
            }
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
        }
    }

    public static void deleteAllPostOfRoom(long room_id){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT id AS post_id FROM iz_post WHERE room_id = ?";
            JsonArray data = bridge.query(query, room_id);
            IzihousePostService ser = new IzihousePostService();
            for (JsonElement n: data) {
                ser.deletePost(n.getAsJsonObject());
            }
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
        }
    }

    public static void deleteAllSavedOfPost(long post_id){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT id FROM iz_saved_post WHERE post_id = ?";
            JsonArray data = bridge.query(query, post_id);
            IzihousePostService ser = new IzihousePostService();
            for (JsonElement n: data) {
                ser.unSavePost(n.getAsJsonObject());
            }
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
        }
    }

    public static void deleteAllRequestOfPost(long post_id){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT id FROM iz_request WHERE post_id = ?";
            JsonArray data = bridge.query(query, post_id);
            IzihouseRentService ser = new IzihouseRentService();
            for (JsonElement n: data) {
                ser.deleteRequest(n.getAsJsonObject());
            }
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
        }
    }

    public void updateRequest(long post_id){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT id FROM iz_request WHERE post_id = ?";
            JsonArray data = bridge.query(query, post_id);
            IzihouseRentService ser = new IzihouseRentService();
            for (JsonElement n: data) {
                ser.deleteRequest(n.getAsJsonObject());
            }
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
        }
    }
}
