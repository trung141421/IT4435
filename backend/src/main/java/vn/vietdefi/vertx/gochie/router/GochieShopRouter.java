package vn.vietdefi.vertx.gochie.router;

import com.google.gson.JsonObject;
import io.vertx.ext.web.RoutingContext;
import vn.vietdefi.util.json.GsonUtil;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.services.VdefServices;

public class GochieShopRouter {
        public static void createShop(RoutingContext rc){
            try{
                long userid = Long.parseLong(rc.request().getHeader("userid"));

                String data = rc.body().asString();
                JsonObject json = GsonUtil.toJsonObject(data);

                JsonObject response =  VdefServices.gochieShopService.createShop(userid,json);
                rc.response().end(response.toString());
            }catch(Exception e ){
                e.printStackTrace();
                JsonObject response = BaseResponse.createFullMessageResponse(
                        1, "system_error"
                );
                rc.response().end(response.toString());

            }
        }
        public static void managerUpdateShopInfo(RoutingContext rc){
            try{
                long userId = Long.parseLong(rc.request().getHeader("userId"));
                String data = rc.body().asString();
                JsonObject json = GsonUtil.toJsonObject(data);
                json.addProperty("userId",userId);
                JsonObject response = VdefServices.gochieShopService.managerUpdateShopInfo(json);
                rc.response().end(response.toString());
            }catch(Exception e){
                e.printStackTrace();
                JsonObject response = BaseResponse.createFullMessageResponse(
                        1, "system_error"
                );
                rc.response().end(response.toString());
            }
        }
        public static void acceptEmployeeRequest(RoutingContext rc){
            try{
                String data = rc.body().asString();
                JsonObject json = GsonUtil.toJsonObject(data);

                Long userid = json.get("userid").getAsLong();

                JsonObject response = VdefServices.gochieShopService.acceptEmployeeRequest(userid);
                rc.response().end(response.toString());
            }catch(Exception e ){
                e.printStackTrace();
                JsonObject response = BaseResponse.createFullMessageResponse(
                        1, "system_error"
                );
                rc.response().end(response.toString());

            }
        }
        public static void getEmployeeRequest(RoutingContext rc){
            try{
                long shop_id = Long.parseLong(rc.request().getParam("shop_id"));

                JsonObject response = VdefServices.gochieShopService.getEmployeeRequest(shop_id);
                rc.response().end(response.toString());
            }catch(Exception e ){
                e.printStackTrace();
                JsonObject response = BaseResponse.createFullMessageResponse(
                        1, "system_error"
                );
                rc.response().end(response.toString());

            }
        }
        public static void getStaffList(RoutingContext rc){
            try{
                long shop_id = Long.parseLong(rc.request().getParam("shop_id"));

                JsonObject response = VdefServices.gochieShopService.getStaffList(shop_id);
                rc.response().end(response.toString());
            }catch(Exception e ){
                e.printStackTrace();
                JsonObject response = BaseResponse.createFullMessageResponse(
                        1, "system_error"
                );
                rc.response().end(response.toString());

            }
        }
        public static void updateEmployeeInfo(RoutingContext rc){
            try{
                String data = rc.body().asString();
                JsonObject json = GsonUtil.toJsonObject(data);

                JsonObject response = VdefServices.gochieShopService.updateEmployeeInfo(json);
                rc.response().end(response.toString());
            }catch(Exception e ){
                e.printStackTrace();
                JsonObject response = BaseResponse.createFullMessageResponse(
                        1, "system_error"
                );
                rc.response().end(response.toString());

            }
        }
        public static void changeDirector (RoutingContext rc){
            try{
                long director_id = Long.parseLong(rc.request().getHeader("userid"));

                String data = rc.body().asString();
                JsonObject json = GsonUtil.toJsonObject(data);

                long manager_id = json.get("manager_id").getAsLong();

                JsonObject response = VdefServices.gochieShopService.changeDirector(director_id,manager_id);
                rc.response().end(response.toString());
            }catch(Exception e ){
                e.printStackTrace();
                JsonObject response = BaseResponse.createFullMessageResponse(
                        1, "system_error"
                );
                rc.response().end(response.toString());

            }
        }
//        public static void shopSupport(RoutingContext rc){
//            try{
//                long userid = Long.parseLong(rc.request().getHeader("userid"));
//
//                String data = rc.body().asString();
//                JsonObject json = GsonUtil.toJsonObject(data);
//
//                String text = json.get("text").getAsString();
//                JsonObject response = VdefServices.gochieShopService.shopSupport(userid,text);
//                rc.response().end(response.toString());
//            }catch(Exception e ){
//                e.printStackTrace();
//                JsonObject response = BaseResponse.createFullMessageResponse(
//                        1, "system_error"
//                );
//                rc.response().end(response.toString());
//
//            }
//        }
//        public static void getSupportHistory(RoutingContext rc){
//            try{
//                long userid = Long.parseLong(rc.request().getHeader("userid"));
//                JsonObject response = VdefServices.gochieShopService.getSupportHistory(userid);
//                rc.response().end(response.toString());
//            }catch(Exception e ){
//                e.printStackTrace();
//                JsonObject response = BaseResponse.createFullMessageResponse(
//                        1, "system_error"
//                );
//                rc.response().end(response.toString());
//
//            }
//        }
        public static void fireEmployee(RoutingContext rc){
            try{
                String data = rc.body().asString();
                JsonObject json = GsonUtil.toJsonObject(data);

                long userid = json.get("userid").getAsLong();

                JsonObject response = VdefServices.gochieShopService.fireEmployee(userid);
                rc.response().end(response.toString());
            }catch(Exception e ){
                e.printStackTrace();
                JsonObject response = BaseResponse.createFullMessageResponse(
                        1, "system_error"
                );
                rc.response().end(response.toString());

            }
        }
        public static void fire(RoutingContext rc){
            try{
                String data = rc.body().asString();
                JsonObject json = GsonUtil.toJsonObject(data);

                long userid = json.get("userid").getAsLong();

                JsonObject response = VdefServices.gochieShopService.fire(userid);
                rc.response().end(response.toString());
            }catch(Exception e ){
                e.printStackTrace();
                JsonObject response = BaseResponse.createFullMessageResponse(
                        1, "system_error"
                );
                rc.response().end(response.toString());

            }
        }
        public static void employeeRegistration(RoutingContext rc){
            try{
                long userid = Long.parseLong(rc.request().getHeader("userid"));

                String data = rc.body().asString();
                JsonObject json = GsonUtil.toJsonObject(data);

                long shop_id = json.get("shop_id").getAsLong();

                JsonObject response = VdefServices.gochieShopService.employeeRegistration(userid,shop_id);
                rc.response().end(response.toString());
            }catch (Exception e){
                e.printStackTrace();
                JsonObject response = BaseResponse.createFullMessageResponse(
                        1, "system_error"
                );
                rc.response().end(response.toString());
            }
        }
        public static void deleteEmployeeRequest(RoutingContext rc){
            try{
                long userid = Long.parseLong(rc.request().getHeader("userid"));

                JsonObject response = VdefServices.gochieShopService.deleteEmployeeRequest(userid);
                rc.response().end(response.toString());
            }catch (Exception e){
                e.printStackTrace();
                JsonObject response = BaseResponse.createFullMessageResponse(
                        1, "system_error"
                );
                rc.response().end(response.toString());
            }
        }

    }
