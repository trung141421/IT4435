package vn.vietdefi.services.vdef.auth.facebook.webhook;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.log.DebugLogger;

public class FacebookWebhook implements IFacebookWebhook{

    @Override
    public JsonObject webhookResponse(JsonObject json) {
        try{
            DebugLogger.info(json.getAsString());
            JsonArray info = json.get("entry").getAsJsonArray();
            JsonObject entry = info.get(0).getAsJsonObject();
            JsonArray messaging = entry.get("messaging").getAsJsonArray();
            JsonObject data = messaging.get(0).getAsJsonObject();
            JsonObject sender = data.get("sender").getAsJsonObject();
            Long senderid = sender.get("id").getAsLong();

            JsonObject message = data.get("message").getAsJsonObject();
            String text = message.get("text").getAsString();

            JsonObject response = VdefServices.messengerBotService.receiveMessage(senderid,text);
            return response;

        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject webhookPost(JsonObject json) {
        return null;
    }

    @Override
    public JsonObject webhookGet(String mode, String challenge, String verify_token) {
        return null;
    }
}
