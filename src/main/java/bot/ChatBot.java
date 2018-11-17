package bot;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import dialog.Dialog;
import dialog.Phrases;
import storage.DatabaseFilmHandler;
import storage.IDatabase;
import structures.Field;
import structures.Film;
import structures.User;
import utils.FilmUtils;
import utils.UserUtils;

public class ChatBot {
	private IDatabase database;

	public ChatBot(IDatabase database) throws Exception {
		this.database = database;
	}

	public void startChat(InputStream inputStream, OutputStream outputStream) throws Exception {

		Scanner scan = new Scanner(inputStream);
		PrintStream printStream = new PrintStream(outputStream);

		printStream.println(Phrases.HELLO);
		String name = scan.nextLine();

		DatabaseFilmHandler filmHandler = new DatabaseFilmHandler(database);

		User user = UserUtils.getUser(name, name);
		Dialog dialog = new Dialog(user, filmHandler.filmList);

		printStream.println(dialog.startDialog());
		try {
			while (true) {
				String req = scan.nextLine();
				if ("/exit".equals(req)) {
					UserUtils.saveUser(user);
					break;
				}
				// мб стоит вообще запилить отдельный класс со статическим методом?
				if ("/add".equals(req)) {
					printStream.println("Введите название фильма");
					String title = scan.nextLine();
					printStream.println(
							"Введите страну-создателя фильма. Если стран несколько, то введите их через запятую с пробелом");
					String country = scan.nextLine();
					printStream.println("Введите год создания фильма");
					String year = scan.nextLine();
					printStream.println(
							"Введите жанр фильма. Если жанров несколько, то введите их через запятую с пробелом");
					String genre = scan.nextLine();
					try {
						filmHandler.addFilmToDatabase(title, country, year, genre);
						printStream.println("Фильм успешно добавлен в базу");
					} catch (Exception e) {
						printStream.println(e.getMessage());
					}
					
				}
				String answer = dialog.processInput(req);
				printStream.println(answer);
			}
		} finally {
			scan.close();
		}
	}

}
