package api.v1.model;

import api.v1.helper.ModelHelper;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Date;

public class Schedule extends TaskAssistantModel{
    private int id;
    private int userId;
    private Date startDate;
    private Date endDate;
    private RepeatTypes repeatType;
    public enum RepeatTypes {NONE, DAILY, WEEKLY, MONTHLY, YEARLY};
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
        this.repeatType=RepeatTypes.NONE;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public RepeatTypes getRepeatType() {
        return repeatType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setRepeatType(RepeatTypes repeatType){
	this.repeatType=repeatType;
    }

    @Override
    public ArrayList<Integer> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(ArrayList<Integer> taskIds) {
        this.taskIds = taskIds;
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
     * Create a serialized JSON String of this instance
     * using GSON.
     * @return
     */
    public String toJson(){
        String format="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        Gson gson = new GsonBuilder().setDateFormat(format).create();
        //Gson gson=new Gson();
        return gson.toJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule)) return false;

        Schedule schedule = (Schedule) o;

        if (getId() != schedule.getId()) return false;
        if (getUserId() != schedule.getUserId()) return false;
        if (getStartDate() != null ? !getStartDate().equals(schedule.getStartDate()) : schedule.getStartDate() != null)
            return false;
        if (getEndDate() != null ? !getEndDate().equals(schedule.getEndDate()) : schedule.getEndDate() != null)
            return false;
        if (getRepeatType() != schedule.getRepeatType()) return false;
        if (getCategoryIds() != null ? !getCategoryIds().equals(schedule.getCategoryIds()) : schedule.getCategoryIds() != null)
            return false;
        if (getTaskListIds() != null ? !getTaskListIds().equals(schedule.getTaskListIds()) : schedule.getTaskListIds() != null)
            return false;
        return getTaskIds() != null ? getTaskIds().equals(schedule.getTaskIds()) : schedule.getTaskIds() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getUserId();
        result = 31 * result + (getStartDate() != null ? getStartDate().hashCode() : 0);
        result = 31 * result + (getEndDate() != null ? getEndDate().hashCode() : 0);
        result = 31 * result + (getRepeatType() != null ? getRepeatType().hashCode() : 0);
        result = 31 * result + (getCategoryIds() != null ? getCategoryIds().hashCode() : 0);
        result = 31 * result + (getTaskListIds() != null ? getTaskListIds().hashCode() : 0);
        result = 31 * result + (getTaskIds() != null ? getTaskIds().hashCode() : 0);
        return result;
    }

    /**
     * @param schedule
     */
    public Schedule(Schedule schedule){
        this.id=schedule.getId();
        this.userId=schedule.getUserId();
        this.startDate=new Date(schedule.getStartDate().getTime());
        this.endDate=new Date(schedule.getEndDate().getTime());
        this.repeatType=schedule.getRepeatType(); //Pretty sure this is a deep copy.
        this.categoryIds=ModelHelper.copyIntegerArrayList(schedule.getCategoryIds());
        this.taskIds= ModelHelper.copyIntegerArrayList(schedule.getTaskIds());
    }

    @Override
    public ArrayList<Integer> getTaskListIds() {
        return taskListIds;
    }

    public void setTaskListIds(ArrayList<Integer> taskListIds) {
        this.taskListIds = taskListIds;
    }
}
