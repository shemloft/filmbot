package telegram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import dialog.Phrases;
import storage.FilmDatabase;
import structures.Field;
import structures.Film;
import structures.User;

public class State {

	private DialogState currentState;
	private FilmDatabase database;

	private ReplyKeyboardMarkup keyboard;

	public String answerString;
	
	private User user;

	public State(User user, FilmDatabase database) {
		this.user = user;
		this.currentState = user.currentState();
		this.database = database;
	}

	public void processInput(String input) {
		switch(input) {
		case "NEXT":
			user.nowGettingFilm();
			keyboard = getBasicKeyboard();
			user.printCurOpt();
			onCompletedRequest();
			return;
		case "/help":
			answerString = Phrases.HELP;
			user.clearData();	
			return;
		case "/start":
			user.clearData();
			answerString = user.firstTime 
					? String.format("Добро пожаловать, %s.%s", user.getName(), Phrases.HELP) 
					: String.format("Давно не виделись, %s.", user.getName());
			break;
		}	

		switch (currentState) {
		case BASIC:
			processBasicState(input);
			break;
		case CHOSING:
			processChosingState(input);
			break;
		case MORE_OPTIONS:
			processMoreOptionsState(input);
			break;
		}
		
		if (user.requestComplete) {
			onCompletedRequest();
		}
	}
	
	private void onCompletedRequest() {
		answerString = getResponse(database.getFilm(user), user);
		user.requestComplete = false;
	}

	private void processMoreOptionsState(String input) {
		if ("ЕЩЕ ОПЦИЯ".equals(input)) {
			keyboard = getBasicKeyboard();
			user.nowChosingMore();
			answerString = "Выберите еще опцию";
		} else if ("ПОЛУЧИТЬ ФИЛЬМ".equals(input)) {
			keyboard = getBasicKeyboard();
			user.nowGettingFilm();
		} else {
			answerString = Phrases.UNKNOWN_COMMAND;
			keyboard = getBasicKeyboard();
			user.clearData();
		}
		user.printCurOpt();
	}

	private void processChosingState(String input) {
		if (!database.requestExistInDatabase(user.currentField(), input)) {
			answerString = Phrases.UNKNOWN_COMMAND;
			keyboard = getBasicKeyboard();
			user.clearData();
		} else {
			user.nowAdding(input);
			answerString = "Есть еще параметры?";
			keyboard = getMoreOptionsKeyboard();
		}
		user.printCurOpt();
		
	}

	private void processBasicState(String input) {
		if (!Arrays.toString(Field.values()).contains(input)) {
			answerString = Phrases.UNKNOWN_COMMAND;
			keyboard = getBasicKeyboard();
			user.clearData();
		} else {
			if (!user.gettingMoreOptions)
				user.clearData();
			Field field = Field.valueOf(input);			
			user.nowChosing(field);
			answerString = field.nowChoose();
			keyboard = getChosingKeyboard(field);
		}
		user.printCurOpt();
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

	private ReplyKeyboardMarkup getChosingKeyboard(Field field) {
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
		String[] buttons = database.getFieldValuesArray(field);
		for (String button : buttons) {
			KeyboardRow row = new KeyboardRow();
			row.add(button);
			keyboard.add(row);
		}
		keyboardMarkup.setKeyboard(keyboard);
		return keyboardMarkup;

	}

	private ReplyKeyboardMarkup getMoreOptionsKeyboard() {
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
		KeyboardRow row = new KeyboardRow();
		row.add("ЕЩЕ ОПЦИЯ");
		keyboard.add(row);
		row = new KeyboardRow();
		row.add("ПОЛУЧИТЬ ФИЛЬМ");
		keyboard.add(row);
		keyboardMarkup.setKeyboard(keyboard);
		return keyboardMarkup;

	}

	private ReplyKeyboardMarkup getBasicKeyboard() {
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
		KeyboardRow row = new KeyboardRow();
		row.add("YEAR");
		keyboard.add(row);
		row = new KeyboardRow();
		row.add("GENRE");
		keyboard.add(row);
		row = new KeyboardRow();
		row.add("NEXT");
		keyboard.add(row);
		keyboardMarkup.setKeyboard(keyboard);
		return keyboardMarkup;
	}

	public ReplyKeyboardMarkup getKeyboard() {
		return keyboard;
	}

}