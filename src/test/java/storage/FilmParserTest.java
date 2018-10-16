package storage;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import storage.FilmParser;
import structures.Field;
import structures.Film;
import structures.User;

public class FilmParserTest {
	private Film getFilm(String title, String country, String year) {
		Map<Field, String> filmData = new HashMap<Field, String>();
		filmData.put(Field.COUNTRY, country);
		filmData.put(Field.YEAR, year);
		return new Film(title, filmData);
	}

	@Test
	public void testGetFilmList() throws Exception {
		FilmParser filmParser = new FilmParser(new HelperCSV("Database"));
		List<Film> filmList = filmParser.getFilmList();
		assertEquals(20, filmList.size());
		assertEquals(getFilm("Побег из Шоушенка", "США", "1994"), filmList.get(0));
	}

	@Test
	public void testSaveFilms() throws Exception {
		List<Film> filmList = new ArrayList<Film>();
		Film film = getFilm("Бойцовский клуб", "США", "1999");
		filmList.add(film);
		User user = new User("test", filmList);
		HelperCSV helperCSV = new HelperCSV(user.name);
		FilmParser parser = new FilmParser(helperCSV);
		parser.saveFilms(user.savedFilms);
		assertEquals(film.title, user.savedFilms.get(0).title);
		assertEquals(film.title, new HelperCSV("test").extractData().get(0)[0]);
		File file = new File("test.csv");
		file.delete();
	}

}
