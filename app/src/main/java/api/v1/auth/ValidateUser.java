package api.v1.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import api.v1.error.BusinessException;
import api.v1.error.SystemException;
import api.v1.helper.CreateUserHelper;
import api.v1.helper.ErrorHelper;
import api.v1.model.TaskAssistantModel;
import com.google.appengine.repackaged.com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import api.v1.AuthRequestHandler;
import api.v1.model.User;

/**
 * ValidateUser accepts a User login and determines if the User is a 
 * valid user.
 * @author kennethlyon
 *
 */
@WebServlet("/api/v1/auth/ValidateUser")
public class ValidateUser extends AuthRequestHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateUser.class);
    /**
     * POST
     * request 
     *   email
     *   password
     * response
     *   success
     *   error
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)throws ServletException, IOException {
        //First get the email and password.
        boolean error=false;
        int errorCode=1;
        String errorMsg = "no error";
        String json="";
        User clientUser=new User();
        User serverUser=null;
        Gson gson=new Gson();
        try{
            json=request.getParameter("params");
            clientUser = (User) getMyObject(json, clientUser);
            CreateUserHelper.verifyEmailIsValid(clientUser.getEmail());
            CreateUserHelper.verifyPasswordIsValid(clientUser.getPassword());
            serverUser=userRepository.get(clientUser);
            CreateUserHelper.validatePassword(clientUser, serverUser);
            serverUser.setPassword(null);
        }catch(BusinessException e){
            LOGGER.error("An error occurred while handling a ValidateUser Request: {}.", json, e);
            LOGGER.error(e.getMessage());
            errorMsg = "Error " + e.getMessage();
            errorCode = e.getError().getCode();
            error = true;
        }catch(SystemException s){
            LOGGER.error("An error occurred while handling a ValidateUser Request: {}.", json, s);
            errorMsg = "Error " + s.getMessage();
            errorCode = s.getError().getCode();
            error = true;
        }
        JSONObject jsonResponse = createResponse(error, errorCode, errorMsg, serverUser, TaskAssistantModel.Type.USER);
        sendMessage(jsonResponse, response);
    }
}