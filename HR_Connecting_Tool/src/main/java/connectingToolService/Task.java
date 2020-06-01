package connectingToolService;

import java.util.LinkedList;

public class Task {
	
	private String taskId, taskText, solvedWith;
	
	public Task(String taskId) {
		this.taskId = taskId;
	}
	
	public Task(String taskId, String taskText) {
		this.taskId = taskId;
		this.taskText = taskText;
	}
	
	public Task(String taskId, String taskText, String solvedWith) {
		this(taskId, taskText);
		this.solvedWith = solvedWith;
	}
	
	public String getTaskId(){
		return taskId;
	}
	
	public String getTaskText() {
		return taskText;
	}
	
	public String getSolvedWith() {
		return solvedWith;
	}

	public void setTaskText(String taskText) {
		this.taskText = taskText;
	}
	
	public void setSolvedWith(String solvedWith) {
		this.solvedWith = solvedWith;
	}
	
	
	
}
