package telegram;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import structures.BotMessage;
import structures.Messages;

public class Converter {
	public static TelegramMessage[] convertToTelegramMessageArray(Messages messages, Long id) {
		List<TelegramMessage> result = new ArrayList<TelegramMessage>();
		
		for (BotMessage message : messages) {
			TelegramMessage telegramMessage = new TelegramMessage();			
			
			SendMessage sendMessage = Converter.convertToSendMessage(message);			
			sendMessage.setChatId(id);
			telegramMessage.setSendMessage(sendMessage);			
			
			SendPhoto sendPhoto = Converter.convertToSendPhoto(message);
			if (sendPhoto != null) {
				sendPhoto.setChatId(id);
				telegramMessage.setSendPhoto(sendPhoto);
			}
			
			result.add(telegramMessage);
		}
		
		return result.toArray(new TelegramMessage[messages.count()]);
		
	}
	
	public static SendMessage convertToSendMessage(BotMessage botMessage) {
		SendMessage message = new SendMessage();
		message.setText(botMessage.getText());
		message.setReplyMarkup(getKeyboard(botMessage.getPossibleAnswers()));
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
			sendPhoto.setPhoto(botMessage.getImage());
			return sendPhoto;
		}
		
		return null;
	}
}
