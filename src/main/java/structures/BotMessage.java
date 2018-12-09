package structures;

public class BotMessage {
	
	public String messageText;	
	public String[] possibleAnswers;
	public String image;
	
	public BotMessage(String messageText, String[] possibleAnswers) {
		this(messageText, possibleAnswers, null);
	}
	
	public BotMessage(String messageText, String[] possibleAnswers, String image) {
		this.messageText = messageText;
		this.possibleAnswers = possibleAnswers;
		this.image = image;
	}
	
	public boolean hasImage() {
		return image != null;
	}

}
