package telegram;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import structures.BotMessage;

public class Converter {
	public static SendMessage convertToSendMessage(BotMessage botMessage) {
		SendMessage message = new SendMessage();
		message.setText(botMessage.messageText);
		message.setReplyMarkup(getKeyboard(botMessage.possibleAnswers));
		return message;
	}
	
	private static ReplyKeyboardMarkup getKeyboard(String[] buttons) {
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
	
	public static SendPhoto convertToSendPhoto(BotMessage botMessage) {
		if (botMessage.hasImage()) {
			SendPhoto sendPhoto = new SendPhoto();
			sendPhoto.setPhoto(botMessage.image);
			return sendPhoto;
		}
		
		return null;
	}
}
