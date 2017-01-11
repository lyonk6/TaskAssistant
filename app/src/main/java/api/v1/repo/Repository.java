package api.v1.repo;
import api.v1.error.BusinessException;
import api.v1.error.SystemException;

public interface Repository<TaskAssistantModel> {

	TaskAssistantModel add(final TaskAssistantModel t) throws BusinessException, SystemException;
	TaskAssistantModel get(final TaskAssistantModel t) throws BusinessException, SystemException;
	void update(final TaskAssistantModel t) throws BusinessException, SystemException;
	void delete(final TaskAssistantModel t) throws BusinessException, SystemException;
	void dump(long timestamp) throws SystemException;
}
