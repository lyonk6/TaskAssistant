package api.v1.taskList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import api.v1.TaskListRequestHandler;
import api.v1.helper.InsecurityHelper;
import api.v1.model.Task;
import com.google.appengine.repackaged.com.google.gson.Gson;
import org.json.simple.JSONObject;
import api.v1.error.BusinessException;
import api.v1.error.SystemException;
import api.v1.helper.ErrorHelper;
import java.io.IOException;
import java.util.ArrayList;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import api.v1.model.TaskList;

/**
 * This api is used to retrieve a given taskList. Use the class member
 * doGet(HttpServletRequest, HttpServletResponse) to retrieve this
 * taskList.
 *
 *  @author Ken Lyon
 */
@WebServlet("/api/v1/taskList/GetTasks")
public class GetTasks extends TaskListRequestHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetTasks.class);
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
        String listOfTasksAsJson="";
        TaskList taskList = new TaskList();
        ArrayList<Task> listOfTasks;
        int errorCode = 0;
        String json="";
        try {
            json = request.getParameter("params");
            taskList=(TaskList) getMyObject(json, taskList);
            taskList=taskListRepository.get(taskList);
            //TaskRepository takes a valid TaskList and returns an ArrayList of corresponding tasks.
            listOfTasks=taskRepository.getListOfTasks(taskList);

            listOfTasksAsJson=new Gson().toJson(listOfTasks);
        } catch (BusinessException b) {
            LOGGER.error("An error occurred while handling an GetTaskList  Request: {}.", json, b);
            errorMsg = "Error. " + b.getMessage();
            errorCode = b.getError().getCode();
            error = true;
        } catch (SystemException s) {
            LOGGER.error("An error occurred while handling an GetTaskList Request: {}.", json, s);
            errorMsg = "Error. " + s.getMessage();
            errorCode = s.getError().getCode();
            error = true;
        }
        JSONObject jsonResponse = createResponse(error, errorCode, errorMsg);
        if (!error) {
            jsonResponse.put("LIST_OF_TASKS", InsecurityHelper.encryptString(listOfTasksAsJson));
        }
        sendMessage(jsonResponse, response);
    }
}
