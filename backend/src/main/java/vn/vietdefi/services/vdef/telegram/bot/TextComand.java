package vn.vietdefi.services.vdef.telegram.bot;

import com.google.gson.JsonObject;
import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.services.vdef.telegram.util.TelegramKeyboard;
import vn.vietdefi.util.log.DebugLogger;

public class TextComand {
    public static void process(VdefTelegramBot bot, Update update) {
        try {
            String command = update.message().text();
            if (command.startsWith("/start")) {
                startCommand(bot, update, command);
            }else
            if (command.startsWith(TelegramKeyboard.share_phone_number)) {
                sharePhoneCommand(bot, update);
            }else
            if (command.startsWith("/registerLogListener")) {
                registerLoglistener(bot, update, command);
            }else
            if (command.startsWith("/stopLogListener")) {
                stopLoglistener(bot, update, command);
            }else
            if (command.startsWith("/registerAdmin")) {
                registerAdmin(bot, update, command);
            }else
            if (command.startsWith("/stopAdmin")) {
                stopAdmin(bot, update, command);
            }else {
                long telegramId = update.message().from().id();
                bot.sendMessage(telegramId, "I will send OTP and update notification as need.");
            }
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
        }
    }

    private static void sharePhoneCommand(VdefTelegramBot bot, Update update) {
        Contact contact = update.message().contact();
        long telegramId = update.message().from().id();
        if(contact != null){
            String phone = contact.phoneNumber();
            JsonObject resonse = VdefServices.telegramService.updatePhoneNumber(telegramId, phone);
            if(BaseResponse.isSuccessFullMessage(resonse)) {
                bot.sendMessage(telegramId, "I will send OTP and update notification as need.");
            }else{
                requestForPhoneNumber(bot, telegramId);
            }
        }else {
            requestForPhoneNumber(bot, telegramId);
        }
    }

    private static void registerAdmin(VdefTelegramBot bot, Update update, String command) {
        long from = update.message().from().id();
        if(bot.superAdmin == from){
            try {
                long adminId = Long.parseLong(command.substring(15));
                bot.registerAdmin(adminId);
                String message = "Admin is registered successfully";
                bot.sendMessage(from, message);
                message = "Your request is accepted. You are admin now";
                bot.sendMessage(adminId, message);
            }catch (Exception e){
                String message = "System Error";
                bot.sendMessage(from, message);
            }
        }else{
            String message = new StringBuilder("/registerAdmin_").append(from).toString();
            bot.sendMessage(bot.superAdmin, message);
            message = "Your request is sent to SUPER AMDIN. Please wait for process.";
            bot.sendMessage(from, message);
        }
    }


    private static void registerLoglistener(VdefTelegramBot bot, Update update, String command) {
        long from = update.message().from().id();
        if(bot.superAdmin == from){
            try {
                long logListener = Long.parseLong(command.substring(21));
                if(bot.isLogListener(logListener)){
                    String message = "You are log listener already";
                    bot.sendMessage(from, message);
                    return;
                }
                bot.registerLogListener(logListener);
                String message = "Log Listener is registered successfully";
                bot.sendMessage(from, message);
                message = "Your request is accepted. You are log listener now";
                bot.sendMessage(logListener, message);
            }catch (Exception e){
                String message = "System Error";
                bot.sendMessage(from, message);
            }
        }else{
            String message = new StringBuilder("/registerLogListener_").append(from).toString();
            bot.sendMessage(bot.superAdmin, message);
            message = "Your request is sent to SUPER AMDIN. Please wait for process.";
            bot.sendMessage(from, message);
        }
    }

    private static void stopAdmin(VdefTelegramBot bot, Update update, String command) {
        long from = update.message().from().id();
        if(bot.superAdmin == from){
            long admin = Long.parseLong(command.substring(11));
            bot.stopAdmin(admin);
        }
    }

    private static void stopLoglistener(VdefTelegramBot bot, Update update, String command) {
        long from = update.message().from().id();
        if(bot.superAdmin == from){
            long logListener = Long.parseLong(command.substring(18));
            bot.stopLogListener(logListener);
        }
    }

    private static void startCommand(VdefTelegramBot bot, Update update, String command) {
        long telegramId = update.message().from().id();
        String firstName = update.message().chat().firstName();
        String lastName = update.message().chat().lastName();
        String fullName = new StringBuilder(firstName).append(lastName).toString();
        String active_code = command.substring(7);
        JsonObject serviceResponse = VdefServices.telegramService.activeTelegramAccount(telegramId, active_code,fullName);
        if (BaseResponse.isSuccessFullMessage(serviceResponse)) {
            bot.sendMessage(telegramId, "Link successfully");
            requestForPhoneNumber(bot, telegramId);
        } else {
            bot.sendMessage(telegramId, "Can not active your account. Please try again by open again from APP!");
        }
    }

    private static void requestForPhoneNumber(VdefTelegramBot bot, long telegramId) {
        SendMessage sendMessage = new SendMessage(telegramId,
                "Share your phone number! If you don't see any number, press on 4 dots button.");
        Keyboard keyboard = new ReplyKeyboardMarkup(
                new KeyboardButton[]{
                        new KeyboardButton(TelegramKeyboard.share_phone_number).requestContact(true)
                }
        );
        ((ReplyKeyboardMarkup) keyboard).resizeKeyboard(true);
        sendMessage.replyMarkup(keyboard);
        bot.bot.execute(sendMessage);
    }
}
