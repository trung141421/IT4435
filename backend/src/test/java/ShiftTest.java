import org.apache.log4j.xml.DOMConfigurator;
import trung.room.services.logic.ShiftCached;
import trung.util.json.GsonUtil;
import trung.util.log.DebugLogger;
import trung.util.sql.HikariClients;

public class ShiftTest {
    public static void main(String[] args){
        try {
            DOMConfigurator.configure("config/log4j.xml");
            HikariClients.instance().init();
            ShiftCached.instance();
            DebugLogger.info("{}", GsonUtil.toBeautifulJsonStringGson(ShiftCached.instance().shifts));
        }catch (Exception e){

        }
    }
}
