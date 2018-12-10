package telegram;

import java.util.concurrent.ConcurrentHashMap;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import storage.FilmDatabase;
import structures.User;

public class TelegramBot extends TelegramLongPollingBot {

	private String bot_username;
	private String bot_token;
	private ConcurrentHashMap<Long, User> users;
	private FilmDatabase database;	


	public TelegramBot(FilmDatabase database, String username, String token) {
		this.bot_username = username;
		this.bot_token = token;
		this.database = database;
		users = new ConcurrentHashMap<Long, User>();		
	}

	public TelegramBot(FilmDatabase database, String username, String token, DefaultBotOptions options) {
		super(options);
		this.bot_username = username;
		this.bot_token = token;
		this.database = database;
		users = new ConcurrentHashMap<Long, User>();
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
	
	private SendMessage communicate(Message inputMessage) {
		String inputCommand = inputMessage.getText();
		Long id = inputMessage.getChatId();
		String userFirstName = inputMessage.getFrom().getFirstName();
		SendMessage message = new SendMessage();
		
		users.putIfAbsent(id, new User(userFirstName));
		User user = users.get(id);
		
		user.updateName(userFirstName);
		

		System.out.println(userFirstName + ": " + inputCommand);
		
		State state = getState(user);
		state.processInput(inputCommand);
		
		String answer = state.answerString;

		message.setText(answer);
		message.setReplyMarkup(state.getKeyboard());

		message.setChatId(inputMessage.getChatId());
		return message;
	}
	
	public String getAnswerText(Message inputMessage) {
		return communicate(inputMessage).getText();
	}
		

	public State getState(User user) {	
		State currentState = new State(user, database);
		return currentState;
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
