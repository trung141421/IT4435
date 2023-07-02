package trung.util.file;

import com.google.gson.JsonObject;
import trung.util.json.GsonUtil;

import java.io.*;

public class FileUtil {
    public static String getString(String filePath, boolean relative) {
        String path = "";
        if(relative){
            path = System.getProperty("user.dir");
        }
        File file = new File(path + filePath);
        StringBuffer contents = new StringBuffer();
        BufferedReader reader = null;
        try {
            Reader r = new InputStreamReader( new FileInputStream(file),"UTF-8");
            reader = new BufferedReader(r);
            String text = null;
            while ((text = reader.readLine()) != null) {
                contents.append(text).append(System.getProperty("line.separator"));
            }
            return contents.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static JsonObject getJsonObject(String filePath, boolean relative) {
        String data = getString(filePath, relative);
        return GsonUtil.toJsonObject(data);
    }
}
