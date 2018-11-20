package storage;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import storage.FileFilmHandler;
import structures.Field;
import structures.Film;
import utils.FilmUtils;

public class FileFilmHandlerTest {

	private static IFilmHandler filmHandler;
	private static List<Film> filmList;
	private static Film film1 = FilmUtils.getFilm("1", "Бойцовский клуб", "США, Германия", "1999",
			"триллер, драма, криминал");
	private static Film film2 = FilmUtils.getFilm("2", "Крестный отец", "США", "1972", "драма, криминал");

	@Before
	public void setUp() throws Exception {
		createFilmList();
		filmHandler = new FileFilmHandler(new TestFilmDatabaseFileHandler(FilmUtils.filmListToStringList(filmList)));
	}

	private void createFilmList() {
		filmList = new ArrayList<Film>();
		filmList.add(FilmUtils.getFilm("ID", "Фильм", "Страна", "Год", "Жанр"));
		filmList.add(FilmUtils.getFilm("0", "Леон", "Франция", "1994", "триллер, драма, криминал"));
		filmList.add(film1);
		filmList.add(film2);
		filmList.add(FilmUtils.getFilm("3", "Криминальное чтиво", "США", "1994", "триллер, комедия, криминал"));
	}

	@Test
	public void testGetFilmsCount() {
		int filmsCount = filmHandler.getFilmsCount();
		assertEquals(4, filmsCount);
	}

	@Test
	public void testGetFilmsByOptions() {
		Map<Field, List<String>> options = new HashMap<Field, List<String>>();
		options.put(Field.COUNTRY, new ArrayList<String>());
		options.get(Field.COUNTRY).add("США");
		options.put(Field.GENRE, new ArrayList<String>());
		options.get(Field.GENRE).add("драма");
		List<Film> filmsByOptions = filmHandler.getFilmsByOptions(options);
		assertEquals(2, filmsByOptions.size());
		assertEquals(true, filmsByOptions.contains(film1));
		assertEquals(true, filmsByOptions.contains(film2));
	}

	@Test
	public void testGetAvaliableFieldValues() {
		String[] fieldValues = filmHandler.getAvaliableFieldValues(Field.COUNTRY);
		String[] expectedData = { "Франция", "США", "Германия" };
		for (int i = 0; i < fieldValues.length; i++)
			assertEquals(expectedData[i], fieldValues[i]);
	}

}
