package api.v2.model;

import api.v2.error.CriticalException;
import api.v2.error.Error;

import java.util.ArrayList;

/**
 * Created by kennethlyon on 10/31/16.
 */

public abstract class TaskAssistantModel implements Cleanable, Bindable{
    public enum Type {USER, TASK, TASKLIST, CALENDAR, CATEGORY, REMINDER, SCHEDULE, TIMEBLOCK};

    public abstract int getId();
    public abstract void setId(int i);
    public abstract String toJson();
    public abstract int getParent();
    public abstract TaskAssistantModel clone();


    // Get object relational model reference arrays.
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

    // Add object to relational model reference arrays.
    @Override
    public void addCategory(Category category) throws CriticalException {
        throw new CriticalException("Categories cannot be assigned to this Type.", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
    }

    @Override
    public void addSchedule(Schedule schedule) throws CriticalException {
        throw new CriticalException("Schedules cannot be assigned to this Type.", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
    }

    @Override
    public void addCalendar(Calendar calendar) throws CriticalException {
        throw new CriticalException("Calendars cannot be assigned to this Type.", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
    }

    @Override
    public void addTaskList(TaskList taskList) throws CriticalException {
        throw new CriticalException("TaskLists cannot be assigned to this Type.", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
    }

    @Override
    public void addReminder(Reminder reminder) throws CriticalException {
        throw new CriticalException("Reminders cannot be assigned to this Type.", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
    }

    @Override
    public void addTimeBlock(TimeBlock timeBlock) throws CriticalException {
        throw new CriticalException("TimeBlocks cannot be assigned to this Type.", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
    }

    @Override
    public void addTask(Task task) throws CriticalException {
        throw new CriticalException("Tasks cannot be assigned to this Type.", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
    }

    // Add objects to relational model reference arrays.
    @Override
    public void setCategoryIds(ArrayList<Integer> categories) throws CriticalException {
        throw new CriticalException("Categories cannot be assigned to this Type.", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
    }

    @Override
    public void setScheduleIds(ArrayList<Integer> schedules) throws CriticalException {
        throw new CriticalException("Schedules cannot be assigned to this Type.", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
    }

    @Override
    public void setCalendarIds(ArrayList<Integer> calendars) throws CriticalException {
        throw new CriticalException("Calendars cannot be assigned to this Type.", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
    }

    @Override
    public void setTaskListIds(ArrayList<Integer> taskLists) throws CriticalException {
        throw new CriticalException("TaskLists cannot be assigned to this Type.", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
    }

    @Override
    public void setReminderIds(ArrayList<Integer> reminders) throws CriticalException {
        throw new CriticalException("Reminders cannot be assigned to this Type.", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
    }

    @Override
    public void setTimeBlockIds(ArrayList<Integer> timeBlocks) throws CriticalException {
        throw new CriticalException("TimeBlocks cannot be assigned to this Type.", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
    }

    @Override
    public void setTaskIds(ArrayList<Integer> tasks) throws CriticalException {
        throw new CriticalException("Tasks cannot be assigned to this Type.", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
    }
}
