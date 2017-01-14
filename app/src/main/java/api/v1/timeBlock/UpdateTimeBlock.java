package api.v1.timeBlock;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import api.v1.TimeBlockRequestHandler;

import api.v1.error.CriticalException;
import api.v1.model.TaskAssistantModel;
import org.json.simple.JSONObject;
import api.v1.error.BusinessException;
import api.v1.error.SystemException;
import api.v1.helper.ErrorHelper;
import java.io.IOException;
import api.v1.model.TimeBlock;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * This api is used to update a given timeBlock. Use the class member
 * doPut(HttpServletRequest, HttpServletResponse) to update this
 * timeBlock.
 *
 * @author Ken Lyon
 */
@WebServlet("/api/v1/timeBlock/UpdateTimeBlock")
public class UpdateTimeBlock extends TimeBlockRequestHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateTimeBlock.class);
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
        TimeBlock clientTimeBlock=new TimeBlock();
        TimeBlock serverTimeBlock;
        String json="";
        int errorCode = 0;
        try {
            json = request.getParameter("params");
            //Create and validate the client timeBlock.
            clientTimeBlock=(TimeBlock) getMyObject(json, clientTimeBlock);
            verifyScheduleExists(clientTimeBlock.getScheduleId());

            // Clean references to the current timeBlock using the serverTimeBlock:
            serverTimeBlock=timeBlockRepository.get(clientTimeBlock);

            removeReferences(serverTimeBlock);
            addTimeBlockToSchedule(clientTimeBlock);
            timeBlockRepository.update(clientTimeBlock);

        } catch (BusinessException b) {
            LOGGER.error("An error occurred while handling an PutTimeBlock  Request: {}.", json, b);
            errorMsg = "Error. " + b.getMessage();
            errorCode = b.getError().getCode();
            error = true;
        } catch (SystemException s) {
            LOGGER.error("An error occurred while handling an PutTimeBlock Request: {}.", json, s);
            errorMsg = "Error. " + s.getMessage();
            errorCode = s.getError().getCode();
            error = true;
        } catch (CriticalException c) {
            LOGGER.error("An error occurred while handling an PutTimeBlock Request: {}.", json, c);
            errorMsg = "Error. " + c.getMessage();
            errorCode = c.getError().getCode();
            error = true;
        }
        JSONObject jsonResponse = createResponse(error, errorCode, errorMsg, null, TaskAssistantModel.Type.TIMEBLOCK);
        sendMessage(jsonResponse, response);
    }
}
