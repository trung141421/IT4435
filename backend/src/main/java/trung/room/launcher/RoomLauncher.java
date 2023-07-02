package trung.room.launcher;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.apache.log4j.xml.DOMConfigurator;
import trung.room.vertx.TrungHttpServer;
import trung.util.log.DebugLogger;
import trung.util.sql.HikariClients;

public class RoomLauncher {
    public static Vertx vertx;
    public static void main(String[] args) {
        try {
            DOMConfigurator.configure("config/log4j.xml");
            HikariClients.instance().init();
            startHttpServer();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void startHttpServer() {
        VertxOptions vxOptions = new VertxOptions().setBlockedThreadCheckInterval(30000);
        vertx = Vertx.vertx(vxOptions);
        DeploymentOptions deploymentOptions = new DeploymentOptions();
        deploymentOptions.setWorker(true);
        int procs = Runtime.getRuntime().availableProcessors();

        vertx.deployVerticle(TrungHttpServer.class.getName(), deploymentOptions.setInstances(procs*2), event -> {
                    if (event.succeeded()) {
                        DebugLogger.info("Your Vert.x application is started!");
                    } else {
                        DebugLogger.info("Unable to start your application {}", event.cause());
                    }
                });
    }
}