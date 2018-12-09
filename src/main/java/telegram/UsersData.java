package telegram;

import java.util.concurrent.ConcurrentHashMap;

import bot.IBot;
import bot.IBotFactory;
import structures.BotMessage;

public class UsersData {
	private ConcurrentHashMap<Long, IBot> userStates;
	private IBotFactory factory;
	
	public UsersData(IBotFactory factory) {
		userStates = new ConcurrentHashMap<Long, IBot>();	
		this.factory = factory;
	}
	
	public BotMessage getAnswer(Long id, String username, String input) {
		userStates.putIfAbsent(id, factory.getInstance(username));
		IBot currentState = userStates.get(id);
		currentState.updateName(username);
		return currentState.getAnswer(input);		
	}

}
