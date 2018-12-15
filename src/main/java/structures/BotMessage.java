package structures;

import java.util.Arrays;
import java.util.stream.Stream;

public class BotMessage {
	
	private String messageText;	
	private String[] possibleAnswers;
	private String image;
	
	public BotMessage(String messageText) {
		this(messageText, null, null);
	}
	
	public BotMessage(String messageText, String[] possibleAnswers) {
		this(messageText, possibleAnswers, null);
	}
	
	public BotMessage(String messageText, String[] possibleAnswers, String image) {
		this.messageText = messageText;
		this.possibleAnswers = possibleAnswers;
		this.image = image;
	}
	
	public String getText() {
		return messageText;
	}
	
	public String[] getPossibleAnswers() {
		return possibleAnswers;
	}
	
	public String getImage() {
		return image;		
	}
	
	public boolean hasImage() {
		return image != null;
	}
	
	public boolean hasPossibleAnswers() {
		return possibleAnswers != null;
	}
	
	public void addPossibleAnswer(String answer) {
		possibleAnswers = Stream.concat(Arrays.stream(possibleAnswers), Stream.of(answer)).toArray(String[]::new);
	}
	
	public void setPossibleAnswers(String[] answers) {
		possibleAnswers = answers;
	}

}
