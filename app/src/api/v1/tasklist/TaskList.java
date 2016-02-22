package api.v1.tasklist;
import java.util.ArrayList;

import api.v1.model.Task;

/**
 * This class serves as a list of Tasks. 
 * TODO decide how we want to order prioritize tasks. Should this
 * be done here or in the view?
 * @author kennethlyon
 */
public class TaskList {	
	private ArrayList<Task> taskArrayList;

	public TaskList(){
		this.taskArrayList = new ArrayList<Task>();
	}
	
	public void addTask(Task task){
		this.taskArrayList.add(task);
	}
	


	
}
