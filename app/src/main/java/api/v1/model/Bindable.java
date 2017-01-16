package api.v1.model;

import api.v1.error.CriticalException;
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
}
