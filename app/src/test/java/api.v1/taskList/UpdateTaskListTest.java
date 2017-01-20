package api.v1.taskList;

import api.v1.model.*;
import api.v1.repo.*;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.ArrayList;


/**
 *
 * @author kennethlyon on 20160711.
 */
public class UpdateTaskListTest extends TaskListApiHelper {
    private Logger LOGGER = LoggerFactory.getLogger(UpdateTaskListTest.class);
    private static UpdateTaskList updateTaskListInstance;
    private static ArrayList<String> validTaskLists;
    private static ArrayList<String> validTaskListUpdates;
    private static ArrayList<String> errorTaskListUpdates;
    private static ScheduleRepository scheduleRepository;
    private static TaskListRepository taskListRepository;
    private static TaskRepository taskRepository;
    private static UserRepository userRepository;
    private static ArrayList<MockHttpServletRequest> validRequestList = new ArrayList();
    private static ArrayList<MockHttpServletRequest> errorRequestList = new ArrayList();
    private static ArrayList<String> sampleTasks=new ArrayList<String>();
    private static ArrayList<String> sampleUsers=new ArrayList<String>();
    private static ArrayList<String> sampleSchedules=new ArrayList<String>();


    /**
     * Create a new Instance of UpdateTaskList() object, then add new
     * taskList test cases to validRequestList and errorRequestList.
     *
     * Test cases:
     * Add valid TaskLists to the repository.
     * Make valid updates to the repo. Verify that they have changed.
     * Make invalid updates to the repo. Verify that an exception is thrown.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        // Create a UpdateTaskList object.
        LOGGER.info("************ Here we are at the begining of the UpdateTaskList unit test! ");
        updateTaskListInstance=new UpdateTaskList();
        scheduleRepository=updateTaskListInstance.getScheduleRepository();
        taskListRepository=updateTaskListInstance.getTaskListRepository();
        taskListRepository=updateTaskListInstance.getTaskListRepository();
        taskRepository=updateTaskListInstance.getTaskRepository();
        userRepository=updateTaskListInstance.getUserRepository();

        sampleUsers.add("0`mikehedden@gmail.com`a681wo$dKo`[]`[]`[0,1,2]`[0]");
        sampleUsers.add("1`kenlyon@gmail.com`Mouwkl87%qo  `[]`[]`[3,4]`[1,2]");

        sampleTasks.add("0`0`Mike's work task 01`TRUE`This task belongs to Mike H.`60000`100000`TRUE`2020-05-31T00:00:00.100Z`NEW`[]`[0]");  //   [0]
        sampleTasks.add("1`0`Mike's work task 02`TRUE`This task belongs to Mike H.`60000`100000`TRUE`2020-05-31T00:00:00.100Z`NEW`[]`[0]");  //   [0]
        sampleTasks.add("2`0`Mike's work task 03`TRUE`This task belongs to Mike H.`60000`100000`TRUE`2020-05-31T00:00:00.100Z`NEW`[]`[1,2]");//   [1,2]
        sampleTasks.add("3`0`Mike's work task 04`TRUE`This task belongs to Mike H.`60000`100000`TRUE`2020-05-31T00:00:00.100Z`NEW`[]`[1,2]");//   [1,2]
        sampleTasks.add("4`1`Ken's  work task 01`TRUE`This task belongs to  Kenny.`60000`100000`TRUE`2020-05-31T00:00:00.100Z`NEW`[]`[3]");  //   [3]
        sampleTasks.add("5`1`Ken's  work task 02`TRUE`This task belongs to  Kenny.`60000`100000`TRUE`2020-05-31T00:00:00.100Z`NEW`[]`[3]");  //   [3]
        sampleTasks.add("6`2`Ken's  home task 01`TRUE`This task belongs to  Kenny.`60000`100000`TRUE`2020-05-31T00:00:00.100Z`NEW`[]`[4,5]");//   [4,5]
        sampleTasks.add("7`2`Ken's  home task 02`TRUE`This task belongs to  Kenny.`60000`100000`TRUE`2020-05-31T00:00:00.100Z`NEW`[]`[4,5]");//   [4,5]


        sampleSchedules.add("0`0`2016-06-28T18:00:00.123Z`2016-06-28T19:00:00.123Z`DAILY `[0]");
        sampleSchedules.add("1`0`2016-07-03T09:00:00.123Z`2016-06-28T10:00:00.123Z`WEEKLY`[0]");
        sampleSchedules.add("2`0`2016-06-28T09:00:00.123Z`2016-06-28T17:00:00.123Z`DAILY `[0]");
        sampleSchedules.add("3`1`2016-06-30T18:00:00.123Z`2016-06-28T19:00:00.123Z`WEEKLY`[1]");
        sampleSchedules.add("4`1`2016-07-03T16:00:00.123Z`2016-07-03T15:00:00.123Z`WEEKLY`[2]");


        validTaskLists=new ArrayList<String>();
        validTaskLists.add("0`0`TaskList 0 `This is Mike's TaskList.        `[0,1,2,3]`[0,1,2]");
        validTaskLists.add("1`1`TaskList 1 `This is Ken's work TaskList.    `[4,5]`[3]");
        validTaskLists.add("2`1`TaskList 2 `This is Ken's personal TaskList.`[6,7]`[4]");


        validTaskListUpdates=new ArrayList<String>();
        validTaskListUpdates.add("0`0`TaskList 0 `This is Mike's BESTEST TaskList.`[0,1,2,3]`[0,1]");
        validTaskListUpdates.add("1`1`TaskList 1 `This is Ken's work TaskList.    `[4,5]`[4]");
        validTaskListUpdates.add("2`1`TaskList 2 `This is Ken's personal TaskList.`[6,7]`[3]");

        errorTaskListUpdates=new ArrayList<String>();
        errorTaskListUpdates.add("0`3`TaskList 0 `This is Mike's BESTEST TaskList.`[0,1,2,3]`[0,1]"); // User DNE
        errorTaskListUpdates.add("1`0`TaskList 1 `This is Ken's work TaskList.    `[4,5]`[4]");       // Bad Owner owener
        errorTaskListUpdates.add("2`1`TaskList 2 `What the heck is going on here? `[6,7]`[0]");       // Reference Schedule not owned.

        for(Task task: toTasks(sampleTasks))
            taskRepository.add(task);

        for(Schedule schedule: toSchedules(sampleSchedules))
            scheduleRepository.add(schedule);

        for(User user: TaskListApiHelper.toUsers(sampleUsers))
            userRepository.add(user);

        for(TaskList taskList: TaskListApiHelper.toTaskLists(validTaskLists))
            taskListRepository.add(taskList);

        for(JSONObject jsonObj: TaskListApiHelper.toJSONObjects(validTaskListUpdates))
            validRequestList.add(createDoPostMockRequest(jsonObj));

        // Create invalid mock TaskLists.
        for(JSONObject jsonObj: TaskListApiHelper.toJSONObjects(errorTaskListUpdates))
            errorRequestList.add(createDoPostMockRequest(jsonObj));
    }

    /**
     * After doPost runs, remove TaskLists from the repository, then set
     * pertinent objects to null.
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        for(TaskList taskList: TaskListApiHelper.toTaskLists(validTaskLists))
            taskListRepository.delete(taskList);

        for(Schedule schedule: toSchedules(sampleSchedules))
            scheduleRepository.delete(schedule);

        for(User user: toUsers(sampleUsers))
            userRepository.delete(user);


        for(Task task: toTasks(sampleTasks))
            taskRepository.delete(task);

        updateTaskListInstance = null;
        validRequestList = null;
        errorRequestList = null;
        verifyRepositoriesAreClean();//*/
    }

    /**
     * Loop though validRequestList and errorRequestList sending each
     * MockHttpServletRequest to UpdateTaskList then forward responses
     * to validateDoPostValidResponse and validateDoPostErrorResponse
     * respectfully.
     *
     * @throws Exception
     */
    @Test
    public void doPost() throws Exception {
        MockHttpServletRequest  request=null;
        MockHttpServletResponse response=null;
        TaskList originalTaskList, updatedTaskList;
        for(int i=0;i<validRequestList.size();i++){
            request=validRequestList.get(i);
            //First get this particular TaskList from the repository.
            originalTaskList=new TaskList();
            originalTaskList.setId(i);
            originalTaskList=taskListRepository.get(originalTaskList);

            //Next, apply the update:
            response = new MockHttpServletResponse();
            updateTaskListInstance.doPost(request, response);

            //Now get the new TaskList from the repository.
            updatedTaskList=new TaskList();
            updatedTaskList.setId(i);
            updatedTaskList=taskListRepository.get(updatedTaskList);

            //Verify that they ARE different.
            LOGGER.info("Original TaskList: " +originalTaskList.toJson());
            LOGGER.info("Updated  TaskList: " +updatedTaskList.toJson());
            ///*
            validateDoPostValidResponse(response);
            if(originalTaskList.toJson().equals(updatedTaskList.toJson()))
                throw new Exception("Error! TaskList was not updated!");

            //*/
        }
        for(int i=0;i<errorRequestList.size();i++){
            request=errorRequestList.get(i);
            //First get this particular TaskList from the repository.
            originalTaskList=new TaskList();
            originalTaskList.setId(i);
            originalTaskList=taskListRepository.get(originalTaskList);

            //Next, apply the update:
            response = new MockHttpServletResponse();
            updateTaskListInstance.doPost(request, response);

            //Now get the "new" TaskList from the repository.
            updatedTaskList=new TaskList();
            updatedTaskList.setId(i);
            updatedTaskList=taskListRepository.get(updatedTaskList);

            //Verify that the TaskList in the repository HAS NOT been updated.
            LOGGER.info("Original TaskList: " + originalTaskList.toJson());
            LOGGER.info("Updated  TaskList: " + updatedTaskList.toJson());
            validateDoPostErrorResponse(response);
            if(!originalTaskList.toJson().equals(updatedTaskList.toJson()))
                throw new Exception("Error! TaskList was not updated!");
        }//*/
    }
}
