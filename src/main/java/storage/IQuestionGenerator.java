package storage;

import game.Question;

public interface IQuestionGenerator {
	public Question getNextQuestion();
	
	public Question getCurrentQuestion();
	
	public void reset();
}
