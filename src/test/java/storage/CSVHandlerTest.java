package storage;

import storage.CSVHandler;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CSVHandlerTest {

	@Test(expected = FileNotFoundException.class)
	public void testExtractEmptyData() throws Exception {
		new CSVHandler("").extractData();
	}

	@Test
	public void testExtractWrongData() throws Exception {
		try {
			new CSVHandler("Kek").extractData();
		} catch (FileNotFoundException e) {
			assertEquals("Ошибочка с базой данных, перепроверьте её", e.getMessage());
		}
	}

	@Test
	public void testExtractRightData() throws Exception {
		List<String[]> data = new ArrayList<String[]>();
		data = new CSVHandler("testDatabase").extractData();
		assertEquals(4, data.size());
		assertEquals("Операция «Ы» и другие приключения Шурика", data.get(0)[0]);
		assertEquals("СССР", data.get(0)[1]);
		assertEquals("1965", data.get(0)[2]);
	}

	@Test
	public void testCreateFile() throws IOException {
		new CSVHandler("Vika").saveData(new ArrayList<String[]>());
		File file = new File("Vika.csv");
		assertEquals(true, file.isFile());
		file.delete();
	}

	@Test
	public void testAddAndReadInfo() throws Exception {
		List<String[]> savedFilms = new ArrayList<String[]>();
		String[] row = { "Леон", "Франция", "1994" };
		savedFilms.add(row);
		new CSVHandler("test").saveData(savedFilms);
		File file = new File("test.csv");
		List<String[]> newSavedFilms = new CSVHandler("test").extractData();
		String[] readRow = newSavedFilms.get(0);
		for (int i = 0; i < row.length; i++)
			assertEquals(row[i], readRow[i]);
		file.delete();
	}

}
