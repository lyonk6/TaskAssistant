package api.v1.service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import api.v1.BaseRequestHandler;
import org.json.simple.JSONObject;
import api.v1.error.SystemException;
import api.v1.helper.ErrorHelper;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import org.slf4j.Logger;

/**
 * This api is used to create a new category. Use the class member
 * doPost(HttpServletRequest, HttpServletResponse) to create a
 * new category.
 *
 * @author Ken Lyon
 */
@WebServlet("/api/v1/category/AddCategory")
public class DumpRepository extends BaseRequestHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DumpRepository.class);
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
        String credentials="";
        int errorCode=0;

        try{
            credentials=request.getParameter("credentials");
            long timstamp=System.currentTimeMillis();
            userRepository.dump(timstamp);
            taskRepository.dump(timstamp);
            categoryRepository.dump(timstamp);
            reminderRepository.dump(timstamp);
            scheduleRepository.dump(timstamp);
            taskListRepository.dump(timstamp);
            timeBlockRepository.dump(timstamp);

        /*} catch (BusinessException b) {
            LOGGER.error("Error. Failed to complete dump.." + b.getMessage());
            errorMsg = "Error. " + b.getMessage();
            errorCode = b.getError().getCode();
            error = true;//*/
        } catch (SystemException s) {
            LOGGER.error("Error. Failed to complete dump.."+ s.getMessage());
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
