package vn.vietdefi.services.vdef.auth.facebook.webhook;

import com.google.gson.JsonObject;

public interface IFacebookWebhook {
    JsonObject webhookResponse(JsonObject json);
    JsonObject webhookPost(JsonObject json);
    JsonObject webhookGet (String mode, String challenge, String verify_token);
}
