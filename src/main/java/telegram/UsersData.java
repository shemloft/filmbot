package telegram;

import java.util.concurrent.ConcurrentHashMap;

import storage.IFilmDatabase;

public class UsersData {
	private ConcurrentHashMap<Long, UserState> userStates;
	private IFilmDatabase database;
	
	public UsersData(IFilmDatabase database) {
		userStates = new ConcurrentHashMap<Long, UserState>();	
		this.database = database;
	}
	
	public BotMessage getAnswer(Long id, String username, String input) {
		userStates.putIfAbsent(id, new UserState(username, database));
		UserState currentState = userStates.get(id);
		currentState.updateName(username);
		return currentState.getAnswer(input);		
	}

}
