package structures;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import structures.Film;
import structures.User;
import utils.FilmUtils;

public class UserTest extends TestCase {

	@Test
	public void testCreateUserFirstEntry() {
		User user = new User("Даша", null);
		assertEquals("Даша", user.name);
		assertEquals(new ArrayList<Film>(), user.savedFilms);
	}

	@Test
	public void testCreateUserSecondEntry() {
		List<Film> filmList = new ArrayList<Film>();
		filmList.add(FilmUtils.getFilm("8", "Бойцовский клуб", "США, Германия", "1999", "триллер, драма, криминал"));
		User user = new User("Даша", filmList);
		assertEquals("Даша", user.name);
		assertEquals("Бойцовский клуб", user.savedFilms.get(0).title);
		assertEquals(1, user.savedFilms.size());
	}

	@Test
	public void testAddFilm() {
		List<Film> filmList = new ArrayList<Film>();
		filmList.add(FilmUtils.getFilm("8", "Бойцовский клуб", "США, Германия", "1999", "триллер, драма, криминал"));
		User user = new User("Даша", filmList);
		user.addFilm(FilmUtils.getFilm("13", "Криминальное чтиво", "США", "1994", "триллер, комедия, криминал"));
		assertEquals("Бойцовский клуб", user.savedFilms.get(0).title);
		assertEquals("Криминальное чтиво", user.savedFilms.get(1).title);
		assertEquals(2, user.savedFilms.size());
	}
}