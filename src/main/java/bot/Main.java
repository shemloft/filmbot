package bot;

import dialog.FilmDatabase;
import dialog.MovieApiHandler;
import game.ApiQuestionDatabase;
import game.IQuestionDatabase;
import telegram.TelegramChatBot;
import telegram.UsersData;

public class Main {

	private static UsersData usersData;

	public static void main(String[] args) throws Exception {		
		String apikey = "ab2ffab6977110905d92c5979e9ae9fa";
//		String apikey = "<apikey>";	
		IQuestionDatabase questionDatabase = new ApiQuestionDatabase(apikey);
		usersData = new UsersData(new BotFactory(
				new FilmDatabase(new MovieApiHandler(apikey)), 
				questionDatabase, 10));
		System.out.println("Starting bot");
		startTelegramBot();
	}

	public static void startTelegramBot() throws Exception {
		TelegramChatBot bot = new TelegramChatBot(usersData);
		bot.startTelegramChatBot();

	}

}