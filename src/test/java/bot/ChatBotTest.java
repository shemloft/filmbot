package bot;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import logic.Film;
import dialog.Phrases;



public class ChatBotTest extends TestCase {

	protected final ByteArrayOutputStream output = new ByteArrayOutputStream();
	protected final String name = "test_name";
	
	protected final String dialogStartFirst = "Назовите себя, пожалуйста\r\n" + 
		    "Добро пожаловать, test_name.";
	protected final String dialogStartSecond = "Назовите себя, пожалуйста\r\n" + 
		    "Давно не виделись, test_name.";
	
	private InputStream getInput(String[] commands) throws UnsupportedEncodingException {
		StringBuilder builder = new StringBuilder();
		for (String command : commands) {
			builder.append(command);
			builder.append("\n");
		}
		return new ByteArrayInputStream(builder.toString().getBytes("UTF-8"));
	}

	private ArrayList<Film> getFilms() {
		ArrayList<Film> filmList = new ArrayList<Film>();
		filmList.add(new Film("Престиж", "2006", "США"));
		filmList.add(new Film("Крестный отец", "1972", "США"));
		filmList.add(new Film("Жизнь прекрасна", "1997", "Италия"));
		filmList.add(new Film("Достучаться до небес", "1997", "Германия"));
		filmList.add(new Film("Леон", "1994", "Франция"));
		return filmList;
	}
	
	private void tryToDeleteSavedFile() {
		File userFile = new File(name + ".csv");
		userFile.delete();
	}
	

	@Before
	public void setUp() {
		System.setOut(new PrintStream(output));
		tryToDeleteSavedFile();
	}

	@Test
	public void testStartDialogFirstTime() throws Exception {
		ChatBot bot = new ChatBot(getFilms());
		String[] commands = { name, "/exit" };
		bot.startChat(getInput(commands));
		Assert.assertEquals(
				dialogStartFirst, 
			    output.toString().substring(0, dialogStartFirst.length()));	
	}
	
	@Test
	public void testStartDialogSecondTime() throws Exception {
		ChatBot bot = new ChatBot(getFilms());
		String[] commands = { name, "/exit" };
		bot.startChat(getInput(commands));
		output.reset();
		ChatBot botSecondTime = new ChatBot(getFilms());
		bot.startChat(getInput(commands));
		Assert.assertEquals(
				dialogStartSecond, 
			    output.toString().substring(0, dialogStartSecond.length()));			
	}
	
//	@Test
//	public void testStartDialogGetFilm() throws Exception {
//		ChatBot bot = new ChatBot(getFilms());
//		String[] commands = { name, "/y 2006", "/exit" };
//		bot.startChat(getInput(commands));
//		String film = "Престиж";
//		Assert.assertEquals(
//				film, 
//			    output.toString().substring(dialogStartFirst.length() + Phrases.HELP.length(), 58 + Phrases.HELP.length() + 7));			
//	}
	
	@Test
	public void testStartDialogGetFilmSecondTime() throws Exception {
		ChatBot bot = new ChatBot(getFilms());
		String[] commands = { name, "/y 2006", "/exit" };
		bot.startChat(getInput(commands));
		output.reset();
		ChatBot botSecondTime = new ChatBot(getFilms());
		bot.startChat(getInput(commands));
		String answer = "Все фильмы этого года, имеющиеся в базе, были предоставлены";
		Assert.assertEquals(
				answer, 
			    output.toString().substring(58, 58 + answer.length()));			
	}


	@After
	public void tearDown() {
		System.setOut(null);
		tryToDeleteSavedFile();
	}

}
