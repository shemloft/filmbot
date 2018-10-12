package storage;

import junit.framework.TestCase;
import storage.HelperCSV;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;;

public class HelperCSVTest extends TestCase {
	
	@Test(expected = FileNotFoundException.class) // вот тут тебе вроде что то сделать надо
	public void testExtractEmptyData() throws Exception {
		try {
			new HelperCSV("").extractData();
		} catch (FileNotFoundException e) {
			assertEquals("Ошибочка с базой данных, перепроверьте её", e.getMessage());
		}
	}

	@Test(expected = FileNotFoundException.class)
	public void testExtractWrongData() throws Exception {
		try {
			new HelperCSV("Kek").extractData();
		} catch (FileNotFoundException e) {
			assertEquals("Ошибочка с базой данных, перепроверьте её", e.getMessage());
		}
	}

	@Test
	public void testExtractRightData() throws Exception {
		List<String[]> data = new ArrayList<String[]>();
		try {
			data = new HelperCSV("Database").extractData();
		} catch (FileNotFoundException e) {
			assertEquals("Ошибочка с базой данных, перепроверьте её", e.getMessage());
		}
		assertEquals(20, data.size());
	}

	@Test
	public void testCreateFile() throws IOException {
		new HelperCSV("Vika").saveData(new ArrayList<String[]>());
		File file = new File("Vika.csv");
		assertEquals(true, file.isFile());
		file.delete();
	}

	@Test
	public void testAddAndReadInfo() throws Exception {
		List<String[]> savedFilms = new ArrayList<String[]>();
		String[] row = {"Леон", "Франция", "1994"};
		savedFilms.add(row);
		new HelperCSV("test").saveData(savedFilms);
		File file = new File("test.csv");
		List<String[]> newSavedFilms = new HelperCSV("test").extractData();
		String[] readRow = newSavedFilms.get(0);
		for (int i = 0; i < row.length; i++)
			assertEquals(row[i], readRow[i]);
		file.delete();
	}

}
