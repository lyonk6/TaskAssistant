package api.v1.task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import api.v1.error.CriticalException;
import api.v1.helper.BinderHelper;
import api.v1.model.*;
import org.json.simple.JSONObject;
import api.v1.error.BusinessException;
import api.v1.error.SystemException;
import api.v1.TaskRequestHandler;
import java.io.IOException;
import java.util.ArrayList;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * This api is used to create a new task. Use the class member
 * doPost(HttpServletRequest, HttpServletResponse) to create a
 * new task.
 *
 * @author Ken Lyon
 */
@WebServlet("/api/v1/task/AddTask")
public class AddTask extends TaskRequestHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddTask.class);
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

        int errorCode = 0;
        Task task = new Task();
        String json="";
        try {
            json=request.getParameter("params");
            task=(Task)getMyObject(json, task);

            task=taskRepository.add(task);
            // Fetch an updated TaskList.
            TaskList taskList=BinderHelper.getUpdatedTaskList(task, TaskAssistantModel.Type.TASK);
            //Verify privileges to modify Schedules and Categories.
            verifySchedulePrivileges(taskList.getUserId(), task.getScheduleIds());
            verifyCategoryPrivileges(taskList.getUserId(), task.getCategoryIds());

            ArrayList<Schedule> updatedSchedules= BinderHelper.getUpdatedSchedules(task, TaskAssistantModel.Type.TASK);
            ArrayList<Category> updatedCategories=BinderHelper.getUpdatedCategories(task, TaskAssistantModel.Type.TASK);


            taskListRepository.update(taskList);
            for(Schedule schedule: updatedSchedules)
                scheduleRepository.update(schedule);
            for(Category category: updatedCategories)
                categoryRepository.update(category);

        } catch (BusinessException b) {
            LOGGER.error("An error occurred while handling an AddTask  Request: {}.", json, b);
            errorMsg = "Error. " + b.getMessage();
            errorCode = b.getError().getCode();
            error = true;
        } catch (SystemException s) {
            LOGGER.error("An error occurred while handling an AddTask Request: {}.", json, s);
            errorMsg = "Error. " + s.getMessage();
            errorCode = s.getError().getCode();
            error = true;
        } catch (CriticalException c) {
            LOGGER.error("An error occurred while handling an AddTask Request: {}.", json, c);
            errorMsg = "Error. " + c.getMessage();
            errorCode = c.getError().getCode();
            error = true;
        }
        JSONObject jsonResponse = createResponse(error, errorCode, errorMsg, task, TaskAssistantModel.Type.TASK);
        if (error)
            cleanUp(task);
        sendMessage(jsonResponse, response);
    }

    /**
     * Here we attempt to remove a Task from the repository that
     * could not be added fully.
     * @param task
     */
    private void cleanUp(Task task){
        try{
            taskRepository.delete(task);
        } catch (BusinessException b) {
            LOGGER.error("Could not remove this Task from the TaskRepository. ", b);
        } catch (SystemException s) {
            LOGGER.error("Could not remove this Task from the TaskRepository. ", s);
        }
    }
}
