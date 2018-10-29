package structures;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import structures.User;
import utils.FilmUtils;

public class UserTest extends TestCase {

	@Test
	public void testCreateUserFirstEntry() {
		User user = new User("Даша", "Даша", null, null, null);

		assertEquals("Даша", user.name);
		assertEquals(new ArrayList<String>(), user.savedFilmsIDs);
	}

	@Test
	public void testCreateUserSecondEntry() {
		List<String> IDList = new ArrayList<String>();
		IDList.add("8");
		User user = new User("Даша", "Даша", IDList, null, null);

		assertEquals("Даша", user.name);
		assertEquals("8", user.savedFilmsIDs.get(0));
		assertEquals(1, user.savedFilmsIDs.size());
	}

	@Test
	public void testCreateUserEntryWithField() {
		List<String> IDList = new ArrayList<String>();
		IDList.add("8");
		User user = new User("Даша", "Даша", IDList, "COUNTRY", "США");

		assertEquals("Даша", user.name);
		assertEquals("8", user.savedFilmsIDs.get(0));
		assertEquals(1, user.savedFilmsIDs.size());
		assertEquals(Field.COUNTRY, user.currentField);
		assertEquals("США", user.currentOptions.get(Field.COUNTRY));
		assertEquals(null, user.currentOptions.get(Field.YEAR));
	}

	@Test
	public void testAddFilm() {
		List<String> IDList = new ArrayList<String>();
		IDList.add("8");
		User user = new User("Даша", "Даша", IDList, null, null);

		user.addFilm(FilmUtils.getFilm("13", "Криминальное чтиво", "США", "1994", "триллер, комедия, криминал"));

		assertEquals("8", user.savedFilmsIDs.get(0));
		assertEquals("13", user.savedFilmsIDs.get(1));
		assertEquals(2, user.savedFilmsIDs.size());
	}

	@Test
	public void testChangeCurrentOption() {
		List<String> IDList = new ArrayList<String>();
		IDList.add("8");
		User user = new User("Даша", "Даша", IDList, "COUNTRY", "США");

		user.changeCurrentOption(Field.YEAR, "2008");

		assertEquals(Field.YEAR, user.currentField);
		assertEquals(null, user.currentOptions.get(Field.COUNTRY));
		assertEquals("2008", user.currentOptions.get(Field.YEAR));
	}

	@Test
	public void testClearCurrentField() {
		List<String> IDList = new ArrayList<String>();
		IDList.add("8");
		User user = new User("Даша", "Даша", IDList, "COUNTRY", "США");

		user.clearCurrentField();

		assertEquals(null, user.currentField);
		assertEquals(null, user.currentOptions.get(Field.COUNTRY));
	}

	@Test
	public void testGetCurrentKey() {
		List<String> IDList = new ArrayList<String>();
		IDList.add("8");
		User user = new User("Даша", "Даша", IDList, "COUNTRY", "США");

		assertEquals("США", user.getCurrentKey());
	}

	@Test
	public void testGetCurrentKeyNull1() {
		List<String> IDList = new ArrayList<String>();
		IDList.add("8");
		User user = new User("Даша", "Даша", IDList, null, null);

		assertEquals(null, user.getCurrentKey());
	}

	@Test
	public void testGetCurrentKeyNull2() {
		List<String> IDList = new ArrayList<String>();
		IDList.add("8");
		User user = new User("Даша", "Даша", IDList, "COUNTRY", null);

		assertEquals(null, user.getCurrentKey());
	}

}