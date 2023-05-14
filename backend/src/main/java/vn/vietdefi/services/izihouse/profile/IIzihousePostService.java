package vn.vietdefi.services.izihouse.profile;

import com.google.gson.JsonObject;

public interface IIzihousePostService {
    JsonObject createHousePost(JsonObject json);

    JsonObject createRoomPost(JsonObject json);

    JsonObject getPostInfo(JsonObject json);

    JsonObject getAllPost(JsonObject json);
    
    JsonObject hidePost(JsonObject json);

    JsonObject getHidePost(JsonObject json);
    
    JsonObject unhidePost(JsonObject json);

    JsonObject deletePost(JsonObject json);

    JsonObject savePost(JsonObject json);

    JsonObject getSavePost(JsonObject json);

    JsonObject unSavePost(JsonObject json);

    JsonObject getUserPost(JsonObject json);

    JsonObject getHouseListToPost(JsonObject json);

    JsonObject getRoomListToPost(JsonObject json);
}
