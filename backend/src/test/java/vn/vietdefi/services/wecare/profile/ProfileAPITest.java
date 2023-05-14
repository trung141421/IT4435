package vn.vietdefi.services.wecare.profile;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.*;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.launcher.VdefLauncher;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.network.OkHttpUtil;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProfileAPITest {
    @BeforeAll
    static void init() {
        VdefLauncher.main(new String[]{});
    }

    @Nested
    class GetProfile {
        Map<String, String> map = new HashMap<>();

        @Test
        @DisplayName("get")
        void getProfile() {
            map.put("userId", "1");
            map.put("token", "abcxyz");
            JsonObject response = OkHttpUtil.get("http://localhost:8080/v1/wc/profile/get", map);
            DebugLogger.info("getProfile {}", response);
            assertTrue(BaseResponse.isSuccessFullMessage(response));
        }
    }

    @Nested
    class CreateProfile {
        Map<String, String> map = new HashMap<>();

        @Test
        @DisplayName("create")
        void createProfile() {
            map.put("userId", "17");
            map.put("token", "1");
            JsonObject json = new JsonObject();
            json.addProperty("phoneNumber", "12345");
            json.addProperty("country", "123");
            json.addProperty("city", "123");
            json.addProperty("district", "123");
            json.addProperty("role", 1);
            json.addProperty("gender", 1);
            json.addProperty("status", 1);
            json.addProperty("detail", "1");
            JsonObject response = OkHttpUtil.postJson("http://localhost:8080/v1/wc/profile/create", json.toString(), map);
            DebugLogger.info("createProfile {}", response);
            assertTrue(BaseResponse.isSuccessFullMessage(response));
        }
    }

    @Nested
    class UpdateProfile {
        Map<String, String> map = new HashMap<>();

        @Test
        @DisplayName("update")
        void updateProfile() {
            map.put("userId", "17");
            map.put("token", "1");
            JsonObject json = new JsonObject();
            json.addProperty("phoneNumber", "123456");
            json.addProperty("country", "123");
            json.addProperty("city", "123");
            json.addProperty("district", "123");
            json.addProperty("role", 1);
            json.addProperty("gender", 1);
            json.addProperty("status", 1);
            json.addProperty("detail", "1");
            JsonObject response = OkHttpUtil.postJson("http://localhost:8080/v1/wc/profile/update", json.toString(), map);
            DebugLogger.info("update {}", response);
            assertTrue(BaseResponse.isSuccessFullMessage(response));
        }
    }

    @Nested
    class GetProvider {
        Map<String, String> map = new HashMap<>();
        @Test
        @DisplayName("get-provider")
        void getProvider() {
            map.put("userId", "1");
            map.put("token", "abcxyz");
            JsonObject response = OkHttpUtil.get("http://localhost:8080/v1/wc/profile/get-provider", map);
            DebugLogger.info("getProvider {}", response);
            assertTrue(BaseResponse.isSuccessFullMessage(response));
        }
    }

    @Nested
    class CreateProvider {
        Map<String, String> map = new HashMap<>();
        @Test
        @DisplayName("create-provider")
        void createProfile() {
            map.put("userId", "17");
            map.put("token", "1");
            JsonObject json = new JsonObject();
            json.addProperty("userId", "17");
            json.addProperty("createService", "13");
            json.addProperty("dateOfBirth", "123");
            json.addProperty("detail", "1");
            JsonObject response = OkHttpUtil.postJson("http://localhost:8080/v1/wc/profile/create-provider", json.toString(), map);
            DebugLogger.info("createProvider {}", response);
            assertTrue(BaseResponse.isSuccessFullMessage(response));
        }
    }
    @Nested
    class UpdateProvider {
        Map<String, String> map = new HashMap<>();

        @Test
        @DisplayName("update-provider")
        void updateProfile() {
            map.put("userId", "17");
            map.put("token", "1");
            JsonObject json = new JsonObject();
            json.addProperty("createService", "1223");
            json.addProperty("dateOfBirth", "123");
            json.addProperty("detail", "1");
            JsonObject response = OkHttpUtil.postJson("http://localhost:8080/v1/wc/profile/update-provider", json.toString(), map);
            DebugLogger.info("updateProvider {}", response);
            assertTrue(BaseResponse.isSuccessFullMessage(response));
        }
    }

    @AfterAll
    static void done() {
        DebugLogger.info("ProfileAPITest done");
    }
}
