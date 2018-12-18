package structures;

import java.util.Arrays;
import java.util.stream.Stream;

public class BotMessage {
	
	private String messageText;	
	private String[] possibleAnswers;
	private String image;
	private Long addresserID;
	private boolean needToKeepPreviousAnswers;
	
	public BotMessage(Long addresserID, String messageText) {
		this(addresserID, messageText, null, null);
	}
	
	public BotMessage(Long addresserID, String messageText, String[] possibleAnswers) {
		this(addresserID, messageText, possibleAnswers, null);
	}
	
	public BotMessage(Long addresserID, String messageText, String[] possibleAnswers, String image) {
		this.messageText = messageText;
		this.possibleAnswers = possibleAnswers;
		this.image = image;
		this.addresserID = addresserID;
	}
	
	public Long getId() {
		return addresserID;
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
	
	public boolean needToKeepPreviousAnswers() {
		return needToKeepPreviousAnswers;
	}
	
	public void keepPreviousAnswers() {
		needToKeepPreviousAnswers = true;
	}

}
