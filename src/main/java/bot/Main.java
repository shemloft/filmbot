package bot;

import storage.FilmDatabase;
import storage.MovieApiHandler;
import storage.QuestionDatabase;
import storage.RandomQuestionGenerator;
import telegram.TelegramChatBot;
import telegram.UsersData;

public class Main {

	private static UsersData usersData;

	public static void main(String[] args) throws Exception {		
		String apikey = "ab2ffab6977110905d92c5979e9ae9fa";
//		String apikey = "<apikey>";	
		usersData = new UsersData(new BotFactory(
				new FilmDatabase(new MovieApiHandler(apikey)), 
				new RandomQuestionGenerator(new QuestionDatabase(apikey))));
		System.out.println("Starting bot");
		startTelegramBot();
	}

	public static void startTelegramBot() throws Exception {
		TelegramChatBot bot = new TelegramChatBot(usersData);
		bot.startTelegramChatBot();

	}

}
