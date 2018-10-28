package bot;

import java.util.List;
import java.util.Map;

import storage.CSVHandler;
import storage.Database;
import storage.DatabaseFilmHandler;
import storage.FilmHandler;
import structures.Field;
import structures.Film;
import telegram.TelegramChatBot;
import utils.FilmUtils;

public class Main {

	private static Map<Field, Map<String, List<Film>>> filmMapsByField;

	public static void main(String[] args) throws Exception {
		Database database = new CSVHandler("Database");
		FilmHandler filmHandler = new DatabaseFilmHandler(database);
		filmMapsByField = FilmUtils.getFilmMapsByField(filmHandler.getFilmList());
		
		startTelegramBot();


	}

	public static void startTelegramBot() throws Exception {
		TelegramChatBot bot = new TelegramChatBot(filmMapsByField);
		bot.startTelegramChatBot();

	}

	public static void startConsoleBot() throws Exception {
		ChatBot chatBot = new ChatBot(filmMapsByField);
		chatBot.startChat(System.in, System.out);
	}

}
