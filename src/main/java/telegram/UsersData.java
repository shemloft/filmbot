package telegram;

import java.util.concurrent.ConcurrentHashMap;

import bot.IBot;
import bot.IBotFactory;
import structures.Messages;
import structures.User;

public class UsersData {
	private ConcurrentHashMap<Long, IBot> userBotState;
	private ConcurrentHashMap<Long, User> users;
	private IBotFactory factory;	
	
	public UsersData(IBotFactory factory) {
		userBotState = new ConcurrentHashMap<Long, IBot>();	
		users = new ConcurrentHashMap<Long, User>();
		this.factory = factory;
		
	}
	
	public Messages getAnswer(Long id, String username, String input) {
		userBotState.putIfAbsent(id, factory.getInstance(username, users));
		IBot botState = userBotState.get(id);
		users.putIfAbsent(id, botState.getUser());
		botState.updateName(username);
		Messages answer = botState.getAnswer(input);
		users.put(id, botState.getUser());
		return answer;		
	}


}
