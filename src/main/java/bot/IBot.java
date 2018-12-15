package bot;

import structures.Messages;
import structures.User;

public interface IBot {
	public Messages getAnswer(String input);
	
	public void updateName(String username);
	
	public User getUser();
}
