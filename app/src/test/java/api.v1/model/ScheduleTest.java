package api.v1.model;

import api.v1.UnitTestHelper;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.GsonBuilder;
import org.junit.Test;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.fail;

import java.util.ArrayList;

/**
 * This class serves a a container for test case proto-Schedules.
 * Created by kennethlyon on 6/9/16.
 */
public class ScheduleTest extends UnitTestHelper{
    private static Logger LOGGER = LoggerFactory.getLogger(ScheduleTest.class);
    private static ArrayList<String> validSchedules=new ArrayList<String>();
    private static ArrayList<String> validUpdates=new ArrayList<String>();

    
    @Before
    public void setUp() throws Exception {

        // id, 
        // userId, 
        // name, 
        // categoryIds, 
        // taskIds, 
        // taskListIds, 
        // timeBlockIds,

        validSchedules.add("0`0`exercize `[0,1]`[0]`[1]`[0]");  // 
        validSchedules.add("1`0`church   `[2,3]`[0]`[1]`[1]");  // 
        validSchedules.add("2`0`work     `[2,3]`[0]`[1]`[2]");  // 
        validSchedules.add("3`1`shopping `[4,5]`[5]`[1]`[3]");  // 
        validSchedules.add("4`1`Home     `[6,7]`[5]`[0]`[4]");  // 

        validUpdates.add("0`0`exercize `   []`[0]`[1]`[0]");   // exercize
        validUpdates.add("1`0`church   `[2,3]`[1]`[1]`[1]");   // church
        validUpdates.add("2`0`work     `[3,2]`[0]`[1]`[2]");   // workdays
        validUpdates.add("3`1`shopping `[4,5]`[5]`[1]`[93]");  // shopping
        validUpdates.add("4`1`Home     `[6,7]`[5]`[90]`[4]");  // Home
    }

  /**
     * Accept an ArrayList of backtick delimited strings and return an ArrayList of Categories.
     * @param backtickCategories
     * @return ArrayList<Schedule>
     * @throws Exception
     */
    protected static ArrayList<Schedule> toSchedules(ArrayList<String> backtickCategories) throws Exception{
        ArrayList<Schedule> mySchedules = new ArrayList<Schedule>();
        for(String s:backtickCategories){
            String[] elements = s.split("`");
            Schedule schedule = new Schedule();
            schedule.setId(Integer.parseInt(elements[0]));
            schedule.setUserId(Integer.parseInt(elements[1]));
            schedule.setName(elements[2]);
            schedule.setCategoryIds(toIntegerArrayList(elements[3]));
            schedule.setTaskIds(toIntegerArrayList(elements[4]));
            schedule.setTaskListIds(toIntegerArrayList(elements[5]));
            schedule.setTimeBlockIds(toIntegerArrayList(elements[6]));
            mySchedules.add(schedule);
        }
        return mySchedules;
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        // Verify that clones generated from "validSchedules" are identical to themselves:
        ArrayList<Schedule> mySchedules=toSchedules(validSchedules);
        ArrayList<Schedule> myUpdates=toSchedules(validUpdates);
        LOGGER.info("Verifying object equivalence.");
        Schedule tempSchedule=null;
        for(int i=0; i<mySchedules.size(); i++){
            tempSchedule=new Schedule(mySchedules.get(i));
            LOGGER.info("Evaluating {} {}",
                    mySchedules.get(i),
                    tempSchedule);
            if(!mySchedules.get(i).equals(tempSchedule)){
                LOGGER.error("These objects were evaluated as not equal when they should be: {} {}",
                        mySchedules.get(i).toJson(),
                        tempSchedule.toJson());
                fail("Error! These objects should be equal!");
            }
        }

        // Verify that instances made from "validSchedules" and validUpdates are not equal to eachother.
        LOGGER.info("Verifying object non-equivalence.");
        for(int i=0; i<mySchedules.size(); i++){
            LOGGER.info("Evaluating {} {}",
                    mySchedules.get(i),
                    myUpdates.get(i));
            if(mySchedules.get(i).equals(myUpdates.get(i))){
                LOGGER.error("These objects were evaluated to be equal when they should not be: {} {}",
                        mySchedules.get(i).toJson(),
                        myUpdates.get(i).toJson());
                fail("Error! These objects should not be equal!");
            }
        }

        // Verify Gson serialization works properly:
        LOGGER.info("Verifying Gson serialization works properly.");
        String format="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        Gson gson = new GsonBuilder().setDateFormat(format).create();
        String json="";
        for(int i=0; i<mySchedules.size(); i++){
            json=mySchedules.get(i).toJson();
            LOGGER.info("Evaluating {} {}", json, (gson.fromJson(json, Schedule.class)).toJson());
            if(!mySchedules.get(i).equals(gson.fromJson(json, Schedule.class))){
                LOGGER.info("Error attempting to serialize/deserialize the schedule {} {}", json, (gson.fromJson(json, Schedule.class)).toJson() );
            }
        }

        testAddTasksAndCategories();
    }


    /**
     * Validate the addCategory & addTask methods.
     */
    private void testAddTasksAndCategories() throws Exception{

        Category category=new Category();
        Task task=new Task();

        ArrayList<Schedule> mySchedules=toSchedules(validSchedules);
        Schedule scheduleCategory, scheduleTask;
        scheduleCategory=new Schedule(mySchedules.get(0));
        scheduleTask=new Schedule(mySchedules.get(1));

        category.setId(0);
        task.setId(0);

        scheduleCategory.addCategory(category);
        scheduleTask.addTask(task);


        if(!mySchedules.get(0).equals(scheduleCategory)){
            LOGGER.error("These objects were evaluated to be not equal when they should be: {} {}",
                    mySchedules.get(0).toJson(),
                    scheduleCategory.toJson());
            fail("Error! These objects should be equal!");
        }
        if(!mySchedules.get(1).equals(scheduleTask)){
            LOGGER.error("These objects were evaluated to be not equal when they should be: {} {}",
                    mySchedules.get(1).toJson(),
                    scheduleTask.toJson());
            fail("Error! These objects should be equal!");
        }

        category.setId(31);
        task.setId(31);

        scheduleCategory.addCategory(category);
        scheduleTask.addTask(task);

        if(mySchedules.get(0).equals(scheduleCategory)){
            LOGGER.error("These objects were evaluated to be equal when they should not be: {} {}",
                    mySchedules.get(0).toJson(),
                    scheduleCategory.toJson());
            fail("Error! These objects should be not equal!");
        }
        if(mySchedules.get(1).equals(scheduleTask)){
            LOGGER.error("These objects were evaluated to be equal when they should not be: {} {}",
                    mySchedules.get(1).toJson(),
                    scheduleTask.toJson());
            fail("Error! These objects should be not equal!");
        }
    }
}
