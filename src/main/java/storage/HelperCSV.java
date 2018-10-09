package storage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import logic.Film;

public class HelperCSV {

	private String sFileName;
	public ArrayList<Film> filmList = new ArrayList<Film>();

	public HelperCSV(String inputFileName) throws Exception {
		sFileName = inputFileName;
		getFilmsList();
	}

	public static void createFile(String s_name) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(s_name + ".csv", true), ';', '"');
		String[] record = { "Фильм", "Страна", "Год" };
		writer.writeNext(record);
		writer.close();
	}

	public static void addInfo(String s_name, ArrayList<Film> savedFilms) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(s_name + ".csv", true), ';', '"');
		for (Film film : savedFilms) {
			String[] record = { film.getTitle(), film.getCountry(), film.getYear() };
			writer.writeNext(record);
		}
		writer.close();
	}

	public ArrayList<Film> readInfo(String s_name) throws Exception {
		HelperCSV parser = new HelperCSV(s_name + ".csv");
		return parser.filmList;
	}

	private void getFilmsList() throws Exception {
		List<String[]> Database = extractData(sFileName);
		for (String[] row : Database) {
			String name = row[0];
			String country = row[1];
			String year = row[2];
			Film film = new logic.Film(name, year, country);
			filmList.add(film);
		}
	}

	public static List<String[]> extractData(String fileName) throws Exception {
		List<String[]> allRows = new ArrayList<String[]>();
		try {
			CSVReader reader = new CSVReader(new FileReader(fileName), ';', '"', 1);
			allRows = reader.readAll();
			reader.close();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(dialog.Phrases.DATABASE_ERROR);
		}
		return allRows;
	}
}