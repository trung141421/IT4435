package vn.vietdefi.vertx.wecare;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import vn.vietdefi.vertx.vdef.router.AuthRouter;
import vn.vietdefi.vertx.wecare.router.WeCareScheduleRouter;
import vn.vietdefi.vertx.wecare.router.WecareProfileRouter;
import vn.vietdefi.vertx.wecare.router.WecareReviewRouter;
import vn.vietdefi.vertx.wecare.router.WecareServiceRouter;

public class WecareAPI {
    public static void configAPI(Router router) {
        profileAPI(router);
        serviceApi(router);
        scheduleApi(router);
        reviewAPI(router);
    }

    private static void profileAPI(Router router) {
        router.get("/v1/wc/profile/get")
                .handler(AuthRouter::authorizeUser)
                .handler(WecareProfileRouter::getProfile);
        router.post("/v1/wc/profile/create")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(WecareProfileRouter::createProfile);
        router.post("/v1/wc/profile/update")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)

                .handler(WecareProfileRouter::updateProfile);
        router.get("/v1/wc/profile/get-provider")
                .handler(AuthRouter::authorizeUser)

                .handler(WecareProfileRouter::getProvider);
        router.post("/v1/wc/profile/create-provider")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)

                .handler(WecareProfileRouter::createProvider);
        router.post("/v1/wc/profile/update-provider")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)

                .handler(WecareProfileRouter::updateProvider);
    }


    private static void serviceApi(Router router) {
        router.get("/v1/wc/service/test")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(WecareServiceRouter::doServiceTest);
        router.post("/v1/wc/service/create")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(WecareServiceRouter::createService);
        router.post("/v1/wc/service/create-pack")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(WecareServiceRouter::createServicePack);
        router.get("/v1/wc/service/get")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(WecareServiceRouter::getService);
        router.get("/v1/wc/service/get-pack")
                .handler(BodyHandler.create())

                .handler(AuthRouter::authorizeUser)
                .handler(WecareServiceRouter::getServicePack);
        router.post("/v1/wc/service/update")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(WecareServiceRouter::updateService);
        router.post("/v1/wc/service/update-pack")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(WecareServiceRouter::updateServicePack);
        router.post("/v1/wc/service/active")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(WecareServiceRouter::activeService);
    }

    private static void scheduleApi(Router router) {
        router.post("/v1/wc/schedule/accept")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(WeCareScheduleRouter::acceptSchedule);
        router.post("/v1/wc/schedule/decline")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(WeCareScheduleRouter::declineSchedule);
        router.post("/v1/wc/schedule/change-status")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(WeCareScheduleRouter::changeStatusSchedule);
        router.post("/v1/wc/schedule/book")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(WeCareScheduleRouter::bookSchedule);
        router.post("/v1/wc/schedule/book-fast")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(WeCareScheduleRouter::bookScheduleFast);
        router.post("/v1/wc/schedule/create-personal")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(WeCareScheduleRouter::createPersonalSchedule);
        router.get("/v1/wc/schedule/get-customer-booking")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(WeCareScheduleRouter::getCustomerBookingSchedule);
        router.get("/v1/wc/schedule/get-provider-booking")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(WeCareScheduleRouter::getProviderBookingSchedule);
        router.get("/v1/wc/schedule/get-personal")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(WeCareScheduleRouter::getPersonalSchedule);
        router.post("/v1/wc/schedule/update-personal")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(WeCareScheduleRouter::updatePersonalSchedule);
    }

    private static void reviewAPI(Router router) {
        router.post("/v1/wc/review/customer")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(WecareReviewRouter::reviewCustomer);
        router.post("/v1/wc/review/service")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(WecareReviewRouter::reviewService);
        router.get("/v1/wc/review/get-customer-review")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(WecareReviewRouter::getCustomerReview);
        router.get("/v1/wc/review/get-service-review")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(WecareReviewRouter::getServiceReview);
    }
}
