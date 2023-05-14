package vn.vietdefi.services.gochie.market;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;

public class GochieMarketService implements IGochieMarketService{
    public JsonObject getShopList(long userId){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            String query = "SELECT id,name,premium, avatar from gochie_shop";
            JsonArray data = bridge.query(query);

            return BaseResponse.createFullMessageResponse(0,"success",data);
        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject getCategoryList(long userId) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            String query = "SELECT id,category_name,category_image from gochie_category";
            JsonArray data = bridge.query(query);

            return BaseResponse.createFullMessageResponse(0,"success",data);
        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

}
