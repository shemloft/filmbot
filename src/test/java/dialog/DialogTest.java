package dialog;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import dialog.Dialog;
import dialog.Phrases;
import structures.User;
import structures.FilmsStructure;
import structures.Film;
import structures.Field;

public class DialogTest extends TestCase {
	private FilmsStructure filmsStruct;
	private User user;
	private Dialog dialog;

	private Film getFilm(String title, String country, String year) {
		Map<Field, String> filmData = new HashMap<Field, String>();
		filmData.put(Field.COUNTRY, country);
		filmData.put(Field.YEAR, year);
		return new structures.Film(title, filmData);
	}	
	
	@Before
	protected void setUp() throws Exception {
		List<Film> filmList = new ArrayList<Film>();
		filmList.add(getFilm("Бойцовский клуб", "США", "1999"));
		filmList.add(getFilm("Леон", "Франция", "1994"));
		filmList.add(getFilm("Криминальное чтиво", "США", "1994"));
		filmList.add(getFilm("Крестный отец", "США", "1972"));
		
		ArrayList<Film> userList = new ArrayList<Film>();
		userList.add(getFilm("Крестный отец", "США", "1972"));
		filmsStruct = new FilmsStructure(filmList);
		user = new User("name", userList);
		dialog = new Dialog(user, filmsStruct);
	}

	@Test
	public void testStartDialogFirstTime() throws Exception {
		user = new User("name", null);
		dialog = new Dialog(user, filmsStruct);
		assertEquals(String.format("Добро пожаловать, name.\n%s", Phrases.HELP), dialog.startDialog());
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
	public void testsUserSeenFilmYear() {
		assertEquals(Field.YEAR.noFilmsLeft(), dialog.processInput("/y 1972"));
	}

}
