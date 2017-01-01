package api.v1.repo;
import api.v1.error.BusinessException;
import api.v1.error.SystemException;
import api.v1.error.Error;
import java.util.HashMap;
import api.v1.model.TimeBlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * This is the ProtoTimeBlockRepository. This class does not yet
 * interact with a database.
 */
public class TimeBlockRepository implements Repository<TimeBlock>{
    private static Logger LOGGER = LoggerFactory.getLogger(TimeBlockRepository.class);
    private HashMap<Integer, TimeBlock> timeBlockMap;


    /**
     * Create a new instance of a repository.
     */
    public TimeBlockRepository(){
        timeBlockMap=new HashMap<Integer, TimeBlock>();
    }

    /**
     * First discover a timeBlock id that has not been used and assign it to
     * the given task. Then, add the new task to the repository.
     *
     * @param r
     * @throws BusinessException
     * @throws SystemException
     */
    public TimeBlock add(TimeBlock r) throws BusinessException, SystemException{
        int timeBlockId=0;
        while(timeBlockMap.containsKey(timeBlockId))
            timeBlockId++;
        r.setId(timeBlockId);
        timeBlockMap.put(timeBlockId, r);
        return r;
    }

    /**
     * Fetch a timeBlock object from the repository with the given timeBlock id.
     * @param r
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
	public TimeBlock get(TimeBlock r)throws BusinessException, SystemException{
        if(timeBlockMap.containsKey(r.getId()))
            return timeBlockMap.get(r.getId());
        else
            throw new BusinessException(" TimeBlock not found. ID=" + r.getId(), Error.valueOf("NO_SUCH_OBJECT_ERROR"));
    }

    /**
     * Replace an instance of TimeBlock with the instance provided.
     * @param r
     * @throws BusinessException
     * @throws SystemException
     */
	public void update(TimeBlock r) throws BusinessException, SystemException{
        if (timeBlockMap.containsKey(r.getId())) {
            timeBlockMap.remove(r.getId());
            timeBlockMap.put(r.getId(), r);
        } else
            throw new BusinessException(" TimeBlock not found. ID=" + r.getId(), Error.valueOf("NO_SUCH_OBJECT_ERROR"));
	}

    /**
     * Deletes the provided timeBlock.
     * @param r
     * @throws BusinessException
     * @throws SystemException
     */
	public void delete(TimeBlock r) throws BusinessException, SystemException{
        if(timeBlockMap.containsKey(r.getId())){
            timeBlockMap.remove(r.getId());
        }
        else
            throw new BusinessException(" TimeBlock not found. ID=" + r.getId(), Error.valueOf("NO_SUCH_OBJECT_ERROR"));
    }
}
