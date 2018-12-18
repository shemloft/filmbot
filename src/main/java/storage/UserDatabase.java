package storage;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import bot.IBot;
import bot.IState;
import structures.User;

public class UserDatabase implements IUserDatabase {
	
	private ConcurrentHashMap<Long, IBot> userBotState;
	private ConcurrentHashMap<Long, User> users;
	ConcurrentHashMap<Long, Long> userOpponent;
	
	public UserDatabase() {
		userBotState = new ConcurrentHashMap<Long, IBot>();	
		users = new ConcurrentHashMap<Long, User>();	
	}
	
	public void addUser(Long id, IBot bot) {
		userBotState.putIfAbsent(id, bot);
		users.putIfAbsent(id, bot.getUser());
	}
	
	public IBot getBot(Long id) {
		return userBotState.get(id);
	}
	
	public Collection<User> getUserCollection(){
		return users.values();
	}
	
	public void setUserState(User user, IState state) {
		userBotState.get(user.getID()).setState(state);
	}

}
