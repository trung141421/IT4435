package vn.vietdefi.services.gochie.shop;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;
import vn.vietdefi.common.BaseResponse;

public class GochieShopService implements IGochieShopService{
    public JsonObject createShop(long userId, JsonObject shopInfo) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "INSERT INTO gochie_shop (name, description, address, website, phone_number, tax_code, introduce, logo, image,balance_diamond, branch) VALUES (?,?,?,?,?,?,?,?,?,0,?)";

            String name = shopInfo.get("name").getAsString();
            String description = shopInfo.get("description").getAsString();
            String address = shopInfo.get("address").getAsString();
            String website = shopInfo.get("website").getAsString();
            String phone_number = shopInfo.get("phone_number").getAsString();
            String tax_code = shopInfo.get("tax_code").getAsString();
            String introduce = shopInfo.get("introduce").getAsString();
            String logo = shopInfo.get("logo").getAsString();
            String image = shopInfo.get("image").getAsString();
            JsonArray branch = shopInfo.get("branch").getAsJsonArray();

            bridge.update(query, name, description, address, website, phone_number, tax_code, introduce, logo, image, branch);
            query = "SELECT id,name FROM gochie_shop WHERE name = ? AND description = ? AND address = ? AND website = ? AND phone_number = ? AND tax_code = ? AND logo = ?";
            JsonObject shop_info = bridge.queryOne(query,name, description, address, website, phone_number, tax_code,logo);
            long shop_id = shop_info.get("id").getAsLong();
            String shop_name = shop_info.get("name").getAsString();

            query = "UPDATE gochie_sales_manager_profile SET shop_id = ?, shop_name = ? WHERE userId= ?";
            bridge.update(query,shop_id,shop_name,userId);
            return BaseResponse.createFullMessageResponse(0, "success");

        }
        catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }
    @Override
    public JsonObject managerUpdateShopInfo(JsonObject data) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            long userId = data.get("userId").getAsLong();
            String query = "SELECT shop_id FROM gochie_sales_manager_profile WHERE userId = ?";
            long shop_id = bridge.queryLong(query,userId);

            String name = data.get("name").getAsString();
            String description = data.get("description").getAsString();
            String address = data.get("address").getAsString();
            String website = data.get("website").getAsString();
            String phoneNumber = data.get("phone_number").getAsString();
            String tax_code = data.get("tax_code").getAsString();
            String introduce = data.get("introduce").getAsString();
            String logo = data.get("logo").getAsString();
            String image = data.get("image").getAsString();
            JsonArray branch = data.get("branch").getAsJsonArray();

            query = "UPDATE gochie_shop SET name = ?, description=?, address=?, website=?, phone_number = ?, tax_code = ?, introduce = ?, logo=?, image=?, branch=? WHERE id = ?";
            bridge.update(query,name, description, address, website,phoneNumber,tax_code,introduce,logo,image,branch,shop_id);
            query = "UPDATE gochie_sales_manager_profile SET shop_name = ? WHERE userId = ?";
            bridge.update(query,name,userId);

            return BaseResponse.createFullMessageResponse(0, "success");
        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1, "system_error");
        }
    }

    public JsonObject acceptEmployeeRequest(long userId){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            String query = "UPDATE gochie_sales_manager_profile SET status = 1,working_status = 1 WHERE userId = ?";
            bridge.update(query,userId);

            return  BaseResponse.createFullMessageResponse(0,"success");
        }catch(Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }
    public JsonObject getEmployeeRequest (long shop_id){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            String query = "SELECT fullname, userId FROM gochie_sales_manager_profile WHERE shop_id=? and status = 0  ";
            JsonArray data = bridge.query(query,shop_id);

            return  BaseResponse.createFullMessageResponse(0,"success",data);
        }catch(Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject getStaffList(long shop_id) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            String query = "SELECT fullname, userId FROM gochie_sales_manager_profile WHERE shop_id = ? AND status = 1 ";
            JsonArray data = bridge.query(query,shop_id);

            return  BaseResponse.createFullMessageResponse(0,"success",data);
        }catch(Exception e){
            e.printStackTrace();
            return  BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject updateEmployeeInfo(JsonObject data) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            long userId = data.get("userId").getAsLong();
            int role = data.get("role").getAsInt();
            int status = data.get("status").getAsInt();
            String branch = data.get("branch").getAsString();
            int working_status = data.get("working_status").getAsInt();

            String query = "UPDATE gochie_sales_manager_profile SET role = ?, status = ?, branch = ?, working_status = ? WHERE id = ?)";
            bridge.update(query,role,status,branch,working_status,userId);

            return BaseResponse.createFullMessageResponse(0,"success");

        }catch(Exception e){
            e.printStackTrace();
            return  BaseResponse.createFullMessageResponse(1,"system_error");
        }

    }

    @Override
    public JsonObject changeDirector(long director_id,long manager_id) {
        try{
            SQLJavaBridge bridge =  HikariClients.instance().defaulSQLJavaBridge();

            String query = "UPDATE gochie_sales_manager_profile SET role = ? WHERE userId = ?";
            bridge.update(query , 2,director_id);
            query = "UPDATE gochie_sales_manager_profile SET role = ? WHERE userId = ?";
            bridge.update(query,3,manager_id);

            return BaseResponse.createFullMessageResponse(0,"success");
        }catch (Exception e){
            e.printStackTrace();
            return  BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

//    @Override
//    public JsonObject shopSupport(long userId,String text) {
//        try{
//            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
//
//            long cs = System.currentTimeMillis()/1000;
//
//            String query = "INSERT INTO gochie_support(userId,time,content) VALUE(?,?,?)";
//            bridge.update(query,userId,cs,text);
//
//            return BaseResponse.createFullMessageResponse(0,"success");
//        }catch(Exception e){
//            e.printStackTrace();
//            return BaseResponse.createFullMessageResponse(1,"system_error");
//        }
//    }
//    public JsonObject getSupportHistory(long userId){
//        try{
//            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
//
//            String query = "SELECT * FROM support WHERE userId = ?";
//            JsonArray data = bridge.query(query,userId);
//
//            return BaseResponse.createFullMessageResponse(0,"success",data);
//        }catch(Exception e){
//            e.printStackTrace();
//            return BaseResponse.createFullMessageResponse(1,"system_error");
//        }
//    }
    public JsonObject fireEmployee(long userId){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            String query = "UPDATE gochie_sales_manager_profile SET shop_id = NULL, shop_name = NULL, status = 0 ,branch  = NULL, working_status = 0 WHERE userId = ? AND role =1";
            bridge.update(query,userId);

            return BaseResponse.createFullMessageResponse(0,"success");
        }catch(Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }
    public JsonObject fire(long userId){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            String query = "UPDATE gochie_sales_manager_profile SET shop_id = NULL, shop_name = NULL, status = 0 ,branch  = NULL, working_status = 0 WHERE userId = ? AND role <> 3";
            bridge.update(query,userId);

            return BaseResponse.createFullMessageResponse(0,"success");
        }catch(Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }
    public JsonObject employeeRegistration(long userId,long shop_id){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            String query = "SELECT name FROM gochie_shop WHERE id = ?";
            JsonObject data = bridge.queryOne(query,shop_id);
            String shop_name = data.get("name").getAsString();

            query = "UPDATE gochie_sales_manager_profile SET shop_id = ?, shop_name = ? WHERE userId = ?";
            bridge.update(query,shop_id,shop_name,userId);

            return BaseResponse.createFullMessageResponse(0,"success");
        }catch(Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }
    public JsonObject deleteEmployeeRequest(long userId){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();

            String query = "UPDATE gochie_sales_manager_profile SET shop_id = NULL, shop_name = NULL WHERE userId = ?";
            bridge.update(query,userId);

            return BaseResponse.createFullMessageResponse(0,"success");
        }catch(Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }
}
