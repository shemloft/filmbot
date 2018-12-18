package bot;

import structures.Messages;

public interface IState {
	public Messages getAnswer(String input);
	
	public Messages processExit();
	
	public String getName();
	
	public StateType getType();
	
	public Messages start();
	
	public Messages processHelp();	
}
