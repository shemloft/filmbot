//package storage;
//
//import junit.framework.Assert;
//import junit.framework.TestCase;
//import structures.Film;
//import structures.FilmsStructure;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.junit.Test;
//
//public class FilmsStructureTest extends TestCase {
//	@Test
//	public void testFilmsByYear() throws Exception {
//		Map<String, ArrayList<Film>> filmsByYear = new HashMap<String, ArrayList<Film>>();
//		filmsByYear.put("2006", new ArrayList<Film>());
//		filmsByYear.get("2006").add(new Film("Престиж", "2006", "США"));
//		FilmsStructure filmsStruct1 = new FilmsStructure(filmsByYear.get("2006"));
//		Assert.assertEquals(filmsByYear, filmsStruct1.getFilmsByKey("year"));
//		filmsByYear = new HashMap<String, ArrayList<Film>>();
//		filmsByYear.put("1994", new ArrayList<Film>());
//		filmsByYear.get("1994").add(new Film("Леон", "1994", "Франция"));
//		filmsByYear.get("1994").add(new Film("Криминальное чтиво", "1994", "США"));
//		FilmsStructure filmsStruct2 = new FilmsStructure(filmsByYear.get("1994"));
//		Assert.assertEquals(filmsByYear, filmsStruct2.getFilmsByKey("year"));
//	}
//	
//	@Test
//	public void testFilmsByCountry() throws Exception {
//		Map<String, ArrayList<Film>> filmsByCountry = new HashMap<String, ArrayList<Film>>();
//		filmsByCountry.put("США", new ArrayList<Film>());
//		filmsByCountry.get("США").add(new Film("Престиж", "2006", "США"));
//		filmsByCountry.get("США").add(new Film("Криминальное чтиво", "1994", "США"));
//		FilmsStructure filmsStruct1 = new FilmsStructure(filmsByCountry.get("США"));
//		Assert.assertEquals(filmsByCountry, filmsStruct1.getFilmsByKey("country"));
//		filmsByCountry = new HashMap<String, ArrayList<Film>>();
//		filmsByCountry.put("Франция", new ArrayList<Film>());
//		filmsByCountry.get("Франция").add(new Film("Леон", "1994", "Франция"));
//		FilmsStructure filmsStruct2 = new FilmsStructure(filmsByCountry.get("Франция"));
//		Assert.assertEquals(filmsByCountry, filmsStruct2.getFilmsByKey("country"));
//	}
//}
