package vn.vietdefi.services.vdef.otp;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;

public class OTPService implements IOTPService {
    @Override
    public JsonObject generateRandomOTP(long userId, int type) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            int otp = RandomUtils.nextInt(100000, 999999);
            JsonObject data = new JsonObject();
            data.addProperty("randomOtp", otp);

            Long id = bridge.queryLong("SELECT id FROM random_otp WHERE userId = ?", userId);
            if (id != null) {
                String update = "UPDATE random_otp SET random_otp = ?, expired_time = ? WHERE userId = ?";
                long expiredTime = System.currentTimeMillis() + 5*60 * 1000;
                bridge.update(update, otp, expiredTime, userId);
                data.addProperty("otpId", id);
                return data;
            } else {
                String insert = "INSERT INTO random_otp(userId,random_otp,expired_time,status,type) VALUE(?,?,?,?,?)";
                long expiredTime = System.currentTimeMillis() + 60 * 1000;
                bridge.update(insert, userId, otp, expiredTime, 0, type);
                Long otpId = bridge.queryLong("SELECT id FROM random_otp WHERE userId = ? and type = ?", userId, type);
                data.addProperty("otpId", otpId);
                return data;
            }
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return null;
        }
    }


    @Override
    public JsonObject authorizeRandomOTP(long otpId, long userId, int otp) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM random_otp WHERE userId = ?";
            JsonObject json;
            try {
                json = bridge.queryOne(query, userId);
            } catch (Exception e) {
                return BaseResponse.createFullMessageResponse(10, "otp_first");
            }
            long baseId = json.get("id").getAsLong();
            if (baseId != otpId) {
                return BaseResponse.createFullMessageResponse(11, "otp_not_found");
            } else {
                int baseOtp = json.get("random_otp").getAsInt();
                if (baseOtp != otp) {
                    return BaseResponse.createFullMessageResponse(12, "otp_invalid");
                } else {
                    long expiredTime = json.get("expired_time").getAsLong();
                    long current = System.currentTimeMillis() / 1000;
                    if (current > expiredTime) {
                        return BaseResponse.createFullMessageResponse(13, "otp_expired");
                    } else {
                        String used = "UPDATE random_otp SET status = ? WHERE id = ?";
                        bridge.update(used, 1, otpId);
                        return BaseResponse.createFullMessageResponse(0, "success");
                    }
                }
            }
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    public JsonObject verifyOTP(JsonObject data){
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long userId = data.get("userid").getAsLong();
            int otp = data.get("otp").getAsInt();
            String query = "SELECT random_otp FROM random_otp WHERE userId = ?";
            Integer random_otp = bridge.queryInteger(query, userId);
            if(random_otp!=null){
                if(random_otp==otp){
                    query = "SELECT expired_time FROM random_otp WHERE userId = ?";
                    Long expiredTime = bridge.queryLong(query, userId);
                    if(System.currentTimeMillis()>expiredTime){
                        return BaseResponse.createFullMessageResponse(10, "time_out");
                    }else {
                        query = "SELECT token FROM user WHERE id = ?";
                        data = bridge.queryOne(query, userId);
                        data.addProperty("userid", userId);
                        return BaseResponse.createFullMessageResponse(0, "success", data);
                    }
                }else{
                    return BaseResponse.createFullMessageResponse(10, "otp_wrong");
                }
            }else{
                return BaseResponse.createFullMessageResponse(11, "otp_first");
            }
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject sendMessengerOtp(long userId) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            String query = "SELECT messengerId FROM messenger_user WHERE userId = ?";
            Long messengerId = bridge.queryLong(query,userId);
            if(messengerId == null) return BaseResponse.createFullMessageResponse(10, "link_messenger_first");

            JsonObject data = VdefServices.otpService.generateRandomOTP(userId,2);
            int otp = data.get("randomOtp").getAsInt();
            Long otpId = data.get("otpId").getAsLong();
            JsonObject json = BaseResponse.createFullMessageResponse(0,"success");
            json.addProperty("otp",otp);
            json.addProperty("otpId",otpId);
            return json;
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }

    }

}
