package api.v1;
import api.v1.error.BusinessException;
import api.v1.error.CriticalException;
import api.v1.error.SystemException;
import api.v1.error.Error;
import api.v1.helper.DereferenceHelper;
import api.v1.helper.RepositoryHelper;
import api.v1.model.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.ArrayList;

/**
 * CategoryRequestHandler contains, fields and methods that are common to
 * category APIs. All category APIs inherit CategoryRequestHandler. 
 */
public class CategoryRequestHandler extends AuthRequestHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryRequestHandler.class);
    /**
     * Fetch a User that now references the Category provided. Note that
     * this User is a deep copy and that the UserRepository has not yet
     * been updated.
     * @param category
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
    protected User getUpdatedUser(Category category) throws BusinessException, SystemException{
        User user=new User();
        user.setId(category.getUserId());
        user=userRepository.get(user);
        user.addCategory(category);
        return user;
    }

    /**
     * Fetch an ArrayList of Tasks that have had their Category ids updated.
     * Note that these Tasks are deep copies, and the Tasks in the repository
     * have not yet been updated.
     * @param category
     * @throws BusinessException
     * @throws SystemException
     */
    protected ArrayList<Task> getUpdatedTasks(Category category) throws BusinessException, SystemException{
        ArrayList<Task> myTasks = new ArrayList<Task>();
        if(category.getTaskIds()==null)
            return myTasks;
        for(int i: category.getTaskIds()) {
            Task task=new Task();
            task.setId(i);
            task=taskRepository.get(task);
            task.addCategory(category);
            myTasks.add(task);
        }
        return myTasks;
    }

    /**
     * Fetch an ArrayList of Schedules that have had their Category ids updated.
     * Note that these Schedules are deep copies, and the Tasks in the repository
     * have not yet been updated.
     * @param category
     * @throws BusinessException
     * @throws SystemException
     */
    protected ArrayList<Schedule> getUpdatedSchedules(Category category) throws BusinessException, SystemException{
        ArrayList<Schedule> mySchedule = new ArrayList<Schedule>();
        if(category.getScheduleIds()==null)
            return mySchedule;
        for(int i: category.getScheduleIds()) {
            Schedule schedule=new Schedule();
            schedule.setId(i);
            schedule=scheduleRepository.get(schedule);
            schedule.addCategory(category);
            mySchedule.add(schedule);
        }
        return mySchedule;
    }

    /**
     * Fetch an ArrayList of Schedules that no longer reference this Category.
     * Note that these Schedules are deep copies, and the Schedules in the
     * repository have not yet been updated.
     *
     * @param category
     * @throws BusinessException
     * @throws SystemException
     */
    protected ArrayList<Schedule> getCleanedSchedules(Category category) throws BusinessException, SystemException, CriticalException{
        ArrayList<Schedule> mySchedules;
        ArrayList<Cleanable> myCleanables=new ArrayList<>();
        mySchedules = RepositoryHelper.fetchSchedules(scheduleRepository, category.getScheduleIds());
        for(Schedule schedule: mySchedules)
            myCleanables.add(schedule);
        DereferenceHelper.dereferenceCategory(category.getId(), myCleanables);
        return mySchedules;
    }//*/

    /**
     * Fetch a User that no longer references the Category provided. Note
     * that this User is a deep copy and that the UserRepository has not
     * yet been updated.
     * @param category
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
    protected User getCleanedUser(Category category) throws BusinessException, SystemException, CriticalException{
        User user=new User();
        user.setId(category.getUserId());
        user=userRepository.get(user);
        DereferenceHelper.dereferenceCategory(category.getId(), user);
        return user;
    }

    /**
     * Fetch an ArrayList of Tasks that no longer reference this Category.
     * Note that these Tasks are deep copies, and the Tasks in the repository
     * have not yet been updated.
     * @param category
     * @throws BusinessException
     * @throws SystemException
     */
    protected ArrayList<Task> getCleanedTasks(Category category) throws BusinessException, SystemException, CriticalException{
        ArrayList<Task> myTasks;
        ArrayList<Cleanable> myCleanables=new ArrayList<>();
        myTasks = RepositoryHelper.fetchTasks(taskRepository, category.getTaskIds());
        for(Task task: myTasks)
            myCleanables.add(task);
        DereferenceHelper.dereferenceCategory(category.getId(), myCleanables);
        return myTasks;
    }
}
