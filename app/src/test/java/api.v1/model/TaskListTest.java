package api.v1.model;

import api.v1.error.BusinessException;
import api.v1.error.Error;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This model test is the trickiest of all. As it stands, there are
 * no errors thrown form TaskList directly. However, we still need
 * to generate error and valid JSON tasks as well as complete and
 * valid tasks.
 *
 * To populate our TaskList with Tasks, we use the sample tasks in
 * the class TaskTest.
 * Created by kennethlyon on 6/9/16.
 */
public class TaskListTest {
    private static Logger LOGGER = LoggerFactory.getLogger(TaskListTest.class);
    private static ArrayList<String> validTaskListsAsJson;
    private static ArrayList<String> errorTaskListsAsJson;

    static {
        /* Add valid TaskLists.
         * To add valid tasks, create an ArrayList of valid task objects. Use 
         * getValidTestTasksAsTasks to set the ArrayListOfTasks.
         */

        validTaskListsAsJson=new ArrayList<String>();
        errorTaskListsAsJson=new ArrayList<String>();

    }

    public static ArrayList<JSONObject> getValidTestTaskListsAsJson() {
        return null;
    }

    public static ArrayList<JSONObject> getErrorTestTaskListsAsJson() {
        return null;
    }

    public static ArrayList<TaskList> getValidTestTaskListsAsTaskLists() throws Exception{
        ArrayList<TaskList> taskListArrayList = new ArrayList<TaskList>(2);

        // First create two new task lists...
        TaskList tl0=new TaskList();
        TaskList tl1=new TaskList();
        // Set their name, id and description fields:
        tl0.setId(0);
        tl1.setId(1);
        tl0.setName("TaskList 0 created from ValidTasks");
        tl1.setName("TaskList 1 created from ValidTaskUpdates");
        tl0.setDescription("This is a valid TaskList composed of Tasks from: TaskTest.getValidTestTasksAsTasks().");
        tl1.setDescription("This is a valid TaskList composed of Tasks from: TaskTest.getValidTestTasksUpdatesAsTasks().");
        // Finally assign a valid ArrayList of Tasks ...
        tl0.setTaskArrayList(TaskTest.getValidTestTasksAsTasks());
        tl1.setTaskArrayList(TaskTest.getValidTestTasksUpdatesAsTasks());

        // Then add them to the ArrayList
        taskListArrayList.add(tl0);
        taskListArrayList.add(tl1);

        return taskListArrayList;
    }

    private static TaskList toTaskList(String s) throws Exception{
        String[] taskListElementArray = s.split("`");
        TaskList taskList = new TaskList();
        taskList.setId(Integer.parseInt(taskListElementArray[0]));
        return taskList;
    }

    private static JSONObject toJson(String stringTaskList) {
        String[] taskListElementArray = stringTaskList.split("`");
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("id",         taskListElementArray[0]);
        //TODO how does one populate Json with nested objects?
        LOGGER.info("Created request {}", jsonObj.toJSONString());
        return jsonObj;
    }

    /**
     * @throws Exception
     */
    @Test
    public void setUp() throws Exception {

        /* Start by creating valid TaskLists, and converting them
         * to Json strings.
         */
        LOGGER.info("Creating valid TaskList objects.");
        ArrayList<TaskList> taskListArrayList=getValidTestTaskListsAsTaskLists();
        /* Next attempt to convert these valid TaskLists into a json string.
         * Place the resulting strings into the private static field "validTaskLists"
         */
        LOGGER.info("Writing as Json.");
        String taskListAsJson0=taskListArrayList.get(0).toJson();
        String taskListAsJson1=taskListArrayList.get(1).toJson();

        validTaskListsAsJson.add(taskListAsJson0);
        validTaskListsAsJson.add(taskListAsJson1);
        LOGGER.info(validTaskListsAsJson.get(0));
        LOGGER.info(validTaskListsAsJson.get(1));

        /* TODO implement errorTaskLists as json objects.
         *
         */

    }

    public void validateErrorTaskList(String s){
        boolean error=false;
        try{
            TaskListTest.toTaskList(s);
        }catch(Exception e){
            error=true;
            LOGGER.info("Invalid TaskList returned error. " + e.getMessage(), e);
        }
        if(!error){
            fail("Success returned for invalid TaskList: " + s);
        }
    }
}