package vn.vietdefi.services.gochie.notification;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;

public class GochieNotificationService implements IGochieNotificationService {

    @Override
    public JsonObject createTransactionHistory(JsonObject transactionDetails) {
        try {
            long currentSecond = System.currentTimeMillis() / 1000;
            long seller_id = transactionDetails.get("seller_id").getAsLong();
            long buyer_id = transactionDetails.get("buyer_id").getAsLong();
            String voucher_name = transactionDetails.get("voucher_name").getAsString();
            int type = transactionDetails.get("type").getAsInt();
            long balance = transactionDetails.get("price").getAsLong();
            long voucher_template_id = transactionDetails.get("voucher_template_id").getAsLong();
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "INSERT INTO gochie_transaction_history (seller_id, buyer_id, type, balance_fluctuations, voucher_name, time_transaction,voucher_template_id) VALUES (?,?,?,?,?,?,?)";
            bridge.update(query, seller_id, buyer_id, type, balance, voucher_name, currentSecond,voucher_template_id);
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject getTransactionHistory(long userid) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM gochie_transaction_history WHERE seller_id = ? OR buyer_id = ?";
            JsonArray data = bridge.query(query, userid, userid);
            return BaseResponse.createFullMessageResponse(0, "success", data);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    public JsonObject create(JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long create_time = System.currentTimeMillis() / 1000;
            String query = "INSERT INTO notification(title,content, create_time,type) VALUE(?,?,?,?)";
            String title = json.get("title").getAsString();
            int type = json.get("type").getAsInt();
            String content = json.get("content").getAsString();
            bridge.update(query, title, content, create_time, type);
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject get(JsonObject json) {
        try{
            long notification_id = json.get("notification_id").getAsLong();
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM notification WHERE id = ?";
            JsonObject data = bridge.queryOne(query, notification_id);
            boolean exist = bridge.queryExist(query, notification_id);
            if(!exist){
                return BaseResponse.createFullMessageResponse(10,"notification_not_found");
            }
            return BaseResponse.createFullMessageResponse(0,"success",data);

        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    public JsonObject send(JsonObject json) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            long userid = json.get("userid").getAsLong();
            long notification_id = json.get("notification_id").getAsLong();
            String queryUser = "SELECT * FROM user where id = ? ";
            boolean userExist = bridge.queryExist(queryUser, userid);
            if (!userExist) {
                return BaseResponse.createFullMessageResponse(10, "user_not_found");
            }
            String query = "SELECT * FROM notification WHERE id = ? ";
            boolean exist = bridge.queryExist(query, notification_id);
            if (!exist) {
                return BaseResponse.createFullMessageResponse(11, "notification_not_found");
            }
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
}
