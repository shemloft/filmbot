package bot;

import structures.BotMessage;

public interface IBot {
	public BotMessage getAnswer(String input);
	
	public void updateName(String username);
}
