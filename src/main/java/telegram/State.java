package telegram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import storage.FilmDatabase;
import structures.Field;
import utils.FilmUtils;

public class State {

	private DialogState currentState;
	public DialogState newState;
	private Map<Field, List<String>> currentIdFieldMap;
	private FilmDatabase database;

	private ReplyKeyboardMarkup keyboard;

	public String command = null;
	public String answerString;
	public Field currentField;

	public State(DialogState state, Map<Field, List<String>> currentIdFieldMap, FilmDatabase database,
			Field currentField) {
		this.currentState = state;
		this.currentIdFieldMap = currentIdFieldMap;
		this.database = database;
		this.currentField = currentField;
	}

	public void processInput(String input) {
		if ("NEXT".equals(input)) {
			command = "/next";
			currentField = null;
			for (Field field : Field.values())
				currentIdFieldMap.remove(field);
			newState = DialogState.BASIC;
			keyboard = getBasicKeyboard();
			return;
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
	}

	private void processMoreOptionsState(String input) {
		if ("ЕЩЕ ОПЦИЯ".equals(input)) {
			keyboard = getBasicKeyboard();
			newState = DialogState.BASIC;
			answerString = "Выберите еще опцию";
		} else if ("ПОЛУЧИТЬ ФИЛЬМ".equals(input)) {
			keyboard = getBasicKeyboard();
			newState = DialogState.BASIC;
			currentField = null;
			command = FilmUtils.getCommand(currentIdFieldMap);
			for (Field field : Field.values())
				currentIdFieldMap.remove(field);
		} else {
			command = input;
			keyboard = getBasicKeyboard();
			newState = DialogState.BASIC;
			currentField = null;
		}
	}

	private void processChosingState(String input) {
		if (!database.requestExistInDatabase(currentField, input)) {
			command = input;
			keyboard = getBasicKeyboard();
			newState = DialogState.BASIC;
			currentField = null;
		} else {
			currentIdFieldMap.get(currentField).add(input);
			newState = DialogState.MORE_OPTIONS;
			answerString = "Есть еще параметры?";
			currentField = null;
			keyboard = getMoreOptionsKeyboard();
		}
	}

	private void processBasicState(String input) {
		if (!Arrays.toString(Field.values()).contains(input)) {
			command = input;
			keyboard = getBasicKeyboard();
			newState = DialogState.BASIC;
			currentField = null;
		} else {
			Field field = Field.valueOf(input);
			if (currentIdFieldMap.get(field) == null)
				currentIdFieldMap.put(field, new ArrayList<String>());
			currentField = field;
			newState = DialogState.CHOSING;
			answerString = field.nowChoose();
			keyboard = getChosingKeyboard(field);
		}
	}

	public State getNewState() {
		return new State(newState, currentIdFieldMap, database, currentField);
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
		row.add("COUNTRY");
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