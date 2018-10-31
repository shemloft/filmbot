package telegram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import dialog.Dialog;
import dialog.Phrases;
import structures.Field;
import structures.Film;
import structures.User;
import utils.UserUtils;

public class TelegramBot extends TelegramLongPollingBot {

	private String bot_username;
	private String bot_token;
	Map<Field, Map<String, List<Film>>> filmMapsByField;
	private Map<String, Field> idFieldMap;

	public TelegramBot(Map<Field, Map<String, List<Film>>> filmMapsByField, String username, String token) {
		this.bot_username = username;
		this.bot_token = token;
		this.filmMapsByField = filmMapsByField;
		idFieldMap = new HashMap<String, Field>();
	}
	
	public TelegramBot(Map<Field, Map<String, List<Film>>> filmMapsByField, String username, String token,
			DefaultBotOptions options) {
		super(options);
		this.bot_username = username;
		this.bot_token = token;
		this.filmMapsByField = filmMapsByField;
		idFieldMap = new HashMap<String, Field>();
	}

	private String processInput(String input, String username, String chatId) throws Exception {
		User user = UserUtils.getUser(username, chatId);
		Dialog dialog = new Dialog(user, filmMapsByField);
		String answer;
		if (input.equals("/start"))
			answer = dialog.startDialog();
		else
			answer = dialog.processInput(input);
		UserUtils.saveUser(user);
		return answer;
	}

	@Override
	public void onUpdateReceived(Update update) {

		Message inputMessage = update.getMessage();
		String inputCommand = inputMessage.getText();
		String id = inputMessage.getChatId().toString();
		String userFirstName = inputMessage.getFrom().getFirstName();
		SendMessage message = new SendMessage();

		System.out.println(userFirstName + ": " + inputCommand);

		String answer = getAnswer(inputCommand, id, userFirstName);
		
		message.setText(answer);
		message.setReplyMarkup(answer.equals("Теперь ето") ? getFieldsKeyboard(idFieldMap.get(id)) : getStartKeyboard());	
		
		message.setChatId(inputMessage.getChatId());

		try {
			execute(message);
		} catch (TelegramApiException e) {
			//e.printStackTrace();
		}
	}
	
	public String getAnswer(String inputCommand, String id, String userFirstName) {
		String chatBotAnswer = "";		
		if (Arrays.toString(Field.values()).contains(inputCommand)) {
			Field field = Field.valueOf(inputCommand);
			idFieldMap.put(id, field);
			chatBotAnswer = "Теперь ето";
		} else if (idFieldMap.containsKey(id) && idFieldMap.get(id) != null
				&& Arrays.asList(idFieldMap.get(id).avaliableFields()).contains(inputCommand)) {
			String command = idFieldMap.get(id).shortCut() + " " + inputCommand;
			idFieldMap.put(id, null);
			try {
				chatBotAnswer = processInput(command, userFirstName, id);
			} catch (Exception e) {
//				e.printStackTrace();
			}
		} else {
			String command = inputCommand.equals("NEXT") ? "/next" : inputCommand;
			try {
				chatBotAnswer = processInput(command, userFirstName, id);
			} catch (Exception e) {
//				e.printStackTrace();
			}
		}		
		return chatBotAnswer;		
	}

	public ReplyKeyboardMarkup getFieldsKeyboard(Field field) {
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		List<KeyboardRow> keyboard = new ArrayList<>();
		String[] buttons = null;
		switch (field) {
		case COUNTRY:
			buttons = Phrases.COUNTRIES;
			break;
		case GENRE:
			buttons = Phrases.GENRES;
			break;
		case YEAR:
			buttons = Phrases.YEARS;
			break;
		}
		for (String button : buttons) {
			KeyboardRow row = new KeyboardRow();
			row.add(button);
			keyboard.add(row);
		}
		keyboardMarkup.setKeyboard(keyboard);
		return keyboardMarkup;
	}

	public ReplyKeyboardMarkup getStartKeyboard() {
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		List<KeyboardRow> keyboard = new ArrayList<>();
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

	@Override
	public String getBotUsername() {
		return bot_username;
	}

	@Override
	public String getBotToken() {
		return bot_token;
	}

}
