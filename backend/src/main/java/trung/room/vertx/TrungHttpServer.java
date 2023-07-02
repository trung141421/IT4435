package trung.room.vertx;

import com.google.gson.JsonObject;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import trung.room.vertx.router.AuthRouter;
import trung.room.vertx.router.RoomRouter;
import trung.util.file.FileUtil;

import java.util.HashSet;
import java.util.Set;

public class TrungHttpServer extends AbstractVerticle {
    private HttpServer httpServer;
    @Override
    public void start() throws Exception {
        JsonObject config = FileUtil.getJsonObject("config/http/httpserver.json", false);
        Router router = Router.router(vertx);
        crossAccessControl(router);
        authApi(router);
        roomApi(router);
        httpServer = vertx.createHttpServer()
                .requestHandler(router)
                .listen(config.get("port").getAsInt()).result();
    }

    private void authApi(Router router) {
        router.post("/auth/register")
                .handler(BodyHandler.create())
                .handler(AuthRouter::register);
        router.post("/auth/login")
                .handler(BodyHandler.create())
                .handler(AuthRouter::login);
    }

    private void roomApi(Router router) {
        router.get("/room/getroom")
                .handler(BodyHandler.create())
                .handler(RoomRouter::getRoom);
        router.post("/room/createroom")
                .handler(BodyHandler.create())
                .handler(RoomRouter::createRoom);
        router.post("/room/deleteroom")
                .handler(BodyHandler.create())
                .handler(RoomRouter::deleteRoom);
        router.get("/room/editroom")
                .handler(BodyHandler.create())
                .handler(RoomRouter::deleteRoom);
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
                        .allowedMethods(allowedMethods));
    }
}