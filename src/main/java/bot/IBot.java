package bot;

import structures.BotMessage;
import structures.User;

public interface IBot {
	public BotMessage[] getAnswer(String input);
	
	public void updateName(String username);
	
	public User getUser();
}
