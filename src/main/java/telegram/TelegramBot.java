package telegram;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import structures.BotMessage;

public class TelegramBot extends TelegramLongPollingBot {

	private String bot_username;
	private String bot_token;
	private UsersData usersData;


	public TelegramBot(UsersData usersData, String username, String token) {
		this.bot_username = username;
		this.bot_token = token;
		this.usersData = usersData;
	}

	public TelegramBot(UsersData usersData, String username, String token, DefaultBotOptions options) {
		super(options);
		this.bot_username = username;
		this.bot_token = token;
		this.usersData = usersData;
	}

	@Override
	public void onUpdateReceived(Update update) {

		Message inputMessage = update.getMessage();
		TelegramMessage[] messages = communicate(inputMessage);

		try {
			for (TelegramMessage message : messages) {
				if (message.hasSendMessage()) {
					execute(message.getSendMessage());
				}
				if (message.hasSendPhoto()) {
					execute(message.getSendPhoto());
				}
			}
		} catch (TelegramApiException e) {
//			e.printStackTrace();
		}
	}
	
	public TelegramMessage[] communicate(Message inputMessage) {
		String input = inputMessage.getText();
		Long id = inputMessage.getChatId();
		String username = inputMessage.getFrom().getFirstName();
		
		System.out.println(username + ": " + input);
		
		BotMessage[] answers = usersData.getAnswer(id, username, input);
		TelegramMessage[] result = new TelegramMessage[answers.length];
		
		for (int i = 0; i < answers.length; i++) {	
			result[i] = new TelegramMessage();
			SendMessage sendMessage = Converter.convertToSendMessage(answers[i]);
			sendMessage.setChatId(inputMessage.getChatId());
			result[i].setSendMessage(sendMessage);
			
			SendPhoto sendPhoto = Converter.convertToSendPhoto(answers[i]);
			if (sendPhoto != null) {
				sendPhoto.setChatId(inputMessage.getChatId());
				result[i].setSendPhoto(sendPhoto);
			}
		}
		
		return result;
	}	

	@Override
	public String getBotUsername() {
		return bot_username;
	}

	@Override
	public String getBotToken() {
		return bot_token;
	}

}
