package game;

import java.util.ArrayList;
import java.util.List;

import game.Question;
import storage.IQuestionGenerator;

public class TestQuestionGenerator implements IQuestionGenerator {

	private List<Question> questionList;
	private int counter;
	
	public TestQuestionGenerator() {
		questionList = new ArrayList<Question>();
	}
	
	
	public Question getNextQuestion() {
		if (counter < questionList.size()) {
			Question result = questionList.get(counter);
			counter++;
			return result;
		}		
		return null;
	}
	
	public void addQuestion(Question question) {
		questionList.add(question);
	}


	@Override
	public Question getCurrentQuestion() {
		if (counter != 0)
			return questionList.get(counter - 1);
		return null;
	}


	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
