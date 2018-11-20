package storage;

import storage.CSVHandler;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dialog.Phrases;

public class CSVHandlerTest {

	private static List<String[]> savedFilms;
	
	@Before
	public void setUp() {
		savedFilms = new ArrayList<String[]>();
		String[] header = { "ID", "Фильм", "Страна", "Год", "Жанр" };
		String[] row = { "5", "Леон", "Франция", "1994", "триллер, драма, криминал" };
		savedFilms.add(header);
		savedFilms.add(row);
	}
	
	@Test(expected = FileNotFoundException.class)
	public void testExtractEmptyData() throws Exception {
		new CSVHandler("").extractData();
	}

	@Test
	public void testExtractWrongData() throws Exception {
		try {
			new CSVHandler("Kek").extractData();
		} catch (FileNotFoundException e) {
			assertEquals(Phrases.DATABASE_ERROR, e.getMessage());
		}
	}

	@Test
	public void testExtractRightData() throws Exception {
		List<String[]> data = new ArrayList<String[]>();
		data = new CSVHandler("testDatabase").extractData();
		String[] filmData = data.get(1);
		assertEquals(5, data.size());
		assertEquals("0", filmData[0]);
		assertEquals("Леон", filmData[1]);
		assertEquals("Франция", filmData[2]);
		assertEquals("1994", filmData[3]);
		assertEquals("триллер, драма, криминал", filmData[4]);
	}

	@Test
	public void testCreateFile() throws IOException {
		new CSVHandler("Vika").saveData(new ArrayList<String[]>());
		File file = new File("Vika.csv");
		assertEquals(true, file.isFile());
		file.delete();
	}
	
	@Test
	public void testDeleteData() throws IOException {
		CSVHandler csvHandler = new CSVHandler("a");
		csvHandler.saveData(savedFilms);
		csvHandler.deleteData(1);
		List<String[]> newSavedFilms = csvHandler.extractData();
		assertEquals(1, newSavedFilms.size());
		for (int i = 0; i < savedFilms.get(0).length; i++)
			assertEquals(savedFilms.get(0)[i], newSavedFilms.get(0)[i]);
		File file = new File("a.csv");
		file.delete();
	}

	@Test
	public void testAddAndReadInfo() throws Exception {
		new CSVHandler("test").saveData(savedFilms);
		List<String[]> newSavedFilms = new CSVHandler("test").extractData();
		String[] readRow = newSavedFilms.get(1);
		String[] row = savedFilms.get(1);
		for (int i = 0; i < row.length; i++)
			assertEquals(row[i], readRow[i]);
	}

	@After
	public void deleteFile() {
		File file = new File("test.csv");
		file.delete();
	}

}
