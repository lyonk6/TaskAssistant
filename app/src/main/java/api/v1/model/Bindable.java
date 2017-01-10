package api.v1.model;

import api.v1.error.CriticalException;
import java.util.ArrayList;

/**
 * Created by kennethlyon on 01/09/17.
 */
public interface Bindable {
    public void addCalendar(Calendar calendar) throws CriticalException;
    public void addCategory(Category category) throws CriticalException;
    public void addReminder(Reminder reminder) throws CriticalException;
    public void addSchedule(Schedule schedule) throws CriticalException;
    public void addTask(Task task) throws CriticalException;
    public void addTaskList(TaskList taskList) throws CriticalException;
    public void addTimeBlock(TimeBlock timeBlock) throws CriticalException; 
}
