package vn.vietdefi.vertx.mystikos;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import vn.vietdefi.vertx.mystikos.router.MtProfileRouter;
import vn.vietdefi.vertx.mystikos.router.MtTarotRouter;
import vn.vietdefi.vertx.vdef.router.AuthRouter;

public class MtAPI {
    public static void configAPI(Router router){
        profileAPI(router);
        tarotAPI(router);
    }
    private static void profileAPI(Router router){
        router.get("/v1/mt/profile/getprofile")
                .handler(AuthRouter::authorizeUser)
                .handler(MtProfileRouter::getProfile);
        router.post("/v1/mt/profile/updateprofile")
                .handler(AuthRouter::authorizeUser)
                .handler(MtProfileRouter::updateProfile);
        router.post("/v1/mt/profile/choosecardback")
                .handler(AuthRouter::authorizeUser)
                .handler(MtProfileRouter::chooseCardBack);
    }
    private static void tarotAPI(Router router){
        router.post("/v1/mt/tarot/dailycard")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(MtTarotRouter::dailyCard);

    }
}
