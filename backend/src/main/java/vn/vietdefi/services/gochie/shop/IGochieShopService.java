package vn.vietdefi.services.gochie.shop;

import com.google.gson.JsonObject;

public interface IGochieShopService {
    JsonObject createShop(long userid, JsonObject shopInfo);
    JsonObject managerUpdateShopInfo (JsonObject data);
    JsonObject acceptEmployeeRequest (long userid);
    JsonObject getEmployeeRequest(long shop_id);
    JsonObject getStaffList(long shop_id);
    JsonObject updateEmployeeInfo(JsonObject data);
    JsonObject changeDirector(long director_id,long manager_id);
//    JsonObject shopSupport (long userid,String text );
//    JsonObject getSupportHistory (long userid);
    JsonObject fireEmployee (long userid);
    JsonObject fire(long userid);
    JsonObject employeeRegistration(long userid,long shop_id);
    JsonObject deleteEmployeeRequest(long userid);
}
