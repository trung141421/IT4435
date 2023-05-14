package vn.vietdefi.services.wecare.service;

import com.google.gson.JsonObject;
import com.restfb.json.Json;

public interface IWecareServiceService {
    JsonObject doServiceTest(JsonObject json);

    JsonObject createService(JsonObject json);

    JsonObject createServicePack (JsonObject json);

    JsonObject getService(JsonObject json);

    JsonObject getServicePack (JsonObject json);

    JsonObject updateService(JsonObject json);

    JsonObject updateServicePack (JsonObject json);

    JsonObject activeService(JsonObject json);
}
