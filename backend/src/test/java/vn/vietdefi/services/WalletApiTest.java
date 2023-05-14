package vn.vietdefi.services;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.*;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.launcher.VdefLauncher;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.network.OkHttpUtil;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;



public class WalletApiTest {
    @BeforeAll
    static void init() {
        VdefLauncher.main(new String[]{});
    }
    @Nested
    class GetWallet {
        long userId;
        Map<String, String> map = new HashMap<String, String>();
        @Test
        @DisplayName("getWalletExist")
        void getWalletExist() {
            map.put("userId","1");
            map.put("token","abcxyz");
            map.put("username","admin");

            JsonObject response = OkHttpUtil.get(
                    "http://localhost:8080/v1/wallet/get",
                     map
            );
            DebugLogger.info("getWallet {}", response);
            assertTrue(BaseResponse.isSuccessFullMessage(response));
        }
        @Test
        @DisplayName("getWalletNotExist")
        void getWalletNotExist() {
            map.put("userId","1");
            map.put("token","abcxyz");
            map.put("username","num5");

            JsonObject response = OkHttpUtil.get(
                    "http://localhost:8080/v1/wallet/get",
                    map
            );
            DebugLogger.info("getWallet {}", response);
            assertTrue(BaseResponse.isSuccessFullMessage(response));
        }
    }
    @Nested
    class LockWallet {
        Map<String, String> map = new HashMap<String, String>();

        @Test
        @DisplayName("lockWallet")
        void lockWallet() {
            map.put("userId","2");
            JsonObject response = OkHttpUtil.get(
                    "http://localhost:8080/v1/wallet/lock",
                     map
            );
            DebugLogger.info("lockWallet {}", response);
            assertTrue(BaseResponse.isSuccessFullMessage(response));
        }
    }

    @Nested
    class AddBalance {
        Map<String, String> map = new HashMap<String, String>();
        @Test
        @DisplayName("addBalanceSuccessfully")
        void addBalanceSuccessfully(){
            map.put("userId","1");
            map.put("username","admin");
            map.put("status","0");
            JsonObject json = new JsonObject();
            json.addProperty("amount",100);
            json.addProperty("service",1);
            json.addProperty("detail",1);
            json.addProperty("reference",1);
            JsonObject response = OkHttpUtil.postJson(
                    "http://localhost:8080/v1/wallet/add",
                    json.toString(), map
            );
            DebugLogger.info("addBalance {}", response);
            assertTrue(BaseResponse.isSuccessFullMessage(response));
        }

        @Test
        @DisplayName("negativeAmount")
        void negativeAmount(){
            map.put("userId","1");
            map.put("username","admin");
            map.put("status","0");
            JsonObject json = new JsonObject();
            json.addProperty("amount",-100);
            json.addProperty("service",1);
            json.addProperty("detail",1);
            json.addProperty("reference",1);
            JsonObject response = OkHttpUtil.postJson(
                    "http://localhost:8080/v1/wallet/add",
                    json.toString(), map
            );
            DebugLogger.info("addBalance {}", response);
            System.out.println(response);
            assertTrue(response.get("error").getAsInt() == 12);
        }
        @Test
        @DisplayName("addLockWallet")
        void addLockWallet(){
            map.put("userId","2");
            map.put("username","2");
            map.put("status","1");
            JsonObject json = new JsonObject();
            json.addProperty("amount",10);
            json.addProperty("service",1);
            json.addProperty("detail",1);
            json.addProperty("reference",1);
            JsonObject response = OkHttpUtil.postJson(
                    "http://localhost:8080/v1/wallet/add",
                    json.toString(), map
            );
            DebugLogger.info("addBalance {}", response);
            System.out.println(response);
            assertTrue(response.get("error").getAsInt() == 11);
        }

    }
    @Nested
    class SubBalance {
        Map<String, String> map = new HashMap<String, String>();
        @Test
        @DisplayName("subBalanceSuccessfully")
        void addBalanceSuccessfully(){
            map.put("userId","1");
            map.put("username","admin");
            map.put("status","0");
            JsonObject json = new JsonObject();
            json.addProperty("amount",10);
            json.addProperty("service",1);
            json.addProperty("detail",1);
            json.addProperty("reference",1);
            JsonObject response = OkHttpUtil.postJson(
                    "http://localhost:8080/v1/wallet/sub",
                    json.toString(), map
            );
            DebugLogger.info("subBalance {}", response);
            assertTrue(BaseResponse.isSuccessFullMessage(response));
        }

        @Test
        @DisplayName("negativeAmount")
        void negativeAmount(){
            map.put("userId","1");
            map.put("username","admin");
            map.put("status","0");

            JsonObject json = new JsonObject();
            json.addProperty("amount",-100);
            json.addProperty("service",1);
            json.addProperty("detail",1);
            json.addProperty("reference",1);
            JsonObject response = OkHttpUtil.postJson(
                    "http://localhost:8080/v1/wallet/sub",
                    json.toString(), map
            );
            DebugLogger.info("subBalance {}", response);
            assertTrue(response.get("error").getAsInt() ==12);
        }

        @Test
        @DisplayName("subLockWallet")
        void subLockWallet(){
            map.put("userId","2");
            map.put("username","2");
            map.put("status","1");
            JsonObject json = new JsonObject();
            json.addProperty("amount",10);
            json.addProperty("service",1);
            json.addProperty("detail",1);
            json.addProperty("reference",1);
            JsonObject response = OkHttpUtil.postJson(
                    "http://localhost:8080/v1/wallet/sub",
                    json.toString(), map
            );
            DebugLogger.info("addBalance {}", response);
            System.out.println(response);
            assertTrue(response.get("error").getAsInt() == 11);
        }

        @Test
        @DisplayName("subAmountMoreThanAccount")
        void subAmountMoreThanAccount(){
            map.put("userId","1");
            map.put("username","admin");
            map.put("status","0");

            JsonObject json = new JsonObject();
            json.addProperty("amount",10000);
            json.addProperty("service",1);
            json.addProperty("detail",1);
            json.addProperty("reference",1);
            JsonObject response = OkHttpUtil.postJson(
                    "http://localhost:8080/v1/wallet/sub",
                    json.toString(), map
            );
            DebugLogger.info("subBalance {}", response);
            assertTrue(response.get("error").getAsInt() ==10);
        }
    }
    @Nested
    class getWalletExchange {

    }
    @AfterAll
    static void done(){
        DebugLogger.info("WalletApiTest done");
    }
}
