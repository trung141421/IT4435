package trung.room.services;

import com.google.gson.JsonObject;
import trung.common.BaseResponse;
import trung.util.sql.HikariClients;
import trung.util.sql.SQLJavaBridge;
import trung.util.string.StringUtil;

public class AuthService implements IAuthService{
    public JsonObject register(String username, String password){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT id FROM user WHERE username = ?";

            boolean isExisted = bridge.queryExist(query, username);

            if(isExisted){
                return BaseResponse.createFullMessageResponse(10, "user_exist");
            }

            query = "INSERT INTO user (username, password) VALUE (?,?)";

            bridge.update(query, username, password);
            return login(username, password);
        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
    public JsonObject login(String username, String password){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            String query = "SELECT * FROM user WHERE username = ?";

            Boolean data = bridge.queryExist(query, username);

            if(!data){
                return BaseResponse.createFullMessageResponse(11,"wrong username or password");
            }

            return BaseResponse.createFullMessageResponse(0, "success");
        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }
}
