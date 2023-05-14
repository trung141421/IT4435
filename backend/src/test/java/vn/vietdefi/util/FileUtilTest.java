package vn.vietdefi.util;

import org.apache.log4j.xml.DOMConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import vn.vietdefi.util.file.FileUtil;
import vn.vietdefi.util.log.DebugLogger;

public class FileUtilTest {
    @BeforeAll
    static void init(){
        DOMConfigurator.configure("config/log4j.xml");
    }

    @Test
    void testReadWriteFile(){
        String data = "abc";
        FileUtil.writeStringToFile("logs/data.pub", data);
        String x = FileUtil.getString("logs/data.pub", false);
        DebugLogger.info("{} {} {}", data, x, data.equals(x));
    }
}
