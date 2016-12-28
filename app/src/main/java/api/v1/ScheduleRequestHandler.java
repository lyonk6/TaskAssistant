package api.v1;
import api.v1.error.BusinessException;
import api.v1.error.CriticalException;
import api.v1.error.SystemException;
import api.v1.error.Error;
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
     * Fetch a User that now references the Schedule provided. Note that
     * this User is a deep copy and that the UserRepository has not yet
     * been updated.
     * @param schedule
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
    protected User getUpdatedUser(Schedule schedule) throws BusinessException, SystemException{
        User user=new User();
        user.setId(schedule.getUserId());
        user=userRepository.get(user);
        user.addSchedule(schedule);
        return user;
    }

    /**
     * Fetch an ArrayList of Tasks that have had their Schedule ids updated.
     * Note that these Tasks are deep copies, and the Tasks in the repository
     * have not yet been updated.
     * @param schedule
     * @throws BusinessException
     * @throws SystemException
     */
    protected ArrayList<Task> getUpdatedTasks(Schedule schedule) throws BusinessException, SystemException{
        ArrayList<Task> myTasks = new ArrayList<Task>();
        if(schedule.getTaskIds()==null)
            return myTasks;
        for(int i: schedule.getTaskIds()) {
            Task task=new Task();
            task.setId(i);
            task=taskRepository.get(task);
            task.addSchedule(schedule);
            myTasks.add(task);
        }
        return myTasks;
    }

    /**
     * Fetch an ArrayList of Categories that have had their Schedule ids updated.
     * Note that these Categories are deep copies, and the Tasks in the repository
     * have not yet been updated.
     * @param schedule
     * @throws BusinessException
     * @throws SystemException
     */
    protected ArrayList<Category> getUpdatedCategories(Schedule schedule) throws BusinessException, SystemException{
        ArrayList<Category> myCategory = new ArrayList<Category>();
        if(schedule.getCategoryIds()==null)
            return myCategory;
        for(int i: schedule.getCategoryIds()) {
            Category category=new Category();
            category.setId(i);
            category=categoryRepository.get(category);
            category.addSchedule(schedule);
            myCategory.add(category);
        }
        return myCategory;
    }
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
        ModelHelper.dereferenceSchedule(schedule.getId(), user);
        return user;
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
        ArrayList<Cleanable> myCleanables=new ArrayList<>();
        myTasks = RepositoryHelper.fetchTasks(taskRepository, schedule.getTaskIds());
        for(Task task: myTasks)
            myCleanables.add(task);
        ModelHelper.dereferenceSchedule(schedule.getId(), myCleanables);
        return myTasks;
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
        ArrayList<Cleanable> myCleanables=new ArrayList<>();
        myCategories = RepositoryHelper.fetchCategories(categoryRepository, schedule.getCategoryIds());
        for(Category category: myCategories)
            myCleanables.add(category);
        ModelHelper.dereferenceSchedule(schedule.getId(), myCleanables);
        return myCategories;
    }
}
