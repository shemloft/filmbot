package bot;

import java.util.Scanner;



public class ChatBot 
{	
	public FilmsStructure filmsStructure;
	public Phrases phrases;
	
	public void startChat() throws Exception {
		// считать базу пользователей 
		
		HelperCSV parser = new bot.HelperCSV("Database.csv");
		filmsStructure = new bot.FilmsStructure(parser.filmList);
		phrases = new Phrases();
		
		
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
		scan.close();
	}

}
