package bot;

import storage.CSVHandler;
import storage.Database;
import storage.DatabaseFilmHandler;
import storage.FilmHandler;

public class Main {

	public static void main(String[] args) throws Exception {
		Database database = new CSVHandler("Database");
		FilmHandler filmHandler = new DatabaseFilmHandler(database);		
		ChatBot chatBot = new ChatBot(filmHandler.getFilmList());
		chatBot.startChat(System.in, System.out);	
	}

}
