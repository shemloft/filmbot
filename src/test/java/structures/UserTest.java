package structures;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import structures.Film;
import structures.User;

public class UserTest extends TestCase {
	private Film getFilm(String title, String country, String year) {
		Map<Field, String> filmData = new HashMap<Field, String>();
		filmData.put(Field.COUNTRY, country);
		filmData.put(Field.YEAR, year);
		return new structures.Film(title, filmData);
	}
	
	@Test
	public void testCreateUserFirstEntry() {
		User user = new User("Даша", null);
		Assert.assertEquals("Даша", user.name);
		Assert.assertEquals(new ArrayList<Film>(), user.savedFilms);
	}

	@Test
	public void testCreateUserSecondEntry() {
		List<Film> filmList = new ArrayList<Film>();
		filmList.add(getFilm("Бойцовский клуб", "США", "1999"));
		User user = new User("Даша", filmList);
		Assert.assertEquals("Даша", user.name);
		Assert.assertEquals("Бойцовский клуб", user.savedFilms.get(0).title);
		Assert.assertEquals(1, user.savedFilms.size());
	}

	@Test
	public void testAddFilm() {
		List<Film> filmList = new ArrayList<Film>();
		filmList.add(getFilm("Бойцовский клуб", "США", "1999"));
		User user = new User("Даша", filmList);
		user.addFilm(getFilm("Криминальное чтиво", "США", "1994"));
		Assert.assertEquals("Бойцовский клуб", user.savedFilms.get(0).title);
		Assert.assertEquals("Криминальное чтиво", user.savedFilms.get(1).title);
		Assert.assertEquals(2, user.savedFilms.size());
	}
}