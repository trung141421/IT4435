package vn.vietdefi.vertx.vdef.router;

import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.json.GsonUtil;

public class WalletRouter {
    public static void getWallet(RoutingContext rc) {
        long userId = Long.parseLong(rc.request().getHeader("userId"));
        String username = rc.request().getHeader("username");
        JsonObject response = VdefServices.walletService.getWallet(userId,username);
        rc.response().end(response.toString());
    }

    public static void lockWallet(RoutingContext rc) {
        long userId = Long.parseLong(rc.request().getHeader("userId"));
        JsonObject response = VdefServices.walletService.lockWallet(userId);
        rc.response().end(response.toString());
    }

    public static void addBalance(RoutingContext rc) {
        long userId = Long.parseLong(rc.request().getHeader("userId"));
        String username = rc.request().getHeader("username");
        int status = Integer.parseInt(rc.request().getHeader("status"));
        String body = rc.body().asString();
        JsonObject data = GsonUtil.toJsonObject(body);
        JsonObject response = VdefServices.walletService.addBalance(userId,username,status, data);
        rc.response().end(response.toString());
    }

    public static void subBalance(RoutingContext rc) {
        long userId = Long.parseLong(rc.request().getHeader("userId"));
        String username = rc.request().getHeader("username");
        int status = Integer.parseInt(rc.request().getHeader("status"));
        String body = rc.body().asString();
        JsonObject data = GsonUtil.toJsonObject(body);
        JsonObject response = VdefServices.walletService.subBalance(userId,username,status,data);
        rc.response().end(response.toString());
    }

    public static void listExchangeByPage(RoutingContext rc) {
        long userId = Long.parseLong(rc.request().getHeader("userId"));
        long page = Long.parseLong(rc.request().getHeader("page"));
        long records_per_page = Long.parseLong(rc.request().getHeader("records_per_page"));
        JsonObject data = new JsonObject();
        data.addProperty("userId",userId);
        data.addProperty("page",page);
        data.addProperty("records_per_page",records_per_page);
        JsonObject response = VdefServices.walletService.getWalletExchangeByPage(userId,page,records_per_page);
        rc.response().end(response.toString());
    }

    public static void getWalletExchange(RoutingContext rc) {
        long userId = Long.parseLong(rc.request().getHeader("userId"));
        int limit = Integer.parseInt(rc.request().getHeader("limit"));
        int offset = Integer.parseInt(rc.request().getHeader("offset"));
        JsonObject data = new JsonObject();
        data.addProperty("userId",userId);
        data.addProperty("limit",limit);
        data.addProperty("offset",offset);
        JsonObject response = VdefServices.walletService.getWalletExchangeLimitOffset(userId,limit,offset);
        rc.response().end(response.toString());
    }

}