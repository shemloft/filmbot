package storage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import structures.Field;
import structures.Film;

public class HelperCSV implements DatabaseHelper {
	/*очень сумбурный класс, может как то разбить или что то такое
	 * потому что вот есть у нас в конструкторе инпут, а есть еще куча рандомных статтических метожов и так далее*/

	private String sFileName;

	public HelperCSV(String inputFileName) throws Exception {
		sFileName = inputFileName;
	}

	public static void createFile(String s_name) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(s_name + ".csv", true), ';', '"');
		String[] record = { "Фильм", "Страна", "Год" };
		writer.writeNext(record);
		writer.close();
	}

	public static void addInfo(String s_name, List<Film> savedFilms) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(s_name + ".csv", true), ';', '"');
		for (Film film : savedFilms) {
			String[] record = { film.title, film.getField(Field.COUNTRY), film.getField(Field.YEAR)};
			writer.writeNext(record);
		}
		writer.close();
	}

//	public List<Film> readInfo(String s_name) throws Exception {
//		HelperCSV parser = new HelperCSV(s_name + ".csv"); // погоди, почему тут хелпер в хелпере, глупость какая то
//		return parser.filmList;
//	}



	public List<String[]> extractData() throws Exception {
		List<String[]> allRows = new ArrayList<String[]>();
		try {
			CSVReader reader = new CSVReader(new FileReader(sFileName), ';', '"', 1);
			allRows = reader.readAll();
			reader.close();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(dialog.Phrases.DATABASE_ERROR);
		}
		return allRows;
	}
}