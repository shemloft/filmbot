package telegram;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import dialog.Phrases;
import storage.FilmDatabase;
import structures.Field;
import structures.Film;
import structures.User;

public class TelegramBot extends TelegramLongPollingBot {

	private String bot_username;
	private String bot_token;
	private Map<String, User> users;
	private FilmDatabase database;	


	public TelegramBot(FilmDatabase database, String username, String token) {
		this.bot_username = username;
		this.bot_token = token;
		this.database = database;
		users = new HashMap<String, User>();		
	}

	public TelegramBot(FilmDatabase database, String username, String token, DefaultBotOptions options) {
		super(options);
		this.bot_username = username;
		this.bot_token = token;
		this.database = database;
		users = new HashMap<String, User>();
	}

	@Override
	public void onUpdateReceived(Update update) {

		Message inputMessage = update.getMessage();
		String inputCommand = inputMessage.getText();
		String id = inputMessage.getChatId().toString();
		String userFirstName = inputMessage.getFrom().getFirstName();
		SendMessage message = new SendMessage();
		
		User user = users.get(id);
		
		if (user == null) {
			user = new User(id, userFirstName);
			users.put(id, user);			
		}
		
		user.updateName(userFirstName);
		

		System.out.println(userFirstName + ": " + inputCommand);
		
		State state = getState(inputCommand, user);
		
		String answer = state.answerString;
		
		if (user.requestComplete) {
			answer = getResponse(database.getFilm(user), user);
			user.requestComplete = false;
		}
		

		message.setText(answer);
		message.setReplyMarkup(state.getKeyboard());

		message.setChatId(inputMessage.getChatId());

		try {
			execute(message);
		} catch (TelegramApiException e) {
			// e.printStackTrace();
		}
	}
	
	private String getResponse(Film film, User user) {
		if (film != null && film.ID == 0) // !!!!!!!!!
			return Phrases.NO_SUCH_FILM;
		else if (film != null)
			user.addFilm(film);
		else
			user.clearData();
		return film != null ? film.title : Phrases.NO_MORE_FILM;
	}
		

	public State getState(String input, User user) {	
		State currentState = new State(user, database);
		currentState.processInput(input);
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
