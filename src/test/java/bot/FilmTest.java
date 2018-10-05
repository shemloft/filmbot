package bot;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;

import bot.Film;

public class FilmTest extends TestCase {
	public void testGetPossibleFields() {
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("country");
		fields.add("year");
		fields.add("title");
		Assert.assertEquals(fields, Film.getPossibleFields());
	}

	public void testGetField() {
		Film film = new Film("Леон", "1994", "Франция");
		Assert.assertEquals("Леон", film.getField("title"));
		Assert.assertEquals("Франция", film.getField("country"));
		Assert.assertEquals("1994", film.getField("year"));
		Assert.assertEquals(null, film.getField("unknown field"));
	}

	public void testGetTitle() {
		Film film = new Film("Бойцовский клуб", "1999", "США");
		Assert.assertEquals("Бойцовский клуб", film.getTitle());
	}

	public void testGetYear() {
		Film film = new Film("Бойцовский клуб", "1999", "США");
		Assert.assertEquals("1999", film.getYear());
	}

	public void testGetCountry() {
		Film film = new Film("Бойцовский клуб", "1999", "США");
		Assert.assertEquals("США", film.getCountry());
	}

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

	public void testHashCode() {
		Film film1 = new Film("Бойцовский клуб", "1999", "США");
		Film film2 = new Film("Бойцовский клуб", "1999", "США");
		Assert.assertEquals(film1.hashCode(), film2.hashCode());
		Assert.assertEquals(1062275238, film1.hashCode());
		Assert.assertEquals(1062275238, film2.hashCode());
	}

	public void testTostring() {
		Film film1 = new Film("Престиж", "2006", "США");
		Film film2 = new Film("Крестный отец", "1972", "США");
		Film film3 = new Film("Жизнь прекрасна", "1997", "Италия");
		Assert.assertEquals("Название: Престиж, год: 2006, страна: США", film1.toString());
		Assert.assertEquals("Название: Крестный отец, год: 1972, страна: США", film2.toString());
		Assert.assertEquals("Название: Жизнь прекрасна, год: 1997, страна: Италия", film3.toString());
	}
}
