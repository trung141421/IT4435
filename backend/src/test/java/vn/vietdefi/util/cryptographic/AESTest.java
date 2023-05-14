package vn.vietdefi.util.cryptographic;

import org.apache.log4j.xml.DOMConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.string.StringUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AESTest {
    @BeforeAll
    static void init(){
        DOMConfigurator.configure("config/log4j.xml");
    }

    @Test
    void encryptDecryptTest(){
        try {
            String plainText = StringUtil.randomString(64);
            DebugLogger.info(plainText);
            String password = StringUtil.randomString(64);
            String cipherText = AESUtil.encrypt(plainText, password);
            String decryptedText = AESUtil.decrypt(cipherText, password);
            DebugLogger.info(decryptedText);
            assertEquals(plainText, decryptedText);
        }catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            assertEquals(1, 0);
        }
    }
}
