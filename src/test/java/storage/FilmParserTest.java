//package storage;
//
//import static org.junit.Assert.assertEquals;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Test;
//
//import storage.DatabaseFilmHandler;
//import structures.Field;
//import structures.Film;
//import structures.User;
//
//public class FilmParserTest {
//	private Film getFilm(String title, String country, String year) {
//		Map<Field, String> filmData = new HashMap<Field, String>();
//		filmData.put(Field.COUNTRY, country);
//		filmData.put(Field.YEAR, year);
//		return new Film(title, filmData);
//	}
//
//	@Test
//	public void testGetFilmList() throws Exception {
//		DatabaseFilmHandler filmParser = new DatabaseFilmHandler(new CSVHandler("Database"));
//		List<Film> filmList = filmParser.getFilmList();
//		assertEquals(20, filmList.size());
//		assertEquals(getFilm("Побег из Шоушенка", "США", "1994"), filmList.get(0));
//	}
//
//	@Test
//	public void testSaveFilms() throws Exception {
//		List<Film> filmList = new ArrayList<Film>();
//		Film film = getFilm("Бойцовский клуб", "США", "1999");
//		filmList.add(film);
//		User user = new User("test", filmList);
//		CSVHandler helperCSV = new CSVHandler(user.name);
//		DatabaseFilmHandler parser = new DatabaseFilmHandler(helperCSV);
//		parser.saveFilms(user.savedFilms);
//		assertEquals(film.title, user.savedFilms.get(0).title);
//		assertEquals(film.title, new CSVHandler("test").extractData().get(0)[0]);
//		File file = new File("test.csv");
//		file.delete();
//	}
//
//}
