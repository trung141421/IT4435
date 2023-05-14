package vn.vietdefi.services.vdef.otp;

import com.google.gson.JsonObject;

public interface IOTPService {
    JsonObject generateRandomOTP(long userId,int type);
    JsonObject authorizeRandomOTP(long otpId, long userId, int otp);
    JsonObject verifyOTP(JsonObject data);
    JsonObject sendMessengerOtp (long userId);
}
