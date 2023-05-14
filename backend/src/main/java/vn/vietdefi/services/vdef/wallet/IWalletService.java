package vn.vietdefi.services.vdef.wallet;

import com.google.gson.JsonObject;

public interface IWalletService {
    JsonObject getWallet(long userId, String username);

    JsonObject lockWallet(long userId);

    JsonObject addBalance(long userId, String username, int status, JsonObject data);

    JsonObject subBalance(long userId, String username, int status, JsonObject data);

    JsonObject getWalletExchangeByPage(long userId, long page, long records_per_page);

    JsonObject getWalletExchangeLimitOffset(long userId, long limit, long offset);
}