package api.v1.timeBlock;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import api.v1.TimeBlockRequestHandler;
import api.v1.error.BusinessException;
import api.v1.error.CriticalException;
import api.v1.error.SystemException;
import org.json.simple.JSONObject;
import api.v1.helper.ErrorHelper;
import java.io.IOException;
import api.v1.model.TimeBlock;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * This api is used to delete a given timeBlock. Use the class member
 * doDelete(HttpServletRequest, HttpServletResponse) to delete
 * this timeBlock.
 *
 * @author Ken Lyon
 */
@WebServlet("/api/v1/timeBlock/DeleteTimeBlock")
public class DeleteTimeBlock extends TimeBlockRequestHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteTimeBlock.class);
    /**
     * Delete a particular timeBlock. A timeBlock "id" is required to specify the 
     * timeBlock to be removed.
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
        TimeBlock timeBlock=new TimeBlock();
        String json="";
        try {
            json=request.getParameter("params");
            timeBlock=(TimeBlock) getMyObject(json, timeBlock);
            timeBlock=timeBlockRepository.get(timeBlock);
            removeReferences(timeBlock);
            timeBlockRepository.delete(timeBlock);
        } catch (BusinessException b) {
            LOGGER.error("An error occurred while handling a DeleteTimeBlock Request: {}.", json, b);
            errorMsg = "Error. " + b.getMessage();
            errorCode = b.getError().getCode();
            error = true;
        } catch (SystemException s) {
            LOGGER.error("An error occurred while handling a DeleteTimeBlock Request: {}.", json, s);
            errorMsg = "Error. " + s.getMessage();
            errorCode = s.getError().getCode();
            error = true;
        } catch (CriticalException c) {
            LOGGER.error("An error occurred while handling an PutTimeBlock Request: {}.", json, c);
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
