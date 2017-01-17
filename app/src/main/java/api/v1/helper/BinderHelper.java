package api.v1.helper;

import api.v1.BaseRequestHandler;
import api.v1.error.BusinessException;
import api.v1.error.CriticalException;
import api.v1.error.SystemException;
import api.v1.error.Error;
import api.v1.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * This class is used to bind object IDs to a given TaskAssistantModel object.
 * Created by kennethlyon on 1/16/17.
 */
public class BinderHelper extends BaseRequestHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(BinderHelper.class);

    public static void bindObjects(TaskAssistantModel object, TaskAssistantModel.Type type, ArrayList<Bindable> bindableItems)  throws CriticalException {
     for(Bindable b: bindableItems)
        bindIndividual(object, type, b);
    }

    /**
     * Return an appropriate Object for the provided TaskAssistantModel Object.
     */

    public static void bindIndividual(TaskAssistantModel object, TaskAssistantModel.Type type, Bindable bindableItem) throws CriticalException {
            switch (type) {
                case TASK:
                    bindableItem.addTask((Task) object);
                    break;

                case TASKLIST:
                    bindableItem.addTaskList((TaskList) object);
                    break;

                case CALENDAR:
                    bindableItem.addCalendar((Calendar) object);
                    break;

                case CATEGORY:
                    bindableItem.addCategory((Category) object);
                    break;

                case REMINDER:
                    bindableItem.addReminder((Reminder) object);
                    break;

                case SCHEDULE:
                    bindableItem.addSchedule((Schedule) object);
                    break;

                case USER:
                    //TODO what should happen here? Do we need this use case?
                    throw new CriticalException("You cannot use this method to set Users!", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
            }
    }


    /**
     * Fetch an ArrayList of Categories that have had their reference ids updated. Note
     * that these Categories are deep copies, and the Categories in the repository have
     * not yet been updated.
     *
     * @param object
     * @param type
     * @return
     * @throws BusinessException
     * @throws SystemException
     * @throws CriticalException
     */
    public static ArrayList<Category> getUpdatedCategories(TaskAssistantModel object, TaskAssistantModel.Type type)
            throws BusinessException, SystemException, CriticalException {
        ArrayList<Category> myCategories;
        myCategories= RepositoryHelper.fetchCategories(categoryRepository, object.getCategoryIds());
        bindObjects(object, type, (ArrayList<Bindable>)(ArrayList<?>) myCategories);
        return myCategories;
    }

    /**
     * Fetch an ArrayList of Tasks that have had their reference ids updated. Note
     * that these Tasks are deep copies, and the Tasks in the repository Have not
     * yet been updated.
     *
     * @param object
     * @param type
     * @return
     * @throws BusinessException
     * @throws SystemException
     * @throws CriticalException
     */
    public static ArrayList<Task> getUpdatedTasks(TaskAssistantModel object, TaskAssistantModel.Type type)
            throws BusinessException, SystemException, CriticalException {
        ArrayList<Task> myTasks;
        myTasks= RepositoryHelper.fetchTasks(taskRepository, object.getTaskIds());
        bindObjects(object, type, (ArrayList<Bindable>)(ArrayList<?>) myTasks);
        return myTasks;
    }

    /**
     * Fetch an ArrayList of Schedules that have had their reference ids updated. Note
     * that these Schedules are deep copies, and the Schedules in the repository have
     * not yet been updated.
     *
     * @param object
     * @param type
     * @return
     * @throws BusinessException
     * @throws SystemException
     * @throws CriticalException
     */
    public static ArrayList<Schedule> getUpdatedSchedules(TaskAssistantModel object, TaskAssistantModel.Type type)
            throws BusinessException, SystemException, CriticalException {
        ArrayList<Schedule> mySchedules;
        mySchedules= RepositoryHelper.fetchSchedules(scheduleRepository, object.getScheduleIds());
        bindObjects(object, type, (ArrayList<Bindable>)(ArrayList<?>) mySchedules);
        return mySchedules;
    }

    /**
     * Fetch an ArrayList of TaskLists that have had their reference ids updated. Note
     * that these TaskLists are deep copies, and the TaskLists in the repository have
     * not yet been updated.
     *
     * @param object
     * @param type
     * @return
     * @throws BusinessException
     * @throws SystemException
     * @throws CriticalException
     */
    public static ArrayList<TaskList> getUpdatedTaskLists(TaskAssistantModel object, TaskAssistantModel.Type type)
            throws BusinessException, SystemException, CriticalException {
        ArrayList<TaskList> myTaskLists;
        myTaskLists= RepositoryHelper.fetchTaskLists(taskListRepository, object.getTaskListIds());
        bindObjects(object, type, (ArrayList<Bindable>)(ArrayList<?>) myTaskLists);
        return myTaskLists;
    }

    /**
     * Fetch a User that now references the model object provided. Note that this
     * User is a deep copy and that the UserRepository has not yet been updated.
     *
     * @param object
     * @param type
     * @return
     * @throws BusinessException
     * @throws SystemException
     * @throws CriticalException
     */
    public static User getUpdatedUser(TaskAssistantModel object, TaskAssistantModel.Type type)
            throws BusinessException, SystemException, CriticalException {
        User user=new User();
        user.setId(object.getParent());
        if(type!= TaskAssistantModel.Type.SCHEDULE && type!= TaskAssistantModel.Type.TASKLIST && type!= TaskAssistantModel.Type.CATEGORY)
            throw new CriticalException("The Type: " + type + " does not directly reference a User!", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
        user=userRepository.get(user);
        bindIndividual(object, type, (Bindable) user);
        return user;
    }



    /**
     * Fetch a TaskList that now references the model object provided. Note that this
     * TaskList is a deep copy and that the TaskListRepository has not yet been updated.
     *
     * @param object
     * @param type
     * @return
     * @throws BusinessException
     * @throws SystemException
     * @throws CriticalException
     */
    public static TaskList getUpdatedTaskList(TaskAssistantModel object, TaskAssistantModel.Type type)
            throws BusinessException, SystemException, CriticalException {
        TaskList taskList=new TaskList();
        taskList.setId(object.getParent());
        if(type!= TaskAssistantModel.Type.TASK)
            throw new CriticalException("The Type: " + type + " does not directly reference a TaskList!", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
        taskList=taskListRepository.get(taskList);
        bindIndividual(object, type, (Bindable) taskList);
        return taskList;
    }


    /**
     * Fetch a Schedule that now references the model object provided. Note that this
     * TaskList is a deep copy and that the ScheduleRepository has not yet been updated.
     *
     * @param object
     * @param type
     * @return
     * @throws BusinessException
     * @throws SystemException
     * @throws CriticalException
     */
    public static Schedule getUpdatedSchedule(TaskAssistantModel object, TaskAssistantModel.Type type)
            throws BusinessException, SystemException, CriticalException {
        Schedule schedule=new Schedule();
        schedule.setId(object.getParent());
        if(type!= TaskAssistantModel.Type.TIMEBLOCK)
            throw new CriticalException("The Type: " + type + " does not directly reference a Schedule!", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
        schedule=scheduleRepository.get(schedule);
        bindIndividual(object, type, (Bindable) schedule);
        return schedule;
    }

}
