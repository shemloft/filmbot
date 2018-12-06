package telegram;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
		SendMessage message = communicate(inputMessage);

		try {
			execute(message);
		} catch (TelegramApiException e) {
//			e.printStackTrace();
		}
	}
	
	public SendMessage communicate(Message inputMessage) {
		String input = inputMessage.getText();
		Long id = inputMessage.getChatId();
		String username = inputMessage.getFrom().getFirstName();
		
		System.out.println(username + ": " + input);
			
		SendMessage message = usersData
				.getAnswer(id, username, input)
				.convertToSendMessage();

		message.setChatId(inputMessage.getChatId());
		return message;
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
