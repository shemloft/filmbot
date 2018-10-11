//package dialog;
//
//import junit.framework.Assert;
//import junit.framework.TestCase;
//import logic.User;
//import structures.Film;
//
//import java.util.ArrayList;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import bot.ChatBot;
//import dialog.Dialog;
//import dialog.Phrases;
//
//public class DialogTest extends TestCase {
//	private ChatBot bot;
//	private User user;
//	private Dialog dialog;
//
//	@Before
//	protected void setUp() throws Exception {
//		ArrayList<Film> filmList = new ArrayList<Film>();
//		filmList.add(new Film("Престиж", "2006", "США"));
//		filmList.add(new Film("Крестный отец", "1972", "США"));
//		filmList.add(new Film("Жизнь прекрасна", "1997", "Италия"));
//		filmList.add(new Film("Достучаться до небес", "1997", "Германия"));
//		filmList.add(new Film("Леон", "1994", "Франция"));
//		ArrayList<Film> userList = new ArrayList<Film>();
//		userList.add(new Film("Леон", "1994", "Франция"));
//		bot = new ChatBot(filmList);
//		user = new User("name", userList);
//		dialog = new Dialog(bot, user);
//	}
//
//	@Test
//	public void testStartDialogFirstTime() throws Exception {
//		user = new User("name", null);
//		dialog = new Dialog(bot, user);
//		Assert.assertEquals(String.format("Добро пожаловать, name.\n%s", Phrases.HELP), dialog.startDialog());
//	}
//
//	@Test
//	public void testStartDialogNotFirstTime() {
//		Assert.assertEquals("Давно не виделись, name.", dialog.startDialog());
//	}
//
//	@Test
//	public void testProcessInputHelp() {
//		Assert.assertEquals(Phrases.HELP, dialog.processInput("/help"));
//	}
//
//	@Test
//	public void testProcessInputShort() {
//		Assert.assertEquals(Phrases.SHORT_COMMAND, dialog.processInput("c:"));
//	}
//
//	@Test
//	public void testGetCountry() {
//		Assert.assertEquals("Престиж", dialog.processInput("/c США"));
//	}
//
//	@Test
//	public void testGetNextCountry() {
//		Assert.assertEquals("Престиж", dialog.processInput("/c США"));
//		Assert.assertEquals("Крестный отец", dialog.processInput("/next"));
//	}
//
//	@Test
//	public void testGetYear() {
//		Assert.assertEquals("Жизнь прекрасна", dialog.processInput("/y 1997"));
//	}
//
//	@Test
//	public void testGetNextYear() {
//		Assert.assertEquals("Жизнь прекрасна", dialog.processInput("/y 1997"));
//		Assert.assertEquals("Достучаться до небес", dialog.processInput("/next"));
//	}
//
//	@Test
//	public void testUnknownCommand() {
//		Assert.assertEquals(Phrases.UNKNOWN_COMMAND, dialog.processInput("/p lol"));
//	}
//
//	@Test
//	public void testYearNan() {
//		Assert.assertEquals(Phrases.YEAR_NAN, dialog.processInput("/y year"));
//	}
//
//	@Test
//	public void testGetYearWhichNotInFilmList() {
//		Assert.assertEquals("В базе нет фильмов, снятых в этот год :с", dialog.processInput("/y 900"));
//	}
//
//	@Test
//	public void testGetCountryWhichNotInFilmList() {
//		Assert.assertEquals("В базе нет фильмов, снятых в этой стране :с", dialog.processInput("/c Нарния"));
//	}
//
//	@Test
//	public void testNextWithoutOption() {
//		Assert.assertEquals(Phrases.NEXT_WITHOUT_OPT, dialog.processInput("/next"));
//	}
//
//	@Test
//	public void testNoNextFilmYear() {
//		Assert.assertEquals("Престиж", dialog.processInput("/y 2006"));
//		Assert.assertEquals("Все фильмы этого года, имеющиеся в базе, были предоставлены",
//				dialog.processInput("/next"));
//	}
//
//	@Test
//	public void testNoNextFilmCountry() {
//		Assert.assertEquals("Достучаться до небес", dialog.processInput("/c Германия"));
//		Assert.assertEquals("Все фильмы этой страны, имеющиеся в базе, были предоставлены",
//				dialog.processInput("/next"));
//	}
//
//	@Test
//	public void testsUserSeenFilmCountry() {
//		Assert.assertEquals("Все фильмы этой страны, имеющиеся в базе, были предоставлены",
//				dialog.processInput("/c Франция"));
//	}
//
//}
