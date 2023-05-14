package vn.vietdefi.services.izihouse.profile;

import com.google.gson.JsonObject;

public interface IIzihouseNotiService {
    JsonObject createNoti(JsonObject json);

    JsonObject getNoti(JsonObject json);
}
