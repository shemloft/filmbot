package duel;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import bot.IState;
import bot.StateType;
import storage.IUserDatabase;
import structures.BotMessage;
import structures.Messages;
import structures.Phrases;
import structures.User;

public class DuelState implements IState {
	private IUserDatabase userDatabase;
	private User user;
	private User[] currentUsers;
	private DuelStateType type;
	private Duel duel;
	private Request sendedRequest;

	public DuelState(User user, IUserDatabase userDatabase, Duel duel) {
		this(user, userDatabase, duel, DuelStateType.WAITING);
	}
	
	public DuelState(User user, IUserDatabase userDatabase, Duel duel, DuelStateType type) {
		this.userDatabase = userDatabase;	
		this.user = user;
		this.duel = duel;
		this.type = type;
	}

	@Override
	public Messages getAnswer(String input) {
		
		if (duel.isOver && type == DuelStateType.GAME) {
			clear();
		}
		
		if (user.hasRequest()) {
			return processRequest(input);
		}

		switch (type) {
		case WAITING:
			return processWaitingState(input);
		case REQUEST:
			return processOutgoingRequest(input);
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
				.filter((u) -> u.inDuel && u != user && u.getOpponent() == null)
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
		
		if (user.getOpponent() == null || (duel.isOver && type == DuelStateType.GAME)) {
			clear();
		}
		
		if (user.hasRequest()) {
			processRequest("Нет");
		}
		
		user.inDuel = false;
		
		switch (type) {
		case WAITING:
			return null;
		case REQUEST:
			type = DuelStateType.WAITING;
			if (sendedRequest.isAccepted()) {
				Messages answer = duel.processOpponentLeave(user.getOpponent(), user);
				clear();
				return answer;
			}
			
			return null;
		case GAME:
			type = DuelStateType.WAITING;
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
		
		if (input.equals(Phrases.REFRESH)) {
			User[] users = usersInDuel();
			currentUsers = users;
			messages.addMessage(new BotMessage(user.getID(), Phrases.REFRESH, getUserChosingAnswers(users)));
			return messages;
		}
		
		if (getUsersNames(currentUsers).contains(input)) {
			User opponent = currentUsers[getUsersNames(currentUsers).indexOf(input)];
			user.setOpponent(opponent);
			sendedRequest = new Request(user, duel);
			opponent.sendRequest(sendedRequest);
			type = DuelStateType.REQUEST;
			messages.addMessage(new BotMessage(opponent.getID(), "Присоединиться к игроку " + user.getName(), new String[] { "Да", "Нет"}));
			return messages;
		}
		
		messages.addMessage(new BotMessage(user.getID(), Phrases.DUEL_HELP, getUserChosingAnswers(currentUsers)));
		return messages;
	}
	
	private Messages processGameState(String input) {
		return duel.processGameState(input, user);
	}
	
	private Messages processRequest(String input) {
		if ("Да".equals(input)) {
			user.setOpponent(user.getRequest().getSender());
			duel = user.getRequest().getDuel();
			type = DuelStateType.GAME;
			user.getRequest().accept();
			user.resetRequest();
			return duel.getFirstQuestion(user);
		}
		User opponent = user.getRequest().getSender(); 
		opponent.resetOpponent();
		user.resetRequest();
		Messages messages = new Messages();
		messages.addMessage(new BotMessage(user.getID(), "Вы отказались от дуэли", getUserChosingAnswers(currentUsers)));
		messages.addMessage(new BotMessage(opponent.getID(), "Оппонент отказался от дуэли", getUserChosingAnswers(currentUsers)));
		return messages;
	}
	
	private Messages processOutgoingRequest(String input) {
		if (sendedRequest.isAccepted()) {
			type = DuelStateType.GAME;
			return duel.processGameState(input, user);
		}
		
		user.resetOpponent();
		clear();
		return processWaitingState(input);
	}
	
	private void clear() {
		type = DuelStateType.WAITING;
		duel.questionGenerator.reset();
		duel.isOver = false;
		//start();
	}

	@Override
	public Messages processHelp() {
		BotMessage helpMessage = new BotMessage(user.getID(), Phrases.DUEL_HELP, new String[0]);
		helpMessage.keepPreviousAnswers();
		return new Messages(helpMessage);
	}


}
