package api.v1.timeBlock;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;


import api.v1.TimeBlockRequestHandler;
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
 * This api is used to retrieve a given timeBlock. Use the class member
 * doGet(HttpServletRequest, HttpServletResp`onse) to retrieve this
 * timeBlock.
 *
 *  @author Ken Lyon
 */
@WebServlet("/api/v1/timeBlock/GetTimeBlock")
public class GetTimeBlock extends TimeBlockRequestHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetTimeBlock.class);
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
        TimeBlock timeBlock = new TimeBlock();
        String json="";
        int errorCode = 0;
        try {
            json = request.getParameter("params");
            timeBlock=(TimeBlock) getMyObject(json, timeBlock);
            timeBlock=timeBlockRepository.get(timeBlock);
        } catch (BusinessException b) {
            LOGGER.error("An error occurred while handling an GetTimeBlock  Request: {}.", json, b);
            errorMsg = "Error. " + b.getMessage();
            errorCode = b.getError().getCode();
            error = true;
        } catch (SystemException s) {
            LOGGER.error("An error occurred while handling an GetTimeBlock Request: {}.", json, s);
            errorMsg = "Error. " + s.getMessage();
            errorCode = s.getError().getCode();
            error = true;
        }
        JSONObject jsonResponse = createResponse(error, errorCode, errorMsg, timeBlock, TaskAssistantModel.Type.TIMEBLOCK);
        sendMessage(jsonResponse, response);
    }
}
