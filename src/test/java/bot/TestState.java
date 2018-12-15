package bot;

import structures.Messages;

public class TestState implements IState {
	
	private Messages answer;
	private String name;
	private StateType type;
	
	public TestState(Messages answer, String name, StateType type) {
		this.answer = answer;
		this.name = name;
		this.type = type;
	}

	@Override
	public Messages getAnswer(String input) {
		return answer;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public StateType getType() {
		return type;
	}

}
