package vn.vietdefi.launcher;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.xml.DOMConfigurator;
import vn.vietdefi.services.VdefServices;
import vn.vietdefi.services.vdef.telegram.bot.VdefTelegramBot;
import vn.vietdefi.vertx.HttpServer;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.sql.HikariClients;

public class VdefLauncher {
    public static Vertx vertx;
    public static void main(String[] args) {
        try {
            DOMConfigurator.configure("config/log4j.xml");
            VdefTelegramBot.instance().run();
            VdefServices.telegramService.sendLogMessage("VdefLauncher - starting");
            HikariClients.instance().init();
            startHttpServer();
            VdefServices.telegramService.sendLogMessage("VdefLauncher - started OK");
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            VdefServices.telegramService.sendLogMessage("VdefLauncher - started ERROR");
            VdefServices.telegramService.sendLogMessage(stacktrace);
        }
    }


    private static void startHttpServer() {

        VertxOptions vxOptions = new VertxOptions().setBlockedThreadCheckInterval(30000);
        vertx = Vertx.vertx(vxOptions);
        DeploymentOptions deploymentOptions = new DeploymentOptions();
        deploymentOptions.setWorker(true);
        int procs = Runtime.getRuntime().availableProcessors();
        vertx.deployVerticle(HttpServer.class.getName(), deploymentOptions.setInstances(procs*2), event -> {
            if (event.succeeded()) {
                DebugLogger.info("Your Vert.x application is started!");
            } else {
                DebugLogger.info("Unable to start your application {}", event.cause());
            }
        });
    }
}
