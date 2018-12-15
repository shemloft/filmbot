package bot;

import structures.Messages;

public interface IState {
	public Messages getAnswer(String input);
	
	public String getName();
	
	public StateType getType();
}
