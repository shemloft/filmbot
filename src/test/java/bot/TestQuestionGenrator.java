package bot;

import java.util.ArrayList;
import java.util.List;

import storage.IQuestionGenerator;
import structures.Question;

public class TestQuestionGenrator implements IQuestionGenerator {

	private List<Question> questionList;
	private int counter;
	
	public TestQuestionGenrator() {
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

}
