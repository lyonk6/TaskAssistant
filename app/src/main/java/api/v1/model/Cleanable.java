package api.v1.model;

import api.v1.error.CriticalException;

import java.util.ArrayList;

/**
 * Created by kennethlyon on 10/31/16.
 */

public interface Cleanable {
    enum Type {USER, TASK, TASKLIST, CALENDAR, CATEGORY, REMINDER, SCHEDULE, TIMEBLOCK};
    int getId();
    String toJson();
    ArrayList<Integer> getCategoryIds() throws CriticalException;
    ArrayList<Integer> getScheduleIds() throws CriticalException;
    ArrayList<Integer> getCalendarIds() throws CriticalException;
    ArrayList<Integer> getTaskListIds() throws CriticalException;
    ArrayList<Integer> getReminderIds() throws CriticalException;
    ArrayList<Integer> getTimeBlockIds() throws CriticalException;
    ArrayList<Integer> getTaskIds() throws CriticalException;

    // Schedulable
    // Categorizable
    // Taskable
    // TaskListable
    // Remindable


}
