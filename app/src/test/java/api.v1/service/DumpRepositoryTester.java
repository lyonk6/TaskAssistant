package api.v1.service;

import api.v1.UnitTestHelper;
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
    private static ArrayList<String> validUpdates=new ArrayList<String>();
    private static ArrayList<String> errorUpdates=new ArrayList<String>();
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

        sampleUsers.add("0`mikehedden@gmail.com`a681wo$dKo");
        sampleUsers.add("1`kenlyon@gmail.com`Mouwkl87%qo");
        for (User user : TaskApiHelper.toUsers(sampleUsers))
            userRepository.add(user);

        sampleTaskLists.add("0`0`Mike's TaskList.`This is Mike's  TaskList.`[0,1,2,3]");
        sampleTaskLists.add("1`1`Ken's TaskList 1`This is Kenny's TaskList.`[4,5,6,7]");
        sampleTaskLists.add("2`1`Ken's TaskList 2`This is Kens other TaskList.`[]");
        LOGGER.debug("Starting at the very beginning. These are the TaskLists as they are when they are put in the repository:");
        for (TaskList taskList : toTaskLists(sampleTaskLists))
            taskListRepository.add(taskList);

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
        validCredentials.add("0`mikehedden@gmail.com`a681wo$dKo");

        errorCredentials.add("1`kenlyon@gmail.com`Mouwkl87%qo");
        errorCredentials.add("1`kenlyon@gmail.com`Mouwkl87%qo");

        // Create invalid mock tasks.
        for (JSONObject jsonObj : DumpRepositoryTester.toJSONObjects(validCredentials))
            errorRequestList.add(createDoPostMockRequest(jsonObj));

        // Create valid mock tasks.
        for (JSONObject jsonObj : toJSONObjects(validUpdates))
            validRequestList.add(createDoPostMockRequest(jsonObj));
    }

    @After
    public void tearDown() throws Exception {
        LOGGER.info("After test.");
    }

    @Test
    public void doPost() throws Exception {

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
