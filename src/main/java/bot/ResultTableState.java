package bot;

import java.util.stream.Collectors;

import dialog.Phrases;
import storage.UserDatabase;
import structures.BotMessage;
import structures.Messages;
import structures.User;

public class ResultTableState implements IState {
	
	private UserDatabase userDatabase;
	private User user;
	
	public ResultTableState(UserDatabase userDatabase, User user) {
		this.userDatabase = userDatabase;
		this.user = user;
	}

	public Messages getAnswer(String input) {		
		BotMessage message = new BotMessage (
				user.getID(),
				String.join("\n",			
				userDatabase.getUserCollection().stream()
				.sorted((us1, us2) -> us2.getPoints() - us1.getPoints())
				.map((u) -> u.getName() + ": " + u.getPoints())
				.collect(Collectors.toList())));
		return new Messages(message);		
	}

	public String getName() {
		return Phrases.RESULT_TABLE;
	}

	@Override
	public StateType getType() {
		return StateType.ANSWER;
	}

	@Override
	public Messages processExit() {
		return null;
	}

	@Override
	public Messages start() {
		// TODO Auto-generated method stub
		return null;
	}

}
