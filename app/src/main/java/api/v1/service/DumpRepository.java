package api.v1.service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import api.v1.BaseRequestHandler;
import api.v1.error.BusinessException;
import api.v1.helper.InsecurityHelper;
import api.v1.model.User;
import com.google.appengine.repackaged.com.google.gson.Gson;
import org.json.simple.JSONObject;
import api.v1.error.SystemException;
import api.v1.helper.ErrorHelper;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import org.slf4j.Logger;

/**
 * This api is used to create a new category. Use the class member
 * doPost(HttpServletRequest, HttpServletResponse) to create a new
 * category.
 *
 * TODO Find another way to start the repository dump.
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
        User user;
        int errorCode=0;
        long timstamp=System.currentTimeMillis();
        try{
            credentials=request.getParameter("credentials");
            credentials= InsecurityHelper.decryptString(credentials);
            user= new Gson().fromJson(credentials, User.class);
            user=userRepository.get(user);
            userRepository.dump(timstamp);
            taskRepository.dump(timstamp);
            categoryRepository.dump(timstamp);
            reminderRepository.dump(timstamp);
            scheduleRepository.dump(timstamp);
            taskListRepository.dump(timstamp);
            timeBlockRepository.dump(timstamp);
            //*/
        } catch (BusinessException b) {
            LOGGER.error("Error. Failed to complete dump..", b);
            errorMsg = "Error. " + b.getMessage();
            errorCode = b.getError().getCode();
            error = true;//*/
        } catch (SystemException s) {
            LOGGER.error("Error. Failed to complete dump.", s);
            errorMsg = "Error. " + s.getMessage();
            errorCode = s.getError().getCode();
            error = true;
        }//*/
        JSONObject jsonResponse = createResponse(error, errorCode, errorMsg);
        sendMessage(jsonResponse, response);
    }
}
