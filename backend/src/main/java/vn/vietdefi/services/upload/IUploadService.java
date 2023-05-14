package vn.vietdefi.services.upload;

import com.google.gson.JsonObject;
import io.vertx.ext.web.FileUpload;

import java.util.List;

public interface IUploadService {
    JsonObject upload(List<FileUpload> listFile);

    JsonObject getFileName(JsonObject json);

    JsonObject deleteFile(JsonObject json);
}
