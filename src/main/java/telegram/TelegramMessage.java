package telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

public class TelegramMessage {
	private SendMessage sendMessage;
	private SendPhoto sendPhoto;
	
	public TelegramMessage() {
		
	}
	
	public void setSendMessage(SendMessage message) {
		this.sendMessage = message;
	}
	
	public void setSendPhoto(SendPhoto photo) {
		this.sendPhoto = photo;
	}
	
	public boolean hasSendMessage() {
		return sendMessage != null;
	}
	
	public boolean hasSendPhoto() {
		return sendPhoto != null;
	}
	
	public SendMessage getSendMessage() {
		return sendMessage;
	}
	
	public SendPhoto getSendPhoto() {
		return sendPhoto;
	}
}
