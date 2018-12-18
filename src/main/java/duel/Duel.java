package duel;

import game.Question;
import storage.IQuestionGenerator;
import structures.BotMessage;
import structures.Messages;
import structures.Phrases;
import structures.User;

public class Duel {
	
	public boolean isOver;
	public IQuestionGenerator questionGenerator;
	
	public Duel(IQuestionGenerator questionGenerator) {
		isOver = false;
		this.questionGenerator = questionGenerator;
	}
	
	public Messages getFirstQuestion(User user) {
		Messages messages = new Messages();
		Question firstQuestion = questionGenerator.getNextQuestion();
		messages.addMessage(new BotMessage(user.getID(), 
				Phrases.YOUR_OPPONENT + user.getOpponent().getName(), 
				firstQuestion.getOptions()));
		messages.addMessage(new BotMessage(user.getID(), firstQuestion.getQuestion(), firstQuestion.getOptions(), firstQuestion.getImage()));
		messages.addMessage(new BotMessage(user.getOpponent().getID(), Phrases.YOUR_OPPONENT + user.getName(), firstQuestion.getOptions()));
		messages.addMessage(new BotMessage(user.getOpponent().getID(), firstQuestion.getQuestion(), firstQuestion.getOptions(), firstQuestion.getImage()));
		return messages;
	}
	
	public Messages processGameState(String input, User user) {
		Messages messages = new Messages();
		Question question = questionGenerator.getCurrentQuestion();
		User opponent = user.getOpponent();
		
		if (opponent != null && !opponent.inDuel) {
			return processOpponentLeave(user, opponent);
		}
		
		if (question != null && question.isOption(input)) {
			user.answeredInDuel = true;
			if (question.isCorrect(input))
				user.correctAnsweredInDuel = true;
			if (opponent.answeredInDuel) {
				Question nextQuestion = questionGenerator.getNextQuestion();
				DuelMessage message = scorePoints(user, opponent);
				String userMessage = message.userMessage;
				String opponentMessage = message.opponentMessage;
				
				prepareForNewAnswer(user, opponent);
				
				if (nextQuestion == null) {
					messages.addMessage(new BotMessage(user.getID(), userMessage, new String[0]));
					messages.addMessage(new BotMessage(opponent.getID(), opponentMessage, new String[0]));
					return processFinishGame(messages, user, opponent);
				}
				
				messages.addMessage(new BotMessage(user.getID(), userMessage, nextQuestion.getOptions()));
				messages.addMessage(new BotMessage(opponent.getID(), opponentMessage, nextQuestion.getOptions()));
				
				messages.addMessage(new BotMessage(user.getID(), question.getQuestion(), nextQuestion.getOptions(), nextQuestion.getImage()));				
				messages.addMessage(new BotMessage(opponent.getID(), question.getQuestion(), nextQuestion.getOptions(), nextQuestion.getImage()));
				return messages;
				
			} else {
				messages.addMessage(new BotMessage(user.getID(), Phrases.WAITING_FOR_OPPONENT, new String[0]));
				return messages;
			}			
		}

		return processFinishGame(messages, user, opponent);
	}
	
	private Messages processFinishGame(Messages messages, User user, User opponent) {
		messages.addMessage(new BotMessage(user.getID(),  Phrases.winOrLose(user.getDuelPoints(), opponent.getDuelPoints()), new String[0]));
		messages.addMessage(new BotMessage(opponent.getID(), Phrases.winOrLose(opponent.getDuelPoints(), user.getDuelPoints()), new String[0]));
		duelFinished(user, opponent);
		return messages;
	}
	
	public Messages processOpponentLeave(User user, User opponent) {
		Messages messages = new Messages();
		messages.addMessage(new BotMessage(
				user.getID(), 
				Phrases.OPPONENT_LEAVE + Phrases.comparePointsText(user.getDuelPoints(), opponent.getDuelPoints()), 
				new String[0]));
		duelFinished(user, opponent);
		return messages;
	}
	
	private void prepareForNewAnswer(User user, User opponent) {
		user.prepareForNewAnswerInDuel();
		opponent.prepareForNewAnswerInDuel();
	}
	
	private void duelFinished(User user, User opponent) {
		user.onDuelFinished();
		opponent.onDuelFinished();
		isOver = true;
	}
	
	private DuelMessage scorePoints(User user, User opponent) {		
		
		boolean opponentWin = opponent.correctAnsweredInDuel;
		boolean userWin = !opponentWin && user.correctAnsweredInDuel;
		
		String userStatusText = opponentWin ? Phrases.OPPONENT_FAST : (userWin ? Phrases.YOU_CORRECT : Phrases.BOTH_INCORRECT);
		String opponentStatusText = opponentWin ? Phrases.YOU_FAST : (userWin ? Phrases.OPPONENT_CORRECT : Phrases.BOTH_INCORRECT);
		
		int userPoints = userWin ? 1 : 0;
		int opponentPoints = opponentWin ? 1 : 0;
		
		opponent.addDuelPoints(opponentPoints);
		user.addDuelPoints(userPoints);
		
		String userMessage = Phrases.getDuelMessage(userStatusText, userPoints, user.getDuelPoints());
		String opponentMessage = Phrases.getDuelMessage(opponentStatusText, opponentPoints, opponent.getDuelPoints());;
					
		return new DuelMessage(userMessage, opponentMessage);
	}
}

class DuelMessage {
	public String userMessage;
	public String opponentMessage;
	
	public DuelMessage(String userMessage, String opponentMessage) {
		this.userMessage = userMessage;
		this.opponentMessage = opponentMessage;
	}
}
