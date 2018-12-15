package bot;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import dialog.Phrases;
import structures.BotMessage;
import structures.Messages;
import structures.User;

public class ResultTableState implements IState {
	
	private ConcurrentHashMap<Long, User> users;
	
	public ResultTableState(ConcurrentHashMap<Long, User> users) {
		this.users = users;
	}

	public Messages getAnswer(String input) {		
		BotMessage message = new BotMessage (
				String.join("\n",			
				users.values().stream()
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

}
