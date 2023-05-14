package vn.vietdefi.services.wecare.review;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;

public class WecareReviewService implements IWecareReviewService {
    @Override
    public JsonObject reviewCustomer(JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            int scheduleId = json.get("scheduleId").getAsInt();
            int serviceId = json.get("serviceId").getAsInt();
            long customerId = json.get("customerId").getAsLong();
            int customerStar = json.get("customerStar").getAsInt();
            // check relationship review exist ( check database to know more )
            String checkRelaReviewExist = "SELECT * FROM wc_review WHERE scheduleId = ?";
            boolean isRelaReview = bridge.queryExist(checkRelaReviewExist, scheduleId);
            if (isRelaReview) {
                String query = "UPDATE wc_review SET customerStar = ? where scheduleId = ?";
                bridge.update(query, customerStar, scheduleId);
            } else {
                String query = "INSERT INTO wc_review (scheduleId,serviceId,customerId,customerStar) VALUES (?,?,?,?)";
                bridge.update(query, scheduleId, serviceId, customerId, customerStar);
            }
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject reviewService(JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            int scheduleId = json.get("scheduleId").getAsInt();
            long customerId = json.get("customerId").getAsLong();
            int serviceId = json.get("serviceId").getAsInt();
            int serviceStar = json.get("serviceStar").getAsInt();
            // check relationship review exist ( check database to know more )
            String checkRelaReviewExist = "SELECT * FROM wc_review WHERE scheduleId = ?";
            boolean isRelaReview = bridge.queryExist(checkRelaReviewExist, scheduleId);
            if (isRelaReview) {
                String query = "UPDATE wc_review SET serviceStar = ? where scheduleId = ?";
                bridge.update(query, serviceStar, scheduleId);
            } else {
                String query = "INSERT INTO wc_review (scheduleId,serviceId,customerId,serviceStar) VALUES (?,?,?,?)";
                bridge.update(query, scheduleId, serviceId, customerId, serviceStar);
            }
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject getCustomerReview(JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long customerId = json.get("customerId").getAsLong();
            String query = "SELECT scheduleId, serviceId, customerStar FROM wc_review WHERE customerId = ?";
            JsonArray data = bridge.query(query, customerId);
            return BaseResponse.createFullMessageResponse(0, "success", data);
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject getServiceReview(JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long customerId = json.get("serviceId").getAsLong();
            String query = "SELECT scheduleId, customerId, serviceStar FROM wc_review WHERE serviceId = ?";
            JsonArray data = bridge.query(query, customerId);
            return BaseResponse.createFullMessageResponse(0, "success", data);
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
}
