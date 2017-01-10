package api.v1.helper;

import api.v1.error.BusinessException;
import api.v1.error.SystemException;
import api.v1.model.*;
import api.v1.repo.*;
import java.util.ArrayList;

/**
 * Created by kennethlyon on 12/27/16.
 */
public class RepositoryHelper {
    /**
     * Fetch an ArrayList of Categories from the provided repository.
     *
     * @param categoryRepository
     * @param categoryIds
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
    public static ArrayList<Category> fetchCategories(CategoryRepository categoryRepository, ArrayList<Integer> categoryIds) throws BusinessException, SystemException {
        return  (ArrayList<Category>)(ArrayList<?>) fetchObjects(categoryRepository, categoryIds, TaskAssistantModel.Type.CATEGORY);
    }

    /**
     * Fetch an ArrayList of Tasks from the provided repository.
     *
     * @param taskRepository
     * @param taskIds
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
    public static ArrayList<Task> fetchTasks(TaskRepository taskRepository, ArrayList<Integer> taskIds) throws BusinessException, SystemException {
        return (ArrayList<Task>)(ArrayList<?>) fetchObjects(taskRepository, taskIds, TaskAssistantModel.Type.TASK);
    }

    /**
     * Fetch an ArrayList of TaskLists from the provided repository.
     *
     * @param taskListRepository
     * @param taskListIds
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
    public static ArrayList<TaskList> fetchTaskLists(TaskListRepository taskListRepository, ArrayList<Integer> taskListIds) throws BusinessException, SystemException {
        return (ArrayList<TaskList>)(ArrayList<?>) fetchObjects(taskListRepository, taskListIds, TaskAssistantModel.Type.TASKLIST);
    }

    /**
     * Fetch an ArrayList of Schedules from the provided repository.
     *
     * @param scheduleRepository
     * @param scheduleIds
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
    public static ArrayList<Schedule> fetchSchedules(ScheduleRepository scheduleRepository, ArrayList<Integer> scheduleIds) throws BusinessException, SystemException {
        return (ArrayList<Schedule>)(ArrayList<?>) fetchObjects(scheduleRepository, scheduleIds, TaskAssistantModel.Type.SCHEDULE);
    }

    /**
     * Fetch an ArrayList of Reminders from the provided repository.
     *
     * @param reminderRepository
     * @param reminderIds
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
    public static ArrayList<Reminder> fetchReminders(ReminderRepository reminderRepository, ArrayList<Integer> reminderIds) throws BusinessException, SystemException {
        return (ArrayList<Reminder>)(ArrayList<?>) fetchObjects(reminderRepository, reminderIds, TaskAssistantModel.Type.REMINDER);
    }

    /**
     * Fetch an ArrayList of Calendars from the provided repository.
     *
     * @param calendarRepository
     * @param calendarIds
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
    public static ArrayList<Calendar> fetchCalendars(CalendarRepository calendarRepository, ArrayList<Integer> calendarIds) throws BusinessException, SystemException {
        ArrayList<TaskAssistantModel> modelObjects= fetchObjects(calendarRepository, calendarIds, TaskAssistantModel.Type.CALENDAR);
        return (ArrayList<Calendar>)(ArrayList<?>) modelObjects;
    }

    /**
     * Fetch an ArrayList of TimeBlocks from the provided repository.
     *
     * @param timeBlockRepository
     * @param timeBlockIds
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
    public static ArrayList<TimeBlock> fetchTimeBlocks(TimeBlockRepository timeBlockRepository, ArrayList<Integer> timeBlockIds) throws BusinessException, SystemException {
        return (ArrayList<TimeBlock>)(ArrayList<?>) fetchObjects(timeBlockRepository, timeBlockIds, TaskAssistantModel.Type.TIMEBLOCK);
    }

    /**
     * Fetch an ArrayList of Users from the provided repository.
     *
     * @param userRepository
     * @param userIds
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
    public static ArrayList<User> fetchUsers(UserRepository userRepository, ArrayList<Integer> userIds) throws BusinessException, SystemException {
        return (ArrayList<User>)(ArrayList<?>) fetchObjects(userRepository, userIds, TaskAssistantModel.Type.USER);
    }


    /**
     * Fetch an ArrayList of Objects from the provided repository. Note that these
     * Objects are deep copies and changes to these will not directly affect the
     * current model.
     *
     * @param repository
     * @param objectIds
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
    public static ArrayList<TaskAssistantModel> fetchObjects(Repository repository, ArrayList<Integer> objectIds, TaskAssistantModel.Type type) throws BusinessException, SystemException {
        ArrayList<TaskAssistantModel> myObjects = new ArrayList<>();
        if(objectIds==null)
            return myObjects;
        for(int i: objectIds) {
            TaskAssistantModel modelObject = TaskAssistantModel.createNewModelObject(type);
            modelObject.setId(i);
            modelObject= (TaskAssistantModel) repository.get(modelObject);
            myObjects.add(modelObject);
        }
        return myObjects;
    }//*/

}
