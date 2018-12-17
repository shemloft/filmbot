package bot;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dialog.Phrases;
import storage.IQuestionGenerator;
import storage.UserDatabase;
import structures.BotMessage;
import structures.Messages;
import structures.User;

public class DuelState implements IState {
	private UserDatabase userDatabase;
	private User user;
	private User[] currentUsers;
	private DuelStateType type;
	private Duel duel;
	private IQuestionGenerator generator;

	public DuelState(User user, UserDatabase userDatabase, IQuestionGenerator generator) {
		this(user, userDatabase, generator, DuelStateType.WAITING);
	}
	
	public DuelState(User user, UserDatabase userDatabase, IQuestionGenerator generator, DuelStateType type) {
		this.userDatabase = userDatabase;	
		this.user = user;
		this.generator = generator;
		this.type = type;
	}

	@Override
	public Messages getAnswer(String input) {
		
		if (duel != null && duel.isOver && type == DuelStateType.GAME) {
			clear();
		}

		switch (type) {
		case WAITING:
			return processWaitingState(input);
		case GAME:
			return processGameState(input);
		default:
			return processWaitingState(input);
		}

	}

	@Override
	public String getName() {
		return Phrases.DUEL;
	}	
	
	
	public User[] usersInDuel() {
		return userDatabase.getUserCollection()
				.stream()
				.filter((u) -> u.inDuel && u != user)
				.toArray(User[]::new);
	}
	
	public String[] getUserChosingAnswers(User[] users) {
		return Stream.concat(getUsersNames(users).stream(), Stream.of(Phrases.REFRESH))				
				.toArray(String[]::new);
	}
	
	public List<String> getUsersNames(User[] users) {
		return Arrays.stream(users)
				.map((u) -> u.getName())
				.collect(Collectors.toList());
	}

	@Override
	public StateType getType() {
		return StateType.DIALOG;
	}

	@Override
	public Messages processExit() {
		
		if (duel != null && duel.isOver && type == DuelStateType.GAME) {
			clear();
		}
		
		user.inDuel = false;
		
		switch (type) {
		case WAITING:
			return null;
		case GAME:
			return duel.processOpponentLeave(user.getOpponent(), user);
		}
		return null;
	}

	@Override
	public Messages start() {
		Messages messages = new Messages();
		user.inDuel = true;
		User[] users = usersInDuel();
		currentUsers = users;			
		messages.addMessage(new BotMessage(user.getID(), Phrases.DUEL_HELP, getUserChosingAnswers(users)));
		return messages;
	}
	
	private Messages processWaitingState(String input) {
		Messages messages = new Messages();
		if (input.equals("/help")) {	
			User[] users = usersInDuel();
			messages.addMessage(new BotMessage(user.getID(), Phrases.DUEL_HELP, getUserChosingAnswers(users)));
			return messages;
		}
		
		if (input.equals(Phrases.REFRESH)) {
			User[] users = usersInDuel();
			currentUsers = users;
			messages.addMessage(new BotMessage(user.getID(), Phrases.REFRESH, getUserChosingAnswers(users)));
			return messages;
		}
		
		if (getUsersNames(currentUsers).contains(input)) {
			User opponent = currentUsers[getUsersNames(currentUsers).indexOf(input)];
			user.setOpponent(opponent);
			if (opponent.getOpponent() == user) {
				type = DuelStateType.GAME;
				DuelState opponentState = new DuelState(opponent, userDatabase, generator, DuelStateType.GAME);
				duel = new Duel(generator);
				opponentState.setDuel(duel);
				userDatabase.setUserState(opponent, opponentState);
				return duel.getFirstQuestion(user);
				
			}
			messages.addMessage(new BotMessage(user.getID(), Phrases.WAITING_FOR_OPPONENT, getUserChosingAnswers(currentUsers)));
			return messages;
		}
		messages.addMessage(new BotMessage(user.getID(), Phrases.DUEL_HELP, getUserChosingAnswers(currentUsers)));
		return messages;
	}
	
	private Messages processGameState(String input) {
		return duel.processGameState(input, user);
	}
	
	private void clear() {
		type = DuelStateType.WAITING;
		generator.reset();
		//start();
	}
	
	public void setDuel(Duel duel) {
		this.duel = duel;
	}

}
