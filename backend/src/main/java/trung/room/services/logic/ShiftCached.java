package trung.room.services.logic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import trung.util.sql.HikariClients;
import trung.util.sql.SQLJavaBridge;

import java.util.*;

public class ShiftCached {

    public List<Shift> shifts;

    public static ShiftCached ins = null;

    public ShiftCached(){
        try{
            SQLJavaBridge bridge = HikariClients.instance().defaulSQLJavaBridge();
            String query = "SELECT * FROM shift";
            JsonArray array = bridge.query(query);
            shifts = new LinkedList<>();
            for(int i = 0; i < array.size(); i++){
                Shift shift= new Shift();
                JsonObject data = array.get(i).getAsJsonObject();
                shift.day_of_week = data.get("day_of_week").getAsInt();
                shift.shift_index = data.get("shift_index").getAsInt();
                shift.start_seconds_of_shift = data.get("start_seconds_of_shift").getAsInt();
                shift.finish_seconds_of_shift = data.get("finish_seconds_of_shift").getAsInt();
                shift.start_check_in_time = data.get("start_check_in_time").getAsInt();
                shift.finish_check_out_time = data.get("finish_check_out_time").getAsInt();
                shifts.add(shift);
            }
            Collections.sort(shifts, (s1, s2) -> {
                if(s1.day_of_week != s2.day_of_week){
                    return s1.day_of_week - s2.day_of_week;
                }
                return s1.shift_index - s2.shift_index;
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static ShiftCached instance(){
        if(ins == null){
            ins = new ShiftCached();
        }
        return ins;
    }

    public boolean checkSchedule(long week_timestamp,
                                 JsonArray oldSchedule, JsonArray newSchedule) {

        long currentWeekTimestamp = TimeLogic.getWeekTimestamp();
        if(week_timestamp == currentWeekTimestamp){
            int currentShift = getCurrentShift();
            for(int i = 0; i < currentShift; i++){
                if(oldSchedule.get(i).getAsInt() != newSchedule.get(i).getAsInt()){
                    return false;
                }
            }
        }
        if(week_timestamp == currentWeekTimestamp + 604800){
            return true;
        }
        return false;
    }

    public int getCurrentShift() {
        Calendar cal = Calendar.getInstance();
        int today = cal.get(Calendar.DAY_OF_WEEK);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        int currentSecond = hour*3600 + minute*60 + second;
        for(int i = 0; i < shifts.size(); i++){
            Shift shift = shifts.get(i);
            if(shift.day_of_week == today){
                if(currentSecond >= shift.start_check_in_time
                        && currentSecond <= shift.finish_check_out_time){
                    return i;
                }
            }
        }
        return -1;
    }
}
