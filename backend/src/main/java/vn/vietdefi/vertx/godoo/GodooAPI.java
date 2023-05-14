package vn.vietdefi.vertx.godoo;

import io.netty.handler.codec.spdy.DefaultSpdyGoAwayFrame;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import vn.vietdefi.vertx.godoo.router.GodooGiftRouter;
import vn.vietdefi.vertx.godoo.router.GodooMatchRouter;
import vn.vietdefi.vertx.godoo.router.GodooProfileRouter;
import vn.vietdefi.vertx.godoo.router.GodooWalletRouter;
import vn.vietdefi.vertx.vdef.router.AuthRouter;

public class GodooAPI {
    public static void configAPI(Router router){
        profileAPI(router);
        giftAPI(router);
        matchAPI(router);
        walletAPI(router);
    }

    private static void profileAPI(Router router){
        router.post("/v1/godoo/profile/compulsoryinfo")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GodooProfileRouter::compulsoryInfo);
        router.get("/v1/godoo/profile/get")
                .handler(AuthRouter::authorizeUser)
                .handler(GodooProfileRouter::getProfile);
        router.post("/v1/godoo/profile/updateadditionalinfo")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GodooProfileRouter::updateAdditionalInfo);
    }
    private static void giftAPI(Router router){
        router.post("/v1/godoo/gift/creategifttemplate")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeAdmin)
                .handler(GodooGiftRouter::createGiftTemplate);
        router.post("/v1/godoo/gift/buygift")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GodooGiftRouter::buyGift);
        router.post("/v1/godoo/gift/giveswipe")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GodooGiftRouter::giveSwipe);
        router.post("/v1/godoo/gift/givechat")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GodooGiftRouter::giveChat);
        router.post("/v1/godoo/gift/getbucket")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GodooGiftRouter::getBucket);
        router.post("/v1/godoo/gift/receivegift")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GodooGiftRouter::receiveGift);
        router.post("/v1/godoo/gift/getgivengift")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GodooGiftRouter::getGivenGift);
        router.post("/v1/godoo/gift/getbegivengift")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GodooGiftRouter::getBeGivenGift);

    }
    private static void matchAPI(Router router){
        router.post("/v1/godoo/match/like")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GodooMatchRouter::like);
        router.post("/v1/godoo/match/setlove")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GodooMatchRouter::setlove);
        router.get("/v1/godoo/match/getyouliked")
                .handler(AuthRouter::authorizeUser)
                .handler(GodooMatchRouter::getYouLiked);
        router.get("/v1/godoo/match/getlikedyou")
                .handler(AuthRouter::authorizeUser)
                .handler(GodooMatchRouter::getLikedYou);
        router.post("/v1/godoo/match/endmatch")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GodooMatchRouter::endMatch);
        router.post("/v1/godoo/match/setschedule")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GodooMatchRouter::setSchedule);
        router.post("/v1/godoo/match/changeschedule")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GodooMatchRouter::changeSchedule);
        router.post("/v1/godoo/match/hide")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GodooMatchRouter::hide);
        router.post("/v1/godoo/match/report")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GodooMatchRouter::report);
    }
    private static void walletAPI(Router router) {
        router.get("/v1/godoo/wallet/showtransaction")
                .handler(AuthRouter::authorizeUser)
                .handler(GodooWalletRouter::showTransaction);
    }

}
