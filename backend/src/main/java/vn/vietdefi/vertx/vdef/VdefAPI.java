package vn.vietdefi.vertx.vdef;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import vn.vietdefi.vertx.vdef.router.*;

public class VdefAPI {
    public static void configAPI(Router router){
        walletApi(router);
        authApi(router);
        googleApi(router);
        facebookApi(router);
        facebookWebhook(router);
        appleApi(router);
        telegramApi(router);
        otpApi(router);
        notificationApi(router);
        messengerApi(router);
        momoApi(router);
    }

    private static void momoApi(Router router){
        router.post("/v1/momo/get-otp")
                .handler(BodyHandler.create())
                .handler(MomoRouter::getOtp);
    }
    private static void otpApi(Router router) {
        router.get("/v1/otp/authorizeotp")
                .handler(AuthRouter::authorizeUser)
                .handler(OTPRouter::authorizeRandomOTP);
        router.post("/v1/otp/verifyotp")
                .handler(BodyHandler.create())
                .handler(OTPRouter::verifyOTP);
    }

    private static void telegramApi(Router router) {
        router.post("/v1/tele-link")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(TelegramRouter::requestLinkAccount);
        router.post("/v1/tele/gen-random-otp")
                .handler(AuthRouter::authorizeUser)
                .handler(TelegramRouter::sendTelegramOTP);
        router.get("/v1/tele/checklinktelegram")
                .handler(AuthRouter::authorizeUser)
                .handler(TelegramRouter::checkLinkTelegram);
    }


    private static void googleApi(Router router) {
        router.post("/v1/gg-login")
                .handler(BodyHandler.create())
                .handler(GoogleAuthRouter::login);
    }
    private static void messengerApi(Router router){
        router.post("/v1/fb/link")
                .handler(MessengerRouter::linkMessenger);
        router.post("/v1/fb/response")
                .handler(BodyHandler.create())
                .handler(MessengerRouter::response);
        router.post("/v1/fb/checklinkmessenger")
                .handler(BodyHandler.create())
                .handler(MessengerRouter::checkLinkMessenger);
    }
    private static void facebookApi(Router router) {
        router.post("/v1/fb-login")
                .handler(BodyHandler.create())
                .handler(FacebookAuthRouter::login);
        router.get("/v1/fb/getfbinfo")
                .handler(FacebookAuthRouter::getFbInfo);
    }
    private static void facebookWebhook(Router router){
        router.post("/v1/webhook")
                .handler(BodyHandler.create())
                .handler(FacebookWebhookRouter::webhookPost);
        router.get("/v1/webhook")
                .handler(BodyHandler.create())
                .handler(FacebookWebhookRouter::webhookGet);
    }
    private static void appleApi(Router router) {
        router.get("/v1/getstate")
                .handler(BodyHandler.create())
                .handler(AppleAuthRouter::getState);
        router.post("/v1/ap-login")
                .handler(BodyHandler.create())
                .handler(AppleAuthRouter::loginApple);
        router.post("/v1/ap-register")
                .handler(BodyHandler.create())
                .handler(AppleAuthRouter::register);
        router.post("/v1/ap-recall")
                .handler(BodyHandler.create())
                .handler(AppleAuthRouter::client_register);
        router.post("/v1/ap-authorize")
                .handler(BodyHandler.create())
                .handler(AppleAuthRouter::authorize);
        router.post("/v1/ap-refreshtoken")
                .handler(BodyHandler.create())
                .handler(AppleAuthRouter::refreshToken);
    }

    private static void authApi(Router router) {
        router.post("/v1/register")
                .handler(BodyHandler.create())
                .handler(AuthRouter::register);
        router.post("/v1/login")
                .handler(BodyHandler.create())
                .handler(AuthRouter::login);
        router.post("/v1/logout")
                .handler(AuthRouter::authorizeUser)
                .handler(AuthRouter::logout);
        router.post("/v1/changepassword")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(AuthRouter::changePassword);
        router.post("/v1/changeusername")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(AuthRouter::changeUsername);
        router.post("/v1/forgetpasswordsendusername")
                .handler(BodyHandler.create())
                .handler(AuthRouter::forgetPasswordSendUsername);
        router.post("/v1/methodgetotp")
                .handler(BodyHandler.create())
                .handler(AuthRouter::methodGetOtp);
        router.post("/v1/createnewpassword")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(AuthRouter::createNewPassword);

    }

    private static void walletApi(Router router) {
        router.get("/v1/wallet/get")
                .handler(AuthRouter::authorizeUser)
                .handler(WalletRouter::getWallet);
        router.get("/v1/wallet/lock")
                .handler(AuthRouter::authorizeUser)
                .handler(WalletRouter::lockWallet);
        router.post("/v1/wallet/add")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(WalletRouter::addBalance);
        router.post("/v1/wallet/sub")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(WalletRouter::subBalance);
        router.get("/v1/wallet/list-exchange")
                .handler(WalletRouter::listExchangeByPage);
        router.get("/v1/wallet/get-exchange")
                .handler(WalletRouter::getWalletExchange);
    }
    private static void notificationApi(Router router){
        router.post("/v1/notification/gochie/create")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeAdmin)
                .handler(NotificationRouter::createNotification);
        router.post("/v1/notification/gochie/send")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeAdmin)
                .handler(NotificationRouter::sendNotification);
        router.post("/v1/notification/gochie/subscribe")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(NotificationRouter::subscribeNotification);
    }
}
