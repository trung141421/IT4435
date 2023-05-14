package vn.vietdefi.vertx.upload;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import vn.vietdefi.vertx.upload.router.UploadRouter;
import vn.vietdefi.vertx.vdef.router.MomoRouter;

public class UploadAPI {

    public static void configAPI(Router router){
        uploadApi(router);
    }

    private static void uploadApi(Router router){
        router.post("/v1/upload")
                .handler(BodyHandler.create())
                .handler(UploadRouter::upload);
        router.get("/v1/getfilename")
                .handler(UploadRouter::getFileName);
        router.post("/v1/deletefile")
                .handler(BodyHandler.create())
                .handler(UploadRouter::deleteFile);
    }
}
