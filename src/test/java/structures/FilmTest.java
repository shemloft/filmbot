//package structures;
//
//import junit.framework.TestCase;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.junit.Test;
//
//import structures.Film;
//import structures.Field;
//
//public class FilmTest extends TestCase {
//
//	private Film getFilm(String title, String country, String year) {
//		Map<Field, String> filmData = new HashMap<Field, String>();
//		filmData.put(Field.COUNTRY, country);
//		filmData.put(Field.YEAR, year);
//		return new structures.Film(title, filmData);
//	}	
//	
//	@Test
//	public void testGetField() {
//		Film film = getFilm("Леон", "Франция", "1994");
//		assertEquals("Франция", film.getField(Field.COUNTRY));
//		assertEquals("1994", film.getField(Field.YEAR));		
//	}
//
//	@Test
//	public void testEquals() {
//		Film film1 = getFilm("Бойцовский клуб", "США", "1999");
//		Film film2 = getFilm("Бойцовский клуб", "США", "1999");
//		Film film3 = getFilm("Бойцовский клуб", "США", "1997");
//		Film film4 = getFilm("Бойцовский клуп", "США", "1997");
//		assertEquals(film1, film2);
//		assertEquals(film1, film1);
//		assertFalse(film1.equals(film3));
//		assertFalse(film1.equals(film4));
//		assertFalse(film1.equals(null));
//	}
//
//	@Test
//	public void testHashCode() {
//		Film film1 = getFilm("Бойцовский клуб", "США", "1999");
//		Film film2 = getFilm("Бойцовский клуб", "США", "1999");
//		assertEquals(film1.hashCode(), film2.hashCode());
//	}
//	
//	@Test
//	public void testTostring() {
//		Film film = getFilm("Престиж", "США", "2006");
//		assertEquals("Название: Престиж, год: 2006, страна: США",  film.toString());
//	}
//
//
//}
