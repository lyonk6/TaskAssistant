package api.v2.model;

import api.v2.helper.ModelHelper;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class Category extends TaskAssistantModel implements Cleanable {
    private int id;
    private int userId;
    private String name;
    private String description;
    private ArrayList<Integer> taskIds;
    private ArrayList<Integer> scheduleIds;

    /**
     * Create an new Category w/o a category id. Categories without an
     * id are assigned an id of -1.
     */
    public Category(){
        this.id=-1;
        this.userId=-1;
        this.description=null;
        this.name=null;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id=id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name=name.trim();
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description.trim();
    }
    @Override
    public ArrayList<Integer> getTaskIds() {
        return taskIds;
    }
    @Override
    public ArrayList<Integer> getScheduleIds() {
        return scheduleIds;
    }
    public void setTaskIds(ArrayList<Integer> taskIds) {
        this.taskIds = taskIds;
    }

    /**
     * Associate a Task with this Category.
     * @param task
     */
    public void addTask(Task task){
        if(this.taskIds==null)
            taskIds=new ArrayList<Integer>();
        // Don't add the same id twice.
        for(int i: taskIds)
            if(i==task.getId())
                return;
       taskIds.add(task.getId());
    }

    public void setScheduleIds(ArrayList<Integer> scheduleIds) {
        this.scheduleIds = scheduleIds;
    }

    /**
     * Associate a Schedule with this Category.
     * @param schedule
     */
    public void addSchedule(Schedule schedule){
        if(this.scheduleIds==null)
            scheduleIds=new ArrayList<Integer>();
        // Don't add the same id twice.
        for(int i: scheduleIds)
            if(i==schedule.getId())
                return;
        scheduleIds.add(schedule.getId());
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
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
    /**
     * Creates and returns a deep copy of this object.
     */
    public Category(Category category){
        this.id=category.getId();
        this.userId=category.getUserId();
        if(category.getName()!=null)
            this.name= new String(category.getName());
        if(category.getDescription()!=null)
            this.description=new String(category.getDescription());
        this.taskIds= ModelHelper.copyIntegerArrayList(category.getTaskIds());
        this.scheduleIds= ModelHelper.copyIntegerArrayList(category.getScheduleIds());
    }

    @Override
    public TaskAssistantModel clone() {
        return new Category(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (id != category.id) return false;
        if (userId != category.userId) return false;
        if (!name.equals(category.name)) return false;
        if (description != null ? !description.equals(category.description) : category.description != null)
            return false;
        if (taskIds != null ? !taskIds.equals(category.taskIds) : category.taskIds != null) return false;
        return scheduleIds != null ? scheduleIds.equals(category.scheduleIds) : category.scheduleIds == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (taskIds != null ? taskIds.hashCode() : 0);
        result = 31 * result + (scheduleIds != null ? scheduleIds.hashCode() : 0);
        return result;
    }

    @Override
    public int getParent(){
        return getUserId();
    }
}
