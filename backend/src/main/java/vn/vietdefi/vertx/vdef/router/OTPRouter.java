package vn.vietdefi.vertx.vdef.router;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pengrad.telegrambot.request.GetFile;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.json.GsonUtil;
import vn.vietdefi.util.log.DebugLogger;

public class OTPRouter {

    public static void authorizeRandomOTP(RoutingContext rc) {
        try {
            long userId = Long.parseLong(rc.request().getHeader("userId"));
            long otpId = Long.parseLong(rc.request().getHeader("otpId"));
            int otp = Integer.parseInt(rc.request().getHeader("otp"));
            JsonObject response = VdefServices.otpService.authorizeRandomOTP(otpId, userId, otp);
            if(BaseResponse.isSuccessFullMessage(response)){
                rc.response().end(BaseResponse.createFullMessageResponse(0,"success").toString());
            }else{
                rc.response().end(BaseResponse.createFullMessageResponse(3,"invalid_otp").toString());
            }
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void verifyOTP(RoutingContext rc){
        try{
            long userId = Long.parseLong(rc.request().getHeader("userid"));
            String body = rc.body().asString();
            JsonObject data = GsonUtil.toJsonObject(body);
            data.addProperty("userid",userId);
            JsonObject response = VdefServices.otpService.verifyOTP(data);
            rc.response().end(response.toString());
        }catch (Exception e){

        }

    }
    public static void sendMessengerOtp(RoutingContext rc){
        try{
            long userId = Long.parseLong(rc.request().getHeader("userId"));
            JsonObject response = VdefServices.otpService.sendMessengerOtp(userId);
            rc.response().end(response.toString());
        }catch(Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

}
