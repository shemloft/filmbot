package bot;

import java.util.ArrayList;

import dialog.Phrases;
import storage.IQuestionGenerator;
import structures.BotMessage;
import structures.Hint;
import structures.Messages;
import structures.Question;
import structures.User;

public class GameState implements IState{
	
	private IQuestionGenerator generator;
	private Question currentQuestion;
	private boolean firstTime;
	private User user;
	private boolean noMoreQuestions;
	public static final int noHintPoints = 7;
	public static final int oneHintPoints = 3;
	public static final int twoHintPoints = 1;
	private int currentPoints;
	
	public GameState(User user, IQuestionGenerator generator) {
		this.generator = generator;
		this.currentQuestion = generator.getNextQuestion();
		this.firstTime = true;
		this.user = user;
		this.noMoreQuestions = false;
	}

	@Override
	public Messages getAnswer(String input) {	
		Messages messages = new Messages();
			
		if (noMoreQuestions) {
			messages.addMessage(new BotMessage(Phrases.NO_MORE_QUESTIONS, new String[0]));
			return messages;
		}
		
		if (firstTime) {
			firstTime = false;
			currentPoints = noHintPoints;
			messages.addMessage(new BotMessage(Phrases.GAME_HELP, currentQuestion.getOptions()));
			messages.addMessage(new BotMessage(currentQuestion.getQuestion(), currentQuestion.getOptions(), currentQuestion.getImage()));
			return messages;
		}
		
		if (input.equals("/help")) {
			messages.addMessage(new BotMessage(Phrases.GAME_HELP,  currentQuestion.getOptions()));
			return messages;			
		}
		
		
		if (currentQuestion.isCorrect(input)) {
			int earnedPoints = currentPoints;
			user.addPoints(earnedPoints);
			getNextQuestion();
			messages.addMessage(new BotMessage(
							Phrases.CORRECT_ANSWER + Phrases.earnedPointsText(earnedPoints, user.getPoints()), 
							currentQuestion.getOptions()));
			messages.addMessage(new BotMessage(currentQuestion.getQuestion(), currentQuestion.getOptions(), currentQuestion.getImage()));
			return messages;
		}
		else {
			currentQuestion.excludeOption(input);
			currentPoints = currentPoints == oneHintPoints ? twoHintPoints : oneHintPoints;
		}
		
		if (currentQuestion.hasHint()) {			
			Hint hint = currentQuestion.getHint();
			messages.addMessage(new BotMessage(Phrases.INCORRECT_ANSWER, currentQuestion.getOptions()));
			messages.addMessage(new BotMessage(hint.getStringValue(), currentQuestion.getOptions()));
			return messages;
		}
		
		getNextQuestion();
		messages.addMessage(new BotMessage(
				Phrases.INCORRECT_ANSWER + Phrases.earnedPointsText(0, user.getPoints()), 
				currentQuestion.getOptions()));
		messages.addMessage(new BotMessage(currentQuestion.getQuestion(), currentQuestion.getOptions(), currentQuestion.getImage()));
		return messages;
	}

	@Override
	public String getName() {
		return "Сыграть в игру";
	}
	
	public void getNextQuestion() {
		currentPoints = noHintPoints;
		currentQuestion = generator.getNextQuestion();
		if (currentQuestion == null) {
			noMoreQuestions = true;
			currentQuestion = new Question(Phrases.NO_MORE_QUESTIONS, new ArrayList<String>());
		}
	}

	@Override
	public StateType getType() {
		return StateType.DIALOG;
	}

}
