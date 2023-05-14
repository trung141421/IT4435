package vn.vietdefi.services.godoo.wallet;

import com.google.gson.JsonObject;

public interface IGodooWalletService {
    JsonObject showTransaction(long userId);
}
