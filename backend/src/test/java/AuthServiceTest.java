import com.google.gson.JsonObject;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.jupiter.api.*;
import trung.common.BaseResponse;
import trung.room.services.TrungServices;
import trung.util.log.DebugLogger;
import trung.util.sql.HikariClients;
import trung.util.string.StringUtil;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A stack")
public class AuthServiceTest {
    @BeforeAll
    static void init(){
        try {
            DOMConfigurator.configure("config/log4j.xml");
            HikariClients.instance().init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nested
    class RegisterNewUser{
        String username;
        String password;
        @BeforeEach
        void newUser(){
            username = StringUtil.randomString(6);
            password = StringUtil.randomString(6);
        }
        @RepeatedTest(3)
        void registerNewUser(){
            JsonObject response = TrungServices.authService
                    .register(username, password);
            assertTrue(BaseResponse.isSuccessFullMessage(response));
            JsonObject data = response.getAsJsonObject("data");
            String regUsername = data.get("username").getAsString();
            assertEquals(username, regUsername);
            assertTrue(data.has("token"));
            assertTrue(!data.has("password"));
            response = TrungServices.authService
                    .register(username, password);
            assertTrue(response.get("error").getAsInt() == 10);
        }
    }

    @BeforeAll
    static void done(){
        DebugLogger.info("AuthServiceTest done");
    }
}
