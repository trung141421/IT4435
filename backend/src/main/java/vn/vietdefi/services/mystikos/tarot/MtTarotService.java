package vn.vietdefi.services.mystikos.tarot;

import com.google.gson.JsonObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import vn.vietdefi.common.BaseResponse;

import java.io.FileInputStream;
import java.util.Iterator;

public class MtTarotService implements IMtTarotService{
    public static final String cardInfoDatabasePath = "config/mystikos/exceldatabase/CardInfoDatabase.xlsx";

    @Override
    public JsonObject dailyCard(JsonObject json) {
        try{
            return cardInfo(json);
        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject cardInfo(JsonObject json) {
        try{
            FileInputStream file = new FileInputStream(cardInfoDatabasePath);
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            String type = json.get("type").getAsString();
            Integer rowNumber = json.get("rowNumber").getAsInt();
            Sheet sheet = workbook.getSheet(type);

            Row row = sheet.getRow(rowNumber);

            Iterator<Cell> cellIterator = row.cellIterator();
            Double stt = row.getCell(0).getNumericCellValue();
            String engName = row.getCell(2).getStringCellValue();
            String vietName = row.getCell(3).getStringCellValue();
            String quote = row.getCell(4).getStringCellValue();
            String goodKeyword = row.getCell(6).getStringCellValue();
            String badKeyword = row.getCell(7).getStringCellValue();
            String description = row.getCell(8).getStringCellValue();
            JsonObject data = new JsonObject();
            data.addProperty("stt", stt);
            data.addProperty("eng_name",engName);
            data.addProperty("viet_name",vietName);
            data.addProperty("quote",quote);
            data.addProperty("good_key_word",goodKeyword);
            data.addProperty("bad_key_word",badKeyword);
            data.addProperty("description",description);
            return data;
        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }

    @Override
    public JsonObject readingCard(JsonObject json) {
        try{
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return BaseResponse.createFullMessageResponse(1,"system_error");
        }
    }
}
