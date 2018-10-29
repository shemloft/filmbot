package dialog;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import dialog.Dialog;
import dialog.Phrases;
import structures.User;
import structures.Film;
import structures.Field;
import utils.FilmUtils;

public class DialogTest extends TestCase {
	private User user;
	private Dialog dialog;
	private Map<Field, Map<String, List<Film>>> filmsData;

	@Before
	protected void setUp() throws Exception {
		List<Film> filmList = new ArrayList<Film>();
		filmList.add(FilmUtils.getFilm("8", "Бойцовский клуб", "США, Германия", "1999", "триллер, драма, криминал"));
		filmList.add(FilmUtils.getFilm("5", "Леон", "Франция", "1994", "триллер, драма, криминал"));
		filmList.add(FilmUtils.getFilm("13", "Криминальное чтиво", "США", "1994", "триллер, комедия, криминал"));
		filmList.add(FilmUtils.getFilm("12", "Крестный отец", "США", "1972", "драма, криминал"));

		List<String> userList = new ArrayList<String>();
		userList.add("12");
		user = new User("name", "name", userList, null, null);
		filmsData = FilmUtils.getFilmMapsByField(filmList);
		dialog = new Dialog(user, filmsData);
	}

	@Test
	public void testStartDialogFirstTime() throws Exception {
		user = new User("name", "name", null, null, null);
		dialog = new Dialog(user, filmsData);
		assertEquals(String.format("Добро пожаловать, name.%s", Phrases.HELP), dialog.startDialog());
	}

	@Test
	public void testStartDialogNotFirstTime() {
		assertEquals("Давно не виделись, name.", dialog.startDialog());
	}

	@Test
	public void testProcessInputHelp() {
		assertEquals(Phrases.HELP, dialog.processInput("/help"));
	}

	@Test
	public void testProcessInputShort() {
		assertEquals(Phrases.SHORT_COMMAND, dialog.processInput("c:"));
	}

	@Test
	public void testGetCountry() {
		assertEquals("Бойцовский клуб", dialog.processInput("/c США"));
	}

	@Test
	public void testGetNextCountry() {
		assertEquals("Бойцовский клуб", dialog.processInput("/c США"));
		assertEquals("Криминальное чтиво", dialog.processInput("/next"));
	}

	@Test
	public void testGetYear() {
		assertEquals("Леон", dialog.processInput("/y 1994"));
	}

	@Test
	public void testGetNextYear() {
		assertEquals("Леон", dialog.processInput("/y 1994"));
		assertEquals("Криминальное чтиво", dialog.processInput("/next"));
	}

	@Test
	public void testGetGenre() {
		assertEquals("Бойцовский клуб", dialog.processInput("/g триллер"));
	}

	@Test
	public void testGetNextGenre() {
		assertEquals("Бойцовский клуб", dialog.processInput("/g триллер"));
		assertEquals("Леон", dialog.processInput("/g триллер"));
	}

	@Test
	public void testUnknownCommand() {
		assertEquals(Phrases.UNKNOWN_COMMAND, dialog.processInput("/p lol"));
	}

	@Test
	public void testGetYearWhichNotInFilmList() {
		assertEquals(Field.YEAR.noFilmsAtAll(), dialog.processInput("/y 900"));
	}

	@Test
	public void testGetCountryWhichNotInFilmList() {
		assertEquals(Field.COUNTRY.noFilmsAtAll(), dialog.processInput("/c Нарния"));
	}

	@Test
	public void testGetGenreWhichNotInFilmList() {
		assertEquals(Field.GENRE.noFilmsAtAll(), dialog.processInput("/g телепузик"));
	}

	@Test
	public void testNextWithoutOption() {
		assertEquals(Phrases.NEXT_WITHOUT_OPT, dialog.processInput("/next"));
	}

	@Test
	public void testNoNextFilmYear() {
		assertEquals("Бойцовский клуб", dialog.processInput("/y 1999"));
		assertEquals(Field.YEAR.noFilmsLeft(), dialog.processInput("/next"));
	}

	@Test
	public void testNoNextFilmCountry() {
		assertEquals("Леон", dialog.processInput("/c Франция"));
		assertEquals(Field.COUNTRY.noFilmsLeft(), dialog.processInput("/next"));
	}

	@Test
	public void testNoNextFilmGenre() {
		assertEquals("Криминальное чтиво", dialog.processInput("/g комедия"));
		assertEquals(Field.GENRE.noFilmsLeft(), dialog.processInput("/next"));
	}

	@Test
	public void testsUserSeenFilmYear() {
		assertEquals(Field.YEAR.noFilmsLeft(), dialog.processInput("/y 1972"));
	}

	@Test
	public void testsUserNextWithCurrentField() {
		List<String> userList = new ArrayList<String>();
		userList.add("5");
		user = new User("name", "name", userList, "YEAR", "1994");
		dialog = new Dialog(user, filmsData);
		assertEquals("Криминальное чтиво", dialog.processInput("/next"));
	}

}
