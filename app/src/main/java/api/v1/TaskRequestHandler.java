package api.v1;
import api.v1.error.BusinessException;
import api.v1.error.CriticalException;
import api.v1.error.Error;
import api.v1.error.SystemException;
import api.v1.helper.ModelHelper;
import api.v1.helper.RepositoryHelper;
import api.v1.model.*;
import com.google.appengine.repackaged.com.google.gson.Gson;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.ArrayList;

/**
 * TaskRequestHandler contains, fields and methods that are common to
 * task APIs. All task APIs inherit TaskRequestHandler. 
 */
public class TaskRequestHandler extends AuthRequestHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRequestHandler.class);


    /**
     * Fetch and update a TaskList object so that it now points to the specified
     * task.
     * @param task
     * @throws BusinessException
     * @throws SystemException
     * @throws CriticalException
     */
    protected TaskList getUpdatedTaskList(Task task) throws BusinessException, SystemException {
        TaskList taskList=new TaskList();
        taskList.setId(task.getTaskListId());
        taskList=taskListRepository.get(taskList);
        taskList.addTask(task);
        return taskList;
    }

    /**
     * Fetch an ArrayList of Schedules that have had their Task ids updated.
     * Note that these Schedules are deep copies, and the Tasks in the repository
     * have not yet been updated.
     * @param task
     * @throws BusinessException
     * @throws SystemException
     */
    protected ArrayList<Schedule> getUpdatedSchedules(Task task) throws BusinessException, SystemException{
        ArrayList<Schedule> mySchedule = new ArrayList<Schedule>();
        if(task.getScheduleIds()==null)
            return mySchedule;
        for(int i: task.getScheduleIds()) {
            Schedule schedule=new Schedule();
            schedule.setId(i);
            schedule=scheduleRepository.get(schedule);
            schedule.addTask(task);
            mySchedule.add(schedule);
        }
        return mySchedule;
    }
    /**
     * Fetch an ArrayList of Categories that have had their Task ids updated. Note
     * that these Categoroes are deep copies, and the Tasks in the repository have
     * not yet been updated.
     * @param task
     * @throws BusinessException
     * @throws SystemException
     */
    protected ArrayList<Category> getUpdatedCategories(Task task) throws BusinessException, SystemException{
        ArrayList<Category> myCategories = new ArrayList<Category>();
        if(task.getCategoryIds()==null)
            return myCategories;
        for(int i: task.getCategoryIds()) {
            Category category=new Category();
            category.setId(i);
            category=categoryRepository.get(category);
            category.addTask(task);
            myCategories.add(category);
        }
        return myCategories;
    }

    /**
     * Fetch an ArrayList of Categories that no longer reference this Task.
     * Note that these Categories are deep copies, and the Categories in the
     * repository have not yet been updated.
     *
     * @param task
     * @throws BusinessException
     * @throws SystemException
     */
    protected ArrayList<Category> getCleanedCategories(Task task) throws BusinessException, SystemException, CriticalException{
        ArrayList<Category> myCategories;
        ArrayList<Cleanable> myCleanables=new ArrayList<>();
        myCategories = RepositoryHelper.fetchCategories(categoryRepository, task.getCategoryIds());
        for(Category category: myCategories)
            myCleanables.add(category);
        ModelHelper.dereferenceTask(task.getId(), myCleanables);
        return myCategories;
    }//*/

    /**
     * Fetch an ArrayList of Schedules that no longer reference this Task.
     * Note that these Schedules are deep copies, and the Schedules in the
     * repository have not yet been updated.
     *
     * @param task
     * @throws BusinessException
     * @throws SystemException
     */
    protected ArrayList<Schedule> getCleanedSchedules(Task task) throws BusinessException, SystemException, CriticalException{
        ArrayList<Schedule> mySchedules;
        ArrayList<Cleanable> myCleanables=new ArrayList<>();
        mySchedules = RepositoryHelper.fetchSchedules(scheduleRepository, task.getScheduleIds());
        for(Schedule schedule: mySchedules)
            myCleanables.add(schedule);
        ModelHelper.dereferenceTask(task.getId(), myCleanables);
        return mySchedules;
    }//*/

    /**
     * Fetch a TaskList that no longer references the Task provided. Note
     * that this TaskList is a deep copy and that the TaskListRepository has not
     * yet been updated.
     * @param task
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
    protected TaskList getCleanedTaskList(Task task) throws BusinessException, SystemException, CriticalException{
        TaskList taskList=new TaskList();
        taskList.setId(task.getTaskListId());
        taskList=taskListRepository.get(taskList);
        ModelHelper.dereferenceTask(task.getId(), taskList);
        return taskList;
    }
}

