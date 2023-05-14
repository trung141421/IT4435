package vn.vietdefi.services.vdef.wallet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import static java.sql.Types.DOUBLE;
import static java.sql.Types.BIGINT;

public class WalletService implements IWalletService {

    @Override
    public JsonObject getWallet(long userId, String username) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM wallet WHERE userId = ?";
            JsonObject data  = bridge.queryOne(query, userId);
            if (data == null) {
                data = new JsonObject();
                data.addProperty("userId", userId);
                data.addProperty("username", username);
                data.addProperty("balance", 0);
                data.addProperty("status", 0);
                query = "INSERT INTO wallet(userId,username,balance,status) VALUES (?,?,0,0)";
                bridge.update(query, userId, username);
                return BaseResponse.createFullMessageResponse(0, "success", data);
            }
            return BaseResponse.createFullMessageResponse(0, "success", data);

        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    // change status from 0 (ok) to 1 (lock)
    @Override
    public JsonObject lockWallet(long userId) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "UPDATE wallet SET status = 1 where userId = ?";
            bridge.update(query, userId);
            return BaseResponse.createFullMessageResponse(0, "success");
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject addBalance(long userId, String username,int status,JsonObject data) {
        try {
            if (status == 1){
                return BaseResponse.createFullMessageResponse(11, "account_lock");
            }
            double amount = data.get("amount").getAsDouble();
            if (amount <= 0) {
                return BaseResponse.createFullMessageResponse(12, "amount_invalid");
            }
            String service = data.get("service").getAsString();
            String detail = data.get("detail").getAsString();
            long createTime = System.currentTimeMillis();
            String reference = data.get("reference").getAsString();
            String query = "CALL add_wallet_balance(?,?,?,?,?,?,?,?,?)";
            JsonObject result = transactionBalanceStoredProcedure(query, userId, username,
                    amount, service, reference, detail, createTime);

            return BaseResponse.createFullMessageResponse(0, "success", result);
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }


    @Override
    public JsonObject subBalance(long userId, String username,int status, JsonObject data) {
        try {
            if (status == 1){
                return BaseResponse.createFullMessageResponse(11, "account_lock");
            }
            double amount = data.get("amount").getAsDouble();
            if (amount <= 0) {
                return BaseResponse.createFullMessageResponse(12, "amount_invalid");
            }
            String service = data.get("service").getAsString();
            String detail = data.get("detail").getAsString();
            long createTime = System.currentTimeMillis();
            String reference = data.get("reference").getAsString();
            String query = "CALL sub_wallet_balance(?,?,?,?,?,?,?,?,?)";
            JsonObject result = transactionBalanceStoredProcedure(query, userId, username,
                    amount, service, reference, detail, createTime);
            // Response amount have "-" represent sub balance
            result.remove("amount");
            result.addProperty("amount",0-amount);
            if (result.get("error") != null){
                return BaseResponse.createFullMessageResponse(10, "balance_can't_be_negative");
            }
            return BaseResponse.createFullMessageResponse(0, "success", result);
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    private JsonObject transactionBalanceStoredProcedure(String query, long userId, String username,
                                                         double amount, String service, String reference,
                                                         String detail, long createTime) throws Exception {
        JsonObject result = null;
        Connection connection = null;
        ResultSet rs = null;
        CallableStatement st = null;
        Exception exception = null;
        try {
            connection = HikariClients.instance().defaulSQLJavaBridge().sqlClient.getConnection();
            st = connection.prepareCall(query);
            st.setLong(1, userId);
            st.setString(2, username);
            st.setDouble(3, amount);
            st.setString(4, service);
            st.setString(5, reference);
            st.setString(6, detail);
            st.setLong(7, createTime);
            st.registerOutParameter(8, DOUBLE);
            st.registerOutParameter(9, BIGINT);
            rs = st.executeQuery();
            double balance = st.getDouble(8);
            long exchange_id = st.getLong(9);
            result = createWalletExchange(exchange_id, userId, username,
                    service, amount, balance, reference, detail, createTime);
            if (exchange_id == 0){
                result.addProperty("error","Balance can't be negative");
            }
        } catch (Exception e) {
            exception = e;
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
        } finally {
            HikariClients.instance().defaulSQLJavaBridge()
                    .sqlClient.releaseConnection(connection, st, rs);
            if (exception != null) {
                throw exception;
            }
            return result;
        }
    }

    private JsonObject createWalletExchange(long exchange_id, long userId,
                                            String username, String service,
                                            double amount, double balance,
                                            String reference, String detail, long createTime) {
        JsonObject data = new JsonObject();
        data.addProperty("id", exchange_id);
        data.addProperty("userId", userId);
        data.addProperty("username", username);
        data.addProperty("service", service);
        data.addProperty("amount", amount);
        data.addProperty("balance", balance);
        data.addProperty("reference", reference);
        data.addProperty("detail", detail);
        data.addProperty("createTime", createTime);
        return data;
    }

    @Override
    public JsonObject getWalletExchangeByPage(long userId, long page, long records_per_page) {
        try {
            long offset = (page - 1) * records_per_page;
            return getWalletExchangeLimitOffset(userId, records_per_page, offset);
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    @Override
    public JsonObject getWalletExchangeLimitOffset(long userId, long limit, long offset) {
        try {
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * from wallet_exchange where userId = ? limit ? offset ?";
            JsonArray walletExchanges = bridge.query(query, userId, limit, offset);
            return BaseResponse.createFullMessageResponse(0,"success", walletExchanges);
        } catch (Exception e) {
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
}