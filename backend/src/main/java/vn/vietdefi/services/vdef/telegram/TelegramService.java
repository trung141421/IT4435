package vn.vietdefi.services.vdef.telegram;

import com.google.gson.JsonObject;
import io.vertx.ext.web.handler.impl.HotpAuthHandlerImpl;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.services.vdef.telegram.bot.VdefTelegramBot;
import vn.vietdefi.util.file.FileUtil;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;
import vn.vietdefi.util.string.StringUtil;
import vn.vietdefi.util.thread.ThreadpoolWorker;

public class TelegramService implements ITelegramService {
    public JsonObject requestLinkAccount(JsonObject json){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long userId = json.get("userid").getAsLong();
            String username = json.get("username").getAsString();
            JsonObject config = VdefTelegramBot.instance().config;
            String url = config.get("url").getAsString();
            String activeCode = StringUtil.generateRandomStringNumberCharacter(6);
            url = new StringBuilder(url).append("?start=").append(activeCode).toString();
            json = new JsonObject();
            String isExist = bridge.queryString("SELECT userId FROM telegram_user WHERE userId = ?", userId);
            if (isExist == null) {
                json.addProperty("link", url);
                String insert = "INSERT INTO telegram_user(userId ,username,active_code) VALUE(?,?,?)";
                bridge.update(insert, userId, username, activeCode);
                return BaseResponse.createFullMessageResponse(0, "success", json);
            }else {
                json.addProperty("link", url);
                String update = "UPDATE telegram_user SET username =? ,active_code = ? WHERE userId = ?";
                bridge.update(update,  username, activeCode,userId);
                return BaseResponse.createFullMessageResponse(0, "success", json);
            }
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    public JsonObject activeTelegramAccount(long telegramId, String activeCode,String fullName) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "UPDATE telegram_user SET telegramId = ? WHERE active_code = ?";
            int row = bridge.update(query, telegramId, activeCode);
            if (row == 1) {
                query = "UPDATE telegram_user SET telegram_info = ? WHERE telegramId = ?";
                bridge.queryOne(query, fullName, telegramId);
                return BaseResponse.createFullMessageResponse(0, "success");
            } else {
                return BaseResponse.createFullMessageResponse(10, "invalid_code");
            }
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject sendMessage(final long telegramdId, final String message) {
        ThreadpoolWorker.instance().executor.execute(() -> {
            VdefTelegramBot.instance().sendMessage(telegramdId, message);
        });
        return BaseResponse.createFullMessageResponse(0, "success");
    }

    @Override
    public JsonObject sendMessageToAdmin(final String message) {
        ThreadpoolWorker.instance().executor.execute(() -> {
            VdefTelegramBot.instance().sendAdminMessage(message);
        });
        return BaseResponse.createFullMessageResponse(0, "success");
    }

    @Override
    public JsonObject sendLogMessage(final String message) {
        ThreadpoolWorker.instance().executor.execute(() -> {
            VdefTelegramBot.instance().sendLogMessage(message);
        });
        return BaseResponse.createFullMessageResponse(0, "success");
    }

    @Override
    public JsonObject updatePhoneNumber(long telegramId, String phone) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "UPDATE telegram_user SET phone_number = ? WHERE telegramId = ?";
            bridge.update(query, phone, telegramId);
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public void sendNotificationService(long userId, JsonObject data) {
        try {
            long telegramId = getTelegramId(userId);
            JsonObject content = data.get("content").getAsJsonObject();
            String title = "";
            String mainContent = "";
            String link = "";
            if(content.has("title")){
                title = content.get("title").getAsString();
            }
            if(content.has("content")){
                mainContent = content.get("content").getAsString();
            }
            if(content.has("link")){
                link = content.get("link").getAsString();
            }
            VdefTelegramBot.instance().sendNotification(telegramId, title,mainContent,link);
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
        }
    }
    public JsonObject sendTelegramOTP(long userId) {
        try {
            long telegramId = VdefServices.telegramService.getTelegramId(userId);
            if (telegramId == -1) {
                return BaseResponse.createFullMessageResponse(10, "link_telegram_first");
            } else {
                JsonObject json = VdefServices.otpService.generateRandomOTP(userId, 1);
                if (json == null) {
                    return BaseResponse.createFullMessageResponse(1, "system_error");
                } else {
                    int otp = json.get("randomOtp").getAsInt();
                    VdefTelegramBot.instance().sendNotification(telegramId,"OTP CODE","Do not share for anyone\n"+otp,null);
                    return BaseResponse.createFullMessageResponse(0, "success", json);
                }
            }
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    public long getTelegramId(long userid){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            Long telegramId = bridge.queryLong("SELECT telegramId FROM telegram_user WHERE userId = ?", userid);
            return telegramId;
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return -1;
        }
    }

    @Override
    public JsonObject checkLinkTelegram(long userId) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT telegram_info FROM telegram_user WHERE userId = ?";
            JsonObject data = bridge.queryOne(query, userId);
            if(data!=null){
                return BaseResponse.createFullMessageResponse(0,"linked",data);
            }else{
                return BaseResponse.createFullMessageResponse(10,"not_link");
            }
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }
    public JsonObject getTelegramInfo(long userId){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT telegram_info FROM telegram_user WHERE userId = ?";
            JsonObject data = bridge.queryOne(query, userId);
            return data;
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return null;
        }
    }
}
