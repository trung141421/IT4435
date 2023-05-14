package vn.vietdefi.services.gochie.wallet;
import com.google.gson.JsonObject;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;

public class GochieWalletService implements IGochieWalletService{

    @Override
    public JsonObject getWallet(long userId) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM gochie_wallet WHERE userId = ?";

            JsonObject data = bridge.queryOne(query,userId);

            return BaseResponse.createFullMessageResponse(0,"success",data);
        }catch(Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }
    public void updateWallet(long userId, long new_balance_coin, long new_balance_diamond){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "UPDATE gochie_wallet SET balance_coin = ?, balance_diamond = ? WHERE userId = ?";
            bridge.update(query,new_balance_coin,new_balance_diamond,userId);
            query = "SELECT balance_coin FROM gochie_wallet WHERE userId = ?";
            JsonObject data = bridge.queryOne(query, userId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public JsonObject changeDiamondToCoin(long userId, long diamond, long coin) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM gochie_wallet WHERE userId = ?";
            JsonObject data = bridge.queryOne(query,userId);
            long currentCoin = data.get("balance_coin").getAsLong();
            long currentDiamond = data.get("balance_diamond").getAsLong();
            if(diamond>currentDiamond) return BaseResponse.createFullMessageResponse(1,"insufficient_diamond");
            updateWallet(userId, currentCoin+coin,currentDiamond-diamond);
            return BaseResponse.createFullMessageResponse(0,"success");

        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }
    @Override
    public JsonObject addDiamond(long userId, long diamond){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT balance_diamond FROM gochie_wallet WHERE userId = ?";
            long balance_diamond = bridge.queryLong(query,userId);
            balance_diamond+=diamond;
            query = "UPDATE gochie_wallet SET balance_diamond = ? WHERE userId = ?";
            bridge.update(query,balance_diamond,userId);
            return BaseResponse.createFullMessageResponse(0,"success");
        }catch(Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }
    @Override
    public JsonObject addPoint(long userId, long point) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT balance_point FROM gochie_wallet WHERE userId = ?";
            long balance_point = bridge.queryLong(query,userId);
            balance_point+=point;
            query = "UPDATE gochie_wallet SET balance_point = ? WHERE userId = ?";
            bridge.update(query,balance_point,userId);
            return BaseResponse.createFullMessageResponse(0,"success");
        }catch(Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

}
