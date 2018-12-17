package bot;

import java.util.Arrays;

import dialog.Phrases;
import structures.BotMessage;
import structures.Messages;
import structures.User;

public class Bot implements IBot {
	
	private IState[] states;
	private IState currentState;
	private User user;
	
	public Bot(User user, IState[] states) {
		this.user = user;
		this.states = states;
	}
	
	public Messages getAnswer(String input) {
		if (currentState == null) {
			return chooseState(input);
		}
		
		if (input.equals(Phrases.EXIT)) {
			Messages messages = getMainMenu();
			Messages exitText = currentState.processExit();
			if (exitText != null) {
				exitText = addBackOption(exitText);
				messages.addMessagesInBegining(exitText);
			}
			currentState = null;
			return messages;
		}
		
		Messages answer = currentState.getAnswer(input);
		return addBackOption(answer);
	}
	
	private Messages getMainMenu() {
		return new Messages (new BotMessage(user.getID(), Phrases.CHOOSE_OPTION, getDefaultPossibleAnswers()));
	}
	
	private Messages chooseState(String input) {	
		
		for (IState state : states) {
			if (state.getName().equals(input)) {
				currentState = state;
				switch (state.getType()) {
				case DIALOG:					
					return addBackOption(currentState.start());
				case ANSWER:
					Messages messages = currentState.getAnswer(null);
					currentState = null;
					messages.getFirstMessage().setPossibleAnswers(getDefaultPossibleAnswers());
					return messages;
				}	
			}
		}		
		
		switch(input) {		
		case "/start":
			return new Messages(new BotMessage(
					user.getID(),
					String.format("Добро пожаловать, %s.%s", user.getName(), Phrases.HELP),
					getDefaultPossibleAnswers()));
		case "/help":
			return new Messages(new BotMessage(user.getID(), Phrases.HELP, getDefaultPossibleAnswers()));
		default:
			return getMainMenu();
		}
	}

	@Override
	public void updateName(String username) {
		user.updateName(username);		
	}
	
	public User getUser() {
		return user;
	}
	
	private String[] getDefaultPossibleAnswers() {		
		return Arrays.stream(states).map((s) -> s.getName()).toArray(String[]::new);
	}
	
	private Messages addBackOption(Messages answer) {
		// это нужно т.к. сейчас в messages лежат сообщения для двух пользователей и одному просто не достается кнопки выхода
		for (BotMessage message : answer) {
			message.addPossibleAnswer(Phrases.EXIT);
		}
		return answer;
	}

	@Override
	public void setState(IState newState) {		
		for (int i = 0; i < states.length; i++) {
			if (states[i].getName().equals(newState.getName()))
				states[i] = newState;
		}
		currentState = newState;
		
	}
}
