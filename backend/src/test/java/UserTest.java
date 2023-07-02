import com.google.gson.JsonObject;
import trung.util.network.OkHttpUtil;

import java.util.HashMap;

public class UserTest {
    public static void main(String[] args){
        JsonObject data = new JsonObject();
        data.addProperty("username", "hieu");
        data.addProperty("password", "123");
        JsonObject response = OkHttpUtil.postJson("http://localhost:8080/api/auth/login",
                data.toString(), new HashMap<>());
        System.out.println(response);
    }
}
