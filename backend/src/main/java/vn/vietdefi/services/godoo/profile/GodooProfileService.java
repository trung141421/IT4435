package vn.vietdefi.services.godoo.profile;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;

public class GodooProfileService implements IGodooProfileService {
    public JsonObject compulsoryInfo(JsonObject data){
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            Long userId = data.get("userid").getAsLong();
            String nickname =  data.get("nickname").getAsString();
            JsonArray picture = data.get("picture").getAsJsonArray();
            String date_of_birth = data.get("date_of_birth").getAsString();
            String zodiac = data.get("zodiac").getAsString();
            JsonArray gender = data.get("gender").getAsJsonArray();
            String introduction = data.get("introduction").getAsString();
            int relationship = data.get("relationship").getAsInt();

            String query = "SELECT userId FROM godoo_profile WHERE userId = ?";
            Long userid = bridge.queryLong(query, userId);
            if(userid != null){
                query = "UPDATE godoo_profile SET nickname= ?,picture=?,date_of_birth=?,zodiac=?,gender=?,introduction=?,relationship=? WHERE userId = ?";
                bridge.queryOne(query, nickname, picture, date_of_birth, zodiac, gender, introduction, relationship, userId);
                return BaseResponse.createFullMessageResponse(0, "success",data);
            }else {
                query = "INSERT INTO godoo_profile(userId,nickname,picture,date_of_birth,zodiac,gender,introduction,relationship) VALUE(?,?,?,?,?,?,?,?)";
                bridge.queryOne(query, userId, nickname, picture, date_of_birth,zodiac, gender, introduction, relationship);
                return BaseResponse.createFullMessageResponse(0, "success",data);
            }
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    public JsonObject getProfile(long userId){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM godoo_profile WHERE userId = ?";
            JsonObject data = bridge.queryOne(query, userId);
            return BaseResponse.createFullMessageResponse(0, "success", data);
        }catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    public JsonObject updateAdditionalInfo(JsonObject data){
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            Long userId = data.get("userid").getAsLong();
            String hobbies = "";
            if(data.has("hobbies")){
                hobbies = data.get("hobbies").getAsString();
            }
            String height = "";
            if(data.has("height")){
                height = data.get("height").getAsString();
            }
            JsonObject career = new JsonObject();
            if(data.has("career")){
                career = data.get("career").getAsJsonObject();
            }
            String education  = "";
            if(data.has("education")){
                education = data.get("education").getAsString();
            }
            String hometown = "";
            if(data.has("hometown")){
                hometown = data.get("hometown").getAsString();
            }
            String living_in = "";
            if(data.has("living_in")){
                living_in = data.get("living_in").getAsString();
            }
            String sexual_orientation = "";
            if(data.has("sexual_orientation")){
                sexual_orientation = data.get("sexual_orientation").getAsString();
            }
            String drinking = "";
            if(data.has("drinking")){
                drinking = data.get("drinking").getAsString();
            }
            String religion = "";
            if(data.has("hobbies")){
                religion = data.get("religion").getAsString();
            }
            String smoking = "";
            if(data.has("hobbies")){
                smoking = data.get("smoking").getAsString();
            }
            String children = "";
            if(data.has("children")){
                children = data.get("children").getAsString();
            }
            String favourite_place = "";
            if(data.has("favourite_place")){
                favourite_place = data.get("favourite_place").getAsString();
            }
            String political = "";
            if(data.has("political")){
                political = data.get("political").getAsString();
            }
            String language = "";
            if(data.has("language")){
                language = data.get("language").getAsString();
            }
            String location = "";
            if(data.has("location")){
                location = data.get("location").getAsString();
            }

            JsonObject additionalInformation = new JsonObject();
            additionalInformation.addProperty("hobbies",hobbies);
            additionalInformation.addProperty("height",height);
            additionalInformation.add("career",career);
            additionalInformation.addProperty("education",education);
            additionalInformation.addProperty("hometown",hometown);
            additionalInformation.addProperty("living_in",living_in);
            additionalInformation.addProperty("sexual_orientation",sexual_orientation);
            additionalInformation.addProperty("drinking",drinking);
            additionalInformation.addProperty("religion",religion);
            additionalInformation.addProperty("smoking",smoking);
            additionalInformation.addProperty("children",children);
            additionalInformation.addProperty("favourite_place",favourite_place);
            additionalInformation.addProperty("political",political);
            additionalInformation.addProperty("language",language);
            additionalInformation.addProperty("location",location);

            String query = "SELECT userId FROM godoo_profile WHERE userId = ?";
            userId = bridge.queryLong(query, userId);
            if(userId != null){
                query = "UPDATE godoo_profile SET additional_information = ? WHERE userId = ?";
                bridge.queryOne(query, additionalInformation.toString(), userId);
                return BaseResponse.createFullMessageResponse(0, "success",data);
            }else {
                query = "INSERT INTO godoo_profile(additional_information) VALUE(?)";
                bridge.queryOne(query, additionalInformation.toString());
                return BaseResponse.createFullMessageResponse(0, "success",data);
            }
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
}
