package storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import structures.User;

public class TestUserDatabase implements IUserDatabase {
	
	private List<User> users;
	
	public TestUserDatabase() {
		users = new ArrayList<User>();
	}
	
	public void addUser(User user) {
		users.add(user);
	}

	@Override
	public Collection<User> getUserCollection() {
		return users;
	}

}
