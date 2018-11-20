package storage;

import static org.junit.Assert.assertEquals;
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
import utils.FilmUtils;

public class FilmDatabaseTest {

	private static List<Film> filmList;
	private static FilmDatabase filmDatabase;
	private static Film film2 = FilmUtils.getFilm("2", "Крестный отец", "США", "1972", "драма, криминал");

	private void createFilmList() {
		filmList = new ArrayList<Film>();
		filmList.add(FilmUtils.getFilm("ID", "Фильм", "Страна", "Год", "Жанр"));
		filmList.add(FilmUtils.getFilm("0", "Леон", "Франция", "1994", "триллер, драма, криминал"));
		filmList.add(FilmUtils.getFilm("1", "Бойцовский клуб", "США, Германия", "1999", "триллер, драма, криминал"));
		filmList.add(film2);
		filmList.add(FilmUtils.getFilm("3", "Криминальное чтиво", "США", "1994", "триллер, комедия, криминал"));
	}

	@Before
	public void setUp() throws Exception {
		createFilmList();
		filmDatabase = new FilmDatabase(
				new FileFilmHandler(new TestFilmDatabaseFileHandler(FilmUtils.filmListToStringList(filmList))));
	}

	@Test
	public void testGetFilm() {
		Map<Field, List<String>> options = new HashMap<Field, List<String>>();
		options.put(Field.COUNTRY, new ArrayList<String>());
		options.get(Field.COUNTRY).add("США");
		options.put(Field.GENRE, new ArrayList<String>());
		options.get(Field.GENRE).add("драма");
		List<String> idList = new ArrayList<String>();
		idList.add("1");
		Film film = filmDatabase.getFilm(options, idList);
		assertEquals(film2, film);
	}

	@Test
	public void testrequestExistInDatabase() throws Exception {
		assertTrue(filmDatabase.requestExistInDatabase(Field.COUNTRY, "США"));
		assertTrue(filmDatabase.requestExistInDatabase(Field.COUNTRY, "Германия"));
		assertTrue(filmDatabase.requestExistInDatabase(Field.GENRE, "комедия"));
		assertTrue(filmDatabase.requestExistInDatabase(Field.GENRE, "триллер"));
		assertTrue(filmDatabase.requestExistInDatabase(Field.YEAR, "1972"));
		assertTrue(filmDatabase.requestExistInDatabase(Field.YEAR, "1994"));
	}

}
