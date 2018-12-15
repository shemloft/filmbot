//package storage;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//import java.util.ArrayList;
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
//import structures.Options;
//import structures.User;
//
//public class FilmDatabaseTest {
//
//	private List<Film> filmList;
//	private FilmDatabase filmDatabase;
//	private IFilmHandler filmHandler;
//
//	private void createFilmList() {
//		filmList = new ArrayList<Film>();
//		filmList.add(new Film(1, "title1", new Options(), null, null));
//		filmList.add(new Film(2, "title2", new Options(), null, null));
//	}
//
//	@Before
//	public void setUp() throws Exception {
//		createFilmList();
//		String[] genres = new String[] {"anime", "memes"};
//		Map<Field, String[]> fields = new HashMap<Field, String[]>();
//		fields.put(Field.GENRE, genres);
//		filmHandler = new TestFilmHandler(filmList, fields);
//		filmDatabase = new FilmDatabase(filmHandler);
//	}
//
//	@Test
//	public void testGetFilmCleanUser() {
//		Options options = new Options();
//		Film film = filmDatabase.getFilm(new ArrayList<Integer>(), options);
//		assertEquals("title1", film.getTitle());
//	}
//	
//	@Test
//	public void testGetFilmSecondTime() {
//		Options options = new Options();
//		User user = new User("");
//		user.addFilm(filmList.get(0));
//		Film film = filmDatabase.getFilm(user.savedFilmsIDs, options);
//		assertEquals("title2", film.getTitle());
//	}
//	
//	@Test
//	public void noFilmsLeft() {
//		Options options = new Options(); 
//		User user = new User("");
//		user.addFilm(filmList.get(0));
//		user.addFilm(filmList.get(1));
//		assertEquals(null, filmDatabase.getFilm(user.savedFilmsIDs, options));
//	}
//	
//	@Test
//	public void noFilmsAtAll() {
//		Options options = new Options();
//		filmHandler = new TestFilmHandler(new ArrayList<Film>(), null);
//		filmDatabase = new FilmDatabase(filmHandler);
//		User user = new User("");
//		Film film = filmDatabase.getFilm(user.savedFilmsIDs, options);
//		assertEquals(null, film.getTitle());
//	}
//	
//	@Test
//	public void testFieldExistsInDatabse() {
//		assertTrue(filmDatabase.requestExistInDatabase(Field.GENRE, "memes"));
//	}
//	
//	@Test
//	public void testFieldNotExistsInDatabse() {
//		assertFalse(filmDatabase.requestExistInDatabase(Field.GENRE, "genre"));
//	}
//
//}
