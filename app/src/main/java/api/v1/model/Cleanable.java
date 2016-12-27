package api.v1.model;

import api.v1.error.CriticalException;

import java.util.ArrayList;

/**
 * Created by kennethlyon on 10/31/16.
 */

public interface Cleanable {
    public enum Type {USER, TASK, TASKLIST, CALENDAR, CATEGORY, REMINDER, SCHEDULE, TIMEBLOCK};
    public int getId();
    public String toJson();
    public ArrayList<Integer> getCategoryIds() throws CriticalException;
    public ArrayList<Integer> getScheduleIds() throws CriticalException;
    public ArrayList<Integer> getCalendarIds() throws CriticalException;
    public ArrayList<Integer> getTaskListIds() throws CriticalException;
    public ArrayList<Integer> getReminderIds() throws CriticalException;
    public ArrayList<Integer> getTimeBlockIds() throws CriticalException;
    public ArrayList<Integer> getTaskIds() throws CriticalException;

    // Schedulable
    // Categorizable
    // Taskable
    // TaskListable
    // Remindable


}
