package game;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Question {
	
	private String question;
	private List<String> options;
	private String correctAnswer;
	private Queue<Hint> hints;
	private String image;
	
	public Question(String question, List<String> options, String correctAnswer, List<Hint> hints, String image) {
		this.question = question;
		this.options = new LinkedList<>(options);
		this.correctAnswer = correctAnswer;
		this.hints = new LinkedList<>(hints);
		this.image = image;
	}
	
	public Question(String question, List<String> options) {
		this(question, options, null, new LinkedList<Hint>(), null);
	}
	
	public boolean isCorrect(String answer) {
		return answer.equals(correctAnswer);
	}
	
	public String getQuestion() {
		return question;
	}
	
	public String[] getOptions() {
		return options.toArray(new String[options.size()]);
	}
	
	public boolean hasHint() {
		return !hints.isEmpty();
	}
	
	public Hint getHint() {
		return hints.poll();
	}
	
	public String getImage( ) {
		return image;
	}
	
	public boolean isOption(String answer) {
		return options.contains(answer);
	}
	
	public void excludeOption(String option) {
		if (isOption(option)) {
			options.remove(option);
		}
	}
	
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	
	public String toString() {
		return correctAnswer;
	}

}
