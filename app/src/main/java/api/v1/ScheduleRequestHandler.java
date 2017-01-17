package api.v1;
import api.v1.error.BusinessException;
import api.v1.error.CriticalException;
import api.v1.error.SystemException;
import api.v1.helper.DereferenceHelper;
import api.v1.helper.ModelHelper;
import api.v1.helper.RepositoryHelper;
import api.v1.model.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.ArrayList;

/**
 * ScheduleRequestHandler contains, fields and methods that are common to
 * schedule APIs. All schedule APIs inherit ScheduleRequestHandler. 
 */
public class ScheduleRequestHandler extends AuthRequestHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleRequestHandler.class);

    /**
     * Fetch a User that no longer references the Schedule provided. Note
     * that this User is a deep copy and that the UserRepository has not
     * yet been updated.
     * @param schedule
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
    protected User getCleanedUser(Schedule schedule) throws BusinessException, SystemException, CriticalException{
        User user=new User();
        user.setId(schedule.getUserId());
        user=userRepository.get(user);
        DereferenceHelper.dereferenceSchedule(schedule.getId(), user);
        return user;
    }

    /**
     * Fetch an ArrayList of Categories that no longer reference this Schedule.
     * Note that these Categories are deep copies, and the Categories in the
     * repository have not yet been updated.
     *
     * @param schedule
     * @throws BusinessException
     * @throws SystemException
     */
    protected ArrayList<Category> getCleanedCategories(Schedule schedule) throws BusinessException, SystemException, CriticalException{
        ArrayList<Category> myCategories;
        myCategories = RepositoryHelper.fetchCategories(categoryRepository, schedule.getCategoryIds());
        DereferenceHelper.dereferenceSchedule(schedule.getId(), (ArrayList<Cleanable>)(ArrayList<?>) myCategories);
        return myCategories;
    }

    /**
     * Fetch an ArrayList of Tasks that no longer reference this Schedule.
     * Note that these Tasks are deep copies, and the Tasks in the repository
     * have not yet been updated.
     * @param schedule
     * @throws BusinessException
     * @throws SystemException
     */
    protected ArrayList<Task> getCleanedTasks(Schedule schedule) throws BusinessException, SystemException, CriticalException{
        ArrayList<Task> myTasks;
        myTasks = RepositoryHelper.fetchTasks(taskRepository, schedule.getTaskIds());
        DereferenceHelper.dereferenceSchedule(schedule.getId(), (ArrayList<Cleanable>)(ArrayList<?>) myTasks);
        return myTasks;
    }

    /**
     * Fetch an ArrayList of TaskLists that no longer reference this Schedule.
     * Note that these TaskLists are deep copies, and the TaskLists in the repository
     * have not yet been updated.
     * @param schedule
     * @throws BusinessException
     * @throws SystemException
     */
    protected ArrayList<TaskList> getCleanedTaskLists(Schedule schedule) throws BusinessException, SystemException, CriticalException{
        ArrayList<TaskList> myTaskLists;
        myTaskLists = RepositoryHelper.fetchTaskLists(taskListRepository, schedule.getTaskListIds());
        DereferenceHelper.dereferenceSchedule(schedule.getId(), (ArrayList<Cleanable>)(ArrayList<?>) myTaskLists);
        return myTaskLists;
    }

    /**
     * This method creates a combined array of Task ids to be added to this Schedule.
     */
    protected ArrayList<Integer> getCombinedTaskIds(Schedule schedule) throws BusinessException, SystemException{
        ArrayList<Integer> returnList=ModelHelper.copyIntegerArrayList(schedule.getTaskIds());
        if(schedule.getTaskListIds()==null)
            return returnList;
        for(Integer i: schedule.getTaskListIds()){
            TaskList tl = new TaskList();
            tl.setId(i);
            tl= taskListRepository.get(tl);
            ModelHelper.mergeIntegerArrayList(returnList, tl.getTaskIds());
        }
        return returnList;
    }
}
