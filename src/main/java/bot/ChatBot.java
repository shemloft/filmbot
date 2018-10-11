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

	private List<Film> tryGetUserFilmList(String name) {
		FilmParser parser = null;
		try {
			HelperCSV helperCSV = new HelperCSV(String.format("%s.csv", name));
			parser = new FilmParser(helperCSV);
			
		} catch (Exception e) {
			return null;
		}
		return parser.getFilmList();
	}

	private void saveUser(User user) throws Exception {
		try {
			HelperCSV.createFile(user.name);
		} catch (Exception e) {
			throw new Exception("Ошибочка при сохранении пользователя");
		}

		HelperCSV.addInfo(user.name, user.savedFilms);
	}
}
