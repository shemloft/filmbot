package bot;

import structures.BotMessage;

public interface IState {
	public BotMessage[] getAnswer(String input);
	
	public String getName();
}
