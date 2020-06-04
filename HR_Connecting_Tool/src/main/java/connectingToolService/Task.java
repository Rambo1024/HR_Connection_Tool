package connectingToolService;


public class Task {
	
	
	// Klasser zur Datenverwaltung für die Aufgaben
	
	
	private String taskId, taskText, solvedWith;		// eindeutige Identifikationsnummer für die Aufgabe, der Aufgabentext, und mit wem eine Aufgabe erfüllt wurde
	
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
	

	//  Setter-Methoden zum anpassen der Variablen
	
	
	public void setTaskText(String taskText) {
		this.taskText = taskText;
	}
	
	public void setSolvedWith(String solvedWith) {
		this.solvedWith = solvedWith;
	}
	
	
	// Getter-Methoden zu abfragen der Variablen
	
	public String getTaskId(){
		return taskId;
	}
	
	public String getTaskText() {
		return taskText;
	}
	
	public String getSolvedWith() {
		return solvedWith;
	}
	
	
	
}
