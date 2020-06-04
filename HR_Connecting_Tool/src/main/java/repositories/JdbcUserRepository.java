package repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import connectingToolService.Task;
import connectingToolService.User;

@Repository
public class JdbcUserRepository implements UserRepository{
	
	
	
		// Klasse zur Datenverwaltung in der Datenbank mit Hilfe von JDBC
	
	
	
		public static String HighestUserId, HighestTaskId;
		public static int HighestSolvedTaskId;
	

		private JdbcTemplate jdbcTemplate;
	
		private Random rnd;
		
		@Autowired
		public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
			this.jdbcTemplate = jdbcTemplate;
			
			rnd = new Random();
			
			HighestTaskId = jdbcTemplate.queryForObject("Select max(taskId) from Tasks", String.class);
			HighestUserId = jdbcTemplate.queryForObject("Select max(id) from Employees", String.class);
			HighestSolvedTaskId = jdbcTemplate.queryForObject("Select max(solvedId) from SolvedTasks", Integer.class);
		}
		
		
		

		// Methode zum Auslesen eines Mitarbeiters aus der Datenbank anhand der ID		
	
		public User findUserByNr(String employee_id) {
			User user = jdbcTemplate.queryForObject("Select id, name, password, taskId from Employees where id=? ", this::mapRowToUser, employee_id);									//Auslesen der Mitarbeiterdaten
			user.setEmployeeTask(jdbcTemplate.queryForObject("Select taskId, taskText from Tasks where taskId=?", this::mapRowToTask, user.getEmployeeTask().getTaskId()));				// Auslesen der momentanen Aufgabe des Mitarbeiters
			
			for(Task task : jdbcTemplate.query("Select SolvedTasks.taskId, SolvedTasks.solvedWith, SolvedTasks.employeeId, Tasks.taskText from SolvedTasks INNER JOIN Tasks On SolvedTasks.taskId = Tasks.taskId where SolvedTasks.employeeId=? ", this::mapRowToSolvedTasks, user.getEmployeeId())) {
				user.addSolvedTask(task);
			}										// Auslesen der bisher erledigten Aufgaben des Mitarbeiters
			return user;
		}
		
		// Methode zum Auslesen eines Mitarbeiters aus der Datenbank anhand des Namens
		
		public User findUserByName(String name) {
			User user = jdbcTemplate.queryForObject("Select id, name, password, taskId from Employees where name=? ", this::mapRowToUser, name);										//Auslesen der Mitarbeiterdaten
			user.setEmployeeTask(jdbcTemplate.queryForObject("Select taskId, taskText from Tasks where taskId=?", this::mapRowToTask, user.getEmployeeTask().getTaskId()));				// Auslesen der momentanen Aufgabe des Mitarbeiters
			
			for(Task task : jdbcTemplate.query("Select SolvedTasks.taskId, SolvedTasks.solvedWith, SolvedTasks.employeeId, Tasks.taskText from SolvedTasks INNER JOIN Tasks On SolvedTasks.taskId = Tasks.taskId where SolvedTasks.employeeId=? ", this::mapRowToSolvedTasks, user.getEmployeeId())) {
				user.addSolvedTask(task);
			}										// Auslesen der bisher erledigten Aufgaben des Mitarbeiters
			return user;
		}
		
		
	 // Methode zum aktulisieren eines Mitarbeiters
		
		public boolean updateUser(User user) {
			try {
				
				jdbcTemplate.update("update Employees set name=?, password=?, where id=?", user.getName(), user.getPassword(), user.getEmployeeId());
				
			}catch(DataAccessException dataException) {
				return false;
			}
			return true;
			
		}
		
		
		// Methode zum anlegen eines neuen Benutzers
		
		
		public boolean createUser(User user) {
			try {
				
				jdbcTemplate.update("insert into Employees (id, name, password, taskId) values (?, ?, ?, ?)", user.getEmployeeId(), user.getName(), user.getPassword(), user.getEmployeeTask().getTaskId());
				
			}catch(DataAccessException dataException) {
				return false;
			}
			return true;
		}
		
		
		// Methode zum erledigen einer Aufgabe mit Überprüfung auf die Existenz des Mitarbeiters und der Erzeugung einer neuen Aufgabe
		
		
		public Task SolveTask(User user) throws DataAccessException{
			
			String solvedEmployee = jdbcTemplate.queryForObject("Select name from Employees where name=?", String.class, user.getTaskSolvedWith()); 	// Mitarbeiter zur Aufgabenerfüllung aus der Datenbank lesen um zu überprüfen ob der Mitarbeiter existiert
			
			user.getEmployeeTask().setSolvedWith(solvedEmployee);
			user.addSolvedTask(user.getEmployeeTask());
			
			jdbcTemplate.update("insert into SolvedTasks (solvedId, employeeId, taskId, solvedWith) values (?, ?, ?, ?)", ++HighestSolvedTaskId, user.getEmployeeId(), user.getEmployeeTask().getTaskId(), solvedEmployee); 	// speichern der erfüllten Aufgabe in die Datenbank
			
			
			int randomTask = rnd.nextInt((new Integer(HighestTaskId)- 5001) + 1 ) + 5001 ;			// Zufallszahl zum laden einer neuen zufälligen Aufgabe
			
			
			return jdbcTemplate.queryForObject("Select taskId, taskText from Tasks where taskId=?", this::mapRowToTask, randomTask);		// Selektieren der Zufallsaufgabe aus der Datenbank
		}
		
		
		
		
		// Methode zur Überprüfung der Logindaten
		
		
		public boolean validateUserLogin(String name, String password) {
			User user;
			try {
			user = jdbcTemplate.queryForObject("Select id, name, password, taskId from Employees where name=? ", this::mapRowToUser, name);
			
			}catch(DataAccessException dataException) {
				return false;
			}
			
			return user.getPassword().equals(password);
		}
		
		
		// Mappingmethoden zum überführen der Datenbankergenisse in das jeweilige Objekt 
		
		private Task mapRowToTask(ResultSet rs, int rowNum) throws SQLException {
			return new Task(rs.getString("taskId"), rs.getString("taskText"));
		}
	
		private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException{
			return new User(rs.getString("id"), rs.getString("name"), rs.getString("password"), new Task(rs.getString("taskId")));		
		}
		
		private Task mapRowToSolvedTasks(ResultSet rs, int rowNum) throws SQLException {
			return new Task(rs.getString("taskId"), rs.getString("taskText"), rs.getString("solvedWith"));
		}
		

}
