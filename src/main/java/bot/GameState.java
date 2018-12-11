package bot;

import java.util.ArrayList;

import dialog.Phrases;
import storage.IQuestionGenerator;
import structures.BotMessage;
import structures.Hint;
import structures.Question;
import structures.User;

public class GameState implements IState{
	
	private IQuestionGenerator generator;
	private Question currentQuestion;
	private boolean firstTime;
	private User user;
	private boolean noMoreQuestions;
	private final int noHintPoints = 7;
	private final int oneHintPoints = 3;
	private final int twoHintPoints = 1;
	private int currentPoints;
	
	public GameState(User user, IQuestionGenerator generator) {
		this.generator = generator;
		this.currentQuestion = generator.getNextQuestion();
		this.firstTime = true;
		this.user = user;
		this.noMoreQuestions = false;
	}

	@Override
	public BotMessage[] getAnswer(String input) {		 
		
		if (noMoreQuestions) {
			return new BotMessage[] {new BotMessage(Phrases.NO_MORE_QUESTIONS, new String[0])};
		}
		
		if (firstTime) {
			firstTime = false;
			currentPoints = noHintPoints;
			return new BotMessage[] {
					new BotMessage(Phrases.GAME_HELP, currentQuestion.getOptions()),
					new BotMessage(currentQuestion.getQuestion(), currentQuestion.getOptions(), currentQuestion.getImage())};
		}
		
		if (input.equals("/help")) {
			return new BotMessage[] {new BotMessage(Phrases.GAME_HELP, currentQuestion.getOptions())};
		}
		
		
		if (currentQuestion.isCorrect(input)) {
			int earnedPoints = currentPoints;
			user.addPoints(earnedPoints);
			getNextQuestion();
			return new BotMessage[]{
					new BotMessage(
							Phrases.CORRECT_ANSWER + Phrases.earnedPointsText(earnedPoints, user.getPoints()), 
							currentQuestion.getOptions()),
					new BotMessage(currentQuestion.getQuestion(), currentQuestion.getOptions(), currentQuestion.getImage())};
		}
		else {
			currentQuestion.excludeOption(input);
			currentPoints = currentPoints == oneHintPoints ? twoHintPoints : oneHintPoints;
		}
		
		if (currentQuestion.hasHint()) {
			
			Hint hint = currentQuestion.getHint();			
			
			return new BotMessage[] {
					new BotMessage(Phrases.INCORRECT_ANSWER, currentQuestion.getOptions()),
					new BotMessage(hint.getStringValue(), currentQuestion.getOptions()) };
		}
		
		getNextQuestion();
		return new BotMessage[] {
				new BotMessage(Phrases.INCORRECT_ANSWER + Phrases.earnedPointsText(0, user.getPoints()), currentQuestion.getOptions()),
				new BotMessage(currentQuestion.getQuestion(), currentQuestion.getOptions(), currentQuestion.getImage())
		};
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

}
