package bot;

import dialog.Phrases;
import structures.BotMessage;
import structures.User;

public class Bot implements IBot {
	
	private IState[] states;
	private IState currentState;
	private User user;
	
	public Bot(User user, IState[] states) {
		this.user = user;
		this.states = states;
	}
	
	public BotMessage[] getAnswer(String input) {
		if (currentState == null) {
			return chooseState(input);
		}
		
		if (input.equals(Phrases.EXIT)) {
			currentState = null;
			return getMainMenu();
		}
		
		BotMessage[] answer = currentState.getAnswer(input);
		return addBackOption(answer);
	}
	
	private BotMessage[] getMainMenu() {
		return new BotMessage[] { new BotMessage("Выберите опцию", getDefaultPossibleAnswers()) };
	}
	
	private BotMessage[] chooseState(String input) {
		for (IState state : states) {
			if (state.getName().equals(input)) {
				currentState = state;
				return addBackOption(currentState.getAnswer("/help"));
			}
		}
		
		
		switch(input) {
		case "/start":
			return new BotMessage[] {new BotMessage(
					String.format("Добро пожаловать, %s.%s", user.getName(), Phrases.HELP),
					getDefaultPossibleAnswers())};
		case "/help":
			return new BotMessage[] { new BotMessage(Phrases.HELP, getDefaultPossibleAnswers()) };
		default:
			return getMainMenu();
		}
	}

	@Override
	public void updateName(String username) {
		this.user.updateName(username);
		
	}
	
	private String[] getDefaultPossibleAnswers() {
		String[] possibleAnswers = new String[states.length];
		for (int i = 0; i < states.length; i++) {
			possibleAnswers[i] = states[i].getName();
		}
		return possibleAnswers;
	}
	
	private BotMessage[] addBackOption(BotMessage[] answer) {
		if (answer.length > 0) {
			BotMessage lastAnswer = answer[answer.length - 1];
			String[] options = new String[lastAnswer.possibleAnswers.length + 1];
			for (int i = 0; i < lastAnswer.possibleAnswers.length; i++) {
				options[i] = lastAnswer.possibleAnswers[i];
			}
			options[options.length - 1] = Phrases.EXIT;
			lastAnswer.possibleAnswers = options;
		}
		return answer;
	}
}
