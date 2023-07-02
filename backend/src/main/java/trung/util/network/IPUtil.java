package trung.util.network;


import io.vertx.core.http.HttpServerRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static io.netty.util.AsciiString.trim;

public class IPUtil {
    public static String readIPFromRequest(HttpServerRequest request){
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null) {
            ip = request.remoteAddress().hostAddress();
        }
        return ip;
    }



}
