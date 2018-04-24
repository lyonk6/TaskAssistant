package api.v2;
import api.v2.error.BusinessException;
import api.v2.error.SystemException;
import api.v2.model.*;
import api.v2.error.Error;
import java.util.ArrayList;

import com.google.gson.Gson;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


/**
 * BaseAuthRequestHandler is a subclass of BaseRequestHandler and provides functionality 
 * for APIs that require Authentication.
 * @author kennethlyon
 *
 */
public class AuthRequestHandler extends BaseRequestHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthRequestHandler.class);

    /**
     * Verify that each taskId supplied belongs to a TaskList that belongs to the
     * supplied User id.
     * @param userId
     * @param taskIds
     */
    protected void verifyTaskPrivileges(int userId, ArrayList<Integer> taskIds)
            throws BusinessException, SystemException {
        if(taskIds==null || taskIds.size()==0)
            return;
        for(Integer i: taskIds){
            // First fetch the Task specified in the taskIds list.
            Task task=new Task();
            task.setId(i);
            task=(Task) modelRepository.get(task);

            //Next fetch the TaskList that owns this Task.
            TaskList taskList=new TaskList();
            taskList.setId(task.getTaskListId());
            taskList=(TaskList)modelRepository.get(taskList);

            LOGGER.debug("TaskList owner id: " + taskList.getUserId());
            LOGGER.debug("User id: " + userId);
            //Finally, verify that ownership of the TaskList.
            if(taskList.getUserId()==userId)
                continue;
            else{
                String message= "This task cannot be accessed by the specified user. ";
                throwObjectOwnershipError(userId, message);
            }
        }
    }
    /**
     * Verify that the User with the specified ID has permission to access these
     * schedules.
     *
     * @param userId
     * @param scheduleIds
     */
    protected void verifySchedulePrivileges(int userId, ArrayList<Integer> scheduleIds) throws BusinessException, SystemException{
        //LOGGER.debug(" Here inside verifySchedulePrivileges. These are our schedule ids {}", new Gson().toJson(scheduleIds));
        if(scheduleIds==null || scheduleIds.size()==0)
            return;
        Schedule schedule=new Schedule();
        for(int i: scheduleIds) {
            schedule.setId(i);
            schedule = (Schedule) modelRepository.get(schedule);
            //LOGGER.debug("Here is the Schedule we are verifying {}", schedule.toJson());
            if (schedule.getUserId() == userId)
                continue;
            else {
                String message = "This schedule cannot be accessed by the specified user. ";
                throwObjectOwnershipError(userId, message);
            }
        }
    }

    /**
     * Verify that the User with the specified ID has permission to access these
     * taskLists.
     *
     * @param userId
     * @param taskListIds
     */
    protected void verifyTaskListPrivileges(int userId, ArrayList<Integer> taskListIds) throws BusinessException, SystemException{
        //LOGGER.debug("Here inside verifyTaskListPrivileges. These are our taskList ids {}", new Gson().toJson(taskListIds));
        if(taskListIds==null || taskListIds.size()==0)
            return;
        TaskList taskList=new TaskList();
        for(int i: taskListIds) {
            taskList.setId(i);
            taskList = (TaskList)modelRepository.get(taskList);
            //LOGGER.debug("Here is the TaskList we are verifying {}", taskList.toJson());
            if (taskList.getUserId() == userId)
                continue;
            else {
                String message = "This taskList cannot be accessed by the specified user. ";
                throwObjectOwnershipError(userId, message);
            }
        }
    }

    /**
     * Verify that the User with the specified ID has permission to access these
     * schedules.
     *
     * @param userId
     * @param categoryIds
     */
    protected void verifyCategoryPrivileges(int userId, ArrayList<Integer> categoryIds) throws BusinessException, SystemException {
        //LOGGER.debug("Here inside verifyCategoryPrivileges. These are our category ids {}", new Gson().toJson(categoryIds));
        if (categoryIds == null || categoryIds.size() == 0)
            return;
        Category category = new Category();
        for (int i : categoryIds) {
            category.setId(i);
            category = (Category) modelRepository.get(category);
            //LOGGER.debug("Here is the Category we are verifying {}", category.toJson());
            if (category.getUserId() == userId)
                continue;
            else {
                String message = "This category cannot be accessed by the specified user. ";
                throwObjectOwnershipError(userId, message);
            }
        }
    }

    private void throwObjectOwnershipError(int userId, String message) throws BusinessException, SystemException{
        User user = new User();
        user.setId(userId);
        user=(User) modelRepository.get(user);
        message+=" {id: " + userId +", email: " + user.getEmail() + "}";
        throw new BusinessException(message, Error.valueOf("OBJECT_OWNERSHIP_ERROR"));
    }

}
