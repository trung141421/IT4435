package vn.vietdefi.services.vdef.auth.apple;

import com.google.gson.JsonObject;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.util.file.FileUtil;
import vn.vietdefi.util.json.GsonUtil;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.network.OkHttpUtil;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;
import vn.vietdefi.util.string.StringUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;


import static vn.vietdefi.services.vdef.auth.apple.AppleLogic.AppleLogic.*;

public class AppleService implements IAppleService {

    public PrivateKey privateKey;
    public String redirect_uri;
    public String client_id;
    public String client_secret;
    public String APPLE_APPLEID_URL;
    public String key_id;
    public String team_id;

    public AppleService(){
        JsonObject appleConfig = FileUtil.getJsonObject("config/apple/appleConfig.json", false);
        redirect_uri = appleConfig.get("redirect_uri").getAsString();
        client_id = appleConfig.get("client_id").getAsString();
        APPLE_APPLEID_URL = appleConfig.get("APPLE_APPLEID_URL").getAsString();
        key_id = appleConfig.get("key_id").getAsString();
        team_id = appleConfig.get("team_id").getAsString();
        Path path = Paths.get("config/apple/AuthKey_GUP7DX4B83.p8");
        try {
            privateKey = getPrivateKey(path, "EC");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        client_secret = generateJWT(appleConfig, privateKey);
    }
    public JsonObject getState(){
        try{
            String state = StringUtil.generateRandomStringNumberCharacter(10);
            JsonObject response = new JsonObject();
            response.addProperty("state", state);
            return BaseResponse.createFullMessageResponse(0,"success",response);
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    //Client
    public JsonObject client_register(String data) {
        try {
            JsonObject json = formDataToJsonObject(data);
            return BaseResponse.createFullMessageResponse(0,"success", json);
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject authorize(String code) {
        try {
            //send request to AppleServer to verify and receive access token
            RequestBody formBody = new FormBody.Builder().add("client_id", client_id)
                    .add("client_secret", client_secret)
                    .add("code", code)
                    .add("grant_type", "authorization_code")
                    .add("redirect_uri", redirect_uri).build();
            JsonObject response = OkHttpUtil.postForm(
                    "https://appleid.apple.com/auth/token",
                    formBody,
                    null
            );
            return BaseResponse.createFullMessageResponse(0,"success", response);
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    public JsonObject registerApple(JsonObject json){
        try{
            long currentAllSeconds = System.currentTimeMillis();
            String id_token = json.get("id_token").getAsString();
            String accessToken = json.get("access_token").getAsString();
            int expires_in = json.get("expires_in").getAsInt();
            long token_expired = currentAllSeconds + expires_in*1000;
            String refresh_token = json.get("refresh_token").getAsString();
            String email = "ap-" + GsonUtil.toJsonObject(getClaimsJWT(id_token)).get("email").getAsString();
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String password = StringUtil.generateRandomStringNumberCharacter(32);
            //create user
            String createUser = "INSERT INTO user (username, password, token, role, status, createTime) VALUES (?,?,?,?,?,?)";
            bridge.update(createUser, email, password, accessToken, 0, 0, currentAllSeconds);


            //create apple-user
            String getid = "SELECT id FROM user WHERE username = ?";
            JsonObject data = bridge.queryOne(getid, email);
            String query = "INSERT INTO ap_user VALUES (?,?,?,?,?)";
            bridge.update(query,email, data.get("id").getAsInt(), accessToken, token_expired, refresh_token);
            return BaseResponse.createFullMessageResponse(0,"success");
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    public JsonObject loginApple(JsonObject json){
        try{
            long currentAllSeconds = System.currentTimeMillis();
            String access_token = json.get("access_token").getAsString();
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT ap_access_token_expired, ap_access_token, userId FROM ap_user WHERE ap_access_token = ?";
            boolean isExist = bridge.queryExist(query, access_token);
            if(!isExist){
                return BaseResponse.createFullMessageResponse(2,"invalid_token");
            }
            JsonObject data = bridge.queryOne(query, access_token);
            if(currentAllSeconds > data.get("ap_access_token_expired").getAsLong()*1000){
                return BaseResponse.createFullMessageResponse(3,"token_expired");
            }
            return BaseResponse.createFullMessageResponse(0,"success", data);
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    public JsonObject refreshToken(JsonObject json){
        try{
            //validate refreshtoken
            String refresh_token = json.get("refresh_token").getAsString();
            RequestBody formBody = new FormBody.Builder().add("client_id", client_id)
                    .add("client_secret", client_secret)
                    .add("grant_type", "refresh_token")
                    .add("refresh_token", refresh_token).build();
            JsonObject response = OkHttpUtil.postForm(
                    "https://appleid.apple.com/auth/token",
                    formBody,
                    null
            );

            //update access_token
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "UPDATE ap_user SET ap_access_token = ?, ap_access_token_expired = ? WHERE email = ?";
            String email = GsonUtil.toJsonObject(getClaimsJWT(response.get("id_token").getAsString())).get("email").getAsString();
            String accessToken = response.get("access_token").getAsString();
            int expires_in = response.get("expires_in").getAsInt();
            bridge.update(query, accessToken, expires_in, email);
            return BaseResponse.createFullMessageResponse(0, "success", response);
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }
}
