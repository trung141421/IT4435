package vn.vietdefi.services.vdef.momo;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.RandomUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.util.network.OkHttpUtil;

import java.util.HashMap;
import java.util.Map;


public class MomoApi {
    public String generateImei() {
        return new StringBuilder(generateRandomString(8)).append("-")
                .append(generateRandomString(4)).append("-")
                .append(generateRandomString(4)).append("-")
                .append(generateRandomString(4)).append("-")
                .append(generateRandomString(12)).toString();
    }
    public String generateRandomString(int length) {
        String characters = "0123456789abcdef";
        int charactersLength = characters.length();
        StringBuilder randomString = new StringBuilder("");
        for (int i = 0; i < length; i++) {
            randomString.append(characters.charAt(RandomUtils.nextInt(0, charactersLength - 1)));
        }
        return randomString.toString();
    }
    public String generateRandom(int length) {
        String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int charactersLength = characters.length();
        StringBuilder randomString = new StringBuilder("");
        for (int i = 0; i < length; i++) {
            randomString.append(characters.charAt(RandomUtils.nextInt(0, charactersLength - 1)));
        }
        return randomString.toString();
    }

    public String getToken() {
        return new StringBuilder(generateRandom(22)).append(":")
                .append(generateRandom(9)).append("-")
                .append(generateRandom(20)).append("-")
                .append(generateRandom(12)).append("-")
                .append(generateRandom(7)).append("-")
                .append(generateRandom(7)).append("-")
                .append(generateRandom(53)).append("-")
                .append(generateRandom(9)).append("_")
                .append(generateRandom(11)).append("-")
                .append(generateRandom(4)).toString();
    }

    public JsonObject sendOtp(JsonObject data) {
        JsonObject result = sendOtpMessage(data);
//        if(result.get("errorCode").getAsInt()!=0){
            return BaseResponse.createFullMessageResponse(1000,"momo_error",result);
//        }else {
//            return BaseResponse.createFullMessageResponse(0,"success");
//        }
    }

    private JsonObject sendOtpMessage(JsonObject data) {
        String phone = data.get("phone").getAsString();
        String imei = data.get("imei").getAsString();
        String device = data.get("device").getAsString();
        String hardware = data.get("hardware").getAsString();
        String facture = data.get("facture").getAsString();
        String secureId = data.get("SECUREID").getAsString();
        String rkey = data.get("rkey").getAsString();
        String AAID = data.get("AAID").getAsString();
        String token = data.get("TOKEN").getAsString();
        String modelId = data.get("MODELID").getAsString();
        long microtime = System.currentTimeMillis();
        //Request Header Map<String, String>
        Map<String, String> header = new HashMap<>();
        header.put("agent_id","undefined");
        header.put("sessionkey","");
        header.put("user_phone","undefined");
        header.put("authorization","Bearer undefined");
        header.put("msgtype","SEND_OTP_MSG");
        header.put("Host","api.momo.vn");
        header.put("User-Agent","okhttp/3.14.17");
        header.put("app_version",momoDataConfig().get("appVer").getAsString());
        header.put("app_code",momoDataConfig().get("appCode").getAsString());
        header.put("device_os","Ios");

        //----Request Body----/
        JsonObject body = new JsonObject();
        body.addProperty("user",data.get("phone").getAsString() );
        body.addProperty("msgType","SEND_OTP_MSG" );
        body.addProperty("cmdId",microtime+"000000");
        body.addProperty("facture","Apple" );
        body.addProperty("lang","vi");
        body.addProperty("time",microtime);
        body.addProperty("channel","APP" );
        body.addProperty("appVer",momoDataConfig().get("appVer").getAsInt() );
        body.addProperty("appCode",momoDataConfig().get("appCode").getAsString() );
        body.addProperty("deviceOS","ANDROID" );
        body.addProperty("buildNumber",0 );
        body.addProperty("appId","vn.momo.platform" );
        body.addProperty("result",true );
        body.addProperty("errorCode",0 );
        body.addProperty("errorDesc","" );
        JsonObject momoMsg = new JsonObject();
        momoMsg.addProperty("_class","mservice.backend.entity.msg.RegDeviceMsg" );
        momoMsg.addProperty("number", phone);
        momoMsg.addProperty("imei",imei );
        momoMsg.addProperty("cname","Vietnam" );
        momoMsg.addProperty("ccode","084" );
        momoMsg.addProperty("device",device );
        momoMsg.addProperty("firmware","23" );
        momoMsg.addProperty("hardware",hardware );
        momoMsg.addProperty("manufacture",facture );
        momoMsg.addProperty("csp","" );
        momoMsg.addProperty("icc","" );
        momoMsg.addProperty("mcc","452");
        momoMsg.addProperty("device_os","Ios" );
        momoMsg.addProperty("secure_id",secureId );
        JsonObject extra = new JsonObject();
        extra.addProperty("action","SEND" );
        extra.addProperty("rkey",rkey );
        extra.addProperty("AAID",AAID );
        extra.addProperty("IDFA","");
        extra.addProperty("TOKEN",token );
        extra.addProperty("SIMULATOR","" );
        extra.addProperty("SECUREID",secureId );
        extra.addProperty("MODELID",modelId );
        extra.addProperty("isVoice",false );
        extra.addProperty("REQUIRE_HASH_STRING_OTP",true );
        extra.addProperty("checkSum","" );
        body.add("momoMsg",momoMsg);
        body.add("extra",extra);
        return OkHttpUtil.postJson("https://api.momo.vn/backend/otp-app/public/SEND_OTP_MSG",body.toString(),header);
    }

    private JsonObject momoDataConfig() {
        JsonObject momoConfig = new JsonObject();
        momoConfig.addProperty("appVer", 4080);
        momoConfig.addProperty("appCode", "4.0.8");
        return momoConfig;
    }

}
