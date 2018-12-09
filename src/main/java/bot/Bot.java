package bot;

import dialog.Phrases;
import structures.BotMessage;

public class Bot implements IBot {
	
	private IState[] states;
	private IState currentState;
	private String username;
	
	public Bot(String username, IState[] states) {
		this.username = username;
		this.states = states;
	}
	
	public BotMessage getAnswer(String input) {
		if (currentState == null) {
			return chooseState(input);
		}
		
		if (input.equals("Назад")) {
			currentState = null;
			return getMainMenu();
		}
		
		BotMessage answer = currentState.getAnswer(input);
		String[] options = new String[answer.possibleAnswers.length + 1];
		for (int i = 0; i < answer.possibleAnswers.length; i++) {
			options[i] = answer.possibleAnswers[i];
		}
		options[options.length - 1] = "Назад";
		answer.possibleAnswers = options;
		return answer;
	}
	
	private BotMessage getMainMenu() {
		return new BotMessage("Выберите опцию", getDefaultPossibleAnswers());
	}
	
	private BotMessage chooseState(String input) {
		for (IState state : states) {
			if (state.getName().equals(input)) {
				currentState = state;
				return currentState.getAnswer("/help");
			}
		}
		
		
		switch(input) {
		case "/start":
			return new BotMessage(
					String.format("Добро пожаловать, %s.%s", username, Phrases.HELP),
					getDefaultPossibleAnswers());
		case "/help":
			return new BotMessage(Phrases.HELP, getDefaultPossibleAnswers());
		default:
			return getMainMenu();
		}
	}

	@Override
	public void updateName(String username) {
		this.username = username;
		
	}
	
	private String[] getDefaultPossibleAnswers() {
		String[] possibleAnswers = new String[states.length];
		for (int i = 0; i < states.length; i++) {
			possibleAnswers[i] = states[i].getName();
		}
		return possibleAnswers;
	}
}
