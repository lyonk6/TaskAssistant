package api.v1;
import api.v1.repo.TaskRepository;

/**
 * TaskRequestHandler contains, fields and methods that are common to
 * task APIs. All task APIs inherit TaskRequestHandler. 
 */
public class TaskRequestHandler extends BaseRequestHandler {

    protected static TaskRepository taskRepository;

    static {
        taskRepository = new TaskRepository();
    }
}