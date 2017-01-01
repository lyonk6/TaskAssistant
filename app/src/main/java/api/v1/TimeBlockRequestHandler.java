package api.v1;
import api.v1.error.BusinessException;
import api.v1.error.CriticalException;
import api.v1.error.Error;
import api.v1.error.SystemException;
import api.v1.model.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.ArrayList;

/**
 * ScheduleRequestHandler contains, fields and methods that are common to
 * schedule APIs. All schedule APIs inherit ScheduleRequestHandler. 
 */
public class TimeBlockRequestHandler extends ScheduleRequestHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeBlockRequestHandler.class);
    /**
     * Verify that a specified ScheduleList actually exists. This
     * is used by AddSchedule and UpdateSchedule to prevent orphaned
     * timeBlocks.
     */
    protected static void verifyScheduleExists(int scheduleId) throws BusinessException, SystemException {
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        scheduleRepository.get(schedule);
    }

    /**
     * Adds a new reference to this TimeBlock to the Schedule this TimeBlock belongs to.
     * @param timeBlock
     * @throws BusinessException
     * @throws SystemException
     */
    protected void addTimeBlockToSchedule(TimeBlock timeBlock) throws BusinessException, SystemException {
        Schedule schedule=new Schedule();
        schedule.setId(timeBlock.getScheduleId());
        schedule=scheduleRepository.get(schedule);
        schedule.addTimeBlock(timeBlock);
    }

    /**
     * Remove the reference to this TimeBlock from the Schedule this TimeBlock belongs to.
     * @param timeBlock
     * @throws BusinessException
     * @throws SystemException
     * @throws CriticalException
     */
    protected void removeReferences(TimeBlock timeBlock) throws BusinessException, SystemException, CriticalException {
        Schedule schedule=new Schedule();
        schedule.setId(timeBlock.getScheduleId());
        schedule=scheduleRepository.get(schedule);
        if(schedule.getTimeBlockIds().contains(timeBlock.getId()))
            schedule.getTimeBlockIds().remove((Object)timeBlock.getId());
        else
            throw new CriticalException("Critical error! Cannot clean this Category. Schedule {name="
                    + schedule.getName() + ", id=" + schedule.getId()
                    + "} does not reference this object!", Error.valueOf("API_DELETE_OBJECT_FAILURE"));
    }
}
