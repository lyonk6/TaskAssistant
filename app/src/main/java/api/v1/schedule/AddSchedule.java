package api.v1.schedule;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import api.v1.error.CriticalException;
import api.v1.helper.BinderHelper;
import api.v1.helper.RepositoryHelper;
import api.v1.model.*;
import api.v1.repo.Repository;
import org.json.simple.JSONObject;
import api.v1.error.BusinessException;
import api.v1.error.SystemException;
import api.v1.ScheduleRequestHandler;
import api.v1.helper.ErrorHelper;
import java.io.IOException;
import java.util.ArrayList;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * This api is used to create a new schedule. Use the class member
 * doPost(HttpServletRequest, HttpServletResponse) to create a
 * new schedule.
 *
 * @author Ken Lyon
 */
@WebServlet("/api/v1/schedule/AddSchedule")
public class AddSchedule extends ScheduleRequestHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddSchedule.class);
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
        Schedule schedule = new Schedule();
        int errorCode = 0;
        String json="";
        try {
            json = request.getParameter("params");
	        schedule= (Schedule) getMyObject(json, schedule);
            // Verify privileges.
            verifyTaskPrivileges(schedule.getUserId(), schedule.getTaskIds());
            verifyCategoryPrivileges(schedule.getUserId(), schedule.getCategoryIds());
            verifyTaskListPrivileges(schedule.getUserId(), schedule.getTaskListIds());
            //Place completed category in the repository.

            // Get a ScheduleId for this Schedule.
            schedule=scheduleRepository.add(schedule);


            ArrayList<Task> tasks= RepositoryHelper.fetchTasks(taskRepository, this.getCombinedTaskIds(schedule));
            ArrayList<TaskList> taskLists=RepositoryHelper.fetchTaskLists(taskListRepository, schedule.getTaskListIds());
            ArrayList<Category> categories=RepositoryHelper.fetchCategories(categoryRepository, schedule.getCategoryIds());
            User user = getUpdatedUser(schedule);

            BinderHelper.bindObjects(schedule, (ArrayList<Bindable>)(ArrayList<?>)tasks, TaskAssistantModel.Type.TASK);
            BinderHelper.bindObjects(schedule, (ArrayList<Bindable>)(ArrayList<?>)taskLists, TaskAssistantModel.Type.TASKLIST);
            BinderHelper.bindObjects(schedule, (ArrayList<Bindable>)(ArrayList<?>)categories, TaskAssistantModel.Type.CATEGORY);


           //Commit changes to Tasks, Schedules and User:
            for(TaskList taskList: taskLists)
                taskListRepository.update(taskList);
            for(Task task: tasks)
                taskRepository.update(task);
            for(Category category: categories)
                categoryRepository.update(category);
            userRepository.update(user);

        } catch (BusinessException b) {
            LOGGER.error("An error occurred while handling an AddSchedule  Request: {}.", json, b);
            errorMsg = "Error. " + b.getMessage();
            errorCode = b.getError().getCode();
            error = true;
        } catch (SystemException s) {
            LOGGER.error("An error occurred while handling an AddSchedule Request: {}.", json, s);
            errorMsg = "Error. " + s.getMessage();
            errorCode = s.getError().getCode();
            error = true;
        }catch (CriticalException c){
            LOGGER.error("An error occurred while handling an AddSchedule Request: {}.", json, c);
            errorMsg = "Error. " + c.getMessage();
            errorCode = c.getError().getCode();
            error = true;
        }
        JSONObject jsonResponse = createResponse(error, errorCode, errorMsg, schedule, TaskAssistantModel.Type.SCHEDULE);
        if (error)
            cleanUp(schedule);
        sendMessage(jsonResponse, response);
    }


    /**
     * Here we attempt to remove a schedule from the repository that
     * could not be added fully.
     * @param schedule
     */
    private void cleanUp(Schedule schedule){
        try{
            scheduleRepository.delete(schedule);
        } catch (BusinessException b) {
            LOGGER.error("Could not remove this schedule from the scheduleRepository. ",b);
        } catch (SystemException s) {
            LOGGER.error("Could not remove this schedule from the scheduleRepository. ",s);
        }
    }
}
