package logic;

import junit.framework.Assert;
import junit.framework.TestCase;
import logic.Film;
import logic.User;

import java.util.ArrayList;

import org.junit.Test;

public class UserTest extends TestCase {
	@Test
	public void testCreateNewUser() {
		User user = new User("Даша", null);
		user.addFilm(new Film("Криминальное чтиво", "1994", "США"));
		Assert.assertEquals("Даша", user.name);
		ArrayList<Film> savedFilms = new ArrayList<Film>();
		savedFilms.add(new Film("Криминальное чтиво", "1994", "США"));
		Assert.assertEquals(savedFilms, user.savedFilms);
	}
	
	@Test
	public void testAddFilm() {
		User user1 = new User("Даша", null);
		user1.addFilm(new Film("Криминальное чтиво", "1994", "США"));
		user1.addFilm(new Film("Властелин колец: Возвращение Короля", "2003", "США"));
		ArrayList<Film> savedFilms1 = new ArrayList<Film>();
		savedFilms1.add(new Film("Криминальное чтиво", "1994", "США"));
		savedFilms1.add(new Film("Властелин колец: Возвращение Короля", "2003", "США"));
		User user2 = new User("Вика", null);
		user2.addFilm(new Film("Бойцовский клуб", "1999", "США"));
		user2.addFilm(new Film("Леон", "1994", "Франция"));
		ArrayList<Film> savedFilms2 = new ArrayList<Film>();
		savedFilms2.add(new Film("Бойцовский клуб", "1999", "США"));
		savedFilms2.add(new Film("Леон", "1994", "Франция"));
		Assert.assertEquals(savedFilms1, user1.savedFilms);
		Assert.assertEquals(savedFilms2, user2.savedFilms);
	}

}