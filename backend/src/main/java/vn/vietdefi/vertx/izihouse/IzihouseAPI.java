package vn.vietdefi.vertx.izihouse;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import vn.vietdefi.vertx.izihouse.router.*;
import vn.vietdefi.vertx.vdef.router.AuthRouter;

public class IzihouseAPI {
    public static void configAPI(Router router){
        profileAPI(router);
        houseAPI(router);
        postAPI(router);
        rentAPI(router);
        notiAPI(router);
    }

    private static void profileAPI(Router router) {
        router.get("/v1/ih/profile/get")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(IzihouseProfileRouter::getProfile);
        router.post("/v1/ih/profile/create")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(IzihouseProfileRouter::createProfile);
        router.post("/v1/ih/profile/update")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(IzihouseProfileRouter::updateProfile);
    }

    private static void houseAPI(Router router) {
        router.post("/v1/ih/house/createhouse")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(IzihouseHouseRouter::createHouse);
        router.post("/v1/ih/house/createroom")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(IzihouseHouseRouter::createRoom);
        router.get("/v1/ih/house/gethouseinfo")
                .handler(AuthRouter::authorizeUser)
                .handler(IzihouseHouseRouter::getHouseInfo);
        router.get("/v1/ih/house/getroominfo")
                .handler(AuthRouter::authorizeUser)
                .handler(IzihouseHouseRouter::getRoomInfo);
        router.get("/v1/ih/house/gethouseofuser")
                .handler(AuthRouter::authorizeUser)
                .handler(IzihouseHouseRouter::getHouseOfUser);
        router.get("/v1/ih/house/getroomofhouse")
                .handler(AuthRouter::authorizeUser)
                .handler(IzihouseHouseRouter::getRoomOfHouse);
        router.post("/v1/ih/house/updatehouse")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(IzihouseHouseRouter::updateHouseInfo);
        router.post("/v1/ih/house/updateroom")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(IzihouseHouseRouter::updateRoomInfo);
        router.post("/v1/ih/house/deletehouse")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(IzihouseHouseRouter::deleteHouse);
        router.post("/v1/ih/house/deleteroom")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(IzihouseHouseRouter::deleteRoom);
    }

    private static void postAPI(Router router){
        router.post("/v1/ih/post/createhousepost")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(IzihousePostRouter::createHousePost);
        router.post("/v1/ih/post/createroompost")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(IzihousePostRouter::createRoomPost);
        router.get("/v1/ih/post/getpostinfo")
                .handler(IzihousePostRouter::getPostInfo);
        router.get("/v1/ih/post/getallpost")
                .handler(IzihousePostRouter::getAllPost);
        router.post("/v1/ih/post/hide")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(IzihousePostRouter::hidePost);
        router.get("/v1/ih/post/gethidepost")
                .handler(AuthRouter::authorizeUser)
                .handler(IzihousePostRouter::getHidePost);
        router.post("/v1/ih/post/unhide")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(IzihousePostRouter::unhidePost);
        router.post("/v1/ih/post/delete")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(IzihousePostRouter::deletePost);
        router.post("/v1/ih/post/save")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(IzihousePostRouter::savePost);
        router.get("/v1/ih/post/getsavepost")
                .handler(AuthRouter::authorizeUser)
                .handler(IzihousePostRouter::getSavePost);
        router.post("/v1/ih/post/unsave")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(IzihousePostRouter::unSavePost);
        router.get("/v1/ih/post/getuserpost")
                .handler(AuthRouter::authorizeUser)
                .handler(IzihousePostRouter::getUserPost);
        router.get("/v1/ih/post/gethousetopost")
                .handler(AuthRouter::authorizeUser)
                .handler(IzihousePostRouter::getHouseListToPost);
        router.get("/v1/ih/post/getroomtopost")
                .handler(AuthRouter::authorizeUser)
                .handler(IzihousePostRouter::getRoomListToPost);
    }

    private static void rentAPI(Router router){
        router.post("/v1/ih/rent/createrequest")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(IzihouseRentRouter::createRequest);
        router.get("/v1/ih/rent/getrequestofrenter")
                .handler(AuthRouter::authorizeUser)
                .handler(IzihouseRentRouter::getRequestOfRenter);
        router.post("/v1/ih/rent/deleterequest")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(IzihouseRentRouter::deleteRequest);
        router.post("/v1/ih/rent/acceptrequest")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(IzihouseRentRouter::acceptRequest);
        router.get("/v1/ih/rent/getrequestofhouse")
                .handler(AuthRouter::authorizeUser)
                .handler(IzihouseRentRouter::getRequestOfHouse);
        router.get("/v1/ih/rent/getrequestofroom")
                .handler(AuthRouter::authorizeUser)
                .handler(IzihouseRentRouter::getRequestOfRoom);
    }

    private static void notiAPI(Router router){
        router.post("/v1/ih/noti/createnoti")
                .handler(BodyHandler.create())
                .handler(AuthRouter::authorizeUser)
                .handler(IzihouseNotiRouter::createNoti);
        router.get("/v1/ih/noti/getnoti")
                .handler(AuthRouter::authorizeUser)
                .handler(IzihouseNotiRouter::getNoti);
    }
}
