package api.v1.service;

import api.v1.UnitTestHelper;
import api.v1.helper.InsecurityHelper;
import api.v1.model.*;
import api.v1.repo.*;
import api.v1.task.TaskApiHelper;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import java.util.ArrayList;
import static org.junit.Assert.fail;

/**
 * This class tests the DumpRepository Class.
 * @author kennethlyon
 */
public class DumpRepositoryTester extends UnitTestHelper {
    private static Logger LOGGER = LoggerFactory.getLogger(DumpRepositoryTester.class);
    private static DumpRepository dumpRepositoryInstance;
    private static TaskRepository taskRepository;
    private static UserRepository userRepository;
    private static CategoryRepository categoryRepository;
    private static ScheduleRepository scheduleRepository;
    private static TaskListRepository taskListRepository;
    private static ArrayList<MockHttpServletRequest> validRequestList = new ArrayList<MockHttpServletRequest>();
    private static ArrayList<MockHttpServletRequest> errorRequestList = new ArrayList<MockHttpServletRequest>();
    private static ArrayList<String> sampleUsers=new ArrayList<String>();
    private static ArrayList<String> sampleTaskLists=new ArrayList<String>();
    private static ArrayList<String> sampleSchedules=new ArrayList<String>();
    private static ArrayList<String> sampleCategories=new ArrayList<String>();
    private static ArrayList<String> validTasks=new ArrayList<String>();
    private static ArrayList<String> validCredentials=new ArrayList<>();
    private static ArrayList<String> errorCredentials=new ArrayList<>();
    /**
     * First create a new Instance of AddTask and UpdateTask.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        LOGGER.info("Before test.");
        dumpRepositoryInstance = new DumpRepository();
        taskListRepository = dumpRepositoryInstance.getTaskListRepository();
        scheduleRepository = dumpRepositoryInstance.getScheduleRepository();
        categoryRepository = dumpRepositoryInstance.getCategoryRepository();
        taskRepository = dumpRepositoryInstance.getTaskRepository();
        userRepository = dumpRepositoryInstance.getUserRepository();

        sampleTaskLists.add("0`0`Mike's TaskList.`This is Mike's  TaskList.`[0,1,2,3]");
        sampleTaskLists.add("1`1`Ken's TaskList 1`This is Kenny's TaskList.`[4,5,6,7]");
        sampleTaskLists.add("2`1`Ken's TaskList 2`This is Kens other TaskList.`[]");
        for (TaskList taskList : toTaskLists(sampleTaskLists))
            taskListRepository.add(taskList);

        sampleUsers.add(     "0`mikehedden@gmail.com`a681wo$dKo` [1,2,3,5,8] ` [10,20,30,40,50]` [11,22,33,44,55]` [2,1,0]");
        sampleUsers.add(       "1`kenlyon@gmail.com`Mouwkl87%qo` [2,1,3,4,7] ` [20,30,40,50,60]` [95,96,97,98,99]` [0,1,2]");
        sampleUsers.add(           "2`kenlyon@test.com`e-W^2VmQ` [0,1,2,3,5] ` [100,101,102,103]`[11,22,33,44,55]` [0,1,2]");
        sampleUsers.add(        "3`fatsteaks@gmail.com`+%D5|x%b` [9,8,7,6,5] ` [40,50,60,70,80]` [11,22,33,44,55]` [0,1,2]");
        sampleUsers.add(      "4`yannisgreek@gmail.com`e-W^2VmQ` [1,2,3,5,8] ` [10,20,30,40,50]` [11,22,33,44,55]` [0,1,2]");
        sampleUsers.add(    "5`rustypuppy123@gmail.com`3Z^V$xkE` [2,1,3,4,7] ` [20,30,40,50,60]` [11,22,33,44,55]` [0,1,2]");
        sampleUsers.add(  "0`yo.momma.so.fat@gmail.com`6PnCK/?8` [0,1,2,3,5] ` [30,40,50,60,70]` [11,22,33,44,55]` [0,1,2]");
        sampleUsers.add("1`under_scores_rule@gmail.com`6~Zas2R*` [0,2,1,3,4] ` [40,50,60,70,80]` [11,22,33,44,55]` [0,1,2]");
        sampleUsers.add(  "2`test@mikehedden.gmail.com`i2@<uMtJ` [1,2,3,5,8] ` [10,20,30,40,50]` [11,22,33,44,55]` [0,1,2]");
        for (User user : TaskApiHelper.toUsers(sampleUsers))
            userRepository.add(user);

        /*
        sampleSchedules.add("0`0`2016-06-28T18:00:00.123Z`2016-06-28T19:00:00.123Z`DAILY  `[0,3]      ");
        sampleSchedules.add("1`0`2016-07-03T09:00:00.123Z`2016-06-28T10:00:00.123Z`WEEKLY `[0,1,2]    ");
        sampleSchedules.add("2`0`2016-06-28T09:00:00.123Z`2016-06-28T17:00:00.123Z`DAILY  `[0,1,2,3]  ");
        sampleSchedules.add("3`1`2016-06-30T18:00:00.123Z`2016-06-28T19:00:00.123Z`WEEKLY `[4]        ");
        sampleSchedules.add("4`1`2016-07-03T16:00:00.123Z`2016-07-03T15:00:00.123Z`WEEKLY `[4]        ");
        sampleSchedules.add("5`1`2016-07-03T16:00:00.123Z`2016-07-01T15:00:00.123Z`WEEKLY `[4]        ");
        for (Schedule schedule : toSchedules(sampleSchedules))
            scheduleRepository.add(schedule);//*/

        sampleCategories.add("0`0`Mikes work`This is for all of the work Mike does        `[0,3]");
        sampleCategories.add("1`0`Mikes home`This is for all of the chores Mike never does`[1]  ");
        sampleCategories.add("2`0`Mikes play`This is for Mike's recreational stuff        `[2]  ");
        sampleCategories.add("3`1`Ken's work`This is for all of the work Ken never does.  `[7,4]");
        sampleCategories.add("4`1`ken's home`This is for all of the chores Ken does.      `[5]  ");
        sampleCategories.add("5`1`Ken's play`This is for the recreational stuff Ken does. `[6]  ");
        for (Category category : toCategories(sampleCategories))
            categoryRepository.add(category);

        // Category`Schedule
        validTasks.add("0`0`Mike's work task 01`TRUE`This task belongs to Mike H.`60000`100000`TRUE`2020-05-31T00:00:00.123Z`NEW`[0]`[0,1,2]");
        validTasks.add("1`0`Mike's work task 02`TRUE`This task belongs to Mike H.`60000`100000`TRUE`2020-05-31T00:00:00.123Z`NEW`[1]`[1,2]");
        validTasks.add("2`0`Mike's home task 01`TRUE`This task belongs to Mike H.`60000`100000`TRUE`2020-05-31T00:00:00.123Z`NEW`[2]`[1,2]");
        validTasks.add("3`0`Mike's home task 02`TRUE`This task belongs to Mike H.`60000`100000`TRUE`2020-05-31T00:00:00.123Z`NEW`[0]`[2,0]");
        validTasks.add("4`1`Ken's  work task 01`TRUE`This task belongs to  Kenny.`60000`100000`TRUE`2020-05-31T00:00:00.123Z`NEW`[3]`[3,4,5]");
        validTasks.add("5`1`Ken's  work task 02`TRUE`This task belongs to  Kenny.`60000`100000`TRUE`2020-05-31T00:00:00.123Z`NEW`[4]`[]");
        validTasks.add("6`1`Ken's  home task 01`TRUE`This task belongs to  Kenny.`60000`100000`TRUE`2020-05-31T00:00:00.123Z`NEW`[5]`[]");
        validTasks.add("7`1`Ken's  home task 02`TRUE`This task belongs to  Kenny.`60000`100000`TRUE`2020-05-31T00:00:00.123Z`NEW`[3]`[]");
        for (Task task : toTasks(validTasks))
            taskRepository.add(task);

        validCredentials.add("0`mikehedden@gmail.com`a681wo$dKo");
        validCredentials.add(  "1`kenlyon@gmail.com`Mouwkl87%qo");

        errorCredentials.add(      "2`kenlyon@test.com`badpsswd");
        errorCredentials.add(   "3`fatsteaks@gmail.com`rongpswd");
        errorCredentials.add(  "1000`Not@ValidUser.com`rongpswd");

        // Create invalid mock tasks.
        for (JSONObject jsonObj : DumpRepositoryTester.toJSONObjects(errorCredentials))
            errorRequestList.add(createInsecuredDoPostMockRequest(jsonObj));

        // Create valid mock tasks.
        for (JSONObject jsonObj : toJSONObjects(validCredentials))
            validRequestList.add(createEncryptedDoPostMockRequest(jsonObj));
    }

    @After
    public void tearDown() throws Exception {
        LOGGER.info("After test.");
    }

    @Test
    public void doPost() throws Exception {


        for (MockHttpServletRequest request : validRequestList) {
            MockHttpServletResponse response = new MockHttpServletResponse();
            dumpRepositoryInstance.doPost(request, response);
            validateDoPostValidResponse(response);
        }

        for (MockHttpServletRequest request : errorRequestList) {
            MockHttpServletResponse response = new MockHttpServletResponse();
            dumpRepositoryInstance.doPost(request, response);
            validateDoPostErrorResponse(response);
        }//*/

    }


    /**
     * Pass this method a json object to return a MockHttpServletRequest.
     * @param jsonObj
     * @return MockHttpServletRequest
     */
    protected MockHttpServletRequest createEncryptedDoPostMockRequest(JSONObject jsonObj){
        MockHttpServletRequest request = new MockHttpServletRequest();
        LOGGER.info("Here is the credential as it exists before encryption: {}", jsonObj.toJSONString());
        String credential= InsecurityHelper.encryptString((jsonObj.toJSONString()));
        LOGGER.info("Here is the credential as it exists after encryption: {}", credential);
        LOGGER.info("Created request {}", credential);
        request.addParameter("credentials", credential);
        return request;
    }


    /**
     * Pass this method a json object to return a MockHttpServletRequest.
     * @param jsonObj
     * @return MockHttpServletRequest
     */
    protected MockHttpServletRequest createInsecuredDoPostMockRequest(JSONObject jsonObj){
        MockHttpServletRequest request = new MockHttpServletRequest();
        String credential= (jsonObj.toJSONString());
        LOGGER.info("Created request {}", credential);
        request.addParameter("credentials", jsonObj.toJSONString());
        return request;
    }


    /**
     * Accept an ArrayList of backtick delimited strings and return an ArrayList of JSONObjects.
     * @param backtickUsers
     * @return
     * @throws Exception
     */
    private static ArrayList<JSONObject> toJSONObjects(ArrayList<String> backtickUsers) throws Exception{
        ArrayList<JSONObject> myJSONObjects = new ArrayList<JSONObject>();
        for(String s: backtickUsers) {
            String[] categoryElementArray = s.split("`");
            JSONObject jsonObj = new JSONObject();

            jsonObj.put("id", Integer.parseInt(categoryElementArray[0]));
            jsonObj.put("email", categoryElementArray[1].trim());
            jsonObj.put("password", categoryElementArray[2].trim());
            if(categoryElementArray.length>3){
                jsonObj.put("calendarIds", toIntegerArrayList(categoryElementArray[3]));
                jsonObj.put("categoryIds", toIntegerArrayList(categoryElementArray[4]));
                jsonObj.put("scheduleIds", toIntegerArrayList(categoryElementArray[5]));
                jsonObj.put("taskListIds", toIntegerArrayList(categoryElementArray[6]));
            }
            myJSONObjects.add(jsonObj);
        }
        return myJSONObjects;
    }
}
