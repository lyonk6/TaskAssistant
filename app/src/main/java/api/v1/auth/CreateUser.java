package api.v1.auth;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import api.v1.error.BusinessException;
import api.v1.error.SystemException;
import api.v1.helper.ErrorHelper;
import org.json.simple.JSONObject;

import api.v1.BaseAuthRequestHandler;
import api.v1.model.User;
import api.v1.repo.UserRepository;

/**
 * CreateUser responds to a request to create a new user. A JSONObject
 * instance must provide a string email and a string password.
 * @author kennethlyon
 *
 */
@WebServlet("/api/v1/auth/CreateUser")
public class CreateUser extends BaseAuthRequestHandler{
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
		User user=new User();
		int errorCode = 0;
        JSONObject jsonRequest = new JSONObject();
		try{
			jsonRequest=parseRequest(request.getParameter("params"));
			String email= parseJsonAsEmail((String)jsonRequest.get("email"));
			String password= parseJsonAsPassword((String)jsonRequest.get("password"));
			user.setEmail(email);
			user.setPassword(password);
			
			/* Internet says I am supposed to use dependency injection.
			 * Also that a static reference is the wrong way to use the 
			 * repository too. So we do this: (which makes even less sense)
			 */
			new UserRepository().add(user);
		}catch(BusinessException b) {
            log.error("An error occurred while handling a CreateUser Request: {}.", jsonRequest.toJSONString(), b);
            errorMsg = "Error. " + b.getMessage();
            errorCode = b.getError().getCode();
            error = true;
		}catch(SystemException s){
            log.error("An error occurred while handling a CreateUser Request: {}.", jsonRequest.toJSONString(), s);
            errorMsg = "Error. " + s.getMessage();
            errorCode = s.getError().getCode();
            error = true;
		}

		JSONObject jsonResponse = new JSONObject();
		if (error){
			jsonResponse.put("error", ErrorHelper.createErrorJson(errorCode, errorMsg));
		}else {
			jsonResponse.put("success", true);
		}
		sendMessage(jsonResponse, response);
	}
}