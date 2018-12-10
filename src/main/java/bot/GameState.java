package bot;

import org.apache.commons.lang3.tuple.Pair;

import dialog.Phrases;
import storage.IQuestionGenerator;
import structures.BotMessage;
import structures.Field;
import structures.Question;
import structures.User;

public class GameState implements IState{
	
	private IQuestionGenerator generator;
	private Question currentQuestion;
	private boolean firstTime;
	private User user;
	private boolean noMoreQuestions;
	
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
			return new BotMessage[] {
					new BotMessage(Phrases.GAME_HELP, currentQuestion.getOptions()),
					new BotMessage(currentQuestion.getQuestion(), currentQuestion.getOptions(), currentQuestion.getImage())};
		}
		
		if (input.equals("/help")) {
			return new BotMessage[] {new BotMessage(Phrases.GAME_HELP, currentQuestion.getOptions())};
		}
		
		
		if (currentQuestion.isCorrect(input)) {
			getNextQuestion();
			return new BotMessage[]{
					new BotMessage(Phrases.CORRECT_ANSWER, currentQuestion.getOptions()),
					new BotMessage(currentQuestion.getQuestion(), currentQuestion.getOptions(), currentQuestion.getImage())};
		}
		else {
			currentQuestion.excludeOption(input);
		}
		
		if (currentQuestion.hasHint()) {
			Pair<Field, String> hint = currentQuestion.getHint();
			String hintAnswer = "";
			
			switch(hint.getKey()) {
			case GENRE:
				hintAnswer = Phrases.GENRE_HINT + hint.getValue();
				break;
			case YEAR:
				hintAnswer = Phrases.YEAR_HINT + hint.getValue();
				break;
			default:
				hintAnswer = hint.getValue();
				break;
			}
			
			return new BotMessage[] {
					new BotMessage(Phrases.INCORRECT_ANSWER, currentQuestion.getOptions()),
					new BotMessage(hintAnswer, currentQuestion.getOptions()) };
		}
		
		getNextQuestion();
		return new BotMessage[] {
				new BotMessage(Phrases.INCORRECT_ANSWER, currentQuestion.getOptions()),
				new BotMessage(currentQuestion.getQuestion(), currentQuestion.getOptions(), currentQuestion.getImage())
		};
	}

	@Override
	public String getName() {
		return "Сыграть в игру";
	}
	
	public void getNextQuestion() {
		currentQuestion = generator.getNextQuestion();
		if (currentQuestion == null) {
			noMoreQuestions = true;
			currentQuestion = new Question(Phrases.NO_MORE_QUESTIONS, new String[0]);
		}
	}

}
