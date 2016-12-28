package api.v1.helper;

import api.v1.error.CriticalException;
import api.v1.model.*;
import org.slf4j.LoggerFactory;
import api.v1.error.Error;
import org.slf4j.Logger;

import java.util.ArrayList;

/**
 * Created by kennethlyon on 8/27/16.
 */
public class ModelHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelHelper.class);
    /**
     * Create a deep copy of an ArrayList of Integers. This method is used
     * frequently to copy reference arrays to
     * @param integerArrayList
     * @return
     */
    public static ArrayList<Integer> copyIntegerArrayList(ArrayList<Integer> integerArrayList){
        ArrayList<Integer> copyOf_integerArrayList=new ArrayList<Integer>();
        if(integerArrayList==null)
            return null;
        else {
            for (Integer i : integerArrayList)
                copyOf_integerArrayList.add(new Integer(i));
            return copyOf_integerArrayList;
        }
    }

    /**
     * Remove references to the provided Schedule from the ArrayList of Cleanable objects.
     * @param scheduleId
     * @param modelObjects
     * @throws CriticalException
     */
    public static void dereferenceSchedule(int scheduleId, ArrayList<Cleanable> modelObjects) throws CriticalException {
        if(modelObjects==null || modelObjects.size()==0)
            return;
        for(Cleanable object: modelObjects)
            dereferenceSchedule(scheduleId, object);
    }

    /**
     * Remove the reference to the provided Schedule from a Cleanable object.
     * @param scheduleId
     * @param object
     * @throws CriticalException
     */
    public static void dereferenceSchedule(int scheduleId, Cleanable object) throws CriticalException {
        if (object.getScheduleIds().contains(scheduleId)) {
            object.getScheduleIds().remove((Object) scheduleId);
        }else {
            LOGGER.error("The schedule id {" + scheduleId +"} is not referenced by this object: " + object.toJson());
            throw new CriticalException("Critical error! Cannot clean this Schedule. Task {id=" + object.getId()
                    + "} does not reference this object!", Error.valueOf("API_DELETE_OBJECT_FAILURE"));
        }
    }

    /**
     * Remove references to the provided TaskList from the ArrayList of Cleanable objects.
     * @param taskListId
     * @param modelObjects
     * @throws CriticalException
     */
    public static void dereferenceTaskList(int taskListId, ArrayList<Cleanable> modelObjects) throws CriticalException {
        if(modelObjects==null || modelObjects.size()==0)
            return;
        for(Cleanable object: modelObjects)
            dereferenceTaskList(taskListId, object);
    }

    /**
     * Remove the reference to the provided TaskList from a Cleanable object.
     * @param taskListId
     * @param object
     * @throws CriticalException
     */
    public static void dereferenceTaskList(int taskListId, Cleanable object) throws CriticalException {
        if (object.getTaskListIds().contains(taskListId)) {
            object.getTaskListIds().remove((Object) taskListId);
        }else {
            LOGGER.error("The taskList id {" + taskListId +"} is not referenced by this object: " + object.toJson());
            throw new CriticalException("Critical error! Cannot clean this TaskList. TaskList {id=" + object.getId()
                    + "} does not reference this object!", Error.valueOf("API_DELETE_OBJECT_FAILURE"));
        }
    }

    /**
     * Remove references to the provided Category from the ArrayList of Cleanable objects.
     * @param categoryId
     * @param modelObjects
     * @throws CriticalException
     */
    public static void dereferenceCategory(int categoryId, ArrayList<Cleanable> modelObjects) throws CriticalException {
        if(modelObjects==null || modelObjects.size()==0)
            return;
        for(Cleanable object: modelObjects)
            dereferenceCategory(categoryId, object);
    }

    /**
     * Remove the reference to the provided Category from a Cleanable object.
     * @param categoryId
     * @param object
     * @throws CriticalException
     */
    public static void dereferenceCategory(int categoryId, Cleanable object) throws CriticalException {
        if (object.getCategoryIds().contains(categoryId)) {
            object.getCategoryIds().remove((Object) categoryId);
        }else {
            LOGGER.error("The category id {" + categoryId +"} is not referenced by this object: " + object.toJson());
            throw new CriticalException("Critical error! Cannot clean this Category. Category {id=" + object.getId()
                    + "} does not reference this object!", Error.valueOf("API_DELETE_OBJECT_FAILURE"));
        }
    }

    /**
     * Remove references to the provided Task from the ArrayList of Cleanable objects.
     * @param taskId
     * @param modelObjects
     * @throws CriticalException
     */
    public static void dereferenceTask(int taskId, ArrayList<Cleanable> modelObjects) throws CriticalException {
        if(modelObjects==null || modelObjects.size()==0)
            return;
        for(Cleanable object: modelObjects)
            dereferenceTask(taskId, object);
    }

    /**
     * Remove the reference to the provided Task from a Cleanable object.
     * @param taskId
     * @param object
     * @throws CriticalException
     */
    public static void dereferenceTask(int taskId, Cleanable object) throws CriticalException {
        if (object.getTaskIds().contains(taskId)) {
            object.getTaskIds().remove((Object) taskId);
        }else {
            LOGGER.error("The task id {" + taskId +"} is not referenced by this object: " + object.toJson());
            throw new CriticalException("Critical error! Cannot clean this Task. Task {id=" + object.getId()
                    + "} does not reference this object!", Error.valueOf("API_DELETE_OBJECT_FAILURE"));
        }
    }
}
