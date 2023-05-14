package vn.vietdefi.util.cryptographic;

import org.apache.log4j.xml.DOMConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import vn.vietdefi.util.file.FileUtil;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.string.StringUtil;

import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.jupiter.api.Assertions.*;

public class RSATest {
    static PublicKey publicKey;
    static PrivateKey privateKey;
    @BeforeAll
    static void init(){
        DOMConfigurator.configure("config/log4j.xml");
        RSAUtil.generateKeyToFile("config/rsa/private.ppk", "config/rsa/public.pub");
        String publicKeyStr = FileUtil.getString("config/rsa/public.pub", false);
        publicKey = RSAUtil.getPublicKey(publicKeyStr);
        String privateKeyStr = FileUtil.getString("config/rsa/private.ppk", false);
        privateKey = RSAUtil.getPrivateKey(privateKeyStr);

    }

    @Test
    void encryptDecryptTest(){
        String source = StringUtil.randomString(64);
        DebugLogger.info(source);
        String encrypt = RSAUtil.encrypt(source, publicKey);
        assertTrue(encrypt != null && !source.equals(encrypt));
        String decrypt = RSAUtil.decrypt(encrypt, privateKey);
        DebugLogger.info(source);
        assertEquals(source, decrypt);
    }
}
