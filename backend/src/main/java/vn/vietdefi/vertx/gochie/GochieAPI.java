package vn.vietdefi.vertx.gochie;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import vn.vietdefi.vertx.gochie.router.*;
import vn.vietdefi.vertx.vdef.router.AuthRouter;


public class GochieAPI {
    public static void configAPI(Router router) {
        profileAPI(router);
        authAPI(router);
        voucherAPI(router);
        marketAPI(router);
        walletAPI(router);
        shopApi(router);
        notificationAPI(router);
    }

    private static void profileAPI(Router router) {
        router.post("/v1/gc/profile/updatepersonalinfo")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GochieProfileRouter::updatePersonalInfo);
        router.post("/v1/gc/profile/updateshoppingpreferences")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GochieProfileRouter::updateShoppingPreferences);
        router.get("/v1/gc/profile/playercheckshopinfo")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GochieProfileRouter::playerCheckShopInfo);
        router.get("/v1/gc/profile/playerchecktransactionhistory")
                .handler(AuthRouter::authorizeUser)
                .handler(GochieProfileRouter::playerCheckTransactionHistory);
        router.get("/v1/gc/profile/managercheckshopinfo")
                .handler(GochieProfileRouter::managerCheckShopInfo);

    }

    private static void authAPI(Router router) {
        router.get("/v1/gc/auth/getprofile")
                .handler(AuthRouter::authorizeUser)
                .handler(GochieAuthRouter::getProfile);
        router.post("/v1/gc/auth/createprofile")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GochieAuthRouter::createProfile);
        router.post("/v1/gc/auth/createsalesprofile")
                .handler(AuthRouter::authorizeUser)
                .handler(GochieAuthRouter::createSalesProfile);
        router.post("/v1/gc/auth/createmanagerprofile")
                .handler(AuthRouter::authorizeUser)
                .handler(GochieAuthRouter::createManagerProfile);
    }

    private static void voucherAPI(Router router) {
        router.get("/v1/gc/voucher/getallvouchermarket")
                .handler(GochieVoucherRouter::getAllVoucherMarket);
        router.get("/v1/gc/voucher/getvoucherbycategory")
                .handler(BodyHandler.create())
                .handler(GochieVoucherRouter::getVoucherByCategory);
        router.get("/v1/gc/voucher/getvoucherofuser")
                .handler(AuthRouter::authorizeUser)
                .handler(GochieVoucherRouter::getVoucherOfUser);
        router.get("/v1/gc/voucher/getvoucherofshop")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GochieVoucherRouter::getVoucherOfShop);
        router.post("/v1/gc/voucher/createvoucher")
                .handler(BodyHandler.create())
                .handler(GochieAuthRouter::authorizeManager)
                .handler(GochieVoucherRouter::createVoucher);
        router.get("/v1/gc/voucher/getuservoucherinfo")
                .handler(GochieVoucherRouter::getUserVoucherInfo);
        router.get("/v1/gc/voucher/getshopvoucherinfo")
                .handler(GochieVoucherRouter::getShopVoucherInfo);
        router.post("/v1/gc/voucher/editvoucher")
                .handler(BodyHandler.create())
                .handler(GochieAuthRouter::authorizeManager)
                .handler(GochieVoucherRouter::editVoucher);
        router.post("/v1/gc/voucher/sellvoucher")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GochieVoucherRouter::sellVoucher);
        router.post("/v1/gc/voucher/cancelsellvoucher")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GochieVoucherRouter::cancelSellVoucher);
        router.post("/v1/gc/voucher/buyvoucherfromshop")
                .handler(BodyHandler.create())
                .handler(GochieVoucherRouter::buyVoucherFromShop);
        router.post("/v1/gc/voucher/buyvoucherfromuser")
                .handler(BodyHandler.create())
                .handler(GochieVoucherRouter::buyVoucherFromUser);
        router.get("/v1/gc/voucher/getvouchermarket")
                .handler(GochieVoucherRouter::getAllVoucherMarket);
        router.post("/v1/gc/voucher/ratevoucher")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GochieVoucherRouter::rateVoucher);
        router.post("/v1/gc/voucher/rateservice")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GochieVoucherRouter::rateService);
        router.post("/v1/gc/voucher/reportvoucher")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GochieVoucherRouter::reportVoucher);
        router.post("/v1/gc/voucher/usevoucher")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GochieVoucherRouter::useVoucher);
    }

    private static void walletAPI(Router router) {
        router.get("/v1/gc/wallet/getwallet")
                .handler(AuthRouter::authorizeUser)
                .handler(GochieWalletRouter::getWallet);
        router.post("/v1/gc/wallet/changediamondtocoin")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GochieWalletRouter::changeDiamondToCoin);
        router.post("/v1/gc/wallet/adddiamond")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(GochieWalletRouter::addDiamond);
        router.post("/v1/gc/wallet/addpoint")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeManager)
                .handler(GochieWalletRouter::addPoint);

    }

    private static void marketAPI(Router router) {
        router.get("/v1/gc/market/getshoplist")
                .handler(AuthRouter::authorizeUser)
                .handler(GochieMarketRouter::getShopList);
        router.get("/v1/gc/market/getcategorylist")
                .handler(AuthRouter::authorizeUser)
                .handler(GochieMarketRouter::getCategoryList);
    }

    private static void shopApi(Router router) {
        router.post("/v1/gc/shop/managerupdateshopinfo")
                .handler(BodyHandler.create())
                .handler(GochieAuthRouter::authorizeManager)
                .handler(GochieShopRouter::managerUpdateShopInfo);
        router.post("/v1/gc/shop/createshop")
                .handler(BodyHandler.create())
                .handler(GochieAuthRouter::authorizeDirector)
                .handler(GochieShopRouter::createShop);
        router.post("/v1/gc/shop/acceptemployeerequest")
                .handler(BodyHandler.create())
                .handler(GochieAuthRouter::authorizeManager)
                .handler(GochieShopRouter::acceptEmployeeRequest);
        router.get("/v1/gc/shop/getemployeerequest")
                .handler(GochieAuthRouter::authorizeManager)
                .handler(GochieShopRouter::getEmployeeRequest);
        router.get("/v1/gc/shop/getstafflist")
                .handler(AuthRouter::authorizeManager)
                .handler(GochieShopRouter::getStaffList);
        router.post("/v1/gc/shop/updateemployeeinfo")
                .handler(BodyHandler.create())
                .handler(GochieAuthRouter::authorizeManager)
                .handler(GochieShopRouter::updateEmployeeInfo);
        router.post("/v1/gc//shop/changedirector")
                .handler(BodyHandler.create())
                .handler(GochieAuthRouter::authorizeDirector)
                .handler(GochieShopRouter::changeDirector);
//        router.post("/gochie/shop/shopsupport")
//                .handler(BodyHandler.create())
//                .handler(GochieAuthRouter::authorizeSales)
//                .handler(GochieShopRouter::shopSupport);
//        router.get("/gochie/shop/getsupporthistory")
//                .handler(GochieAuthRouter::authorizeSales)
//                .handler(GochieShopRouter::getSupportHistory);
        router.post("/v1/gc/shop/fireemployee")
                .handler(BodyHandler.create())
                .handler(GochieAuthRouter::authorizeManager)
                .handler(GochieShopRouter::fireEmployee);
        router.post("/v1/gc/shop/fire")
                .handler(BodyHandler.create())
                .handler(GochieAuthRouter::authorizeDirector)
                .handler(GochieShopRouter::fire);
        router.post("/v1/gc/shop/employeeRegistration")
                .handler(BodyHandler.create())
                .handler(GochieAuthRouter::authorizeSales)
                .handler(GochieShopRouter::employeeRegistration);
        router.post("/v1/gc/shop/deleteemployeerequest")
                .handler(GochieAuthRouter::authorizeManager)
                .handler(GochieShopRouter::deleteEmployeeRequest);
    }
    private static void notificationAPI(Router router){
        router.get("/v1/gc/shop/gettransactionhistory")
                .handler(AuthRouter::authorizeUser)
                .handler(GochieNotificationRouter::getTransactionHistory);
    }
}