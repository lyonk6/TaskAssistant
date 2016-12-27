package api.v1.model;

import api.v1.error.CriticalException;
import api.v1.error.Error;

import java.util.ArrayList;

/**
 * Created by kennethlyon on 10/31/16.
 */

public abstract class TaskAssistantModel implements Cleanable{
    public enum Type {USER, TASK, TASKLIST, CALENDAR, CATEGORY, REMINDER, SCHEDULE, TIMEBLOCK};

    // Schedulable
    // Categorizable
    // Taskable
    // TaskListable
    // Remindable

    @Override
    public ArrayList<Integer> getCategoryIds() throws CriticalException {
        throw new CriticalException("This Type cannot be assigned to Categories.", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
    }

    @Override
    public ArrayList<Integer> getScheduleIds() throws CriticalException {
        throw new CriticalException("This Type cannot be assigned to Schedules", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
    }

    @Override
    public ArrayList<Integer> getCalendarIds() throws CriticalException {
        throw new CriticalException("This Type cannot be assigned Calendars", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
    }

    @Override
    public ArrayList<Integer> getTaskListIds() throws CriticalException {
        throw new CriticalException("This Type cannot be assigned TaskLists", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
    }

    @Override
    public ArrayList<Integer> getReminderIds() throws CriticalException {
        throw new CriticalException("This Type cannot be assigned Reminders", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
    }

    @Override
    public ArrayList<Integer> getTimeBlockIds() throws CriticalException {
        throw new CriticalException("This Type cannot be assigned TimeBlocks", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
    }

    @Override
    public ArrayList<Integer> getTaskIds() throws CriticalException {
        throw new CriticalException("This Type cannot be assigned Tasks", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
    }
}
