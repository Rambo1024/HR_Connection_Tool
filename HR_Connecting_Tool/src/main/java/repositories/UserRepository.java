package repositories;

import connectingToolService.User;

public interface UserRepository {

	
	User findUserByNr(String employee_nr);	
	User findUserByName(String name);
	boolean updateUser(User user);
	boolean createUser(User user);
	boolean validateUserLogin(String name, String password);
	
	
}
