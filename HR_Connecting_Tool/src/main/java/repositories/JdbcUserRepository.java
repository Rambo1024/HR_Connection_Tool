package repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import connectingToolService.Task;
import connectingToolService.User;

@Repository
public class JdbcUserRepository implements UserRepository{
	
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
		
		
	
		public User findUserByNr(String employee_id) {
			User user = jdbcTemplate.queryForObject("Select id, name, password, taskId from Employees where id=? ", this::mapRowToUser, employee_id);
			user.setEmployeeTask(jdbcTemplate.queryForObject("Select taskId, taskText from Tasks where taskId=?", this::mapRowToTask, user.getEmployeeTask().getTaskId()));
			
			
			for(Task task : jdbcTemplate.query("Select SolvedTasks.taskId, SolvedTasks.solvedWith, SolvedTasks.employeeId, Tasks.taskText from SolvedTasks INNER JOIN Tasks On SolvedTasks.taskId = Tasks.taskId where SolvedTasks.employeeId=? ", this::mapRowToSolvedTasks, user.getEmployeeId())) {
				user.addSolvedTask(task);
			}
			
			return user;
		}
		
		public User findUserByName(String name) {
			User user = jdbcTemplate.queryForObject("Select id, name, password, taskId from Employees where name=? ", this::mapRowToUser, name);
			user.setEmployeeTask(jdbcTemplate.queryForObject("Select taskId, taskText from Tasks where taskId=?", this::mapRowToTask, user.getEmployeeTask().getTaskId()));
			
			for(Task task : jdbcTemplate.query("Select SolvedTasks.taskId, SolvedTasks.solvedWith, SolvedTasks.employeeId, Tasks.taskText from SolvedTasks INNER JOIN Tasks On SolvedTasks.taskId = Tasks.taskId where SolvedTasks.employeeId=? ", this::mapRowToSolvedTasks, user.getEmployeeId())) {
				user.addSolvedTask(task);
			}
			
			
			return user;
		}
		
		
		public boolean updateUser(User user) {
			return false;
		}
		
		
		public boolean createUser(User user) {
			return false;
		}
		
		public Task SolveTask(User user){
			String solvedEmployee = jdbcTemplate.queryForObject("Select name from Employees where name=?", String.class, user.getTaskSolvedWith());
			
			user.getEmployeeTask().setSolvedWith(user.getTaskSolvedWith());
			user.addSolvedTask(user.getEmployeeTask());
			
			jdbcTemplate.update("insert into SolvedTasks (solvedId, employeeId, taskId, solvedWith) values (?, ?, ?, ?)", ++HighestSolvedTaskId, user.getEmployeeId(), user.getEmployeeTask().getTaskId(), solvedEmployee);
			int randomTask = rnd.nextInt((new Integer(HighestTaskId)- 5001) + 1 ) + 5001 ;
			
			System.out.println(randomTask);
			
			return jdbcTemplate.queryForObject("Select taskId, taskText from Tasks where taskId=?", this::mapRowToTask, randomTask);
			}
		
		public boolean validateUserLogin(String name, String password) {
			User user = jdbcTemplate.queryForObject("Select id, name, password, taskId from Employees where name=? ", this::mapRowToUser, name);
			return user.getPassword().equals(password);
		}
		
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
