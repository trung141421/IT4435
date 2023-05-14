package vn.vietdefi.services.gochie.voucher;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;
import vn.vietdefi.util.string.StringUtil;

public class GochieVoucherService implements IGochieVoucherService {
    public JsonObject createVoucher(JsonObject voucher_info, long userId){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT shop_id, shop_name FROM gochie_sales_manager_profile WHERE userId = ?";
            JsonObject data = bridge.queryOne(query, userId);
            if(data == null){
                return BaseResponse.createFullMessageResponse(2,"shop_not_found");
            }

            long shop_id = data.get("shop_id").getAsLong();
            String shop_name = data.get("shop_name").getAsString();

            query = "SELECT premium FROM gochie_shop WHERE id = ?";
            int premium = bridge.queryInteger(query,shop_id);

            String voucher_name = voucher_info.get("voucher_name").getAsString();
            String content = voucher_info.get("content").getAsString();
            String user_guide = voucher_info.get("user_guide").getAsString();
            String terms_of_use = voucher_info.get("terms_of_use").getAsString();
            int category = voucher_info.get("category").getAsInt();
            long release_date = voucher_info.get("release_date").getAsLong();
            long open_date = voucher_info.get("open_date").getAsLong();
            long close_date = voucher_info.get("close_date").getAsLong();
            String place = voucher_info.get("place").getAsString();   //luu dia diem su dung ?
            long price = voucher_info.get("price").getAsLong();
            int purchase_limit = voucher_info.get("purchase_limit").getAsInt();
            long total_quantity = voucher_info.get("total_quantity").getAsLong();
            String image = voucher_info.get("image").getAsString();

            String number_code = StringUtil.generateRandomStringNumberCharacter(8);

            query = "INSERT INTO gochie_voucher_template " +
                    "(premium, voucher_name, content, user_guide, terms_of_use, shop_id, shop_name, category, release_date, open_date, close_date, place, price, number_code, purchase_limit, total_quantity,quantity_sold,image,seller_type) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,0,?,?)";
            bridge.update(query,premium, voucher_name, content, user_guide, terms_of_use, shop_id, shop_name, category, release_date, open_date, close_date, place, price, number_code, purchase_limit, total_quantity,image,"shop");

            long point = total_quantity * 1;
            query = "SELECT balance_point FROM gochie_wallet WHERE userId = ?";
            long currentPoint = bridge.queryLong(query,userId);
            if(currentPoint<point) return BaseResponse.createFullMessageResponse(2,"insufficient_point");
            query = "UPDATE gochie_wallet SET balance_diamond = ? WHERE userId = ?";
            bridge.update(query,currentPoint-point,userId);

            return BaseResponse.createFullMessageResponse(0,"success");
        }
        catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject getShopVoucherInfo(long voucher_template_id) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM gochie_voucher_template WHERE id = ?";
            JsonObject data = bridge.queryOne(query, voucher_template_id);
            long shop_id = data.get("shop_id").getAsLong();

            query = "SELECT website FROM gochie_shop WHERE id = ?";
            JsonObject info = bridge.queryOne(query,shop_id);
            String website = info.get("website").getAsString();

            data.addProperty("website",website);
            return BaseResponse.createFullMessageResponse(0,"success", data);
        }
        catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject getUserVoucherInfo(long voucher_id) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT voucher_name,username,userId,status, content, shop_id,user_guide,terms_of_use, shop_name, category, open_date, close_date, place, price,image, avatar,on_sale_time,seller_type FROM gochie_voucher WHERE id = ?";

            JsonObject data = bridge.queryOne(query, voucher_id);
            long shop_id = data.get("shop_id").getAsLong();

            query = "SELECT website FROM gochie_shop WHERE id = ?";
            JsonObject info = bridge.queryOne(query,shop_id);
            String website = info.get("website").getAsString();

            data.addProperty("website",website);
            return BaseResponse.createFullMessageResponse(0,"success", data);
        }
        catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    public JsonObject editVoucher(long voucher_id, JsonObject voucher_info){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT quantity_sold FROM gochie_voucher_template WHERE id = ? ";
            long quantity_sold = bridge.queryLong(query,voucher_id);
            query = "UPDATE gochie_voucher_template SET " +
                    "voucher_name = ?, content = ?, user_guide = ?, terms_of_use = ?, category = ?, release_date = ?, open_date = ?, close_date = ?," +
                    " place = ?, price = ?, purchase_limit = ?, total_quantity = ?, image = ? WHERE id = ?";

            String voucher_name = voucher_info.get("voucher_name").getAsString();
            String content = voucher_info.get("content").getAsString();
            String user_guide = voucher_info.get("user_guide").getAsString();
            String terms_of_use = voucher_info.get("terms_of_use").getAsString();
            int category = voucher_info.get("category").getAsInt();
            long release_date = voucher_info.get("release_date").getAsLong();
            long open_date = voucher_info.get("open_date").getAsLong();
            long close_date = voucher_info.get("close_date").getAsLong();
            String place = voucher_info.get("place").getAsString();   //luu dia diem su dung ?
            long price = voucher_info.get("price").getAsLong();
            int purchase_limit = voucher_info.get("purchase_limit").getAsInt();
            long total_quantity = voucher_info.get("total_quantity").getAsLong();
            String image = voucher_info.get("image").getAsString();

            if(total_quantity<quantity_sold) return BaseResponse.createFullMessageResponse(0,"insufficience_quantity");

            bridge.update(query, voucher_name, content, user_guide, terms_of_use, category, release_date, open_date, close_date, place, price, purchase_limit, total_quantity,image, voucher_id);
            return BaseResponse.createFullMessageResponse(0,"success");
        }
        catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    public JsonObject sellVoucher(long voucher_id, long price){
        try{
            long currentSecond = System.currentTimeMillis()/1000;
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "UPDATE gochie_voucher SET status = 1, price = ?, on_sale_time = ? WHERE id = ?";
            bridge.update(query, price, currentSecond, voucher_id);
            return BaseResponse.createFullMessageResponse(0,"success");
        }
        catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    public JsonObject cancelSellVoucher(long voucher_id){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "UPDATE gochie_voucher SET status = 0, price = NULL, on_sale_time = NULL WHERE id = ?";
            bridge.update(query, voucher_id);
            return BaseResponse.createFullMessageResponse(0,"success");
        }
        catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    public boolean checkIsPurchaseLimit(long userId, long voucher_template_id){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT purchase_limit FROM gochie_voucher_template WHERE id = ?";
            int purchase_limit = bridge.queryInteger(query,voucher_template_id);
            query = "SELECT COUNT(id) AS num FROM gochie_voucher WHERE userId = ? AND voucher_template_id = ? AND purchase_type = 0";
            JsonObject data = bridge.queryOne(query, userId, voucher_template_id);
            if(data.get("num").getAsInt() < purchase_limit){
                return false;
            }
            else return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public JsonObject buyVoucherFromShop(JsonObject json){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long buyer_id = json.get("buyer_id").getAsLong();
            long voucher_template_id = json.get("voucher_id").getAsLong();

            if(checkIsPurchaseLimit(buyer_id, voucher_template_id)){
                return BaseResponse.createFullMessageResponse(2,"reach_purchase_limit");
            }

            String query = "SELECT * FROM gochie_voucher_template WHERE id = ?";
            JsonObject data = bridge.queryOne(query,voucher_template_id);
            long price = data.get("price").getAsLong();
            long type = data.get("type").getAsLong();

            query = "SELECT fullname FROM gochie_profile WHERE userId = ?";
            JsonElement profile = bridge.queryOne(query,buyer_id).get("fullname");
            String fullname;
            if(profile.isJsonNull()) fullname = "";
            else fullname = profile.getAsString();
            query = "SELECT * FROM gochie_wallet WHERE userId = ?";
            JsonObject info = bridge.queryOne(query,buyer_id);

            long coinRemain = info.get("balance_coin").getAsLong();
            long diamondRemain = info.get("balance_diamond").getAsLong();
            long remainAmountCoin = coinRemain;
            long remainAmountDiamond = diamondRemain;
            if(type == 0){
                if(coinRemain < price){
                    return BaseResponse.createFullMessageResponse(3,"not_enough_coin");
                }
                remainAmountCoin = coinRemain - price;
            }
            else {

                if(diamondRemain < price){
                    return BaseResponse.createFullMessageResponse(4,"not_enough_diamond");
                }
                remainAmountDiamond = diamondRemain - price;
            }
            VdefServices.gochieWalletService.updateWallet(buyer_id,remainAmountCoin,remainAmountDiamond);

            long quantity_sold = data.get("quantity_sold").getAsLong();

            query ="UPDATE gochie_voucher_template SET quantity_sold = ? WHERE id = ? ";
            bridge.update(query, quantity_sold+1,voucher_template_id);

            String voucher_name = data.get("voucher_name").getAsString();
            String content = data.get("content").getAsString();
            String user_guide = data.get("user_guide").getAsString();
            String terms_of_use = data.get("terms_of_use").getAsString();
            long shop_id = data.get("shop_id").getAsLong();
            String shop_name = data.get("shop_name").getAsString();
            int category = data.get("category").getAsInt();
            long open_date = data.get("open_date").getAsLong();
            long close_date = data.get("close_date").getAsLong();
            String place = data.get("place").getAsString();
            String number_code = data.get("number_code").getAsString();

            query = "INSERT INTO gochie_voucher " +
                    "(voucher_template_id, userId,username, status, voucher_name, content, user_guide, terms_of_use, shop_id, shop_name, category, open_date," +
                    "close_date, place, number_code, purchase_type) VALUES (?,?,?,0,?,?,?,?,?,?,?,?,?,?,?,0)";
            bridge.update(query, voucher_template_id, buyer_id,fullname, voucher_name, content, user_guide, terms_of_use, shop_id, shop_name, category, open_date, close_date, place, number_code);

            json.addProperty("type",type);
            json.addProperty("seller_id",shop_id);
            json.addProperty("price",price);
            json.addProperty("voucher_name",voucher_name);
            json.addProperty("voucher_template_id",voucher_template_id);

            return VdefServices.gochieNotificationService.createTransactionHistory(json);
        }
        catch(Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject buyVoucherFromUser(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long buyer_id = json.get("buyer_id").getAsLong();
            long voucher_id = json.get("voucher_id").getAsLong();

            String query = "SELECT * FROM gochie_voucher WHERE id = ?";
            JsonObject data = bridge.queryOne(query,voucher_id);
            long price = data.get("price").getAsLong();
            long voucher_template_id = data.get("voucher_template_id").getAsLong();
            long seller_id = data.get("userId").getAsLong();
            String voucher_name = data.get("voucher_name").getAsString();

            query = "SELECT * FROM gochie_wallet WHERE userId = ?";
            JsonObject info = bridge.queryOne(query,buyer_id);

            long coinRemain = info.get("balance_coin").getAsLong();
            long diamondRemain = info.get("balance_diamond").getAsLong();
            if(coinRemain < price){
                return BaseResponse.createFullMessageResponse(3,"not_enough_coin");
            }
            long remainAmountCoin = coinRemain - price;

            VdefServices.gochieWalletService.updateWallet(buyer_id,remainAmountCoin,diamondRemain);

            query = "UPDATE gochie_voucher SET userId = ? AND purchase_type = 1 AND status = 0";
            bridge.update(query, voucher_template_id, buyer_id);

            json.addProperty("type",0);
            json.addProperty("seller_id",seller_id);
            json.addProperty("price",price);
            json.addProperty("voucher_name",voucher_name);
            json.addProperty("voucher_template_id",voucher_template_id);

            return VdefServices.gochieNotificationService.createTransactionHistory(json);
        }catch(Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    public JsonObject getVoucherOfUser(long userId){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM gochie_voucher WHERE userId = ? ";
            JsonArray data = bridge.query(query, userId);
            return BaseResponse.createFullMessageResponse(0,"success",data);
        }
        catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }
    public JsonObject getVoucherOfShop(long shop_id){
        try{
            long currentSecond = System.currentTimeMillis() / 1000;
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT id, shop_name as name, voucher_name,content,price, image,user_guide,terms_of_use, avatar,total_quantity, quantity_sold,type,seller_type,release_date,open_date,close_date FROM gochie_voucher_template WHERE close_date > ? AND shop_id = ?";
            JsonArray data = bridge.query(query,currentSecond, shop_id);
            return BaseResponse.createFullMessageResponse(0,"success",data);
        }catch(Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject getVoucherByCategory(int category_id) {
        try{
            long currentSecond = System.currentTimeMillis() / 1000;
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT id, username as name, voucher_name,content, price,user_guide,terms_of_use, image, avatar, status, seller_type FROM gochie_voucher WHERE status = 1 AND close_date > ? AND category = ?";
            JsonArray data = bridge.query(query, currentSecond,category_id);

            query = "SELECT id,shop_id, shop_name as name, voucher_name,content,price,user_guide,terms_of_use, image, avatar,total_quantity, quantity_sold,type,seller_type,release_date,open_date,close_date FROM gochie_voucher_template WHERE close_date > ? AND category = ?";
            JsonArray data2 = bridge.query(query, currentSecond,category_id);

            data.addAll(data2);
            return BaseResponse.createFullMessageResponse(0,"success",data);
        }
        catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    public JsonObject getAllVoucherMarket(){//chua phan trang
        try{
            long currentSecond = System.currentTimeMillis() / 1000;
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT id, username as name, voucher_name,content, price,user_guide,terms_of_use, image, avatar, status, seller_type FROM gochie_voucher WHERE status = 1 AND close_date > ?";
            JsonArray data = bridge.query(query, currentSecond);

            query = "SELECT id,shop_id, shop_name as name, voucher_name,content,price,user_guide,terms_of_use, image, avatar,total_quantity, quantity_sold,type,seller_type,release_date,open_date,close_date FROM gochie_voucher_template WHERE close_date > ?";
            JsonArray data2 = bridge.query(query, currentSecond);

            data.addAll(data2);
            return BaseResponse.createFullMessageResponse(0,"success",data);
        }
        catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    public JsonObject rateVoucher(long voucher_id, int rate){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "UPDATE gochie_voucher SET rate_voucher = ? WHERE id = ?";
            bridge.update(query,rate,voucher_id);
            return BaseResponse.createFullMessageResponse(0,"success");
        }
        catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    public JsonObject rateService(long voucher_id, int rate){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "UPDATE gochie_voucher SET rate_service = ? WHERE id = ?";
            bridge.update(query,rate,voucher_id);
            return BaseResponse.createFullMessageResponse(0,"success");
        }
        catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }
    public JsonObject reportVoucher(long voucher_id, int report){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "UPDATE gochie_voucher SET report = ? WHERE id = ?";
            bridge.update(query,report,voucher_id);
            return BaseResponse.createFullMessageResponse(0,"success");
        }
        catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject useVoucher(long voucher_id) {
        try{
            long currentSecond = System.currentTimeMillis()/1000;
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "UPDATE gochie_voucher SET type = -1 AND used_time = ? WHERE id = ?";
            bridge.update(query,currentSecond,voucher_id);
            return BaseResponse.createFullMessageResponse(0,"success");
        }catch(Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }
}
