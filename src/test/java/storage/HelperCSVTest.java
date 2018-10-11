//package storage;
//
//import junit.framework.Assert;
//import junit.framework.TestCase;
//import storage.HelperCSV;
//import structures.Film;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Test;;
//
//public class HelperCSVTest extends TestCase {
//
//	@Test(expected = FileNotFoundException.class)
//	public void testExtractEmptyData() throws Exception {
//		try {
//			HelperCSV.extractData("");
//		} catch (FileNotFoundException e) {
//			Assert.assertEquals("Ошибочка с базой данных, перепроверьте её", e.getMessage());
//		}
//	}
//
//	@Test(expected = FileNotFoundException.class)
//	public void testExtractWrongData() throws Exception {
//		try {
//			HelperCSV.extractData("Kek.csv");
//		} catch (FileNotFoundException e) {
//			Assert.assertEquals("Ошибочка с базой данных, перепроверьте её", e.getMessage());
//		}
//	}
//
//	@Test
//	public void testExtractRightData() throws Exception {
//		List<String[]> data = new ArrayList<String[]>();
//		try {
//			data = HelperCSV.extractData("Database.csv");
//		} catch (FileNotFoundException e) {
//			Assert.assertEquals("Ошибочка с базой данных, перепроверьте её", e.getMessage());
//		}
//		Assert.assertEquals(20, data.size());
//	}
//
//	@Test
//	public void testCreateFile() throws IOException {
//		HelperCSV.createFile("Vika");
//		File file = new File("Vika.csv");
//		Assert.assertEquals(true, file.isFile());
//		file.delete();
//	}
//
//	@Test
//	public void testAddAndReadInfo() throws Exception {
//		ArrayList<Film> savedFilms = new ArrayList<Film>();
//		Film film = new Film("Леон", "1994", "Франция");
//		savedFilms.add(film);
//		HelperCSV.createFile("test");
//		File file = new File("test.csv");
//		HelperCSV.addInfo("test", savedFilms);
//		ArrayList<Film> newSavedFilms = new ArrayList<Film>();
//		HelperCSV helper = new HelperCSV("test.csv");
//		newSavedFilms = helper.readInfo("test");
//		Assert.assertEquals(savedFilms, newSavedFilms);
//		file.delete();
//	}
//
//}
