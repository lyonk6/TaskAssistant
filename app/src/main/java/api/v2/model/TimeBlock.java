package api.v2.model;
import com.google.gson.Gson;



/**
 * This class represents a block of time on specified days of the week.
 *
 * Created by kennethlyon on 12/24/16.
 */

public class TimeBlock extends TaskAssistantModel {

    private int id;
    private long stopTime;
    private int scheduleId;
    private long startTime;
    private boolean onMonday;
    private boolean onTuesday;
    private boolean onWednesday;
    private boolean onThursday;
    private boolean onFriday;
    private boolean onSaturday;
    private boolean onSunday;
    public static final long MAX_TIME =86400000L;

    public TimeBlock(){
        this.id=-1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        if(startTime > MAX_TIME)
            this.startTime=MAX_TIME;
        else
            this.startTime = startTime;
    }

    public long getStopTime() {
        return stopTime;
    }

    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
    }

    public boolean isOnMonday() {
        return onMonday;
    }

    public void setOnMonday(boolean onMonday) {
        this.onMonday = onMonday;
    }

    public boolean isOnTuesday() {
        return onTuesday;
    }

    public void setOnTuesday(boolean onTuesday) {
        this.onTuesday = onTuesday;
    }

    public boolean isOnWednesday() {
        return onWednesday;
    }

    public void setOnWednesday(boolean onWednesday) {
        this.onWednesday = onWednesday;
    }

    public boolean isOnThursday() {
        return onThursday;
    }

    public void setOnThursday(boolean onThursday) {
        this.onThursday = onThursday;
    }

    public boolean isOnFriday() {
        return onFriday;
    }

    public void setOnFriday(boolean onFriday) {
        this.onFriday = onFriday;
    }

    public boolean isOnSaturday() {
        return onSaturday;
    }

    public void setOnSaturday(boolean onSaturday) {
        this.onSaturday = onSaturday;
    }

    public boolean isOnSunday() {
        return onSunday;
    }

    public void setOnSunday(boolean onSunday) {
        this.onSunday = onSunday;
    }

    /**
     * Create a serialized JSON String of this instance
     * using GSON.
     * @return
     */
    public String toJson(){
        Gson gson=new Gson();
        return gson.toJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeBlock timeBlock = (TimeBlock) o;

        if (id != timeBlock.id) return false;
        if (scheduleId != timeBlock.scheduleId) return false;
        if (startTime != timeBlock.startTime) return false;
        if (stopTime != timeBlock.stopTime) return false;
        if (onMonday != timeBlock.onMonday) return false;
        if (onTuesday != timeBlock.onTuesday) return false;
        if (onWednesday != timeBlock.onWednesday) return false;
        if (onThursday != timeBlock.onThursday) return false;
        if (onFriday != timeBlock.onFriday) return false;
        if (onSaturday != timeBlock.onSaturday) return false;
        return onSunday == timeBlock.onSunday;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + scheduleId;
        result = 31 * result + (int) (startTime ^ (startTime >>> 32));
        result = 31 * result + (int) (stopTime ^ (stopTime >>> 32));
        result = 31 * result + (onMonday ? 1 : 0);
        result = 31 * result + (onTuesday ? 1 : 0);
        result = 31 * result + (onWednesday ? 1 : 0);
        result = 31 * result + (onThursday ? 1 : 0);
        result = 31 * result + (onFriday ? 1 : 0);
        result = 31 * result + (onSaturday ? 1 : 0);
        result = 31 * result + (onSunday ? 1 : 0);
        return result;
    }

    @Override
    public int getParent(){
        return getScheduleId();
    }
}