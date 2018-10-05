package bot;

import java.util.ArrayList;
import java.util.Scanner;

public class ChatBot {
	public FilmsStructure filmsStructure;
	public Phrases phrases;

	public ChatBot(ArrayList<Film> filmList) throws Exception {		
		filmsStructure = new FilmsStructure(filmList);
		phrases = new Phrases();
	}
	
	public void startChat() throws Exception {		

		Scanner scan = new Scanner(System.in);
		System.out.println(Phrases.HELLO);

		String name = scan.nextLine();
		ArrayList<Film> userFilms = tryGetUserFilmList(name);

		User user = new User(name, userFilms);
		Dialog dialog = new Dialog(this, user);

		System.out.println(dialog.startDialog());
		while (true) {
			String req = scan.nextLine();
			if ("/exit".equals(req)) {
				saveUser(user);
				break;
			}

			String answer = dialog.processInput(req);
			System.out.println(answer);
		}
		scan.close();
	}

	private ArrayList<Film> tryGetUserFilmList(String name) {
		HelperCSV parser = null;
		try {
			parser = new HelperCSV(String.format("%s.csv", name));
		} catch (Exception e) {
			return null;
		}
		return parser.filmList;
	}

	private void saveUser(User user) throws Exception {
		try {
			HelperCSV.createFile(user.name);
		} catch (Exception e) {
		}

		HelperCSV.addInfo(user.name, user.savedFilms);
	}
}
