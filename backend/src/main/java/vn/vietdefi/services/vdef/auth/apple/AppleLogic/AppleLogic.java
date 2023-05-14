package vn.vietdefi.services.vdef.auth.apple.AppleLogic;

import com.google.gson.JsonObject;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import vn.vietdefi.util.log.DebugLogger;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

public class AppleLogic {

    public static PrivateKey getPrivateKey(Path path, String algorithm) throws IOException {
        String content = new String(Files.readAllBytes(path), "utf-8");
        try {
            String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");
            KeyFactory kf = KeyFactory.getInstance(algorithm);
            return kf.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Java did not support the algorithm:" + algorithm, e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("Invalid key format");
        }
    }

    public static JsonObject formDataToJsonObject(String data){
        String[] paramSplits = data.split("&");
        String[] valueSplits;
        JsonObject json = new JsonObject();
        if (paramSplits.length > 0)
        {
            for (String param : paramSplits)
            {
                valueSplits = param.split("=");
                if (valueSplits.length > 0)
                {
                    json.addProperty(valueSplits[0],valueSplits[1]);
                }
            }
        }
        return json;
    }

    public static String generateJWT(JsonObject json, PrivateKey privateKey){
        String keyId = json.get("key_id").getAsString();
        String teamId = json.get("team_id").getAsString();
        String clientId = json.get("client_id").getAsString();
        String APPLE_APPLEID_URL = json.get("APPLE_APPLEID_URL").getAsString();
        String jwt = Jwts.builder().setHeaderParam(JwsHeader.KEY_ID, keyId)
                .setIssuer(teamId).setSubject(clientId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 5)))
                .setAudience(APPLE_APPLEID_URL)
                .signWith(privateKey, SignatureAlgorithm.ES256)
                .compact();
        return jwt;
    }

    public static String getHeaderJWT(String jwt){
        String[] jwtElement = jwt.split("\\.");
        String header = new String(Base64.getDecoder().decode(jwtElement[0]));
        return header;
    }

    public static String getClaimsJWT(String jwt){
        String[] jwtElement = jwt.split("\\.");
        String claims = new String(Base64.getDecoder().decode(jwtElement[1]));
        return claims;
    }
}
