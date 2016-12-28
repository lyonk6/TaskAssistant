package api.v1.schedule;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import api.v1.ScheduleRequestHandler;
import api.v1.error.*;
import api.v1.helper.*;
import api.v1.model.*;
import org.json.simple.JSONObject;
import api.v1.helper.ErrorHelper;
import java.io.IOException;
import java.util.ArrayList;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * This api is used to delete a given schedule. Use the class member
 * doDelete(HttpServletRequest, HttpServletResponse) to delete
 * this schedule.
 *
 * @author Ken Lyon
 */
@WebServlet("/api/v1/schedule/DeleteSchedule")
public class DeleteSchedule extends ScheduleRequestHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteSchedule.class);
    /**
     * Delete a particular schedule. A schedule "id" is required to specify the 
     * schedule to be removed.
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
        Schedule schedule=new Schedule();
        String json="";
        try {
            json = request.getParameter("params");
            schedule=(Schedule) getMyObject(json, schedule);
            schedule=scheduleRepository.get(schedule);

            ArrayList<Category> updatedCategories=getCleanedCategories(schedule);
            ArrayList<Task> updatedTasks=getCleanedTasks(schedule);
            User updatedUser=getCleanedUser(schedule);

            //Commit changes to Tasks, Categories and User:
            for(Task task: updatedTasks)
                taskRepository.update(task);
            for(Category category: updatedCategories)
                categoryRepository.update(category);
            userRepository.update(updatedUser);
            scheduleRepository.delete(schedule);
        } catch (BusinessException b) {
            LOGGER.error("An error occurred while handling a DeleteSchedule Request: {}.", json, b);
            errorMsg = "Error. " + b.getMessage();
            errorCode = b.getError().getCode();
            error = true;
        } catch (SystemException s) {
            LOGGER.error("An error occurred while handling a DeleteSchedule Request: {}.", json, s);
            errorMsg = "Error. " + s.getMessage();
            errorCode = s.getError().getCode();
            error = true;
        } catch (CriticalException c) {
            LOGGER.error("An error occurred while handling a DeleteSchedule Request: {}.", json, c);
            errorMsg = "Error. " + c.getMessage();
            errorCode = c.getError().getCode();
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
