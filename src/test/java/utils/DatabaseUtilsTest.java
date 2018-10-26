package utils;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import structures.Film;
import structures.User;
import utils.FilmUtils;
import utils.DatabaseUtils;

public class DatabaseUtilsTest {
	private List<Film> filmList = new ArrayList<Film>();
	private Film film1 = FilmUtils.getFilm("13", "Криминальное чтиво", "США", "1994", "триллер, комедия, криминал");
	private Film film2 = FilmUtils.getFilm("12", "Крестный отец", "США", "1972", "драма, криминал");
	private User user = new User("a", filmList);

	@Before
	public void createFilmList() {
		filmList.add(film1);
		filmList.add(film2);
	}

	@Before
	public void testSaveUser() throws Exception {
		DatabaseUtils.saveUser(user);
	}

	@Test(expected = Exception.class)
	public void testSaveUserError() throws Exception {
		DatabaseUtils.saveUser(new User("/exit", null));
	}

	@Test
	public void testTryGetUserFilmList() {
		assertEquals(DatabaseUtils.tryGetUserFilmList(user.name), user.savedFilms);
	}

	@After
	public void deleteFile() {
		File file = new File(user.name + ".csv");
		file.delete();
	}
}
