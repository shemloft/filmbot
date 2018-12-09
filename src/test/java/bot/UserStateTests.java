package bot;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import bot.UserState;
import dialog.Phrases;
import storage.TestDatabase;
import structures.BotMessage;
import structures.Field;
import structures.Film;




public class UserStateTests {
	
	private TestDatabase filmDatabase;
	
	private Film generateFilm(String title, String genre, String year, String actor) {
		Map<Field, List<String>> filmData = new HashMap<Field, List<String>>();
		List<String> genres = new ArrayList<String>();
		genres.add(genre);
		List<String> years = new ArrayList<String>();
		years.add(year);
		List<String> actors = new ArrayList<String>();
		actors.add(actor);
		filmData.put(Field.GENRE, genres);
		filmData.put(Field.YEAR, years);
		filmData.put(Field.ACTOR, actors);
		Film film = new Film(1, title, filmData);
		return film;
	}
	
	@Before
	public void setUp() throws Exception {
		Film film1 = generateFilm("title", "thriller", "1900", "actor");
		filmDatabase = new TestDatabase(film1);
	}

	@Test
	public void testIncorrectCommand() {
		UserState state = new UserState("", filmDatabase);
		BotMessage message = state.getAnswer("asd");
		assertEquals(Phrases.UNKNOWN_COMMAND, message.messageText);
	}
	
	@Test
	public void testHelpCommand() {
		UserState state = new UserState("", filmDatabase);
		BotMessage message = state.getAnswer("/help");
		assertEquals(Phrases.HELP, message.messageText);
	}
	
	@Test
	public void testGetFilmGenre() {
		UserState state = new UserState("", filmDatabase);
		state.getAnswer("GENRE");
		state.getAnswer("thriller");
		BotMessage message = state.getAnswer("ПОЛУЧИТЬ ФИЛЬМ");
		assertEquals("title", message.messageText);
	}
	
	@Test
	public void testGetFilmYear() {
		UserState state = new UserState("", filmDatabase);
		state.getAnswer("YEAR");
		state.getAnswer("1900");
		BotMessage message = state.getAnswer("ПОЛУЧИТЬ ФИЛЬМ");
		assertEquals("title", message.messageText);
	}
	
	@Test
	public void testGetFilmActor() {
		UserState state = new UserState("", filmDatabase);
		state.getAnswer("ACTOR");
		state.getAnswer("actor");
		BotMessage message = state.getAnswer("ПОЛУЧИТЬ ФИЛЬМ");
		assertEquals("title", message.messageText);
	}
	
	@Test
	public void testNextFilm() {
		UserState state = new UserState("", filmDatabase);
		state.getAnswer("GENRE");
		state.getAnswer("thriller");
		state.getAnswer("ПОЛУЧИТЬ ФИЛЬМ");
		BotMessage message = state.getAnswer("NEXT");
		assertEquals("title", message.messageText);
	}
	
	@Test
	public void testNoMoreFilms() {
		UserState state = new UserState("", filmDatabase);
		state.getAnswer("GENRE");
		state.getAnswer("thriller");
		state.getAnswer("ПОЛУЧИТЬ ФИЛЬМ");
		filmDatabase.setReturningFilm(null);
		BotMessage message = state.getAnswer("NEXT");
		assertEquals(Phrases.NO_MORE_FILM, message.messageText);
	}
	
	@Test
	public void testNextNoOptions() {
		UserState state = new UserState("", filmDatabase);
		BotMessage message = state.getAnswer("NEXT");
		assertEquals(Phrases.NEXT_WITHOUT_OPT, message.messageText);
	}

}
