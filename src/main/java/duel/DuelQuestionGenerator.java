package duel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import game.IQuestionDatabase;
import game.Question;
import storage.IQuestionGenerator;

public class DuelQuestionGenerator implements IQuestionGenerator{
	private List<Question> questions;
	private int counter;
	private IQuestionDatabase database;
	private int count;
	
	public DuelQuestionGenerator(IQuestionDatabase database, int questionsCount) {
		this.database = database;
		this.count = questionsCount;
		setQuestions();
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
	
	private void setQuestions() {
		List<Question> allQuestions = new ArrayList<Question>(database.getAllQuestions());
		Collections.shuffle(allQuestions);
		this.questions = new ArrayList<Question>(allQuestions.subList(0, count));
		Collections.shuffle(this.questions);
		System.out.println(questions.size());
	}

	@Override
	public void reset() {
		counter = 0;
		setQuestions();
	}

}
