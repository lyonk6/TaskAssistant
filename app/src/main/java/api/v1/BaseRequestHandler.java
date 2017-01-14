package api.v1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import api.v1.helper.ErrorHelper;
import api.v1.helper.InsecurityHelper;
import api.v1.model.TaskAssistantModel;
import api.v1.repo.*;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.GsonBuilder;
import com.google.appengine.repackaged.com.google.gson.JsonSyntaxException;
import org.json.simple.JSONObject;
import api.v1.error.BusinessException;
import org.slf4j.LoggerFactory;
import api.v1.error.Error;
import org.slf4j.Logger;

/**
 * This class is used by all handlers to parse the JSONObject referred
 * to as the JSONObject.
 * @author kennethlyon
 *
 */
public class BaseRequestHandler extends HttpServlet{

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseRequestHandler.class);
    private final static String DATE_FORMAT_KEY="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    protected static TaskRepository taskRepository;
    protected static UserRepository userRepository;
    protected static TaskListRepository taskListRepository;
    protected static ReminderRepository reminderRepository;
    protected static CategoryRepository categoryRepository;
    protected static ScheduleRepository scheduleRepository;
    protected static TimeBlockRepository timeBlockRepository;

    static {
        taskRepository = new TaskRepository();
        userRepository=new UserRepository();
        taskListRepository = new TaskListRepository();
        reminderRepository = new ReminderRepository();
        categoryRepository = new CategoryRepository();
        scheduleRepository = new ScheduleRepository();
        timeBlockRepository= new TimeBlockRepository();
    }

    public static TaskRepository getTaskRepository(){ return taskRepository; }
    public static UserRepository getUserRepository(){ return userRepository; }
    public static TaskListRepository getTaskListRepository() { return taskListRepository; }
    public static ReminderRepository getReminderRepository() { return reminderRepository; }
    public static CategoryRepository getCategoryRepository() { return categoryRepository; }
    public static ScheduleRepository getScheduleRepository() { return scheduleRepository; }
    public static TimeBlockRepository getTimeBlockRepository() {
        return timeBlockRepository;
    }

    /**
     * @param response
     * @param httpResponse
     * @throws IOException
     */
    protected static void sendMessage(JSONObject response, HttpServletResponse httpResponse) throws IOException{
        PrintWriter out = httpResponse.getWriter();
        out.println(response);
    }

    /**
     *
     * @param error
     * @param errorCode
     * @param errorMsg
     * @param object
     * @param type
     * @return
     */
    protected static JSONObject createResponse(boolean error, int errorCode, String errorMsg, TaskAssistantModel object, TaskAssistantModel.Type type){
        JSONObject jsonResponse = new JSONObject();
        if (error) {
            jsonResponse.put("error", ErrorHelper.createErrorJson(errorCode, errorMsg));
        } else {
            jsonResponse.put("success", true);
            if(object!=null)
                jsonResponse.put(type.name(), InsecurityHelper.encryptString(object.toJson()));
        }
        return jsonResponse;
    }


    /**
     * Return a JSONObject with the desired response.
     * @param error
     * @param errorCode
     * @param errorMsg
     * @return
     */
    protected static JSONObject createResponse(boolean error, int errorCode, String errorMsg){
        return createResponse(error, errorCode, errorMsg, null, null);
    }

    /**
     * Accept a json String and an instance of the Object being made.  Return
     * an instance of Object created from the Json String.
     * @param obj
     * @return
     */
     protected Object getMyObject(String json, Object obj) throws BusinessException{
         LOGGER.info("Here is the Json object {} ", json);
         String message="An error occurred while deserializing the JSON object.";
         json=InsecurityHelper.decryptString(json);
         GsonBuilder gsonBuilder = new GsonBuilder();
         Gson gson = gsonBuilder.setDateFormat(DATE_FORMAT_KEY).create();
         try {
             obj = gson.fromJson(json, obj.getClass());

         }catch (NullPointerException npe){
             LOGGER.error(message, npe);
             throw new BusinessException(message, Error.valueOf("DESERIALIZATION_NULL_VALUE_ERROR"));
         }catch (NumberFormatException nfe){
             LOGGER.error(message, nfe);
             throw new BusinessException(message, Error.valueOf("DESERIALIZATION_NOT_A_NUMBER_ERROR"));
         }catch (JsonSyntaxException jse){
             LOGGER.error(message, jse);
             throw new BusinessException(message, Error.valueOf("DESERIALIZATION_JSON_SYNTAX_ERROR"));
         }
         return obj;
     }
}