package api.v1.helper;

import api.v1.error.CriticalException;
import api.v1.model.Cleanable;
import api.v1.model.TaskAssistantModel;
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
     *
     * @param scheduleId
     * @param modelObjects
     * @throws CriticalException
     */
    public static void dereferenceSchedule(int scheduleId, ArrayList<Cleanable> modelObjects) throws CriticalException {
        if(modelObjects==null || modelObjects.size()==0)
            return;
        for(Cleanable object: modelObjects)
            if (object.getScheduleIds().contains(scheduleId)) {
                object.getScheduleIds().remove((Object) scheduleId);
            }else {
                LOGGER.error("The schedule id {" + scheduleId +"} is not referenced by this object: " + object.toJson());
                throw new CriticalException("Critical error! Cannot clean this Schedule. Task {id=" + object.getId()
                        + "} does not reference this object!", Error.valueOf("API_DELETE_OBJECT_FAILURE"));
            }
    }
}
