//package telegram;
//
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import dialog.Phrases;
//import storage.TestDatabase;
//import structures.Field;
//import structures.Film;
//import structures.User;
//
//
//
//
//public class StateTests {
//	
//	private TestDatabase filmDatabase;
//	private User user;
//	
//	private Film generateFilm(String title, String genre, String year, String actor) {
//		Map<Field, List<String>> filmData = new HashMap<Field, List<String>>();
//		List<String> genres = new ArrayList<String>();
//		genres.add(genre);
//		List<String> years = new ArrayList<String>();
//		years.add(year);
//		List<String> actors = new ArrayList<String>();
//		actors.add(actor);
//		filmData.put(Field.GENRE, genres);
//		filmData.put(Field.YEAR, years);
//		filmData.put(Field.ACTOR, actors);
//		Film film = new Film(1, title, filmData);
//		return film;
//	}
//	
//	@Before
//	public void setUp() throws Exception {
//		Film film1 = generateFilm("title", "thriller", "1900", "actor");
//		filmDatabase = new TestDatabase(film1);
//		user = new User("");
//	}
//
//	@Test
//	public void testIncorrectCommand() {
//		State state = new State(user, filmDatabase);
//		state.processInput("asd");
//		assertEquals(Phrases.UNKNOWN_COMMAND, state.answerString);
//	}
//	
//	@Test
//	public void testHelpCommand() {
//		State state = new State(user, filmDatabase);
//		state.processInput("/help");
//		assertEquals(Phrases.HELP, state.answerString);
//	}
//	
//	@Test
//	public void testGetFilmGenre() {
//		State state = new State(user, filmDatabase);
//		state.processInput("GENRE");
//		state = new State(user, filmDatabase);
//		state.processInput("thriller");
//		state = new State(user, filmDatabase);
//		state.processInput("ПОЛУЧИТЬ ФИЛЬМ");
//		assertEquals("title", state.answerString);
//	}
//	
//	@Test
//	public void testGetFilmYear() {
//		State state = new State(user, filmDatabase);
//		state.processInput("YEAR");
//		state = new State(user, filmDatabase);
//		state.processInput("1900");
//		state = new State(user, filmDatabase);
//		state.processInput("ПОЛУЧИТЬ ФИЛЬМ");
//		assertEquals("title", state.answerString);
//	}
//	
//	@Test
//	public void testGetFilmActor() {
//		State state = new State(user, filmDatabase);
//		state.processInput("ACTOR");
//		state = new State(user, filmDatabase);
//		state.processInput("actor");
//		state = new State(user, filmDatabase);
//		state.processInput("ПОЛУЧИТЬ ФИЛЬМ");
//		assertEquals("title", state.answerString);
//	}
//	
//	@Test
//	public void testNextFilm() {
//		State state = new State(user, filmDatabase);
//		state.processInput("GENRE");
//		state = new State(user, filmDatabase);
//		state.processInput("thriller");
//		state = new State(user, filmDatabase);
//		state.processInput("ПОЛУЧИТЬ ФИЛЬМ");
//		state = new State(user, filmDatabase);
//		state.processInput("NEXT");
//		assertEquals("title", state.answerString);
//	}
//	
//	@Test
//	public void testNoMoreFilms() {
//		State state = new State(user, filmDatabase);
//		state.processInput("GENRE");
//		state = new State(user, filmDatabase);
//		state.processInput("thriller");
//		state = new State(user, filmDatabase);
//		state.processInput("ПОЛУЧИТЬ ФИЛЬМ");
//		filmDatabase.setReturningFilm(null);
//		state = new State(user, filmDatabase);
//		state.processInput("NEXT");
//		assertEquals(Phrases.NO_MORE_FILM, state.answerString);
//	}
//	
//	@Test
//	public void testNextNoOptions() {
//		State state = new State(user, filmDatabase);
//		state.processInput("NEXT");
//		assertEquals(Phrases.NEXT_WITHOUT_OPT, state.answerString);
//	}
//
//}
