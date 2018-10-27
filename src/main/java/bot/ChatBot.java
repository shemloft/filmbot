package bot;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import dialog.Dialog;
import dialog.Phrases;
import structures.Field;
import structures.Film;
import structures.User;
import utils.FilmUtils;
import utils.DatabaseUtils;

public class ChatBot {
	private Map<Field, Map<String, List<Film>>> filmMapsByField;

	public ChatBot(List<Film> filmList) throws Exception {
		filmMapsByField = FilmUtils.getFilmMapsByField(filmList);
	}

	public void startChat(InputStream inputStream, OutputStream outputStream) throws Exception {

		Scanner scan = new Scanner(inputStream);
		PrintStream printStream = new PrintStream(outputStream);

		printStream.println(Phrases.HELLO);
		String name = scan.nextLine();
		List<Film> userFilms = DatabaseUtils.tryGetUserFilmList(name);

		User user = new User(name, userFilms);
		Dialog dialog = new Dialog(user, filmMapsByField);

		printStream.println(dialog.startDialog());

		while (true) {
			String req = scan.nextLine();
			if ("/exit".equals(req)) {
				DatabaseUtils.saveUser(user);
				break;
			}

			String answer = dialog.processInput(req);
			printStream.println(answer);
		}

		scan.close();
	}

}
