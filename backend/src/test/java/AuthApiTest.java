import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import trung.common.BaseResponse;
import trung.room.launcher.RoomLauncher;
import trung.util.log.DebugLogger;
import trung.util.network.OkHttpUtil;
import trung.util.string.StringUtil;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthApiTest {
    @BeforeAll
    static void init(){
        RoomLauncher.main(new String[]{});
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
            JsonObject json = new JsonObject();
            json.addProperty("username", username);
            json.addProperty("password", password);
            JsonObject response = OkHttpUtil.postJson(
                    "http://127.0.0.1:8080/hr/register",
                    json.toString(), null
            );
            DebugLogger.info("registerNewUser {}", response);
            assertTrue(BaseResponse.isSuccessFullMessage(response));
            JsonObject data = response.getAsJsonObject("data");
            String regUsername = data.get("username").getAsString();
            assertEquals(username, regUsername);
            assertTrue(data.has("token"));
            assertTrue(!data.has("password"));
            response = OkHttpUtil.postJson(
                    "http://127.0.0.1:8080/hr/register",
                    json.toString(), null
            );
            assertTrue(response.get("error").getAsInt() == 10);
        }
    }

    @BeforeAll
    static void done(){
        DebugLogger.info("AuthServiceTest done");
    }
}
