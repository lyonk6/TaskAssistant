package api.v1.taskList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import api.v1.TaskRequestHandler;
import org.json.simple.JSONObject;
import api.v1.error.BusinessException;
import api.v1.error.SystemException;
import api.v1.helper.ErrorHelper;
import java.io.IOException;
import api.v1.model.TaskList;

/**
 * This api is used to create a new taskList. Use the class member
 * doPost(HttpServletRequest, HttpServletResponse) to create a
 * new taskList.
 *
 * @author Ken Lyon
 */
@WebServlet("/api/v1/taskList/AddTaskList")
public class AddTaskList extends TaskRequestHandler {
    /** /api/v1/user/addtasklist
     * o POST
     * ·  Request
     *    ·  user_id
     *    ·  tasklistid (can be null)
     *    ·  name (can be null)
     *    ·  description (can be null)
     *    ·  permission
     * ·  Response
     *    ·  Success
     *    ·  Tasklistid
     *    ·  error
     * 
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
        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest = parseRequest(request.getParameter("params"));

            // private String name;
            taskList.setName((String)jsonRequest.get("name"));
            taskList.setDescription((String)jsonRequest.get("description"));
            taskListRepository.add(taskList);
        } catch (BusinessException b) {
            log.error("An error occurred while handling an AddTaskList  Request: {}.", jsonRequest.toJSONString(), b);
            errorMsg = "Error. " + b.getMessage();
            errorCode = b.getError().getCode();
            error = true;
        } catch (SystemException s) {
            log.error("An error occurred while handling an AddTaskList Request: {}.", jsonRequest.toJSONString(), s);
            errorMsg = "Error. " + s.getMessage();
            errorCode = s.getError().getCode();
            error = true;
        }

        JSONObject jsonResponse = new JSONObject();
        if (error) {
            jsonResponse.put("error", ErrorHelper.createErrorJson(errorCode, errorMsg));
        } else {
            jsonResponse.put("success", true);
        }
        sendMessage(jsonResponse, response);
    }
}
