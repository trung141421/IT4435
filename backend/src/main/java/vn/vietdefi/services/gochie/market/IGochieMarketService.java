package vn.vietdefi.services.gochie.market;

import com.google.gson.JsonObject;

public interface IGochieMarketService {
    JsonObject getShopList(long userId);
    JsonObject getCategoryList(long userId);
}
