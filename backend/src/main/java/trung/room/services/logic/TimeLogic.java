package trung.room.services.logic;

public class TimeLogic {

   public static long getWeekTimestamp(){
       long currentAllSeconds = System.currentTimeMillis() / 1000;
       long surplusSeconds = currentAllSeconds % 604800;
       long week_timestamp = currentAllSeconds - surplusSeconds + 4 * 86400;
       return week_timestamp;
   }
   public static int getDayOfWeek(int current_shift){
       return ShiftCached.instance().shifts.get(current_shift).day_of_week;
   }
    public static int getShiftIndex(int current_shift){
        return ShiftCached.instance().shifts.get(current_shift).shift_index;
    }
    public static long getCurrentTime(){
       return System.currentTimeMillis()/1000;
    }
    public static long getCurrentWeekTimestamp(long cs){
        long surplusSeconds = cs % 604800;
        long week_timestamp = cs - surplusSeconds + 4 * 86400;
        return week_timestamp;
    }

}
