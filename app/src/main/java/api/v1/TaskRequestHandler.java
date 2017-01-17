package api.v1;
import api.v1.error.BusinessException;
import api.v1.error.CriticalException;
import api.v1.error.Error;
import api.v1.error.SystemException;
import api.v1.helper.DereferenceHelper;
import api.v1.helper.RepositoryHelper;
import api.v1.model.*;
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
        myCategories = RepositoryHelper.fetchCategories(categoryRepository, task.getCategoryIds());
        DereferenceHelper.dereferenceTask(task.getId(),(ArrayList<Cleanable>)(ArrayList<?>) myCategories);
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
        mySchedules = RepositoryHelper.fetchSchedules(scheduleRepository, task.getScheduleIds());
        DereferenceHelper.dereferenceTask(task.getId(), (ArrayList<Cleanable>)(ArrayList<?>) mySchedules);
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
        DereferenceHelper.dereferenceTask(task.getId(), taskList);
        return taskList;
    }
}

