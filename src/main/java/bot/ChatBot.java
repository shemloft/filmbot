package bot;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import dialog.Dialog;
import dialog.Phrases;
import storage.FilmParser;
import storage.HelperCSV;
import structures.Film;
import structures.FilmsStructure;
import structures.User;

public class ChatBot {
	public FilmsStructure filmsStructure;

	public ChatBot(List<Film> filmList) throws Exception {
		filmsStructure = new FilmsStructure(filmList);
	}

	public void startChat(InputStream inputStream, OutputStream outputStream) throws Exception {

		Scanner scan = new Scanner(inputStream);
		PrintStream printStream = new PrintStream(outputStream);		
		
		printStream.println(Phrases.HELLO);			
		String name = scan.nextLine();
		List<Film> userFilms = tryGetUserFilmList(name);

		User user = new User(name, userFilms);
		Dialog dialog = new Dialog(user, filmsStructure);

		printStream.println(dialog.startDialog());
		while (true) {
			String req = scan.nextLine();
			if ("/exit".equals(req)) {
				saveUser(user);
				break;
			}

			String answer = dialog.processInput(req);
			printStream.println(answer);
		}
		scan.close();
	}

	private List<Film> tryGetUserFilmList(String name) throws Exception {
		List<Film> userFilms = null;
		HelperCSV helperCSV = new HelperCSV(name);
		FilmParser parser = new FilmParser(helperCSV);
		try {			
			userFilms = parser.getFilmList();
		} catch (Exception e) {
			return null;
		}
		return userFilms;
	}

	private void saveUser(User user) throws Exception {
		try {
			HelperCSV helperCSV = new HelperCSV(user.name);
			FilmParser parser = new FilmParser(helperCSV);
			parser.saveFilms(user.savedFilms);
		} catch (Exception e) {
			throw new Exception("Ошибочка при сохранении пользователя");
		}
	}
}
