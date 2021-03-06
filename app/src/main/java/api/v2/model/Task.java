package api.v2.model;

import api.v2.error.BusinessException;
import api.v2.error.CriticalException;
import api.v2.error.Error;
import api.v2.helper.ModelHelper;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class serves as a basic data structure for tasks.
 * @author kennethlyon
 */
public class Task extends TaskAssistantModel {
    private int id;
    private int taskListId;
    private String name;
    private String note;
    private long estimatedTime;
    private long investedTime;
    private boolean important;
    private boolean urgent;
    private Date dueDate;
    private Status status;
    public enum Status{NEW, IN_PROGRESS, DELEGATED, DEFERRED, DONE};
    private ArrayList<Integer> categoryIds;
    private ArrayList<Integer> scheduleIds;
    private ArrayList<Integer> reminderIds;


    /**
     * Create a new task w/o an id. Tasks created without an id are assigned
     * an id of -1.
     */
    public Task(){
        this.id=-1;
        this.taskListId=-1;
        this.urgent=false;
        this.status=Status.valueOf("NEW");
    }

    /**
     * Return an deep copy of a given task.
     * @param task
     */
    public Task(Task task){
        this.id=task.getId();
        this.taskListId=task.getTaskListId();
        if(task.getName()!=null)
            this.name=task.getName();
        if(task.getNote()!=null)
            this.note=task.getNote();
        this.estimatedTime=task.getEstimatedTime();
        this.investedTime=task.getInvestedTime();
        this.important=task.getImportant();
        this.urgent=task.getUrgent();
        if(task.getDueDate()!=null)
            this.dueDate=new Date(task.getDueDate().getTime());
        this.status=task.getStatus();
        this.categoryIds = ModelHelper.copyIntegerArrayList(task.getCategoryIds());
        this.scheduleIds = ModelHelper.copyIntegerArrayList(task.getScheduleIds());
        this.reminderIds = ModelHelper.copyIntegerArrayList(task.getReminderIds());
    }

    // return a deep copy
    @Override
    public TaskAssistantModel clone(){
        return new Task(this);
    }

    public void setId(int id) {
        this.id=id;
    }

    public void setTaskListId(int taskListId) {
        this.taskListId = taskListId;
    }

    public void setName(String name)throws BusinessException{
        this.name=name;
    }

    public void setNote(String note){
    this.note = note;
    }

    /**
     * The amount of continuous time a expected for a task to
     * be completed.
     * @param estimatedTime
     */
    public void setEstimatedTime(long estimatedTime){
        this.estimatedTime = estimatedTime;
    }

    /**
     * The amount of time invested in this task so far.
     * @param investedTime
     */
    public void setInvestedTime(long investedTime){
        this.investedTime = investedTime;
    }
    public void setImportant(boolean important){
        this.important=important;
    }
    public void setUrgent(boolean urgent){
    this.urgent = urgent;
    }
    public void setDueDate(Date dueDate){
        this.dueDate = dueDate;
    }
    public void setStatus(Status status){
        this.status=status;
    }

    public int getId(){
        return this.id;
    }
    public int getTaskListId() {
        return taskListId;
    }
    public String getName(){
        return this.name;
    }
    public String getNote(){
        return this.note;
    }
    public long getEstimatedTime(){
        return this.estimatedTime;
    }
    public long getInvestedTime(){
        return this.investedTime;
    }
    public boolean getImportant(){
        return this.important;
    }
    public boolean getUrgent(){
        return this.urgent;
    }
    public Date getDueDate(){
        return this.dueDate;
    }
    public Status getStatus() {
        return status;
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

    /**
     * Associate a Category with this Task.
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

    public void setReminderIds(ArrayList<Integer> reminderIds) {
        this.reminderIds = reminderIds;
    }

    /**
     * Add a reminder to this Task.
     * @param reminder
     */
    public void addReminder(Reminder reminder){
        if(reminderIds==null)
            reminderIds=new ArrayList<Integer>();
        // Don't add the same ID twice.
        for(int i: reminderIds)
            if(i==reminder.getId())
                return;

        reminderIds.add(reminder.getId());
    }

    public void setScheduleIds(ArrayList<Integer> scheduleIds) {
        this.scheduleIds = scheduleIds;
    }

    /**
     * Associate this Task with a Schedule.
     * @param schedule
     */
    public void addSchedule(Schedule schedule){
        if(scheduleIds==null)
            scheduleIds=new ArrayList<Integer>();
        // Don't add the same ID twice.
        for(int i: scheduleIds)
            if(i==schedule.getId())
                return;
        scheduleIds.add(schedule.getId());
    }
    
    public void setCategoryIds(ArrayList<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (id != task.id) return false;
        if (taskListId != task.taskListId) return false;
        if (estimatedTime != task.estimatedTime) return false;
        if (investedTime != task.investedTime) return false;
        if (important != task.important) return false;
        if (urgent != task.urgent) return false;
        if (!name.equals(task.name)) return false;
        if (note != null ? !note.equals(task.note) : task.note != null) return false;
        if (dueDate != null ? !dueDate.equals(task.dueDate) : task.dueDate != null) return false;
        if (status != task.status) return false;
        if (categoryIds != null ? !categoryIds.equals(task.categoryIds) : task.categoryIds != null) return false;
        if (scheduleIds != null ? !scheduleIds.equals(task.scheduleIds) : task.scheduleIds != null) return false;
        return reminderIds != null ? reminderIds.equals(task.reminderIds) : task.reminderIds == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + taskListId;
        result = 31 * result + name.hashCode();
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (int) (estimatedTime ^ (estimatedTime >>> 32));
        result = 31 * result + (int) (investedTime ^ (investedTime >>> 32));
        result = 31 * result + (important ? 1 : 0);
        result = 31 * result + (urgent ? 1 : 0);
        result = 31 * result + (dueDate != null ? dueDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (categoryIds != null ? categoryIds.hashCode() : 0);
        result = 31 * result + (scheduleIds != null ? scheduleIds.hashCode() : 0);
        result = 31 * result + (reminderIds != null ? reminderIds.hashCode() : 0);
        return result;
    }

    @Override
    public ArrayList<Integer> getCategoryIds() {
        return categoryIds;
    }

    @Override
    public ArrayList<Integer> getReminderIds() {
        return reminderIds;
    }

    @Override
    public ArrayList<Integer> getScheduleIds() {
        return scheduleIds;
    }

    @Override
    public int getParent(){
        return getTaskListId();
    }
}
