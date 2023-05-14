package vn.vietdefi.services;

import vn.vietdefi.services.gochie.auth.GochieAuthService;
import vn.vietdefi.services.gochie.auth.IGochieAuthService;
import vn.vietdefi.services.gochie.market.GochieMarketService;
import vn.vietdefi.services.gochie.market.IGochieMarketService;
import vn.vietdefi.services.gochie.notification.GochieNotificationService;
import vn.vietdefi.services.gochie.notification.IGochieNotificationService;
import vn.vietdefi.services.gochie.profile.GochieProfileService;
import vn.vietdefi.services.gochie.profile.IGochieProfileService;
import vn.vietdefi.services.gochie.shop.GochieShopService;
import vn.vietdefi.services.gochie.shop.IGochieShopService;
import vn.vietdefi.services.gochie.voucher.GochieVoucherService;
import vn.vietdefi.services.gochie.voucher.IGochieVoucherService;
import vn.vietdefi.services.gochie.wallet.GochieWalletService;
import vn.vietdefi.services.gochie.wallet.IGochieWalletService;
import vn.vietdefi.services.godoo.gift.GodooGiftService;
import vn.vietdefi.services.godoo.gift.IGodooGiftService;
import vn.vietdefi.services.godoo.match.GodooMatchService;
import vn.vietdefi.services.godoo.match.IGodooMatchService;
import vn.vietdefi.services.godoo.profile.GodooProfileService;
import vn.vietdefi.services.godoo.profile.IGodooProfileService;
import vn.vietdefi.services.godoo.wallet.GodooWalletService;
import vn.vietdefi.services.godoo.wallet.IGodooWalletService;
import vn.vietdefi.services.izihouse.profile.*;
import vn.vietdefi.services.mystikos.profile.IMtProfileService;
import vn.vietdefi.services.mystikos.profile.MtProfileService;
import vn.vietdefi.services.mystikos.tarot.IMtTarotService;
import vn.vietdefi.services.mystikos.tarot.MtTarotService;
import vn.vietdefi.services.upload.IUploadService;
import vn.vietdefi.services.upload.UploadService;
import vn.vietdefi.services.vdef.auth.AuthService;
import vn.vietdefi.services.vdef.auth.IAuthService;
import vn.vietdefi.services.vdef.auth.apple.AppleService;
import vn.vietdefi.services.vdef.auth.apple.IAppleService;
import vn.vietdefi.services.vdef.auth.facebook.bot.IMessengerBotService;
import vn.vietdefi.services.vdef.auth.facebook.bot.MessengerBotService;
import vn.vietdefi.services.vdef.auth.facebook.messenger.IMessengerService;
import vn.vietdefi.services.vdef.auth.facebook.messenger.MessengerService;
import vn.vietdefi.services.vdef.auth.facebook.util.FacebookService;
import vn.vietdefi.services.vdef.auth.facebook.util.IFacebookService;
import vn.vietdefi.services.vdef.auth.facebook.webhook.FacebookWebhook;
import vn.vietdefi.services.vdef.auth.facebook.webhook.IFacebookWebhook;
import vn.vietdefi.services.vdef.auth.google.GoogleService;
import vn.vietdefi.services.vdef.auth.google.IGoogleService;
import vn.vietdefi.services.vdef.momo.IMomoService;
import vn.vietdefi.services.vdef.momo.MomoService;
import vn.vietdefi.services.vdef.notification.INotificationService;
import vn.vietdefi.services.vdef.notification.NotificationService;
import vn.vietdefi.services.vdef.otp.IOTPService;
import vn.vietdefi.services.vdef.otp.OTPService;
import vn.vietdefi.services.vdef.telegram.ITelegramService;
import vn.vietdefi.services.vdef.telegram.TelegramService;
import vn.vietdefi.services.vdef.wallet.IWalletService;
import vn.vietdefi.services.vdef.wallet.WalletService;
import vn.vietdefi.services.wecare.profile.IWecareProfileService;
import vn.vietdefi.services.wecare.profile.WecareProfileService;
import vn.vietdefi.services.wecare.review.IWecareReviewService;
import vn.vietdefi.services.wecare.review.WecareReviewService;
import vn.vietdefi.services.wecare.schedule.IWecareScheduleService;
import vn.vietdefi.services.wecare.schedule.WecareScheduleService;
import vn.vietdefi.services.wecare.service.IWecareServiceService;
import vn.vietdefi.services.wecare.service.WecareServiceService;

public class VdefServices {
    //vdef services
    public static IAuthService authService = new AuthService();
    public static IGoogleService googleService =  new GoogleService();
    public static IMomoService momoService = new MomoService();

    //gochie services
    public static IGochieProfileService gochieProfileService = new GochieProfileService();
    public static IGochieAuthService gochieAuthService = new GochieAuthService();
    public static IGochieNotificationService gochieNotificationService =  new GochieNotificationService();
    public static IGochieShopService gochieShopService = new GochieShopService();
    public static IGochieVoucherService gochieVoucherService = new GochieVoucherService();
    public static IGochieMarketService gochieMarketService = new GochieMarketService();
    public static IGochieWalletService gochieWalletService = new GochieWalletService();
    //wecare services
    public static IWecareProfileService wecareProfileService = new WecareProfileService();
    public static IWecareServiceService wecareServiceService = new WecareServiceService();

    public static IWecareScheduleService wecareScheduleService = new WecareScheduleService();
    public static IWecareReviewService wecareReviewService = new WecareReviewService();

    //mystikos services
    public static IMtProfileService mtProfileService = new MtProfileService();
    public static IMtTarotService mtTarotService = new MtTarotService();

    public static IFacebookService facebookService = new FacebookService();

    public static IWalletService walletService = new WalletService();

    public static IAppleService appleService = new AppleService();
    public static ITelegramService telegramService = new TelegramService();
    public static IOTPService otpService = new OTPService();
    //facebook webhook
    public static IFacebookWebhook facebookWebhook =  new FacebookWebhook();
    //messenger bot
    public static IMessengerBotService messengerBotService = new MessengerBotService();
    public static INotificationService gochieNotifier = new NotificationService("gochie");
    public static IMessengerService messengerService = new MessengerService();

    //Izihouse Service
    public static IIzihouseProfileService izihouseProfileService = new IzihouseProfileService();

    public static IIzihouseHouseService izihouseHouseService = new IzihouseHouseService();
    public static IIzihousePostService iIzihousePostService = new IzihousePostService();

    public static IIzihouseRentService iIzihouseRentService = new IzihouseRentService();

    public static IIzihouseNotiService iIzihouseNotiService = new IzihouseNotiService();

    //Godoo Service
    public static IGodooProfileService godooProfileService = new GodooProfileService();
    public static IGodooGiftService godooGiftService = new GodooGiftService();
    public static IGodooMatchService godooMatchService = new GodooMatchService();
    public static IGodooWalletService godooWalletService = new GodooWalletService();


    //Upload Service

    public static IUploadService uploadService = new UploadService();

}
