package vn.vietdefi.services.godoo.wallet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.godoo.match.IGodooMatchService;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;

public class GodooWalletService implements IGodooWalletService {
    @Override
    public JsonObject showTransaction(long userId) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM godoo_transaction_history WHERE userId = ?";
            JsonArray data = bridge.query(query, userId);
            return BaseResponse.createFullMessageResponse(0,"success",data);
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
}
