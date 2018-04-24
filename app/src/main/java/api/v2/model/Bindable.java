package api.v2.model;

import api.v2.error.CriticalException;
import java.util.ArrayList;

/**
 * Created by kennethlyon on 01/09/17.
 */
public interface Bindable {
    void addCalendar(Calendar calendar) throws CriticalException;
    void addCategory(Category category) throws CriticalException;
    void addReminder(Reminder reminder) throws CriticalException;
    void addSchedule(Schedule schedule) throws CriticalException;
    void addTask(Task task) throws CriticalException;
    void addTaskList(TaskList taskList) throws CriticalException;
    void addTimeBlock(TimeBlock timeBlock) throws CriticalException;

    void setCalendarIds(ArrayList<Integer> calendars) throws CriticalException;
    void setCategoryIds(ArrayList<Integer> categories) throws CriticalException;
    void setReminderIds(ArrayList<Integer> reminders) throws CriticalException;
    void setScheduleIds(ArrayList<Integer> schedules) throws CriticalException;
    void setTaskIds(ArrayList<Integer> tasks) throws CriticalException;
    void setTaskListIds(ArrayList<Integer> taskLists) throws CriticalException;
    void setTimeBlockIds(ArrayList<Integer> timeBlocks) throws CriticalException;

}
