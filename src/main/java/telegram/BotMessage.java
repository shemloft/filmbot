package telegram;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public class BotMessage {
	
	public String messageText;	
	public String[] possibleAnswers;
	
	public BotMessage(String messageText, String[] possibleAnswers) {
		this.messageText = messageText;
		this.possibleAnswers = possibleAnswers;
	}
	
	public SendMessage convertToSendMessage() {
		SendMessage message = new SendMessage();
		message.setText(messageText);
		message.setReplyMarkup(getKeyboard(possibleAnswers));
		return message;
	}
	
	private ReplyKeyboardMarkup getKeyboard(String[] buttons) {
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
		for (String button : buttons) {
			KeyboardRow row = new KeyboardRow();
			row.add(button);
			keyboard.add(row);
		}
		keyboardMarkup.setKeyboard(keyboard);
		return keyboardMarkup;

	}

}
