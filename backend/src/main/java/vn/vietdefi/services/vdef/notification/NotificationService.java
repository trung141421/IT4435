package vn.vietdefi.services.vdef.notification;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;
import vn.vietdefi.util.thread.ThreadpoolWorker;

public class NotificationService implements INotificationService{
    String service = null;
    public NotificationService(String service){
        this.service = service;
    }

    @Override
    public JsonObject subscribeNotification(long userId, JsonObject data) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String channels = data.getAsJsonArray("channels").toString();
            String exceptTypes = data.getAsJsonArray("exceptTypes").toString();
            String query = new StringBuilder("SELECT userId FROM ")
                    .append(service).append("_notification_subscribe ")
                    .append(" WHERE userId = ?").toString();
            boolean isExist = bridge.queryExist(query, userId);
            if(isExist){
                query = new StringBuilder("UPDATE ")
                        .append(service).append("_notification_subscribe ")
                        .append("SET exceptTypes = ?, channels = ?")
                        .append(" WHERE userId = ?").toString();
                bridge.update(query, exceptTypes, channels, userId);
            }else{
                query = new StringBuilder("INSERT INTO ")
                        .append(service).append("_notification_subscribe ")
                        .append(" (userId, exceptTypes, channels) VALUE (?, ?, ?)").toString();
                bridge.update(query, userId, exceptTypes, channels);
            }
            return BaseResponse.createFullMessageResponse(0,"success", data);
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject   createNotification(JsonObject data) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long createTime = System.currentTimeMillis();
            data.addProperty("createTime", createTime);
            String table = new StringBuilder(service).append("_notification").toString();
            bridge.insertObjectToDB(table, data);
            return BaseResponse.createFullMessageResponse(0,"success", data);
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject sendNotification(JsonObject data) {
        try{
            long userId = data.get("userId").getAsLong();
            if(userId == 0){
                return sendNotificationToAll(data);
            }else{
                return sendNotificationToUser(userId, data);
            }
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    public JsonObject sendNotificationToUser(long userId,
                                             JsonObject data) {
        try{
            ThreadpoolWorker.instance().executor.execute(()->{
                try{
                    final String type = data.get("type").getAsString();
                    SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
                    String query = new StringBuilder("SELECT * FROM ")
                            .append(service).append("_notification_subscribe").toString();
                    final JsonObject subscribe = bridge.queryOne(query);
                    sendNotificationToSubscribe(type, userId, subscribe, data);
                }catch (Exception e){
                    String stacktrace = ExceptionUtils.getStackTrace(e);
                    DebugLogger.error(stacktrace);
                }
            });
            return BaseResponse.createFullMessageResponse(0,"success", data);
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    private void sendNotificationToSubscribe(String type,
                                             long userId,
                                             JsonObject subscribe,
                                             JsonObject data) {
        JsonArray exceptTypes = subscribe.getAsJsonArray("exceptTypes");
        boolean ignore = false;
        for(int j = 0; j < exceptTypes.size(); j++){
            String exceptType = exceptTypes.get(j).getAsString();
            if(exceptType.equals(type)) {
                ignore = true;
                break;
            }
        }
        if(!ignore){
            JsonArray channels = subscribe.getAsJsonArray("channels");
            for(int k = 0; k < channels.size(); k++){
                String chanel = channels.get(k).getAsString();
                sendNotificationToUserByChannel(userId, chanel, data);
            }
        }
    }

    private void sendNotificationToUserByChannel(long userId,
                                                 String chanel,
                                                 JsonObject data) {
        if(chanel.equals("telegram")){
            VdefServices.telegramService.sendNotificationService(userId, data);
        }
    }
    public JsonObject sendNotificationToAll(JsonObject data) {
        try{
            String type = data.get("type").getAsString();
            ThreadpoolWorker.instance().executor.execute(()->{
                try {
                    SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
                    String query = new StringBuilder("SELECT * FROM ")
                            .append(service).append("_notification_subscribe").toString();
                    JsonArray subscribers = bridge.query(query);
                    for(int i = 0; i < subscribers.size(); i++){
                        JsonObject subscribe = subscribers.get(i).getAsJsonObject();
                        final long userId = subscribe.get("userId").getAsLong();
                        sendNotificationToSubscribe(type, userId, subscribe, data);
                    }
                }catch (Exception e){
                    String stacktrace = ExceptionUtils.getStackTrace(e);
                    DebugLogger.error(stacktrace);
                }
            });
            return BaseResponse.createFullMessageResponse(0,"success", data);
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }
}