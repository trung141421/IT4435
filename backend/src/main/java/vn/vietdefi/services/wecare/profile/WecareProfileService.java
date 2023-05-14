package vn.vietdefi.services.wecare.profile;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;


public class WecareProfileService implements IWecareProfileService {
    @Override
    public JsonObject getProfile(long userId) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM wc_profile WHERE userId = ?";
            JsonObject data = bridge.queryOne(query, userId);
            return BaseResponse.createFullMessageResponse(0, "success", data);
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject createProfile(long userId, JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String nickName = new StringBuilder("user@").append(userId).toString();
            String phoneNumber = json.get("phoneNumber").getAsString();
            String country = json.get("country").getAsString();
            String city = json.get("city").getAsString();
            String district = json.get("district").getAsString();
            int role = json.get("role").getAsInt();
            int gender = json.get("gender").getAsInt();
            int status = json.get("status").getAsInt();
            JsonObject detail = json.get("detail").getAsJsonObject();

            String query = new StringBuilder("INSERT INTO wc_profile(userId,nickName,phoneNumber,country,city,district,")
                    .append("role,gender,status,detail) VALUES (?,?,?,?,?,?,?,?,?,?)")
                    .toString();
            bridge.update(query, userId, nickName, phoneNumber, country, city, district, role, gender, status, detail.toString());
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject updateProfile(long userId, JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String nickName = json.get("nickName").getAsString();
            String phoneNumber = json.get("phoneNumber").getAsString();
            String country = json.get("country").getAsString();
            String city = json.get("city").getAsString();
            String district = json.get("district").getAsString();
            int role = json.get("role").getAsInt();
            int gender = json.get("gender").getAsInt();
            int status = json.get("status").getAsInt();
            JsonObject detail = json.get("detail").getAsJsonObject();

            String query = new StringBuilder("UPDATE wc_profile SET nickName = ?, phoneNumber = ?, country = ?,city = ?,district = ?,")
                    .append("role = ?,gender = ?,status = ?,detail = ? where userId = ?")
                    .toString();
            bridge.update(query, nickName, phoneNumber, country, city, district, role, gender, status, detail.toString(), userId);
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    public JsonObject getProvider(long userId) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM wc_provider WHERE userId = ?";
            JsonObject data = bridge.queryOne(query, userId);
            return BaseResponse.createFullMessageResponse(0, "success", data);
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject createProvider(long userId, JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String createService = json.get("createService").getAsString();
            String dateOfBirth = json.get("dateOfBirth").getAsString();
            String detail = json.get("detail").getAsString();
            String query = "INSERT INTO wc_provider(userId,createService,dateOfBirth,detail) VALUES (?,?,?,?)";
            bridge.update(query, userId, createService, dateOfBirth, detail);
            setRoleProvider(userId);
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    public JsonObject setRoleProvider(long userId) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "UPDATE wc_profile SET role = 1 where userId = ?";
            bridge.update(query, userId);
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject updateProvider(long userId, JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String createService = json.get("createService").getAsString();
            String dateOfBirth = json.get("dateOfBirth").getAsString();
            JsonObject detail = json.get("detail").getAsJsonObject();
            System.out.println(detail);
            String query = "UPDATE wc_provider SET createService = ?,dateOfBirth = ?,detail = ? where userId = ?";
            bridge.update(query, createService, dateOfBirth, detail.toString(), userId);
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
}
