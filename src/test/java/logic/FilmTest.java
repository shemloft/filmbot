package logic;

import junit.framework.Assert;
import junit.framework.TestCase;
import logic.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FilmTest extends TestCase {
	private static Map<String, String> fields = new HashMap<String, String>();
	private static Map<String, Film> films = new HashMap<String, Film>();

	@Test
	public void testGetPossibleFields() {
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("country");
		fields.add("year");
		fields.add("title");
		Assert.assertEquals(fields, Film.getPossibleFields());
	}
	
	@Before
	public static void setUpGetField() {
		fields.put("Леон", "title");
		fields.put("Франция", "country");
		fields.put("1994", "year");
		fields.put(null, "unknown field");
	}
	
	@After
	public static void clearFields() {
		fields.clear();
	}
	
	@Test
	public void testGetField() {
		Film film = new Film("Леон", "1994", "Франция");
		for (Entry<String, String> entry : fields.entrySet()) {
			final String testData = entry.getValue();
			final String expected = entry.getKey();
			final String actual = film.getField(testData);
			assertEquals(expected, actual);
		}
	}

	@Test
	public void testGetTitle() {
		Film film = new Film("Бойцовский клуб", "1999", "США");
		Assert.assertEquals("Бойцовский клуб", film.getTitle());
	}

	@Test
	public void testGetYear() {
		Film film = new Film("Бойцовский клуб", "1999", "США");
		Assert.assertEquals("1999", film.getYear());
	}

	@Test
	public void testGetCountry() {
		Film film = new Film("Бойцовский клуб", "1999", "США");
		Assert.assertEquals("США", film.getCountry());
	}

	@Test
	public void testEquals() {
		Film film1 = new Film("Бойцовский клуб", "1999", "США");
		Film film2 = new Film("Бойцовский клуб", "1999", "США");
		Film film3 = film1;
		Assert.assertEquals(film1, film2);
		Assert.assertEquals(film1, film3);
		Assert.assertFalse(film1.equals(null));
		Assert.assertFalse(film1.equals("film1"));
		Assert.assertFalse(film1 == film2);
		Assert.assertTrue(film1 == film3);
		Assert.assertFalse(film2 == film3);
		Assert.assertEquals(film2, film3);
	}

	@Test
	public void testHashCode() {
		Film film1 = new Film("Бойцовский клуб", "1999", "США");
		Film film2 = new Film("Бойцовский клуб", "1999", "США");
		Assert.assertEquals(film1.hashCode(), film2.hashCode());
		Assert.assertEquals(1062275238, film1.hashCode());
		Assert.assertEquals(1062275238, film2.hashCode());
	}

	@Before
	public static void setUpToString() {
		Film film1 = new Film("Престиж", "2006", "США");
		Film film2 = new Film("Крестный отец", "1972", "США");
		Film film3 = new Film("Жизнь прекрасна", "1997", "Италия");
		films.put("Название: Престиж, год: 2006, страна: США",  film1);
		films.put("Название: Крестный отец, год: 1972, страна: США", film2);
		films.put("Название: Жизнь прекрасна, год: 1997, страна: Италия", film3);
	}
	
	@After
	public static void clearFilms() {
		films.clear();
	}
	
	@Test
	public void testTostring() {
		for (Entry<String, String> entry : fields.entrySet()) {
			final String testData = entry.getValue();
			final String expected = entry.getKey();
			final String actual = testData.toString();
			assertEquals(expected, actual);
		}
	}
}
