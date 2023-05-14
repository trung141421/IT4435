package vn.vietdefi.services.vdef.momo;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.util.log.DebugLogger;


public class MomoService implements IMomoService {
    public JsonObject getOtp(JsonObject json) {
        try{
            String phone = json.get("phone").getAsString();
            String password = json.get("password").getAsString();

            //Momo data
            MomoApi momo = new MomoApi();
            JsonObject data = new JsonObject();
            data.addProperty("phone",phone );
            data.addProperty("device","iPhone" );
            data.addProperty("hardware","iPhone" );
            data.addProperty("facture","Apple" );
            data.addProperty("SECUREID","");
            data.addProperty("MODELID","");
            data.addProperty("imei",momo.generateImei() );
            data.addProperty("rkey",momo.generateRandom(20) );
            data.addProperty("AAID","" );
            data.addProperty("TOKEN",momo.getToken());

            //TODO
            JsonObject response = momo.sendOtp(data);
            return  response;
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }



}
