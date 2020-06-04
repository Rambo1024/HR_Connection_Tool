package connectingToolService;

import java.util.LinkedList;

public class User {

	
	// Klase zur Datenverwaltung des Benutzers 
	
	
	private String name, password, employee_id, taskSolvedWith;			// Benutzerdaten : Name, Passwort, employee_Id (Mitarbeiternummer) ..../....   TaskSolvedWith Übrtragungsvariable um den Mitarbeiter festzuhalten mit dem die Aufgabe erledigt wurde 
	
	private Task employeeTask;							// Zu erfüllende Aufgabe
	
	private LinkedList<Task> solvedTasks;				// Liste mit erfüllten Aufgaben
	
	private boolean loginError, taskError;				// Flags bei falscher Eingabe im Login oder beim erfüllen einer Aufgabe
	
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
	
	
	//      Setter-Methoden zum anpassen der Variablen
	
	
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
	
	
	public void setLoginError(boolean loginError) {
		this.loginError = loginError;
	}
	
	public void setTaskError(boolean taskError) {
		this.taskError = taskError;
	}
	
	
	// Getter-Methode   zum Abfragen der VAriablen

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
	
	public LinkedList<Task> getSolvedTasks(){
		return solvedTasks;
	}
	
	public boolean getLoginError() {
		return loginError;
	}
	
	public boolean getTaskError() {
		return taskError;
	}
	
	
	
	// Methode zum hinzufügen einer erfüllten Aufgabe
	
	public void addSolvedTask(Task task) {
		solvedTasks.add(task);
	}
	
	
	
	
}
