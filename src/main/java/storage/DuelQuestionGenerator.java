package storage;

import java.util.ArrayList;
import java.util.List;

import structures.Question;

public class DuelQuestionGenerator implements IQuestionGenerator{
	private List<Question> questions;
	private int counter;
	
	public DuelQuestionGenerator(QuestionDatabase database, int questionsCount) {
		this.questions = new ArrayList<Question>(database.getAllQuestions().subList(0, questionsCount));
		System.out.println(questions.size());
		this.counter = 0;
	}
	
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	public Question getCurrentQuestion() {
		if (counter != 0)
			return questions.get(counter - 1);
		return null;
	}

	@Override
	public Question getNextQuestion() {
		
		if (counter < questions.size()) {
			Question result = questions.get(counter);
			counter++;
			return result;
		}
		
		return null;
	}

}
