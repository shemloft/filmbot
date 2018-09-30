package bot;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class ParserCSV {
	@SuppressWarnings("resource")//не уверена, нужно ли это

	public static Map<Integer, ArrayList<String>> getFilmsByYear(String fileName) throws Exception // "Database.csv"
	// словарь со списками фильмов по годам
	{
		Map<Integer, ArrayList<String>> filmsByYear = new HashMap<Integer, ArrayList<String>>();
		List<String[]> Database = extractData(fileName);
		for (String[] row : Database) {
			String[] filmData = row[0].split(";");
			String name = filmData[0];
			Integer year = Integer.parseInt(filmData[2]);
			if (!filmsByYear.containsKey(year)) {
				ArrayList<String> filmList = new ArrayList<String>();
				filmList.add(name);
				filmsByYear.put(year, filmList);
			} else {
				filmsByYear.get(year).add(name);
			}
		}
		return filmsByYear;
	}

	public static Map<String, ArrayList<String>> getFilmsByCountry(String fileName) throws Exception // "Database.csv"
	// словарь со списками фильмов по странам
	{
		Map<String, ArrayList<String>> filmsByCountry = new HashMap<String, ArrayList<String>>();
		List<String[]> Database = extractData(fileName);
		for (String[] row : Database) {
			String[] filmData = row[0].split(";");
			String name = filmData[0];
			String country = filmData[1];
			if (!filmsByCountry.containsKey(country)) {
				ArrayList<String> filmList = new ArrayList<String>();
				filmList.add(name);
				filmsByCountry.put(country, filmList);
			} else {
				filmsByCountry.get(country).add(name);
			}
		}
		return filmsByCountry;
	}

	public static List<String[]> extractData(String fileName) throws Exception {
		List<String[]> allRows = new ArrayList<String[]>();
		try {
			CSVReader reader = new CSVReader(new FileReader(fileName), ',', '"', 1);
			allRows = reader.readAll();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Ошибочка с базой данных, перепроверьте её");
		}
		return allRows;
	}
}