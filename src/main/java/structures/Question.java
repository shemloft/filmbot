package structures;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.lang3.tuple.Pair;

public class Question {
	
	private String question;
	private String[] options;
	private String correctAnswer;
	private Queue<Pair<Field, String>> hints;
	private String image;
	
	public Question(String question, String[] options, String correctAnswer, List<Pair<Field, String>> hints, String image) {
		this.question = question;
		this.options = options;
		this.correctAnswer = correctAnswer;
		this.hints = new LinkedList<>(hints);
		this.image = image;
	}
	
	public boolean isCorrect(String answer) {
		return answer.equals(correctAnswer);
	}
	
	public String getQuestion() {
		return question;
	}
	
	public String[] getOptions() {
		return options;
	}
	
	public boolean hasHint() {
		return !hints.isEmpty();
	}
	
	public Pair<Field, String> getHint() {
		return hints.poll();
	}
	
	public String getImage( ) {
		return image;
	}

}
