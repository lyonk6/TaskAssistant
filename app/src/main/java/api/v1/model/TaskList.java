package api.v1.model;
import api.v1.error.BusinessException;
import api.v1.error.Error;
import api.v1.helper.ModelHelper;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.GsonBuilder;

import java.util.ArrayList;

/**
 * This class serves as a container to which tasks must belong.
 * @author kennethlyon
 */
public class TaskList extends TaskAssistantModel{
    private int id;
    private int userId;
    private String name;
    private String description;
    private ArrayList<Integer> taskIds;
    private ArrayList<Integer> scheduleIds;
    /**
     * Create a new TaskList w/o a taskList id. TasksLists created without
     * an id are assigned an id of -1.
     */
    public TaskList(){
        this.id=-1;
        this.userId=-1;
    }

    /**
     * Create a deep copy of this TaskList.
     * @param taskList
     */
    public TaskList(TaskList taskList){
        this.id=taskList.getId();
        this.userId=taskList.getUserId();
        this.name=new String(taskList.getName());
        this.description=new String(taskList.getDescription());
        this.taskIds= ModelHelper.copyIntegerArrayList(taskList.getTaskIds());
        this.scheduleIds=ModelHelper.copyIntegerArrayList(taskList.getScheduleIds());
    }

    public String getName() {
        return name;
    }

    /**
     * This method sets the name of a task list. Null and empty
     * strings throw an exception.
     *
     * @param name
     * @throws BusinessException
     */
    public void setName(String name) throws BusinessException{
        this.name=name.trim();
        if(this.name==null || this.name.equals(""))
            throw new BusinessException("The task name cannot be empty.", Error.valueOf("INVALID_NAME_ERROR"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id=id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public ArrayList<Integer> getTaskIds() {
        return taskIds;
    }
    public void setTaskIds(ArrayList<Integer> taskIds) {
        this.taskIds = taskIds;
    }

    /**
     * Add a Task to this TaskList.
     * @param task
     */
    public void addTask(Task task){
        if(taskIds==null)
            taskIds=new ArrayList<Integer>();
        // Don't add the same ID twice.
        for(int i: taskIds)
            if(i==task.getId())
                return;

        taskIds.add(task.getId());
    }

    /**
     * Add a Schedule to this TaskList.
     * @param schedule
     */
    public void addSchedule(Schedule schedule){
        if(taskIds==null)
            taskIds=new ArrayList<Integer>();
        // Don't add the same ID twice.
        for(int i: taskIds)
            if(i==schedule.getId())
                return;

        taskIds.add(schedule.getId());
    }
    /**
     * Create a serialized JSON String of this instance
     * using GSON.
     * @return
     */
    public String toJson(){
        String format="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        Gson gson = new GsonBuilder().setDateFormat(format).create();
        return gson.toJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskList)) return false;

        TaskList taskList = (TaskList) o;

        if (getId() != taskList.getId()) return false;
        if (getUserId() != taskList.getUserId()) return false;
        if (getName() != null ? !getName().equals(taskList.getName()) : taskList.getName() != null) return false;
        if (getDescription() != null ? !getDescription().equals(taskList.getDescription()) : taskList.getDescription() != null)
            return false;
        if (getTaskIds() != null ? !getTaskIds().equals(taskList.getTaskIds()) : taskList.getTaskIds() != null)
            return false;
        return getScheduleIds() != null ? getScheduleIds().equals(taskList.getScheduleIds()) : taskList.getScheduleIds() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getUserId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getTaskIds() != null ? getTaskIds().hashCode() : 0);
        result = 31 * result + (getScheduleIds() != null ? getScheduleIds().hashCode() : 0);
        return result;
    }

    @Override
    public ArrayList<Integer> getScheduleIds() {
        return scheduleIds;
    }

    public void setScheduleIds(ArrayList<Integer> scheduleIds) {
        this.scheduleIds = scheduleIds;
    }

    @Override
    public int getParent() {
        return getUserId();
    }
}
