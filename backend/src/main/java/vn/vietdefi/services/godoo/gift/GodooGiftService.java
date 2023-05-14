package vn.vietdefi.services.godoo.gift;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;

public class GodooGiftService implements IGodooGiftService {
    public JsonObject buyGift(JsonObject data){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long userId = data.get("userid").getAsLong();
            int giftTemplateId = data.get("gift_template_id").getAsInt();

            String query = "SELECT balance FROM godoo_wallet WHERE userId = ?";
            Long balance = bridge.queryLong(query, userId);
            query = "SELECT price FROM godoo_gift_template WHERE id = ?";
            Long price = bridge.queryLong(query, giftTemplateId);
            if(balance < price){
                return BaseResponse.createFullMessageResponse(10,"not_enough_balance");
            }else {
                query = "INSERT INTO godoo_gifts(gift_template_id,time_bought,buyer_id) VALUE(?,?,?)";
                bridge.queryOne(query, giftTemplateId, System.currentTimeMillis(), userId);
                balance -= price;
                query = "UPDATE godoo_wallet SET balance = ? WHERE userId = ?";
                bridge.queryOne(query,balance, userId);
                return BaseResponse.createFullMessageResponse(0, "success");
            }
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    public JsonObject createGiftTemplate(JsonObject data){
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String name = data.get("name").getAsString();
            String icon = data.get("icon").getAsString();
            String introduction = data.get("introduction").getAsString();
            long price = data.get("price").getAsLong();
            String query = "INSERT INTO godoo_gift_template(name,icon,introduction,price) VALUE(?,?,?,?)";
            bridge.queryOne(query, name, icon, introduction, price);
            return BaseResponse.createFullMessageResponse(0, "success");
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    public JsonObject giveSwipe(JsonObject data){
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long receiverId = data.get("receiver_id").getAsLong();
            long giftId = data.get("gift_id").getAsLong();
            String message = data.get("message").getAsString();
            String query = "UPDATE godoo_gifts SET receiver_id = ?, message = ?, time_gave = ? WHERE id = ?";
            bridge.queryOne(query, receiverId, message , System.currentTimeMillis(), giftId);
            return BaseResponse.createFullMessageResponse(0, "success",data);
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    public JsonObject giveChat(JsonObject data){
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long receiverId = data.get("receiver_id").getAsLong();
            long giftId = data.get("gift_id").getAsLong();
            String message = data.get("message").getAsString();
            String query = "UPDATE godoo_gifts SET receiver_id = ?, message = ?, time_gave = ?, time_received = ?  WHERE id = ?";
            long timeGive = System.currentTimeMillis();
            bridge.queryOne(query, receiverId, message , timeGive,timeGive,giftId);
            return BaseResponse.createFullMessageResponse(0, "success",data);
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    public JsonObject getBucket(JsonObject data){
        try {
            long userId = data.get("userid").getAsLong();
            int giftTemplateId = data.get("gift_template_id").getAsInt();
            if(giftTemplateId == 0) {
                SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
                String query = "SELECT gift_template_id, COUNT(id) AS SOLUONG FROM godoo_gifts WHERE buyer_id = ? and receiver_id is null GROUP BY gift_template_id";
                JsonArray response = bridge.query(query, userId);
                return BaseResponse.createFullMessageResponse(0, "success", response);
            }else{
                SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
                String query = "SELECT  gift_template_id, COUNT(id) AS SOLUONG FROM godoo_gifts WHERE buyer_id = ? AND receiver_id is null AND gift_template_id = ? GROUP BY gift_template_id";
                JsonArray response = bridge.query(query, userId,giftTemplateId);
                return BaseResponse.createFullMessageResponse(0, "success", response);
            }
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    public JsonObject receiveGift(JsonObject data){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long receiverId = data.get("receiverid").getAsLong();
            long giftId = data.get("gift_id").getAsLong();
            String query = "SELECT receiver_id FROM godoo_gifts WHERE id = ?";
            Long receiveredId = bridge.queryLong(query, giftId);
            if(receiveredId != 0){
                return BaseResponse.createFullMessageResponse(10, "gift_received_before");
            }else {
                query = "UPDATE godoo_gifts SET receiver_id = ? , time_received = ? WHERE id = ?";
                bridge.queryOne(query, receiverId, System.currentTimeMillis(), giftId);
                return BaseResponse.createFullMessageResponse(0, "success", data);
            }
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    public JsonObject getGivenGift(JsonObject data){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long receiverId = data.get("receiver_id").getAsLong();
            long senderId = data.get("senderid").getAsLong();
            if(receiverId == 0) {
                String query = "SELECT * FROM godoo_gifts WHERE buyer_id = ?";
                JsonArray array = bridge.query(query, senderId);
                return BaseResponse.createFullMessageResponse(0,"success",array);
            }else{
                String query = "SELECT * FROM godoo_gifts WHERE receiver_id = ? AND buyer_id = ?";
                JsonArray array = bridge.query(query, receiverId, senderId);
                return BaseResponse.createFullMessageResponse(0,"success",array);
            }
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    public JsonObject getBeGivenGift(JsonObject data){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long receiverId = data.get("receiverid").getAsLong();
            long senderId = data.get("sender_id").getAsLong();
            if(senderId == 0) {
                String query = "SELECT * FROM godoo_gifts WHERE receiver_id = ?";
                JsonArray array = bridge.query(query, receiverId);
                return BaseResponse.createFullMessageResponse(0,"success",array);
            }else{
                String query = "SELECT * FROM godoo_gifts WHERE receiver_id = ? AND buyer_id = ?";
                JsonArray array = bridge.query(query, receiverId, senderId);
                return BaseResponse.createFullMessageResponse(0,"success",array);
            }
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
}
