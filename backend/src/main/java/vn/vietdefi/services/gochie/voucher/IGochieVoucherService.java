package vn.vietdefi.services.gochie.voucher;

import com.google.gson.JsonObject;

public interface IGochieVoucherService {

    JsonObject getVoucherOfUser(long userId);

    JsonObject createVoucher(JsonObject voucher_info, long userId);

    JsonObject getShopVoucherInfo(long id);
    JsonObject getUserVoucherInfo(long id);

    JsonObject editVoucher(long id, JsonObject voucher_info);

    JsonObject sellVoucher(long voucher_id, long price);

    JsonObject buyVoucherFromShop(JsonObject json);
    JsonObject buyVoucherFromUser(JsonObject json);
    JsonObject getAllVoucherMarket();
    JsonObject getVoucherOfShop(long shop_Id);
    JsonObject getVoucherByCategory(int category_id);
    JsonObject cancelSellVoucher(long voucher_id);

    JsonObject rateVoucher(long voucher_id, int rate);

    JsonObject rateService(long voucher_id, int rate);

    JsonObject reportVoucher(long voucher_id, int report);
    JsonObject useVoucher(long voucher_id);

}
