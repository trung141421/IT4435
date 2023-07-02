package trung.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class BaseResponse {
    public static JsonObject createFullMessageResponse(int error, String message, JsonElement data){
        JsonObject json = new JsonObject();
        json.addProperty("error", error);
        json.addProperty("messsage", message);
        json.add("data", data);
        return json;
    }
    public static JsonObject createFullMessageResponse(int error, String message){
        JsonObject json = new JsonObject();
        json.addProperty("error", error);
        json.addProperty("messsage", message);
        return json;
    }
    public static JsonObject creaateShortMessageResponse(int error, String message, JsonElement data){
        JsonObject json = new JsonObject();
        json.addProperty("e", error);
        json.addProperty("m", message);
        json.add("d", data);
        return json;
    }
    public static JsonObject creaateShortMessageResponse(int error, String message){
        JsonObject json = new JsonObject();
        json.addProperty("e", error);
        json.addProperty("m", message);
        return json;
    }

    public static boolean isSuccessFullMessage(JsonObject response) {
        return response.get("error").getAsInt() == 0;
    }
}
