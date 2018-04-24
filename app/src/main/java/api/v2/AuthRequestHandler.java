package api.v2;
import api.v2.error.BusinessException;
import api.v2.error.SystemException;
import api.v2.model.*;
import api.v2.error.Error;
import java.util.ArrayList;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


/**
 * BaseAuthRequestHandler is a subclass of BaseRequestHandler and provides functionality 
 * for APIs that require Authentication.
 * @author kennethlyon
 *
 */
public class AuthRequestHandler extends BaseRequestHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthRequestHandler.class);

    /**
     * This method verifies that an email is well formed.
     * @param email
     * @throws BusinessException
     */
    public static void verifyEmailIsValid(String email) throws BusinessException{
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            LOGGER.error("Supplied email address: {} is not valid.", email);
            throw  new BusinessException("Email address: " + email + " is invalid.", Error.valueOf("INVALID_EMAIL_ERROR"));
        }
    }

    public static void verifyPasswordIsValid(String password) throws BusinessException{
        if(!password.matches("(?=^.{8,16}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+}{\":;'?/>.<,])(?!.*\\s).*$"))
            throw new BusinessException("Try another password. ", Error.valueOf("INVALID_PASSWORD_ERROR"));
    }


    /**
     * Use to validate the supplied password from a GetUser request.
     * @param fromClient
     * @param fromRepository
     * @return
     */
    public static void validatePassword(User fromClient, User fromRepository) throws BusinessException {
        if(fromClient.getPassword().equals(fromRepository.getPassword()))
            return;
        else{
            LOGGER.error(fromClient.toJson());
            LOGGER.error(fromRepository.toJson());
            throw new BusinessException("Incorrect password.", Error.valueOf("INCORRECT_PASSWORD_ERROR"));
        }
    }

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
