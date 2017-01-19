package api.v1.taskList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import api.v1.TaskListRequestHandler;
import api.v1.error.BusinessException;
import api.v1.error.CriticalException;
import api.v1.error.SystemException;
import api.v1.helper.BinderHelper;
import api.v1.model.Schedule;
import api.v1.model.TaskAssistantModel;
import api.v1.model.User;
import org.json.simple.JSONObject;
import api.v1.helper.ErrorHelper;
import java.io.IOException;
import java.util.ArrayList;

import api.v1.model.TaskList;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
/**
 * This api is used to update a given taskList. Use the class member
 * doPost(HttpServletRequest, HttpServletResponse) to update this
 * taskList.
 *
 * TODO currently this api does not update Tasks that have been added or deleted on the client side.
 *
 * @author Ken Lyon
 */
@WebServlet("/api/v1/taskList/UpdateTaskList")
public class UpdateTaskList extends TaskListRequestHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateTaskList.class);
    /**
     * Presently, this class only updates the Name and Description of a TaskList. It
     * cannot be used to delete or add Tasks.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request,
                HttpServletResponse response)throws ServletException, IOException {
        boolean error = false;
        String errorMsg = "no error";
        TaskList clientTaskList = new TaskList();
        TaskList serverTaskList;
        int errorCode = 0;
        String json="";
        try {
            json=request.getParameter("params");
            clientTaskList=(TaskList)getMyObject(json, clientTaskList);
            serverTaskList=taskListRepository.get(clientTaskList);
            // TODO Delete tasks if they have been de-referenced.
            // TODO Clean references to Schedules, then build references to Schedules.
            // Verify privileges to modify Schedules.
            verifySchedulePrivileges(clientTaskList.getUserId(), clientTaskList.getScheduleIds());

            // Clean the serverTaskList
            LOGGER.debug("**********The client TaskList {}", clientTaskList.toJson());
            LOGGER.debug("**********The server TaskList {}", serverTaskList.toJson());
            cleanReferences(serverTaskList);

            // Update objects with the new TaskList
            updateReferences(clientTaskList);

        } catch (BusinessException b) {
            LOGGER.error("An error occurred while handling an PutTaskList  Request: {}.", json, b);
            errorMsg = "Error. " + b.getMessage();
            errorCode = b.getError().getCode();
            error = true;
        } catch (SystemException s) {
            LOGGER.error("An error occurred while handling an PutTaskList Request: {}.", json, s);
            errorMsg = "Error. " + s.getMessage();
            errorCode = s.getError().getCode();
            error = true;
        } catch (CriticalException c) {
            LOGGER.error("An error occurred while handling an PutTaskList Request: {}.", json, c);
            errorMsg = "Error. " + c.getMessage();
            errorCode = c.getError().getCode();
            error = true;
        }

        JSONObject jsonResponse = createResponse(error, errorCode, errorMsg, null, TaskAssistantModel.Type.TASKLIST);
        sendMessage(jsonResponse, response);
    }

    private void updateReferences(TaskList taskList) throws BusinessException, SystemException, CriticalException {
        User user= BinderHelper.getUpdatedUser(taskList, TaskAssistantModel.Type.TASKLIST);
        ArrayList<Schedule> updatedSchedules= BinderHelper.getUpdatedSchedules(taskList, TaskAssistantModel.Type.TASKLIST);

        userRepository.update(user);
        taskListRepository.update(taskList);
        for(Schedule schedule: updatedSchedules)
            scheduleRepository.update(schedule);
    }


    private void cleanReferences(TaskList taskList) throws BusinessException, SystemException, CriticalException{
        ArrayList<Schedule> updatedSchedules=getCleanedSchedules(taskList);
        User user=getCleanedUser(taskList);
        //Commit changes to Tasks, Schedules and User:
        for(Schedule schedule: updatedSchedules)
            scheduleRepository.update(schedule);
        userRepository.update(user);
        taskListRepository.update(taskList);
    }//*/
}
