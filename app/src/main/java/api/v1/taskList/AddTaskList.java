package api.v1.taskList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import api.v1.TaskListRequestHandler;
import api.v1.error.CriticalException;
import api.v1.helper.BinderHelper;
import api.v1.model.Schedule;
import api.v1.model.TaskAssistantModel;
import api.v1.model.User;
import org.json.simple.JSONObject;
import api.v1.error.BusinessException;
import api.v1.error.SystemException;
import api.v1.helper.ErrorHelper;
import java.io.IOException;
import java.util.ArrayList;

import api.v1.model.TaskList;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
/**
 * This api is used to create a new taskList. Use the class member
 * doPost(HttpServletRequest, HttpServletResponse) to create a
 * new taskList.
 *
 * @author Ken Lyon
 */
@WebServlet("/api/v1/taskList/AddTaskList")
public class AddTaskList extends TaskListRequestHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddTaskList.class);
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, 
                HttpServletResponse response)throws ServletException, IOException {
        boolean error = false;
        String errorMsg = "no error";
        TaskList taskList = new TaskList();
        int errorCode = 0;
        String json="";
        try {
            json=request.getParameter("params");
            taskList=(TaskList)getMyObject(json, taskList);
            taskList=taskListRepository.add(taskList);
            User user=new User();
            user.setId(taskList.getUserId());
            user=userRepository.get(user);
            user.addTaskList(taskList);
            userRepository.update(user);

            ArrayList<Schedule> updatedSchedules= BinderHelper.getUpdatedSchedules(taskList, TaskAssistantModel.Type.TASKLIST);
            verifySchedulePrivileges(taskList.getUserId(), taskList.getScheduleIds());
            for(Schedule schedule: updatedSchedules)
                scheduleRepository.update(schedule);
        } catch (BusinessException b) {
            LOGGER.error("An error occurred while handling an AddTaskList  Request: {}.", json, b);
            errorMsg = "Error. " + b.getMessage();
            errorCode = b.getError().getCode();
            error = true;
        } catch (SystemException s) {
            LOGGER.error("An error occurred while handling an AddTaskList Request: {}.", json, s);
            errorMsg = "Error. " + s.getMessage();
            errorCode = s.getError().getCode();
            error = true;
        } catch (CriticalException c) {
            LOGGER.error("An error occurred while handling an AddTaskList Request: {}.", json, c);
            errorMsg = "Error. " + c.getMessage();
            errorCode = c.getError().getCode();
            error = true;
        }
        JSONObject jsonResponse = createResponse(error, errorCode, errorMsg, taskList, TaskAssistantModel.Type.TASKLIST);
        if (error)
            cleanUp(taskList);
        sendMessage(jsonResponse, response);
    }

    /**
     * Here we attempt to remove a Task from the repository that
     * could not be added fully.
     * @param taskList
     */
    private void cleanUp(TaskList taskList){
        try{
            taskListRepository.delete(taskList);
        } catch (BusinessException b) {
            LOGGER.error("Could not remove this Task from the TaskRepository. ", b);
        } catch (SystemException s) {
            LOGGER.error("Could not remove this Task from the TaskRepository. ", s);
        }
    }
}
