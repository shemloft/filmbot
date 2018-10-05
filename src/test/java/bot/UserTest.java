package bot;

import junit.framework.Assert;
import junit.framework.TestCase;

import bot.User;

import java.util.ArrayList;

import bot.Film;

public class UserTest extends TestCase {
	public void testCreateUser() {
		User user = new User("Даша");
		user.addFilm(new Film("Криминальное чтиво", "1994", "США"));
		Assert.assertEquals("Даша", user.Name);
		ArrayList<Film> savedFilms = new ArrayList<Film>();
		savedFilms.add(new Film("Криминальное чтиво", "1994", "США"));
		Assert.assertEquals(savedFilms, user.savedFilms);
	}

	public void testAddFilm() {
		User user1 = new User("Даша");
		user1.addFilm(new Film("Криминальное чтиво", "1994", "США"));
		user1.addFilm(new Film("Властелин колец: Возвращение Короля", "2003", "США"));
		ArrayList<Film> savedFilms1 = new ArrayList<Film>();
		savedFilms1.add(new Film("Криминальное чтиво", "1994", "США"));
		savedFilms1.add(new Film("Властелин колец: Возвращение Короля", "2003", "США"));
		User user2 = new User("Вика");
		user2.addFilm(new Film("Бойцовский клуб", "1999", "США"));
		user2.addFilm(new Film("Леон", "1994", "Франция"));
		ArrayList<Film> savedFilms2 = new ArrayList<Film>();
		savedFilms2.add(new Film("Бойцовский клуб", "1999", "США"));
		savedFilms2.add(new Film("Леон", "1994", "Франция"));
		Assert.assertEquals(savedFilms1, user1.savedFilms);
		Assert.assertEquals(savedFilms2, user2.savedFilms);
	}

}