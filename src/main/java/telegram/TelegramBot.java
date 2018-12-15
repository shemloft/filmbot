package telegram;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import structures.Messages;

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
		System.out.println("Starting bot");
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
			e.printStackTrace();
		}
	}
	
	public TelegramMessage[] communicate(Message inputMessage) {
		String input = inputMessage.getText();
		Long id = inputMessage.getChatId();
		String username = inputMessage.getFrom().getFirstName();
		
		System.out.println(username + ": " + input);
		
		Messages messages = usersData.getAnswer(id, username, input);
		
		return Converter.convertToTelegramMessageArray(messages, id);
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
