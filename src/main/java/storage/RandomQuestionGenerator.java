package storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import structures.Question;

public class RandomQuestionGenerator implements IQuestionGenerator{
	
	private List<Question> questions;
	private int counter;
	
	public RandomQuestionGenerator(QuestionDatabase database) {
		this.questions = new ArrayList<Question>(database.getAllQuestions());
		this.counter = 0;
	}

	@Override
	public Question getNextQuestion() {
		if (counter == 0) {
			Collections.shuffle(this.questions);
		}
		
		if (counter < questions.size()) {
			Question result = questions.get(counter);
			counter++;
			return result;
		}
		
		return null;
	}

	@Override
	public Question getCurrentQuestion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
