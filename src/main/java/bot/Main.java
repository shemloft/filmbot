package bot;

import storage.HelperCSV;
import storage.FilmParser;

public class Main {

	public static void main(String[] args) throws Exception {
		HelperCSV helperCSV = new HelperCSV("Database.csv");
		FilmParser parser = new FilmParser(helperCSV);		
		ChatBot chatBot = new ChatBot(parser.getFilmList());
		chatBot.startChat(System.in, System.out);	
	}

}
