package vn.vietdefi.services.wecare.schedule;

import com.google.gson.JsonObject;

public interface IWecareScheduleService {

    JsonObject changeStatusSchedule(JsonObject json);

    JsonObject acceptSchedule(long userId, JsonObject json);
    JsonObject declineSchedule(JsonObject json);


    JsonObject bookSchedule(long userId, JsonObject json);

    JsonObject bookScheduleFast(long userId, JsonObject json);
    JsonObject createPersonalSchedule (JsonObject json);

    JsonObject getCustomerBookingSchedule(JsonObject json);

    JsonObject getProviderBookingSchedule(JsonObject json);

    JsonObject getPersonalSchedule(long userId);

    JsonObject updatePersonalSchedule (JsonObject json);
}
