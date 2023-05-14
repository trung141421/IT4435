package vn.vietdefi.vertx.gochie.router;
import vn.vietdefi.common.BaseResponse;
import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import vn.vietdefi.util.json.GsonUtil;
import vn.vietdefi.services.VdefServices;

public class GochieVoucherRouter {
    public static void getVoucherOfUser(RoutingContext rc) {
        try {
            long userid = Long.parseLong(rc.request().getHeader("userid"));

            JsonObject response = VdefServices.gochieVoucherService.getVoucherOfUser(userid);
            rc.response().end(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
            rc.response().end(response.toString());
        }
    }
    public static void getVoucherOfShop(RoutingContext rc) {
        try {
            long shop_id = Long.parseLong(rc.request().getParam("shop_id"));

            JsonObject response = VdefServices.gochieVoucherService.getVoucherOfShop(shop_id);
            rc.response().end(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
            rc.response().end(response.toString());
        }
    }

    public static void createVoucher(RoutingContext rc){
        try {
            long userid = Integer.parseInt(rc.request().getHeader("userid"));
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            JsonObject response = VdefServices.gochieVoucherService.createVoucher(json, userid);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(
                    1, "system_error"
            );
            rc.response().end(response.toString());
        }
    }

    public static void getUserVoucherInfo(RoutingContext rc){
        try{
            long voucher_id = Long.parseLong(rc.request().getParam("voucher_id"));

            JsonObject response = VdefServices.gochieVoucherService.getUserVoucherInfo(voucher_id);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void getShopVoucherInfo(RoutingContext rc){
        try{
            long voucher_template_id = Long.parseLong(rc.request().getParam("voucher_template_id"));

            JsonObject response = VdefServices.gochieVoucherService.getShopVoucherInfo(voucher_template_id);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void editVoucher(RoutingContext rc){
        try{
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            JsonObject voucher_info = json.get("voucher_info").getAsJsonObject();
            long voucher_id = json.get("voucher_id").getAsLong();

            JsonObject response = VdefServices.gochieVoucherService.editVoucher(voucher_id, voucher_info);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void sellVoucher(RoutingContext rc){
        try{
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            long voucher_id = json.get("voucher_id").getAsLong();
            long price = json.get("price").getAsLong();

            JsonObject response = VdefServices.gochieVoucherService.sellVoucher(voucher_id, price);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void cancelSellVoucher(RoutingContext rc){
        try{
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            long voucher_id = json.get("voucher_id").getAsLong();

            JsonObject response = VdefServices.gochieVoucherService.cancelSellVoucher(voucher_id);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void buyVoucherFromShop(RoutingContext rc){
        try{
            long userid = Integer.parseInt(rc.request().getHeader("userid"));
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            json.addProperty("buyer_id",userid);
            JsonObject response = VdefServices.gochieVoucherService.buyVoucherFromShop(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void buyVoucherFromUser(RoutingContext rc){
        try{
            long userid = Integer.parseInt(rc.request().getHeader("userid"));
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            json.addProperty("buyer_id",userid);
            JsonObject response = VdefServices.gochieVoucherService.buyVoucherFromUser(json);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void getAllVoucherMarket(RoutingContext rc){
        try{

            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);

            JsonObject response = VdefServices.gochieVoucherService.getAllVoucherMarket();
            rc.response().end(response.toString());
        }
        catch (Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void getVoucherByCategory(RoutingContext rc){
        try{
            int category = Integer.parseInt(rc.request().getParam("category_id"));
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);

            JsonObject response = VdefServices.gochieVoucherService.getVoucherByCategory(category);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void rateVoucher(RoutingContext rc){
        try{
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            long voucher_id = json.get("voucher_id").getAsLong();
            int rate = json.get("rate").getAsInt();
            JsonObject response = VdefServices.gochieVoucherService.rateVoucher(voucher_id,rate);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void rateService(RoutingContext rc){
        try{
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            long voucher_id = json.get("voucher_id").getAsLong();
            int rate = json.get("rate").getAsInt();
            JsonObject response = VdefServices.gochieVoucherService.rateService(voucher_id,rate);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }

    public static void reportVoucher(RoutingContext rc){
        try {
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            long voucher_id = json.get("voucher_id").getAsLong();
            int report = json.get("report").getAsInt();
            JsonObject response = VdefServices.gochieVoucherService.reportVoucher(voucher_id,report);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
    public static void useVoucher(RoutingContext rc){
        try {
            String data = rc.body().asString();
            JsonObject json = GsonUtil.toJsonObject(data);
            long voucher_id = json.get("voucher_id").getAsLong();
            JsonObject response = VdefServices.gochieVoucherService.useVoucher(voucher_id);
            rc.response().end(response.toString());
        }
        catch (Exception e){
            e.printStackTrace();
            JsonObject response = BaseResponse.createFullMessageResponse(1,"system_error");
            rc.response().end(response.toString());
        }
    }
}
