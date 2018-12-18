package bot;

import java.util.Arrays;

import structures.BotMessage;
import structures.Messages;
import structures.Phrases;
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
		if (Arrays.asList(Phrases.CONTROL_TEXT).contains(input))
			return proccesControlText(input);
		
		if (currentState == null) 
			return chooseState(input);		

		Messages answer = currentState.getAnswer(input);
		return addBackOption(answer);
	}
	
	private Messages getMainMenu() {
		return new Messages (new BotMessage(user.getID(), Phrases.CHOOSE_OPTION, getDefaultPossibleAnswers()));
	}
	
	private Messages proccesControlText(String input) {
		switch(input) {				
		
		case "/help":
			if (currentState != null)
				return currentState.processHelp();
			return new Messages(new BotMessage(user.getID(), Phrases.HELP, getDefaultPossibleAnswers()));
		
		case "/start":
		case Phrases.EXIT:			
			Messages messages = getMainMenu();
			if (input.equals("/start")) {				
				messages.addMessageInBegining(new BotMessage(
						user.getID(), 
						Phrases.userWelcome(user.isFirstTime(), user.getName()), 
						getDefaultPossibleAnswers()));
			}			
			Messages exitText = currentState == null ? null : currentState.processExit();
			if (exitText != null) {
				exitText = addBackOption(exitText);
				messages.addMessagesInBegining(exitText);
			}
			currentState = null;
		
 			return messages;			
		
		default:
			return getMainMenu();
		}
	}
	
	private Messages chooseState(String input) {
		if (!Arrays.asList(getDefaultPossibleAnswers()).contains(input))
			return getMainMenu();
		
		currentState = getStateByName(input);
		switch (currentState.getType()) {
		case DIALOG:					
			return addBackOption(currentState.start());
		case ANSWER:
			Messages messages = currentState.getAnswer(null);
			currentState = null;
			messages.getFirstMessage().setPossibleAnswers(getDefaultPossibleAnswers());
			return messages;		
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
	
	private IState getStateByName(String name) {
		return Arrays.stream(states).filter((s) -> s.getName().equals(name)).findFirst().get();		
	}
	
	private Messages addBackOption(Messages answer) {
		for (BotMessage message : answer) 
			message.addPossibleAnswer(Phrases.EXIT);
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
