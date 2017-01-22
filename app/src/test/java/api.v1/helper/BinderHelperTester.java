package api.v1.helper;

import api.v1.UnitTestHelper;

import api.v1.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Random;


/**
 * This class tests the BinderHelper Class.
 * @author kennethlyon
 */
public class BinderHelperTester {
    private Logger LOGGER = LoggerFactory.getLogger(BinderHelperTester.class);
    private static final ArrayList<Integer> TEN = UnitTestHelper.toIntegerArrayList("[0,1,2,3,4,5,6,7,8,9]");
    private static final ArrayList<Integer> TWENTY   = UnitTestHelper.toIntegerArrayList("[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19]");
    private static final ArrayList<Integer> PRIMES   = UnitTestHelper.toIntegerArrayList("[2,3,5,7,9,11,13,17,19]");
    private static final ArrayList<Integer> LUCAS    = UnitTestHelper.toIntegerArrayList("[2,1,3,4,7,11,18]");
    private static final ArrayList<Integer> FIBONACCI= UnitTestHelper.toIntegerArrayList("[0,1,1,2,3,5,8,13,21]");
    private BinderHelper binderHelper;

    @Before
    public void setUp() throws Exception {
        LOGGER.info("Before test.");
        binderHelper=new BinderHelper();
        for(Integer i: TWENTY){
            User user=new User();
            user.setEmail(i+"@mail.com");
            binderHelper.getUserRepository().add(user);
            binderHelper.getTaskRepository().add(new Task());
            binderHelper.getTaskListRepository().add(new TaskList());
            binderHelper.getScheduleRepository().add(new Schedule());
            binderHelper.getCategoryRepository().add(new Category());
            binderHelper.getReminderRepository().add(new Reminder());
            binderHelper.getTimeBlockRepository().add(new TimeBlock());
        }
    }

    @After
    public void tearDown() throws Exception {
        LOGGER.info("After test.");
    }

    @Test
    public void doTest() throws Exception {

        testGetUpdatedSchedules();
        /**
         * Fetch an ArrayList of Schedules that have had their reference ids updated. Note
         * that these Schedules are deep copies, and the Schedules in the repository have
         * not yet been updated.
         *
         * @param object
         * @param type
         * @return
         * @throws BusinessException
         * @throws SystemException
         * @throws CriticalException
         */
    }

    /**
     * Using an ArrayList of integer ids, return an ArrayList of TaskAssistantModel objects
     * whose IDs correspond to objectIds and whose class corresponds to type.
     * @param objectIds
     * @param type
     * @return
     */
    private ArrayList<TaskAssistantModel> createObjects(ArrayList<Integer> objectIds, TaskAssistantModel.Type type) {
        ArrayList<TaskAssistantModel> myObjects=new ArrayList<>();
        for (Integer i : objectIds) {
            TaskAssistantModel object = ModelHelper.createNewModelObject(type);
            object.setId(i);
            myObjects.add(object);
        }
        return myObjects;
    }

    //    ArrayList<Task> getUpdatedTasks(TaskAssistantModel object, TaskAssistantModel.Type type)
    private void testGetUpdatedSchedules()throws Exception{
        // First place mock taskLists in the repository
        int n=10;
        int j=20;
        Task task=new Task();
        task.setId(1000);
        task.setScheduleIds(RandomArray(n,j));
        ArrayList<Schedule> schedules = BinderHelper.getUpdatedSchedules(task, TaskAssistantModel.Type.TASK);
        ArrayList<TaskAssistantModel> objects = (ArrayList<TaskAssistantModel>)(ArrayList<?>) schedules;

        for(Schedule schedule: schedules){
            if(!schedule.getTaskIds().contains(task.getId())) {
                LOGGER.error("Error! These objects do-not reference eachother! {} {}", task.toJson(), schedule.toJson());
                throw new Exception("Error! These objects do-not reference eachother!");
            }
        }
    }

    /**
     * Return an appropriate Object for the provided TaskAssistantModel Object.
     * @param objectType
     * @return
     *
    public static void createNewModelObject(TaskAssistantModel.Type objectType)
    {
       ArrayList<TaskAssistantModelObject> myObjects
        try {
            switch (type) {
                case TASK:
                    myObjects = (ArrayList<TaskAssistantModel>)(ArrayList<?>)BinderHelper.getUpdatedSchedules(task, otherType);
                    break;

                case TASKLIST:
                    myObjects = (ArrayList<TaskAssistantModel>)(ArrayList<?>)BinderHelper.getUpdatedSchedules(task, otherType);
                    break;

                case CATEGORY:
                    myObjects = (ArrayList<TaskAssistantModel>)(ArrayList<?>)BinderHelper.getUpdatedSchedules(task, otherType);
                    break;

                case REMINDER:
                    myObjects = (ArrayList<TaskAssistantModel>)(ArrayList<?>)BinderHelper.getUpdatedSchedules(task, otherType);
                    break;

                case SCHEDULE:
                    myObjects = (ArrayList<TaskAssistantModel>)(ArrayList<?>)BinderHelper.getUpdatedSchedules(task, otherType);
                    break;

                case USER:
                    myObjects = (ArrayList<TaskAssistantModel>)(ArrayList<?>)BinderHelper.getUpdatedSchedules(task, otherType);
                    break;
            }
        }catch (NullPointerException npe){

        }
        return object;
    }//*/



    /**
     *
     * @param n length of the ArrayList
     * @param j Maximum allowed value of random number (not inclusive)
     * @return
     */
    private static ArrayList<Integer> RandomArray(int n, int j)
    {
        ArrayList<Integer> arrayRandom = new ArrayList<Integer>(n);
        for (int i=0; i<n; i++)
        {
            Random rand = new Random();
            rand.setSeed(System.currentTimeMillis());
            Integer r = rand.nextInt() % j;
            arrayRandom.add(r);
        }
        return arrayRandom;
    }

    /**
     *
     * @param type
     * @throws Exception
     *
    private void testGetUpdatedSchedules(TaskAssistantModel.Type type)throws Exception {
        int n = 10;
        int j = 20;
        TaskAssistantModel object = (TaskAssistantModel) ModelHelper.createNewModelObject(type);
        object.setId(1000);
        object.setScheduleIds(RandomArray(n, j));
        ArrayList<Schedule> schedules = BinderHelper.getUpdatedSchedules(object, type);
        ArrayList<TaskAssistantModel> objects = (ArrayList<TaskAssistantModel>) (ArrayList<?>) schedules;

        for (Schedule schedule : schedules) {
            if (!schedule.getObjectIds().contains(object.getId())) {
                LOGGER.error("Error! These objects do-not reference eachother! {} {}", object.toJson(), schedule.toJson());
                throw new Exception("Error! These objects do-not reference eachother!");
            }
        }
    }//*/
}
