package bot;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import dialog.Dialog;
import dialog.Phrases;
import storage.FilmDatabase;
import structures.User;
import utils.UserUtils;

public class ChatBot {
	private FilmDatabase database;

	public ChatBot(FilmDatabase database) throws Exception {
		this.database = database;
	}

	public void startChat(InputStream inputStream, OutputStream outputStream) throws Exception {

		Scanner scan = new Scanner(inputStream);
		PrintStream printStream = new PrintStream(outputStream);

		printStream.println(Phrases.HELLO);
		String name = scan.nextLine();

		User user = UserUtils.getUser(name, name);
		Dialog dialog = new Dialog(user, database);

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
					
					/*Здесь стоило бы объединить все отдельные вводы в один
					 * например ключ + то или иное слово
					 * пример работы: 
					 * запрос: /add
					 * ответ: подробная справка с описанием формата ввода
					 * запрос: /t тайтл /g жанр1, жанр2 ...
					 * ответ: статус - все хорошо или плохо
					 * 
					 * проверять на наличие всех ключей в запросе, кидать ошибку если не все					 * 
					 * (этот разбор - в диалоге)					 * 
					 * скорее всего надо разбивать по слешу ключа, по пробелу нельзя т.к. жанры через запятую с пробелом
					 * можно просто подавать несколько жанров с ключом перед каждым
					 * */
					
					
					
					
					
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
						database.addFilmToDatabase(title, country, year, genre);
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
