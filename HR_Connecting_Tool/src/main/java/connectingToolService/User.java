package connectingToolService;

import java.util.LinkedList;

public class User {

	
	
	private String name, password, employee_id;
	
	private Task employeeTask;
	
	private boolean taskIsSolved;
	
	private String taskSolvedWith;
	
	private LinkedList<Task> solvedTasks;
	
	public User() {	
		solvedTasks = new LinkedList<Task>();
	}

	public User(String employee_id, String name, String password, Task employeeTask) {
		this();
		this.name = name;
		this.password = password;
		this.employee_id = employee_id;
		this.employeeTask = employeeTask;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setEmployeeTask(Task employeeTask) {
		this.employeeTask = employeeTask;
	}	
	
	public void setTaskSolvedWith(String taskSolvedWith) {
		this.taskSolvedWith = taskSolvedWith;
	}
	
	public void setTaskIsSolved(boolean taskIsSolved) {
		this.taskIsSolved = taskIsSolved;
	}

	public String getName() {
		return name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public Task getEmployeeTask() {
		return employeeTask;
	}
	
	public String getEmployeeId() {
		return employee_id;
	}
	
	public String getTaskSolvedWith() {
		return taskSolvedWith;
	}
	
	public boolean getTaskIsSolved() {
		return taskIsSolved;
	}

	public LinkedList<Task> getSolvedTasks(){
		return solvedTasks;
	}
	
	public void addSolvedTask(Task task) {
		solvedTasks.add(task);
	}
	
	
	
	
}
