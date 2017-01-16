package api.v1.helper;

import api.v1.BaseRequestHandler;
import api.v1.error.CriticalException;
import api.v1.error.Error;
import api.v1.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * This class is used to bind object IDs to a given TaskAssistantModel object.
 * Created by kennethlyon on 1/16/17.
 */
public class BinderHelper extends BaseRequestHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(BinderHelper.class);
    public static void bindObjects(TaskAssistantModel object, ArrayList<Bindable> bindableItems, TaskAssistantModel.Type type)  throws CriticalException {
     for(Bindable b: bindableItems)
        bindIndividual(object, b, type);
    }

    /**
     * Return an appropriate Object for the provided TaskAssistantModel Object.
     */

    public static void bindIndividual(TaskAssistantModel object, Bindable bindableItem, TaskAssistantModel.Type type) throws CriticalException {
            switch (type) {
                case TASK:
                    LOGGER.debug("****** Here! We made it Here! why arn't these tasks being updated?");
                    object.addTask((Task) bindableItem);
                    break;

                case TASKLIST:
                    object.addTaskList((TaskList) bindableItem);
                    break;

                case CALENDAR:
                    object.addCalendar((Calendar) bindableItem);
                    break;

                case CATEGORY:
                    object.addCategory((Category) bindableItem);
                    break;

                case REMINDER:
                    object.addReminder((Reminder) bindableItem);
                    break;

                case SCHEDULE:
                    object.addSchedule((Schedule) bindableItem);
                    break;

                case USER:
                    //TODO what should happen here? Do we need this use case?
                    throw new CriticalException("You cannot use this method to set Users!", Error.valueOf("MODEL_RELATIONSHIP_ERROR"));
                    //object.addUser((User)bindableItem);
            }
        
    }
}
