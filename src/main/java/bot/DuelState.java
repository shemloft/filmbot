package bot;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dialog.Phrases;
import storage.DuelQuestionGenerator;
import storage.IQuestionGenerator;
import storage.UserDatabase;
import structures.BotMessage;
import structures.Messages;
import structures.Question;
import structures.User;

public class DuelState implements IState {
	private UserDatabase userDatabase;
	private User user;
//	private boolean firstTime;
	private User[] currentUsers;
	private User opponent;
	private boolean gameInProgress;
	private IQuestionGenerator questionGenerator;

	public DuelState(User user, UserDatabase userDatabase, IQuestionGenerator questionGenerator) {
		this.userDatabase = userDatabase;	
		this.user = user;
		this.questionGenerator = questionGenerator;
//		firstTime = true;
	}

	@Override
	public Messages getAnswer(String input) {
		Messages messages = new Messages();
		//null если еще не было вопроса
		Question question = ((DuelQuestionGenerator)questionGenerator).getCurrentQuestion();
		
		if (opponent != null && !opponent.inDuel) {
			messages.addMessage(new BotMessage(
					user.getID(), 
					"Опонент вышел. " + Phrases.winOrLose(user.getDuelPoints(), opponent.getDuelPoints()), 
					new String[0]));
			return messages;
		}
		
		if (question != null && question.isOption(input)) {
			user.answeredInDuel = true;
			if (question.isCorrect(input))
				user.correctAnsweredInDuel = true;
			if (opponent.answeredInDuel) {
				Question nextQuestion = questionGenerator.getNextQuestion();
				String userMessage;
				String opponentMessage;
				if (opponent.correctAnsweredInDuel) {
					opponent.addDuelPoints(1);
					userMessage = Phrases.FAIL + Phrases.earnedPointsText(0, user.getDuelPoints());
					opponentMessage = Phrases.WIN + Phrases.earnedPointsText(1, opponent.getDuelPoints());				
				} else if (user.correctAnsweredInDuel) {
					user.addDuelPoints(1);
					opponentMessage = Phrases.FAIL + Phrases.earnedPointsText(0, user.getDuelPoints());
					userMessage = Phrases.WIN + Phrases.earnedPointsText(1, opponent.getDuelPoints());	
				} else {
					opponentMessage = Phrases.DRAFT + Phrases.earnedPointsText(0, user.getDuelPoints());
					userMessage = Phrases.DRAFT + Phrases.earnedPointsText(0, opponent.getDuelPoints());	
				}
				user.answeredInDuel = false;
				user.correctAnsweredInDuel = false;
				opponent.answeredInDuel = false;
				opponent.correctAnsweredInDuel = false;
				messages.addMessage(new BotMessage(user.getID(), userMessage, nextQuestion.getOptions()));
				messages.addMessage(new BotMessage(opponent.getID(), opponentMessage, nextQuestion.getOptions()));
				
				messages.addMessage(new BotMessage(user.getID(), question.getQuestion(), nextQuestion.getOptions(), nextQuestion.getImage()));				
				messages.addMessage(new BotMessage(opponent.getID(), question.getQuestion(), nextQuestion.getOptions(), nextQuestion.getImage()));
				return messages;
				
			} else {
				messages.addMessage(new BotMessage(user.getID(), "Ждём опонента", question.getOptions()));
				return messages;
			}			
		}
		
		if (input.equals("/help")) {
			user.inDuel = true;
			User[] users = usersInDuel();
			currentUsers = users;			
			messages.addMessage(new BotMessage(user.getID(), Phrases.DUEL_HELP, getUserChosingAnswers(users)));
			return messages;
		}
		
		if (input.equals(Phrases.REFRESH)) {
			User[] users = usersInDuel();
			currentUsers = users;
			messages.addMessage(new BotMessage(user.getID(), Phrases.REFRESH, getUserChosingAnswers(users)));
			return messages;
		}
		
		if (getUsersNames(currentUsers).contains(input) && !gameInProgress) {
			opponent = currentUsers[getUsersNames(currentUsers).indexOf(input)];
			user.setOpponent(opponent);
			if (opponent.getOpponent() != null) {
				gameInProgress = true;
				userDatabase.setUserState(opponent, new DuelState(opponent, userDatabase, questionGenerator));
				Question firstQuestion = questionGenerator.getNextQuestion();
				messages.addMessage(new BotMessage(user.getID(), "Ваш опонент: " + opponent.getName(), firstQuestion.getOptions()));
				messages.addMessage(new BotMessage(user.getID(), firstQuestion.getQuestion(), firstQuestion.getOptions(), firstQuestion.getImage()));
				messages.addMessage(new BotMessage(opponent.getID(), "Ваш опонент: " + user.getName(), firstQuestion.getOptions()));
				messages.addMessage(new BotMessage(opponent.getID(), firstQuestion.getQuestion(), firstQuestion.getOptions(), firstQuestion.getImage()));
				return messages;
				
			}
			messages.addMessage(new BotMessage(user.getID(), "Ждём опонента", getUserChosingAnswers(currentUsers)));
			return messages;
		}	

		messages.addMessage(new BotMessage(user.getID(), "Hmmmmmmm.", new String[0])); // дописать побела или поравжение
		return messages;

	}

	@Override
	public String getName() {
		return "Дуэль";
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
	public String processExit() {
		user.inDuel = false;		
		int opponentPoints = opponent.getDuelPoints();
		opponent = null;
		gameInProgress = false;
		return "Вы вышли. " + Phrases.winOrLose(user.getDuelPoints(), opponentPoints);// написать победил или проиград
	}

}
