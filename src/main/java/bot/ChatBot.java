package bot;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map;
import java.util.ArrayList;


public class ChatBot {

	public Map<String, ArrayList<Film>> filmsByCountry;
	public Map<String, ArrayList<Film>> filmsByYear;
	
	public void startChat() throws Exception {
		// считать базу пользователей 
		
		ParserCSV parser = new bot.ParserCSV("Database.csv");
		filmsByCountry = parser.getFilmsByCountry();
		filmsByYear = parser.getFilmsByYear();
		
		Scanner scan = new Scanner(System.in);
//		System.out.println(bot.Dialog.HELLO_TEXT);
		String name = scan.nextLine();
		bot.Dialog dialog = null;
//		if (false) // name in base		
//			dialog = null; // извлекаем диалог из базы		
//		else 
		dialog = new bot.Dialog(this);
		System.out.println(dialog.startDialog(name));
		while (true) 
		{
			String req = scan.nextLine();
			if ("/exit".equals(req)) // <- добавить сохранение диалог-лога в файл
				break;
			String answer = dialog.processInput(req);
			System.out.println(answer);
		}
	}

}
