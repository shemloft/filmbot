//package structures;
//
//import junit.framework.TestCase;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Test;
//
//import structures.Film;
//import structures.FilmMaps;
//import structures.Field;
//
//public class FilmsStructureTest extends TestCase {
//
//	private Film getFilm(String title, String country, String year) {
//		Map<Field, String> filmData = new HashMap<Field, String>();
//		filmData.put(Field.COUNTRY, country);
//		filmData.put(Field.YEAR, year);
//		return new structures.Film(title, filmData);
//	}
//
//	private List<Film> getFilmList() {
//		List<Film> filmList = new ArrayList<Film>();
//		filmList.add(getFilm("Бойцовский клуб", "США", "1999"));
//		filmList.add(getFilm("Леон", "Франция", "1994"));
//		filmList.add(getFilm("Престиж", "США", "2006"));
//		filmList.add(getFilm("Криминальное чтиво", "США", "1994"));
//		return filmList;
//	}
//
//	@Test
//	public void testFilmsByCountry() {
//		FilmMaps filmStruct = new FilmMaps(getFilmList());
//		List<Film> filmsByUSA = filmStruct.getFilmsByField(Field.COUNTRY).get("США");
//		assertEquals(3, filmsByUSA.size());
//		String[] filmTitles = { "Бойцовский клуб", "Престиж", "Криминальное чтиво" };
//		for (Film film : filmsByUSA) {
//			assertTrue(Arrays.asList(filmTitles).contains(film.title));
//		}
//	}
//
//	@Test
//	public void testFilmsByYear() {
//		FilmMaps filmStruct = new FilmMaps(getFilmList());
//		List<Film> filmsBy1994 = filmStruct.getFilmsByField(Field.YEAR).get("1994");
//		assertEquals(2, filmsBy1994.size());
//		String[] filmTitles = { "Леон", "Криминальное чтиво" };
//		for (Film film : filmsBy1994) {
//			assertTrue(Arrays.asList(filmTitles).contains(film.title));
//		}
//	}
//}
