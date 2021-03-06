package api.v2.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import api.v2.error.BusinessException;
import api.v2.error.SystemException;
import api.v2.model.TaskAssistantModel;
import org.json.simple.JSONObject;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import api.v2.AuthRequestHandler;
import api.v2.model.User;

/**
 * This api is used to create a new User. Use the class member
 * doPost(HttpServletRequest, HttpServletResponse) to create a
 * new User. A JSONObject must provide a string email and a
 * string password.
 *
 * @author Ken Lyon
 */
@WebServlet("/api/v2/auth/CreateUser")
public class CreateUser extends AuthRequestHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateUser.class);
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
        String errorMsg = "no error";
        String json="";
        User user=new User();
        int errorCode = 0;
        try{
            json=request.getParameter("params");
            user = (User)  getMyObject(json, user);
            verifyEmailIsValid(user.getEmail());
            verifyPasswordIsValid(user.getPassword());
            user=(User) modelRepository.add(user);
        }catch(BusinessException b) {
            LOGGER.error("An error occurred while handling a CreateUser Request: {}.", json, b);
            errorMsg = "Error. " + b.getMessage();
            errorCode = b.getError().getCode();
            error = true;
        }catch(SystemException s){
            LOGGER.error("An error occurred while handling a CreateUser Request:  {}.", json, s);
            errorMsg = "Error. " + s.getMessage();
            errorCode = s.getError().getCode();
            error = true;
        }

        JSONObject jsonResponse = createResponse(error, errorCode, errorMsg, user, TaskAssistantModel.Type.USER);
        sendMessage(jsonResponse, response);
    }
}
