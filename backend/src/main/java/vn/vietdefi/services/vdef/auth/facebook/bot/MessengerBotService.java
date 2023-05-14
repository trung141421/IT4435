package vn.vietdefi.services.vdef.auth.facebook.bot;

import com.google.gson.JsonObject;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.network.OkHttpUtil;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;

public class MessengerBotService implements IMessengerBotService{
    @Override
    public JsonObject receiveMessage(long senderId, String message) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            addMessengerInfo(senderId);
            String reply ;
            message = message.toLowerCase();
            if(message.equals("hi")) {
                reply = "Xin chao, toi co the giup gi";
            }
            else if (message.equals("gioi thieu ve ban di")) reply = "Toi la bot ma thoi";
            else if(message.equals("nhan ma otp")){
                String query = "SELECT userId FROM messenger_user WHERE messengerId = ? ";
                long userId = bridge.queryLong(query,senderId);
                query = "SELECT random_otp FROM random_otp WHERE userId = ?";
                int otp = bridge.queryInteger(query,userId);
                return VdefServices.messengerBotService.responseMessage(senderId,String.valueOf(otp));

            }
            else {
                message= message.substring(2);
                String query = "SELECT * FROM messenger_user WHERE active_code = ? ";
                boolean isExist = bridge.queryExist(query,message );
                if(isExist){
                    query = "UPDATE messenger_user SET messengerId = ? WHERE active_code = ?";
                    bridge.update(query,senderId,message);
                    reply = "Lien ket thanh cong";
                }
                else reply = "Active code sai";
            }

            return responseMessage(senderId,reply);
        }catch(Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject responseMessage(long senderId, String reply) {
        try{
            JsonObject recipient = new JsonObject();
            recipient.addProperty("id",senderId);

            JsonObject message = new JsonObject();
            message.addProperty("text",reply);

            JsonObject data = new JsonObject();
            data.addProperty("messaging_type","RESPONSE");
            data.add("recipient", recipient);
            data.add("message",message);

            JsonObject response = OkHttpUtil.postJson("https://graph.facebook.com/v15.0/110408197549845/messages?access_token=EAASbiV3cpZCwBABEGSeZBjZB7uo2ZCpE5pvTacZBaSVQu6A7ZBC4IS9Qa25SFFPJEV7IS65Tc7XKiHVU4tZBCxeIBhSWMtADpHAXdC35zhrT14VLMav5ymPOe0NZCJzXWrYBQuWQbjw8SY4EG1aBE4PVrJnZAFHgepPH5SYdQLfuweNUlcWF9jIOVKP7UUB6Hs2k40UXCooqoNwZDZD",data.toString(),null);
            return response;

        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public void addMessengerInfo(long senderId) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "UPDATE messenger_user SET messenger_info = ? WHERE messengerId = ?";
            JsonObject response = OkHttpUtil.get("https://graph.facebook.com/"+senderId+"?fields=first_name,last_name,profile_pic&access_token=EAASbiV3cpZCwBABEGSeZBjZB7uo2ZCpE5pvTacZBaSVQu6A7ZBC4IS9Qa25SFFPJEV7IS65Tc7XKiHVU4tZBCxeIBhSWMtADpHAXdC35zhrT14VLMav5ymPOe0NZCJzXWrYBQuWQbjw8SY4EG1aBE4PVrJnZAFHgepPH5SYdQLfuweNUlcWF9jIOVKP7UUB6Hs2k40UXCooqoNwZDZD");
            bridge.update(query,response,senderId);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
