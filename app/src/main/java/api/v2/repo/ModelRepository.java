package api.v2.repo;
import api.v2.error.BusinessException;
import api.v2.error.SystemException;
import api.v2.error.Error;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import api.v2.helper.ModelHelper;
import api.v2.model.TaskAssistantModel;

import api.v2.model.TaskAssistantModel;
import api.v2.model.TaskList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * This is the ProtoModelRepository. This class does not yet
 * interact with a database.
 */
public class ModelRepository {
    private static Logger LOGGER = LoggerFactory.getLogger(ModelRepository.class);
    private HashMap<Integer, TaskAssistantModel> modelMap;


    /**
     * Create a new instance of a repository.
     */
    public ModelRepository(){
        modelMap=new HashMap<Integer, TaskAssistantModel>();
    }

    /**
     * First discover an id that has not been used, then store the object.
     * 
     * @param t
     * @throws BusinessException
     * @throws SystemException
     */
    public TaskAssistantModel add(TaskAssistantModel t) throws BusinessException, SystemException{
        int modelId=0;
        while(modelMap.containsKey(modelId))
            modelId++;
        t.setId(modelId);
        modelMap.put(modelId, t);
        return t;
    }

    /**
     * Return a deep copy of the specified Repository object.
     * @param t
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
	public TaskAssistantModel get(TaskAssistantModel t)throws BusinessException, SystemException{
        if(modelMap.containsKey(t.getId()))
            return (modelMap.get(t.getId())).clone();
        else
            throw new BusinessException(t.getClass() + " Object not found. ", Error.valueOf("NO_SUCH_OBJECT_ERROR"));
    }

    /**
     *
     * @param t
     * @throws BusinessException
     * @throws SystemException
     */
	public void update(TaskAssistantModel t) throws BusinessException, SystemException{
        if (modelMap.containsKey(t.getId())) {
            modelMap.remove(t.getId());
            modelMap.put(t.getId(), t);
        } else
            throw new BusinessException(t.getClass() + " Object not found. ID=" + t.getId(), Error.valueOf("NO_SUCH_OBJECT_ERROR"));
	}

    /**
     * Deletes the provided Object.
     *
     * @param t
     * @throws BusinessException
     * @throws SystemException
     */
	public void delete(TaskAssistantModel t) throws BusinessException, SystemException{
        if(modelMap.containsKey(t.getId())){
            modelMap.remove(t.getId());
        }
        else
            throw new BusinessException(t.getClass() + " Object not found. ", Error.valueOf("NO_SUCH_OBJECT_ERROR"));
    }

    public void dump(long timestamp) throws SystemException {
        String fileName = "data/taskassistant_dump_" + timestamp;
        LOGGER.debug("Repo dump initiated. Writing to file: " + System.getProperty("user.dir") + fileName + ".");

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (Integer i : modelMap.keySet())
                writer.write(i + "`" + modelMap.get(i).toJson() + "\n");
            writer.close();
        } catch (IOException ioe) {
            LOGGER.error("Error! Dump failed. Could not write to file: " + fileName);
            throw new SystemException("Error! repo dump failed. Could not write to file. ", Error.valueOf("REPOSITORY_DUMP_FAILURE"));
        }
    }

    /**
     * Fetch an ArrayList of Objects from the provided repository. Note that these
     * Objects are deep copies and changes to these will not directly affect the
     * current model.
     *
     * Note that this method expects that all objects are of a given type.
     *
     * @param objectIds
     * @param type
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
    public ArrayList<TaskAssistantModel> fetchObjects(ArrayList<Integer> objectIds, TaskAssistantModel.Type type)
            throws BusinessException, SystemException {
        ArrayList<TaskAssistantModel> myObjects = new ArrayList<>();
        if(objectIds==null)
            return myObjects;
        for(int i: objectIds) {
            TaskAssistantModel modelObject = ModelHelper.createNewModelObject(type);
            modelObject.setId(i);
            modelObject= (TaskAssistantModel) this.get(modelObject);
            myObjects.add(modelObject);
        }
        return myObjects;
    }
}
