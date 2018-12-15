package bot;

import structures.Messages;
import structures.User;

public class TestBot implements IBot{
	
	private Messages answer;
	
	public TestBot(Messages answer) {
		this.answer = answer;
	}

	@Override
	public Messages getAnswer(String input) {
		return answer;
	}

	@Override
	public void updateName(String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getUser() {
		// TODO Auto-generated method stub
		return new User("");
	}

}
