package bot;

import junit.framework.TestCase;
import structures.Film;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import utils.FilmUtils;
import structures.Field;

public class ChatBotTest extends TestCase {

	protected final ByteArrayOutputStream output = new ByteArrayOutputStream();
	protected final String name = "test_name";

	protected final String dialogStartFirst = "Назовите себя, пожалуйста\r\n" + "Добро пожаловать, test_name.";
	protected final String dialogStartSecond = "Назовите себя, пожалуйста\r\n" + "Давно не виделись, test_name.\r\n";

	private InputStream getInput(String[] commands) throws UnsupportedEncodingException {
		StringBuilder builder = new StringBuilder();
		for (String command : commands) {
			builder.append(command);
			builder.append("\n");
		}
		return new ByteArrayInputStream(builder.toString().getBytes("UTF-8"));
	}

	private List<Film> getFilms() {
		List<Film> filmList = new ArrayList<Film>();
		filmList.add(FilmUtils.getFilm("8", "Бойцовский клуб", "США, Германия", "1999", "триллер, драма, криминал"));
		filmList.add(FilmUtils.getFilm("5", "Леон", "Франция", "1994", "триллер, драма, криминал"));
		filmList.add(FilmUtils.getFilm("13", "Криминальное чтиво", "США", "1994", "триллер, комедия, криминал"));
		filmList.add(FilmUtils.getFilm("12", "Крестный отец", "США", "1972", "драма, криминал"));
		return filmList;
	}

	private void tryToDeleteSavedFile() {
		File userFile = new File(name + ".csv");
		userFile.delete();
	}

	@Before
	public void setUp() {
		tryToDeleteSavedFile();
	}

	@Test
	public void testStartDialogFirstTime() throws Exception {
		String[] commands = { name, "/exit" };
		new ChatBot(FilmUtils.getFilmMapsByField(getFilms())).startChat(getInput(commands), output);

		assertThat(output.toString(), containsString("Добро пожаловать"));
	}

	@Test
	public void testStartDialogSecondTime() throws Exception {
		String[] commands = { name, "/exit" };
		new ChatBot(FilmUtils.getFilmMapsByField(getFilms())).startChat(getInput(commands), output);
		output.reset();
		new ChatBot(FilmUtils.getFilmMapsByField(getFilms())).startChat(getInput(commands), output);
		assertThat(output.toString(), containsString("Давно не виделись"));
	}

	@Test
	public void testStartDialogGetFilm() throws Exception {
		String[] commands = { name, "/y 1999", "/exit" };
		new ChatBot(FilmUtils.getFilmMapsByField(getFilms())).startChat(getInput(commands), output);
		assertThat(output.toString(), containsString("Бойцовский клуб"));
	}

	@Test
	public void testStartDialogGetFilmSecondTime() throws Exception {
		String[] commands = { name, "/y 1999", "/exit" };
		new ChatBot(FilmUtils.getFilmMapsByField(getFilms())).startChat(getInput(commands), output);
		output.reset();
		new ChatBot(FilmUtils.getFilmMapsByField(getFilms())).startChat(getInput(commands), output);
		assertThat(output.toString(), containsString(Field.YEAR.noFilmsLeft()));
	}

	@After
	public void tearDown() {
		tryToDeleteSavedFile();
	}

}
