package telegram;


import bot.IBot;
import bot.IBotFactory;
import storage.UserDatabase;
import structures.Messages;

public class UsersData {
	private UserDatabase userDatabase;
	private IBotFactory factory;	
	
	public UsersData(IBotFactory factory) {
		userDatabase = new UserDatabase();		
		this.factory = factory;		
	}
	
	public Messages getAnswer(Long id, String username, String input) {
		userDatabase.addUser(id, factory.getInstance(id, username, userDatabase));
		IBot botState = userDatabase.getBot(id);
		botState.updateName(username);
		Messages answer = botState.getAnswer(input);
		return answer;		
	}


}
