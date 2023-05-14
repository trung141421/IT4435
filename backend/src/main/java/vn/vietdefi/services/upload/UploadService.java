package vn.vietdefi.services.upload;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.jsonwebtoken.Header;
import io.vertx.ext.web.FileUpload;
import org.apache.commons.lang3.exception.ExceptionUtils;
import vn.vietdefi.common.BaseResponse;
import vn.vietdefi.util.log.DebugLogger;
import vn.vietdefi.util.sql.HikariClients;
import vn.vietdefi.util.sql.SQLJavaBridge;
import vn.vietdefi.util.string.StringUtil;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

public class UploadService implements IUploadService{

    @Override
    public JsonObject upload(List<FileUpload> listFile) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "INSERT INTO upload (url, filename) VALUES (?,?)";
            JsonArray res = new JsonArray();
            for (FileUpload f: listFile) {
                String filename = f.uploadedFileName();
                filename = filename.replace("file-uploads/","");
                String url = StringUtil.generateRandomStringNumberCharacter(64);
                bridge.update(query, url, filename);
                res.add(url);
            }
            return BaseResponse.createFullMessageResponse(0,"success", res);
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject getFileName(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT filename FROM upload WHERE url = ?";
            String url = json.get("url").getAsString();
            JsonObject data = bridge.queryOne(query, url);
            return BaseResponse.createFullMessageResponse(0,"success", data);
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject deleteFile(JsonObject json) {
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String url = json.get("url").getAsString();
            String query = "SELECT filename FROM upload WHERE url = ? ";
            String filename = bridge.queryOne(query, url).get("filename").getAsString();
            File file = new File("file-uploads/" + filename);
            file.delete();
            query = "DELETE FROM upload WHERE url = ?";
            bridge.update(query, url);
            return BaseResponse.createFullMessageResponse(0,"success");
        }
        catch (Exception e){
            String stacktrace = ExceptionUtils.getStackTrace(e);
            DebugLogger.error(stacktrace);
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }
}
