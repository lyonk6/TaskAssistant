package api.v2.task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import api.v2.error.CriticalException;
import api.v2.model.*;
import org.json.simple.JSONObject;
import api.v2.error.BusinessException;
import api.v2.error.SystemException;
import api.v2.AuthRequestHandler;
import java.io.IOException;
import java.util.ArrayList;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * This api is used to update an existing task.
 *
 * @author Ken Lyon
 */
@WebServlet("/api/v2/task/UpdateTask")
public class UpdateTask extends AuthRequestHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateTask.class);
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
        Task clientTask=new Task();
        Task serverTask;
        String json="";
        try {
            //Create a basic task object:
            json = request.getParameter("params");
            clientTask=(Task) getMyObject(json, clientTask);
            serverTask=(Task) modelRepository.get(clientTask);

            // 1. Deserialize the client task.
            // 2. Use auth to verify permissions.
            // 3. Create an ArrayList<TaskAssistantModel> of all affected objects.
            // 4. Commit changes to the repository.

        } catch (BusinessException b) {
            LOGGER.error("An error occurred while handling an UpdateTask Request: {}.", json, b);
            errorMsg = "Error. " + b.getMessage();
            errorCode = b.getError().getCode();
            error = true;
        } catch (SystemException s) {
            LOGGER.error("An error occurred while handling an UpdateTask Request: {}.", json, s);
            errorMsg = "Error. " + s.getMessage();
            errorCode = s.getError().getCode();
            error = true;
        }
        JSONObject jsonResponse = createResponse(error, errorCode, errorMsg, null, TaskAssistantModel.Type.TASK);
        sendMessage(jsonResponse, response);
    }
}
