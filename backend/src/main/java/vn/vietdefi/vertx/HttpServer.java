package vn.vietdefi.vertx;

import com.google.gson.JsonObject;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.StaticHandler;
import vn.vietdefi.util.file.FileUtil;
import vn.vietdefi.vertx.gochie.GochieAPI;
import vn.vietdefi.vertx.godoo.GodooAPI;
import vn.vietdefi.vertx.izihouse.IzihouseAPI;
import vn.vietdefi.vertx.mystikos.MtAPI;
import vn.vietdefi.vertx.upload.UploadAPI;
import vn.vietdefi.vertx.vdef.VdefAPI;
import vn.vietdefi.vertx.wecare.WecareAPI;


import java.util.HashSet;
import java.util.Set;

public class HttpServer extends AbstractVerticle {
    private io.vertx.core.http.HttpServer httpServer;
    @Override
    public void start(){
        JsonObject config = FileUtil.getJsonObject("config/http/httpserver.json", false);
        Router router = Router.router(vertx);
        crossAccessControl(router);
        router.route("/v1/logs/*").handler(StaticHandler.create().setCachingEnabled(false).setWebRoot("logs"));
        router.route("/v1/getfile/*").handler(StaticHandler.create().setCachingEnabled(false).setWebRoot("file-uploads"));
        router.route("/v1/test").handler(this::test);
        VdefAPI.configAPI(router);
        GochieAPI.configAPI(router);
        WecareAPI.configAPI(router);
        IzihouseAPI.configAPI(router);
        GodooAPI.configAPI(router);
        UploadAPI.configAPI(router);
        MtAPI.configAPI(router);
        httpServer = vertx.createHttpServer()
                .requestHandler(router)
                .listen(config.get("port").getAsInt()).result();
    }
    private void test(RoutingContext rc) {
        rc.response().end("OK");
    }

    @Override
    public void stop() {
        if (httpServer != null) httpServer.close();
    }

    public void crossAccessControl(Router router) {
        Set<String> allowedHeaders = new HashSet<>();
        allowedHeaders.add("*");
        Set<HttpMethod> allowedMethods = new HashSet<>();
        allowedMethods.add(HttpMethod.GET);
        allowedMethods.add(HttpMethod.POST);
        allowedMethods.add(HttpMethod.OPTIONS);
        router.route().handler(CorsHandler.create("*")
                        .allowedHeaders(allowedHeaders)
                        .allowedMethods(allowedMethods)
                        .allowCredentials(true));
    }
}