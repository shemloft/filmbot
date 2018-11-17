//package storage;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.List;
//import org.junit.Before;
//import org.junit.Test;
//
//import storage.DatabaseFilmHandler;
//import structures.Film;
//import utils.FilmUtils;
//
//public class DatabaseFilmHandlerTest {
//
//	private List<Film> filmList;
//
//	@Before
//	public void extractData() throws Exception {
//		DatabaseFilmHandler filmParser = new DatabaseFilmHandler(new CSVHandler("testDatabase"));
//		filmList = filmParser.filmList;
//	}
//
//	@Test
//	public void testGetFilmList() {
//		assertEquals(5, filmList.size());
//		assertEquals(FilmUtils.getFilm("0", "Побег из Шоушенка", "США", "1994", "драма"), filmList.get(0));
//	}
//
//}
