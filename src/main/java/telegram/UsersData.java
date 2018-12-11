package telegram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import bot.IBot;
import bot.IBotFactory;
import dialog.Phrases;
import structures.BotMessage;
import structures.User;

public class UsersData {
	private ConcurrentHashMap<Long, IBot> userStates;
	private IBotFactory factory;
	private ConcurrentHashMap<Long, User> users;
	
	public UsersData(IBotFactory factory) {
		userStates = new ConcurrentHashMap<Long, IBot>();	
		users = new ConcurrentHashMap<Long, User>();
		this.factory = factory;
	}
	
	public BotMessage[] getAnswer(Long id, String username, String input) {
		userStates.putIfAbsent(id, factory.getInstance(username));
		IBot currentState = userStates.get(id);
		currentState.updateName(username);
		BotMessage[] answer = currentState.getAnswer(input);
		users.put(id, currentState.getUser());
		if (answer.length == 1 && answer[0].messageText.equals(Phrases.RESULT_TABLE))
			answer[0].messageText = getResultTable();
		return answer;		
	}
	
	private String getResultTable() {
		
		List<User> allUsers = new ArrayList<User>(users.values());		
		Collections.sort(allUsers, (us1, us2) -> us2.getPoints() - us1.getPoints());			
		String resultTable = "";
		for  (User user : allUsers) 
			resultTable += user.getName() + ": " + user.getPoints() + "\n";
		
		return resultTable;
	}

}
