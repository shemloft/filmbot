package bot;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import bot.DialogState;
import dialog.Phrases;
import storage.TestDatabase;
import structures.Field;
import structures.Film;
import structures.Messages;
import structures.Options;
import structures.User;

public class DialogStateTests {
	
	private TestDatabase filmDatabase;
	private User user;
	private DialogState state;
	
	private Film generateFilm(String title, String genre, String year, String actor) {
		Options options = new Options();
		options.add(Field.GENRE, genre);
		options.add(Field.YEAR, year);
		options.add(Field.ACTOR, actor);		
		Film film = new Film(1, title, options, null, null);
		return film;
	}
	
	@Before
	public void setUp() throws Exception {
		Film film1 = generateFilm("title", "thriller", "1900", "actor");
		filmDatabase = new TestDatabase(film1);
		user = new User("", 0l);
		state = new DialogState(user, filmDatabase);
	}

	@Test
	public void testIncorrectCommand() {		
		Messages message = state.getAnswer("asd");
		assertEquals(Phrases.UNKNOWN_COMMAND, message.getFirstMessage().getText());
	}
	
	@Test
	public void testHelpCommand() {
		Messages message = state.getAnswer("/help");
		assertEquals(Phrases.HELP, message.getFirstMessage().getText());
	}
	
	@Test
	public void testGetFilmGenre() {
		state.getAnswer("GENRE");
		state.getAnswer("thriller");
		Messages message = state.getAnswer("ПОЛУЧИТЬ ФИЛЬМ");
		assertEquals("title", message.getFirstMessage().getText());
	}
	
	@Test
	public void testGetFilmYear() {
		state.getAnswer("YEAR");
		state.getAnswer("1900");
		Messages message = state.getAnswer("ПОЛУЧИТЬ ФИЛЬМ");
		assertEquals("title", message.getFirstMessage().getText());
	}
	
	@Test
	public void testGetFilmActor() {
		state.getAnswer("ACTOR");
		state.getAnswer("actor");
		Messages message = state.getAnswer("ПОЛУЧИТЬ ФИЛЬМ");
		assertEquals("title", message.getFirstMessage().getText());
	}
	
	@Test
	public void testNextFilm() {
		state.getAnswer("GENRE");
		state.getAnswer("thriller");
		state.getAnswer("ПОЛУЧИТЬ ФИЛЬМ");
		Messages message = state.getAnswer("NEXT");
		assertEquals("title", message.getFirstMessage().getText());
	}
	
	@Test
	public void testNoMoreFilms() {
		state.getAnswer("GENRE");
		state.getAnswer("thriller");
		state.getAnswer("ПОЛУЧИТЬ ФИЛЬМ");
		filmDatabase.setReturningFilm(null);
		Messages message = state.getAnswer("NEXT");
		assertEquals(Phrases.NO_MORE_FILM, message.getFirstMessage().getText());
	}
	
	@Test
	public void testNextNoOptions() {
		Messages message = state.getAnswer("NEXT");
		assertEquals(Phrases.NEXT_WITHOUT_OPT, message.getFirstMessage().getText());
	}

}
