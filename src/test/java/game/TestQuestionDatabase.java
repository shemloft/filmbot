package game;

import java.util.ArrayList;
import java.util.List;

public class TestQuestionDatabase implements IQuestionDatabase {

	private List<Question> questions;
	
	public TestQuestionDatabase() {
		questions = new ArrayList<Question>();
	}
	
	public void addQuestion(Question q) {
		questions.add(q);
	}
	
	
	@Override
	public List<Question> getAllQuestions() {		
		return questions;
	}

}
