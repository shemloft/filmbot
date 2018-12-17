package storage;

import structures.Question;

public interface IQuestionGenerator {
	public Question getNextQuestion();
	
	public Question getCurrentQuestion();
	
	public void reset();
}
