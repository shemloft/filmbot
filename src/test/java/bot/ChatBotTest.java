/*package bot;

import junit.framework.Assert;
import junit.framework.TestCase;
import structures.Field;
import structures.Film;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dialog.Phrases;



public class ChatBotTest extends TestCase {

	protected final ByteArrayOutputStream output = new ByteArrayOutputStream();
	protected final String name = "test_name";
	
	protected final String dialogStartFirst = "Назовите себя, пожалуйста\r\n" + 
		    "Добро пожаловать, test_name.";
	protected final String dialogStartSecond = "Назовите себя, пожалуйста\r\n" + 
		    "Давно не виделись, test_name.\r\n";
	
	private InputStream getInput(String[] commands) throws UnsupportedEncodingException {
		StringBuilder builder = new StringBuilder();
		for (String command : commands) {
			builder.append(command);
			builder.append("\n");
		}
		return new ByteArrayInputStream(builder.toString().getBytes("UTF-8"));
	}

	private Film getFilm(String title, String country, String year) {
		Map<Field, String> filmData = new HashMap<Field, String>();
		filmData.put(Field.COUNTRY, country);
		filmData.put(Field.YEAR, year);
		return new structures.Film(title, filmData);
	}	
	
	private List<Film> getFilms() {
		List<Film> filmList = new ArrayList<Film>();
		filmList.add(getFilm("Бойцовский клуб", "США", "1999"));
		filmList.add(getFilm("Леон", "Франция", "1994"));
		filmList.add(getFilm("Криминальное чтиво", "США", "1994"));
		filmList.add(getFilm("Крестный отец", "США", "1972"));
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
		new ChatBot(getFilms()).startChat(getInput(commands), output);
		Assert.assertEquals(
				dialogStartFirst, 
			    output.toString().substring(0, dialogStartFirst.length()));	
	}
	
	@Test
	public void testStartDialogSecondTime() throws Exception {
		String[] commands = { name, "/exit" };
		new ChatBot(getFilms()).startChat(getInput(commands), output);
		output.reset();
		new ChatBot(getFilms()).startChat(getInput(commands), output);
		Assert.assertEquals(
				dialogStartSecond, 
			    output.toString().substring(0, dialogStartSecond.length()));			
	}
	
	@Test
	public void testStartDialogGetFilm() throws Exception {
		String[] commands = { name, "/y 1999", "/exit" };
		new ChatBot(getFilms()).startChat(getInput(commands), output);
		String film = "Бойцовский клуб";
		int startIndex = dialogStartFirst.length() + Phrases.HELP.length() + "\r\n".length();
		Assert.assertEquals(
				film, output.toString().substring(startIndex, startIndex + film.length()));			
	}
	
	@Test
	public void testStartDialogGetFilmSecondTime() throws Exception {
		String[] commands = { name, "/y 1999", "/exit" };
		new ChatBot(getFilms()).startChat(getInput(commands), output);
		output.reset();
		new ChatBot(getFilms()).startChat(getInput(commands), output);
		String answer = "Все фильмы этого года, имеющиеся в базе, были предоставлены";
		Assert.assertEquals(
				answer, 
			    output.toString().substring(dialogStartSecond.length(), dialogStartSecond.length() + answer.length()));			
	}


	@After
	public void tearDown() {
		tryToDeleteSavedFile();
	}

}
*/