package api.v2.model;

import api.v2.error.CriticalException;
import java.util.ArrayList;

/**
 * Created by kennethlyon on 10/31/16.
 */
public interface Cleanable {
    int getId();
    String toJson();
    ArrayList<Integer> getTimeBlockIds() throws CriticalException;
    ArrayList<Integer> getCategoryIds() throws CriticalException;
    ArrayList<Integer> getScheduleIds() throws CriticalException;
    ArrayList<Integer> getCalendarIds() throws CriticalException;
    ArrayList<Integer> getTaskListIds() throws CriticalException;
    ArrayList<Integer> getReminderIds() throws CriticalException;
    ArrayList<Integer> getTaskIds() throws CriticalException;
}
