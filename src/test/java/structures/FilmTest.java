package structures;

import org.junit.Test;

import structures.Film;
import structures.Field;
import utils.FilmUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class FilmTest {

	@Test
	public void testGetField() {
		Film film = FilmUtils.getFilm("5", "Леон", "Франция", "1994", "триллер, драма, криминал");
		assertEquals("Франция", film.getField(Field.COUNTRY));
		assertEquals("1994", film.getField(Field.YEAR));
		assertEquals("триллер, драма, криминал", film.getField(Field.GENRE));
	}

	@Test
	public void testEquals() {
		Film film1 = FilmUtils.getFilm("1", "Бойцовский клуб", "США", "1999", "драма");
		Film film2 = FilmUtils.getFilm("1", "Бойцовский клуб", "США", "1999", "мама");
		Film film3 = FilmUtils.getFilm("2", "Бойцовский клуб", "США", "1997", "рама");
		Film film4 = FilmUtils.getFilm("2", "Бойцовский клуп", "США", "1997", "футурама");
		assertEquals(film1, film2);
		assertEquals(film1, film1);
		assertFalse(film1.equals(film3));
		assertFalse(film1.equals(film4));
		assertFalse(film1.equals(null));
	}

	@Test
	public void testHashCode() {
		Film film1 = FilmUtils.getFilm("1", "Бойцовский клуб", "США", "1999", "драма");
		Film film2 = FilmUtils.getFilm("1", "Бойцовский клуб", "США", "1999", "драма");
		assertEquals(film1.hashCode(), film2.hashCode());
	}

	@Test
	public void testTostring() {
		Film film = FilmUtils.getFilm("14", "Престиж", "США, Великобритания", "2006", "фантастика, триллер, драма");
		assertEquals("Название: Престиж, год: 2006, страна: США, Великобритания, жанр: фантастика, триллер, драма",
				film.toString());
	}

}
