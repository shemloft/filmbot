package bot;

import org.apache.commons.lang3.tuple.Pair;

import dialog.Phrases;
import storage.IQuestionGenerator;
import structures.BotMessage;
import structures.Field;
import structures.Question;

public class GameState implements IState{
	
	private IQuestionGenerator generator;
	private Question currentQuestion;
	
	public GameState(IQuestionGenerator generator) {
		this.generator = generator;
		this.currentQuestion = generator.getNextQuestion();
	}
	
	public GameState() {
		
	}

	@Override
	public BotMessage getAnswer(String input) {
		if (input.equals("/help")) {
			return new BotMessage(Phrases.HELP, currentQuestion.getOptions());
		}
		
		
		if (currentQuestion.isCorrect(input)) {
			currentQuestion = generator.getNextQuestion();
			return new BotMessage(currentQuestion.getQuestion(), currentQuestion.getOptions(), currentQuestion.getImage());
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
			
			return new BotMessage(hintAnswer, currentQuestion.getOptions());
		}
		
		currentQuestion = generator.getNextQuestion();
		return new BotMessage(currentQuestion.getQuestion(), currentQuestion.getOptions(), currentQuestion.getImage());
	}

	@Override
	public String getName() {
		return "Сыграть в игру";
	}

}
