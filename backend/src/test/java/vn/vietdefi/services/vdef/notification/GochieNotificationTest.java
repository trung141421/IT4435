package vn.vietdefi.services.vdef.notification;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.util.sql.HikariClients;

public class GochieNotificationTest {
    @BeforeAll
    static void init() {
        try {
            HikariClients.instance().init();
            DOMConfigurator.configure("config/log4j.xml");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void testSubscribeNotification(){
        JsonObject data = new JsonObject();
        JsonArray channels = new JsonArray();
        channels.add("telegram");
        JsonArray exceptTypes = new JsonArray();
        exceptTypes.add("news");
        data.add("channels", channels);
        data.add("exceptTypes", exceptTypes);
        VdefServices.gochieNotifier.subscribeNotification(25, data);
    }

    @Test
    void testCreateNotification(){
        JsonObject data = new JsonObject();
        data.addProperty("userId", 25);
        JsonObject content = new JsonObject();
        content.addProperty("title", "New Login");
        content.addProperty("content", "You are login at new device");
        content.addProperty("type", "login");
        data.add("content", content);
        VdefServices.gochieNotifier.createNotification(data);
    }

}
