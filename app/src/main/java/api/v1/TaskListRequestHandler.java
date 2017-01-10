package api.v1;


import api.v1.error.BusinessException;
import api.v1.error.CriticalException;
import api.v1.error.SystemException;
import api.v1.helper.DereferenceHelper;
import api.v1.helper.RepositoryHelper;
import api.v1.model.*;

import java.util.ArrayList;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * TaskListRequestHandler contains, fields and methods that are common to
 * calendar APIs. All calendar APIs inherit TaskListRequestHandler. 
 */
public class TaskListRequestHandler extends TaskRequestHandler {
    private static Logger LOGGER = LoggerFactory.getLogger(TaskListRequestHandler.class);
    /**
     * Remove references to these tasks from Categories and Schedules. Delete
     * these tasks and their reminders.
     * @param taskIds
     */
    protected void cleanTasks(ArrayList<Integer> taskIds) throws BusinessException, CriticalException, SystemException {
        for(Integer i: taskIds) {
            Task task=new Task();
            task.setId(i);
            task=taskRepository.get(task);
            ArrayList<Category> updatedCategories = getCleanedCategories(task);
            ArrayList<Schedule> updatedSchedules = getCleanedSchedules(task);
            ArrayList<Reminder> updatedReminders = RepositoryHelper.fetchReminders(reminderRepository, task.getReminderIds());
            for(Schedule schedule: updatedSchedules)
                scheduleRepository.update(schedule);
            for(Category category: updatedCategories)
                categoryRepository.update(category);
            for(Reminder reminder: updatedReminders) {
                //LOGGER.debug("cleanTasks: Here is a reminder we are about to delete {} ", reminder.toJson());
                reminderRepository.delete(reminder);
            }
            taskRepository.delete(task);
        }
    }

    /**
     * Fetch an ArrayList of Schedules that no longer reference this ScheduleList.
     * Note that these Schedules are deep copies, and the Schedules in the repository
     * have not yet been updated.
     * @param taskList
     * @throws BusinessException
     * @throws SystemException
     */
    protected ArrayList<Schedule> getCleanedSchedules(TaskList taskList) throws BusinessException, SystemException, CriticalException{
        ArrayList<Schedule> mySchedules;
        ArrayList<Cleanable> myCleanables=new ArrayList<>();
        mySchedules = RepositoryHelper.fetchSchedules(scheduleRepository, taskList.getScheduleIds());
        for(Schedule schedule: mySchedules)
            myCleanables.add(schedule);
        DereferenceHelper.dereferenceTaskList(taskList.getId(), myCleanables);
        return mySchedules;
    }

}
