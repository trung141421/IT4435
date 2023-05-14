package vn.vietdefi.services.wecare.schedule;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;

public class WecareScheduleService implements IWecareScheduleService{

    @Override
    public JsonObject changeStatusSchedule(JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            int scheduleId = json.get("scheduleId").getAsInt();
            int status = json.get("status").getAsInt();
            String query = "UPDATE wc_bookingSchedule SET status = ? where id = ?";
            bridge.update(query,status,scheduleId);
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject acceptSchedule(long userId, JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            int scheduleId = json.get("scheduleId").getAsInt();
            String query = "UPDATE wc_bookingSchedule SET providerId = ?, status = 1 where id = ?";
            bridge.update(query,userId,scheduleId);
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject declineSchedule(JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            int scheduleId = json.get("scheduleId").getAsInt();
            JsonObject detail = json.get("detail").getAsJsonObject();
            String query = "UPDATE wc_bookingSchedule SET status = -1, detail = ? where id = ?";
            bridge.update(query,detail.toString(),scheduleId);
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject bookSchedule(long userId,JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long providerId = json.get("providerId").getAsLong();
            int serviceId = json.get("serviceId").getAsInt();
            int servicePackId = json.get("servicePackId").getAsInt();
            int paymentMethod = json.get("paymentMethod").getAsInt();
            JsonObject detail = json.get("detail").getAsJsonObject();
            String query = "INSERT INTO wc_bookingSchedule(providerId,customerId,serviceId,servicePackId,paymentMethod,status,detail) VALUES(?,?,?,?,?,0,?)";
            bridge.update(query,providerId,userId,serviceId,servicePackId,paymentMethod,detail.toString());
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject bookScheduleFast(long userId, JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            int serviceId = json.get("serviceId").getAsInt();
            int servicePackId = json.get("servicePackId").getAsInt();
            int paymentMethod = json.get("paymentMethod").getAsInt();
            JsonObject detail = json.get("detail").getAsJsonObject();
            String query = "INSERT INTO wc_bookingSchedule(customerId,serviceId,servicePackId,paymentMethod,status,detail) VALUES(?,?,?,?,0,?)";
            bridge.update(query,userId,serviceId,servicePackId,paymentMethod,detail.toString());
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    @Override
    public JsonObject getCustomerBookingSchedule(JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long customerId = json.get("customerId").getAsLong();
            String query = "SELECT * from wc_bookingSchedule where customerId = ?";
            JsonArray data = bridge.query(query,customerId);
            return BaseResponse.createFullMessageResponse(0, "success",data);
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject getProviderBookingSchedule(JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long providerId = json.get("providerId").getAsLong();
            String query = "SELECT * from wc_bookingSchedule where providerId = ?";
            JsonArray data = bridge.query(query,providerId);
            return BaseResponse.createFullMessageResponse(0, "success",data);
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject createPersonalSchedule(JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long userId = json.get("userId").getAsLong();
            String detail = json.get("detail").getAsString();
            String query = "INSERT INTO wc_personalSchedule(userId,detail) VALUES(?,?)";
            bridge.update(query,userId,detail);
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    public JsonObject getPersonalSchedule(long userId){
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * from wc_personalSchedule where userId = ?";
            JsonArray data = bridge.query(query,userId);
            return BaseResponse.createFullMessageResponse(0, "success",data);
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject updatePersonalSchedule( JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long id = json.get("id").getAsLong();
            String detail = json.get("detail").getAsString();

            String query = "UPDATE wc_personalSchedule SET detail = ? where id = ?";
            bridge.update(query,detail,id);
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
}
