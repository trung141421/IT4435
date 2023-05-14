package vn.vietdefi.services.gochie.wallet;

import com.google.gson.JsonObject;

public interface IGochieWalletService {
    JsonObject getWallet(long userId);
    void updateWallet(long buyer_id, long remainAmountCoin, long remainAmountDiamond);
    JsonObject changeDiamondToCoin (long userId, long diamond, long coin);
    JsonObject addDiamond(long userId, long diamond);
    JsonObject addPoint(long userId, long point);
}
