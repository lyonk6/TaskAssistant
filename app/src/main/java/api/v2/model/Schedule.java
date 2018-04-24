package api.v2.model;

import api.v2.helper.ModelHelper;
import com.google.appengine.repackaged.com.google.gson.Gson;
import java.util.ArrayList;

public class Schedule extends TaskAssistantModel{
    private int id;
    private int userId;
    private String name;
    private ArrayList<Integer> timeBlockIds;
    private ArrayList<Integer> categoryIds;
    private ArrayList<Integer> taskListIds;
    private ArrayList<Integer> taskIds;

    /**
     * Create a new schedule w/o an id. Schedules without an id are
     * assigned an id of -1.
     */
    public Schedule(){
        this.id=-1;
        this.userId=-1;
        this.name="";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ArrayList<Integer> getTimeBlockIds() {
        return timeBlockIds;
    }

    public void setTimeBlockIds(ArrayList<Integer> timeBlockIds) {
        this.timeBlockIds = timeBlockIds;
    }

    public void addTimeBlock(TimeBlock timeBlock){
        if(this.timeBlockIds==null)
            timeBlockIds=new ArrayList<Integer>();
        // Don't add the same ID twice.
        for(int i: timeBlockIds )
            if(i==timeBlock.getId())
                return;
        timeBlockIds.add(timeBlock.getId());
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setId(int id){
        this.id=id;
    }

    @Override
    public ArrayList<Integer> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(ArrayList<Integer> taskIds) {
        this.taskIds = taskIds;
    }

    @Override
    public ArrayList<Integer> getTaskListIds() {
        return taskListIds;
    }

    public void setTaskListIds(ArrayList<Integer> taskListIds) {
        this.taskListIds = taskListIds;
    }

    /**
     * Add a Task to this Schedule.
     * @param task
     */
    public void addTask(Task task){
        if(this.taskIds==null)
            taskIds=new ArrayList<Integer>();
        // Don't add the same ID twice.
        for(int i: taskIds)
            if(i==task.getId())
                return;
        taskIds.add(task.getId());
    }

    @Override
    public ArrayList<Integer> getCategoryIds() {

        return categoryIds;
    }

    public void setCategoryIds(ArrayList<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }

    /**
     * Add a category id to this ArrayList.
     * @param category
     */
    public void addCategory(Category category){
        if(categoryIds==null)
            categoryIds=new ArrayList<Integer>();
        // Don't add the same ID twice.
        for(int i: categoryIds)
            if(i==category.getId())
                return;

        categoryIds.add(category.getId());

    }



    /**
     * Add a taskList id to this ArrayList.
     * @param taskList
     */
    public void addTaskList(TaskList taskList){
        if(taskListIds==null)
            taskListIds=new ArrayList<Integer>();
        // Don't add the same ID twice.
        for(int i: taskListIds)
            if(i==taskList.getId())
                return;
        taskListIds.add(taskList.getId());
    }

    /**
     * Create a serialized JSON String of this instance
     * using GSON.
     * @return
     */
    public String toJson(){
        Gson gson=new Gson();
        return gson.toJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule)) return false;

        Schedule schedule = (Schedule) o;

        if (getId() != schedule.getId()) return false;
        if (getUserId() != schedule.getUserId()) return false;
        if (getName() != null ? !getName().equals(schedule.getName()) : schedule.getName() != null) return false;
        if (getCategoryIds() != null ? !getCategoryIds().equals(schedule.getCategoryIds()) : schedule.getCategoryIds() != null)
            return false;
        if (getTaskListIds() != null ? !getTaskListIds().equals(schedule.getTaskListIds()) : schedule.getTaskListIds() != null)
            return false;
        if (getTimeBlockIds() != null ? !getTimeBlockIds().equals(schedule.getTimeBlockIds()) : schedule.getTimeBlockIds() != null)
            return false;
        return getTaskIds() != null ? getTaskIds().equals(schedule.getTaskIds()) : schedule.getTaskIds() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getUserId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getCategoryIds() != null ? getCategoryIds().hashCode() : 0);
        result = 31 * result + (getTaskListIds() != null ? getTaskListIds().hashCode() : 0);
        result = 31 * result + (getTimeBlockIds() != null ? getTimeBlockIds().hashCode() : 0);
        result = 31 * result + (getTaskIds() != null ? getTaskIds().hashCode() : 0);
        return result;
    }

    /**
     * @param schedule
     */
    public Schedule(Schedule schedule){
        this.id=schedule.getId();
        this.userId=schedule.getUserId();
        this.name=new String(schedule.getName());
        this.categoryIds=ModelHelper.copyIntegerArrayList(schedule.getCategoryIds());
        this.taskListIds=ModelHelper.copyIntegerArrayList(schedule.getTaskListIds());
        this.timeBlockIds=ModelHelper.copyIntegerArrayList(schedule.getTimeBlockIds());
        this.taskIds=ModelHelper.copyIntegerArrayList(schedule.getTaskIds());
    }

    @Override
    public int getParent(){
        return getUserId();
    }
}




