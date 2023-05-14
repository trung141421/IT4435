package vn.vietdefi.services.wecare.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;

public class WecareServiceService implements IWecareServiceService {

    @Override
    public JsonObject createService(JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String name = json.get("name").getAsString();
            int type = json.get("type").getAsInt();
            int userId = json.get("userId").getAsInt();
            String workingHour = json.get("workingHour").getAsString();
            String workingDay = json.get("workingDay").getAsString();
            int letCustomerBook = json.get("letCustomerBook").getAsInt();
            JsonObject detail = json.get("detail").getAsJsonObject();
            String query = new StringBuilder("INSERT INTO wc_service(name,type,userId,workingHour,workingDay,status,letCustomerBook,detail)")
                    .append("VALUE (?,?,?,?,?,0,?,?)")
                    .toString();
            bridge.update(query, name, type, userId, workingHour, workingDay, letCustomerBook,detail.toString());
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject createServicePack(JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            int serviceId = json.get("serviceId").getAsInt();
            int price = json.get("price").getAsInt();
            int hour = json.get("hour").getAsInt();
            String include = json.get("include").getAsString();
            String query = "INSERT INTO wc_servicePack(serviceId,price,hour,include) VALUE(?,?,?,?)";
            bridge.update(query, serviceId, price, hour, include);
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject getService(JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            int serviceId = json.get("serviceId").getAsInt();
            String query = "Select * from wc_service where id = ?";
            JsonObject service = bridge.queryOne(query, serviceId);
            return BaseResponse.createFullMessageResponse(0, "success", service);
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject getServicePack(JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            int serviceId = json.get("serviceId").getAsInt();
            String query = "Select price,hour,include from wc_servicePack where serviceId = ?";
            JsonArray servicePack = bridge.query(query, serviceId);
            return BaseResponse.createFullMessageResponse(0, "success", servicePack);
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject updateService(JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            int serviceId = json.get("serviceId").getAsInt();
            String name = json.get("name").getAsString();
            int type = json.get("type").getAsInt();
            String workingHour = json.get("workingHour").getAsString();
            String workingDay = json.get("workingDay").getAsString();
            int letCustomerBook = json.get("letCustomerBook").getAsInt();
            JsonObject detail = json.get("detail").getAsJsonObject();
            String query = new StringBuilder("UPDATE wc_service SET name = ?,type = ?,workingHour = ?,")
                    .append("workingDay = ?,letCustomerBook = ?,detail = ? where id = ?")
                    .toString();
            bridge.update(query, name, type, workingHour, workingDay,letCustomerBook, detail.toString() , serviceId);
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject updateServicePack(JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            int id = json.get("id").getAsInt();
            int price = json.get("price").getAsInt();
            int hour = json.get("hour").getAsInt();
            String include = json.get("include").getAsString();
            String query = "UPDATE wc_servicePack SET price = ?,hour = ?,include = ? where id = ?";
            bridge.update(query, price, hour, include,id);
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject doServiceTest(JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            int serviceType = json.get("serviceType").getAsInt();
            String query = "SELECT questionContent,answer from wc_testQuestion where serviceType = ?";
            JsonArray questions = bridge.query(query, serviceType);
            return BaseResponse.createFullMessageResponse(0, "success", questions);
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject activeService(JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            int serviceId = json.get("serviceId").getAsInt();
            String query = "UPDATE wc_service SET status = 1 where id = ?";
            bridge.update(query, serviceId);
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
}
