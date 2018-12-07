package storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import storage.FilmDatabase;
import structures.Field;
import structures.Film;
import structures.User;

public class FilmDatabaseTest {

	private List<Film> filmList;
	private FilmDatabase filmDatabase;
	private IFilmHandler filmHandler;

	private void createFilmList() {
		filmList = new ArrayList<Film>();
		Map<Field, List<String>> filmData = new HashMap<Field, List<String>>();
		filmList.add(new Film(1, "title1", filmData));
		filmList.add(new Film(2, "title2", filmData));
	}

	@Before
	public void setUp() throws Exception {
		createFilmList();
		String[] genres = new String[] {"anime", "hentai"};
		Map<Field, String[]> fields = new HashMap<Field, String[]>();
		fields.put(Field.GENRE, genres);
		filmHandler = new TestFilmHandler(filmList, fields);
		filmDatabase = new FilmDatabase(filmHandler);
	}

	@Test
	public void testGetFilmCleanUser() {
		User user = new User("");
		Film film = filmDatabase.getFilm(user);
		assertEquals("title1", film.title);
	}
	
	@Test
	public void testGetFilmSecondTime() {
		User user = new User("");
		user.addFilm(filmList.get(0));
		Film film = filmDatabase.getFilm(user);
		assertEquals("title2", film.title);
	}
	
	@Test
	public void noFilmsLeft() {
		User user = new User("");
		user.addFilm(filmList.get(0));
		user.addFilm(filmList.get(1));
		assertEquals(null, filmDatabase.getFilm(user));
	}
	
	@Test
	public void noFilmsAtAll() {
		filmHandler = new TestFilmHandler(new ArrayList<Film>(), null);
		filmDatabase = new FilmDatabase(filmHandler);
		User user = new User("");
		Film film = filmDatabase.getFilm(user);
		assertEquals(null, film.title);
	}
	
	@Test
	public void testFieldExistsInDatabse() {
		assertTrue(filmDatabase.requestExistInDatabase(Field.GENRE, "hentai"));
	}
	
	@Test
	public void testFieldNotExistsInDatabse() {
		assertFalse(filmDatabase.requestExistInDatabase(Field.GENRE, "genre"));
	}

}
