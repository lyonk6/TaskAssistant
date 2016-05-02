package api.v1.auth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.json.simple.JSONObject;
import api.v1.error.BusinessException;
import api.v1.error.SystemException;
import api.v1.AuthRequestHandler;
import api.v1.helper.ErrorHelper;
import java.io.IOException;
import api.v1.model.User;


/**
 * This api is used to retrieve a given user. Use the class member
 * doGet(HttpServletRequest, HttpServletResponse) to retrieve this
 * user.
 *
 *  @author Ken Lyon
 */
@WebServlet("/api/v1/auth/GetUser")
public class GetUser extends AuthRequestHandler {

	/**
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
     */
	public void doGet(HttpServletRequest request, 
				HttpServletResponse response)throws ServletException, IOException {
		boolean error = false;
		String errorMsg = "no error";
		User user = new User();
		int errorCode = 0;
		JSONObject jsonRequest = new JSONObject();
		try {
			jsonRequest = parseRequest(request.getParameter("params"));
		/**
		 * TODO: Return an instance of this type.
		 * To successfully, return an instance of type to the client, it is necessary to
		 * first discover the type id, then a serialized version of that instance should be
		 * sent back to the client through the HttpServletResponse.
		 */

		userRepository.get(user);
		} catch (BusinessException b) {
			log.error("An error occurred while handling an GetUser  Request: {}.", jsonRequest.toJSONString(), b);
			errorMsg = "Error. " + b.getMessage();
			errorCode = b.getError().getCode();
			error = true;
		} catch (SystemException s) {
			log.error("An error occurred while handling an GetUser Request: {}.", jsonRequest.toJSONString(), s);
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
