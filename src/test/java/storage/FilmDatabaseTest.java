//package storage;
//
//import static org.junit.Assert.assertArrayEquals;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import storage.FilmDatabase;
//import structures.Field;
//import structures.Film;
//import utils.FilmUtils;
//
//public class FilmDatabaseTest {
//
//	private List<Film> filmList;
//	private FilmDatabase filmDatabase;
//	private IFilmDatabaseFileHandler databaseHandler;
//	private IFilmHandler fileFilmHandler;
//	private static Film film2 = FilmUtils.getFilm("2", "Крестный отец",
//			new ArrayList<String>(Arrays.asList(new String[] { "США" })),
//			new ArrayList<String>(Arrays.asList(new String[] { "1972" })),
//			new ArrayList<String>(Arrays.asList(new String[] { "драма", "криминал" })));
//
//	private void createFilmList() {
//		filmList = new ArrayList<Film>();
//		filmList.add(FilmUtils.getFilm("ID", "Фильм", new ArrayList<String>(Arrays.asList(new String[] { "Страна" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "Год" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "Жанр" }))));
//		filmList.add(FilmUtils.getFilm("0", "Леон", new ArrayList<String>(Arrays.asList(new String[] { "Франция" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "1994" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "триллер", "драма", "криминал" }))));
//		filmList.add(FilmUtils.getFilm("1", "Бойцовский клуб",
//				new ArrayList<String>(Arrays.asList(new String[] { "США", "Германия" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "1999" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "триллер", "драма", "криминал" }))));
//		filmList.add(film2);
//		filmList.add(FilmUtils.getFilm("3", "Криминальное чтиво",
//				new ArrayList<String>(Arrays.asList(new String[] { "США" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "1994" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "триллер", "комедия", "криминал" }))));
//	}
//
//	@Before
//	public void setUp() throws Exception {
//		createFilmList();
//		databaseHandler = new TestFilmDatabaseFileHandler(FilmUtils.filmListToStringList(filmList));
//		fileFilmHandler = new FileFilmHandler(databaseHandler);
//		filmDatabase = new FilmDatabase(fileFilmHandler);
//	}
//
//	@Test
//	public void testGetFilm() {
//		Map<Field, List<String>> options = new HashMap<Field, List<String>>();
//		options.put(Field.COUNTRY, new ArrayList<String>());
//		options.get(Field.COUNTRY).add("США");
//		options.put(Field.GENRE, new ArrayList<String>());
//		options.get(Field.GENRE).add("драма");
//		List<String> idList = new ArrayList<String>();
//		idList.add("1");
//		Film film = filmDatabase.getFilm(options, idList);
//		assertEquals(film2, film);
//	}
//
//	@Test
//	public void testrequestExistInDatabase() throws Exception {
//		assertTrue(filmDatabase.requestExistInDatabase(Field.COUNTRY, "США"));
//		assertTrue(filmDatabase.requestExistInDatabase(Field.COUNTRY, "Германия"));
//		assertTrue(filmDatabase.requestExistInDatabase(Field.GENRE, "комедия"));
//		assertTrue(filmDatabase.requestExistInDatabase(Field.GENRE, "триллер"));
//		assertTrue(filmDatabase.requestExistInDatabase(Field.YEAR, "1972"));
//		assertTrue(filmDatabase.requestExistInDatabase(Field.YEAR, "1994"));
//	}
//
//	@Test
//	public void testAddFilmToDatabase() throws Exception {
//		databaseHandler = new CSVHandler("a");
//		databaseHandler.saveData(FilmUtils.filmListToStringList(filmList));
//		fileFilmHandler = new FileFilmHandler(databaseHandler);
//		filmDatabase = new FilmDatabase(fileFilmHandler);
//		int oldSize = fileFilmHandler.getFilmsCount();
//		filmDatabase.addFilmToDatabase("Кек",
//				new ArrayList<String>(Arrays.asList(new String[] { "Новая Зеландия", "США" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "2000" })),
//				new ArrayList<String>(Arrays.asList(new String[] { "аниме" })));
//		List<String[]> newData = databaseHandler.extractData();
//		int newSize = fileFilmHandler.getFilmsCount();
//		assertEquals(oldSize + 1, newSize);
//		String[] lastFilm = newData.get(newSize);
//		assertArrayEquals(new String[] { "4", "Кек", "Новая Зеландия, США", "2000", "аниме" }, lastFilm);
//	}
//
//}
