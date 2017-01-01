package api.v1.timeBlock;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import api.v1.TimeBlockRequestHandler;
import org.json.simple.JSONObject;

import api.v1.error.BusinessException;
import api.v1.error.SystemException;
import api.v1.helper.ErrorHelper;
import api.v1.model.TimeBlock;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 *
 * This api is used to create a new type. Use the class member
 * doPost(HttpServletRequest, HttpServletResponse) to create a
 * new type.
 *
 * Add TimeBlock allows the client to add a timeBlock for a given
 * task. A valid timeBlock must specify a task by it's task_id and
 * specify a time that the timeBlock must be made for this task.
 *
 * @author Ken Lyon
 */
@SuppressWarnings("serial")
@WebServlet("/api/v1/timeBlock/AddTimeBlock")
public class AddTimeBlock extends TimeBlockRequestHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddTimeBlock.class);
    /**
     * Post a new TimeBlock object. Request must provide task_id and timeBlock_time. 
     * Responds with success or error.
     */
    public void doPost(HttpServletRequest request, 
                HttpServletResponse response)throws ServletException, IOException {
        boolean error = false;
        String errorMsg = "no error";
        int errorCode = 0;
        TimeBlock timeBlock = new TimeBlock();
        String json="";
        try{
            json=request.getParameter("params");
            timeBlock=(TimeBlock) getMyObject(json, timeBlock);

            //Verify the existence of the tasks prior to updating anything.
            verifyScheduleExists(timeBlock.getScheduleId());
            timeBlock=timeBlockRepository.add(timeBlock);
            addTimeBlockToSchedule(timeBlock);
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
        }

        JSONObject jsonResponse = new JSONObject();
        if (error) {
            jsonResponse.put("error", ErrorHelper.createErrorJson(errorCode, errorMsg));
        } else {
            jsonResponse.put("success", true);
            jsonResponse.put("TimeBlock", timeBlock.toJson());
        }
        sendMessage(jsonResponse, response);
    }
}