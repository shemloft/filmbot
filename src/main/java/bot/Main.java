package bot;

import java.io.StringReader;

import storage.HelperCSV;

public class Main {

	public static void main(String[] args) throws Exception {
		
		HelperCSV parser = new HelperCSV("Database.csv");
		ChatBot chatBot = new ChatBot(parser.filmList);
		chatBot.startChat(System.in);
	
	}

}
