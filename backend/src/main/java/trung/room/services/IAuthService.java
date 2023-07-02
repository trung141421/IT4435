package trung.room.services;

import com.google.gson.JsonObject;

public interface IAuthService {
    JsonObject register(String username, String password);
    JsonObject login(String username, String password);
}
