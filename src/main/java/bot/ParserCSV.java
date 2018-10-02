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

	private String sFileName;
	private List<Film> filmList = new ArrayList<Film>();
	
	
	public ParserCSV(String inputFileName) throws Exception
	{
		sFileName = inputFileName;
		getFilmsList();
	}
	
	private void getFilmsList() throws Exception
	{
		List<String[]> Database = extractData(sFileName);
		for (String[] row : Database) {			
			String[] filmData = row[0].split(";");
			String name = filmData[0];
			String country = filmData[1];
			Integer year = Integer.parseInt(filmData[2]);
			Film film = new bot.Film(name, year, country);
			filmList.add(film);		
		}		
	}
	
	
	public Map<Integer, ArrayList<Film>> getFilmsByYear() throws Exception // "Database.csv"
	// словарь со списками фильмов по годам
	{
		Map<Integer, ArrayList<Film>> filmsByYear = new HashMap<Integer, ArrayList<Film>>();
		for (Film film : filmList) {
			Integer year = film.getYear();
			if (!filmsByYear.containsKey(year)) {
				ArrayList<Film> filmList = new ArrayList<Film>();
				filmList.add(film);
				filmsByYear.put(year, filmList);
			} else {
				filmsByYear.get(year).add(film);
			}
		}
		return filmsByYear;
	}

	public Map<String, ArrayList<Film>> getFilmsByCountry() throws Exception // "Database.csv"
	// словарь со списками фильмов по странам
	{
		Map<String, ArrayList<Film>> filmsByCountry = new HashMap<String, ArrayList<Film>>();
		for (Film film: filmList) {			
			String country = film.getCountry();
			if (!filmsByCountry.containsKey(country)) {
				ArrayList<Film> filmList = new ArrayList<Film>();
				filmList.add(film);
				filmsByCountry.put(country, filmList);
			} else {
				filmsByCountry.get(country).add(film);
			}
		}
		return filmsByCountry;
	}

	private List<String[]> extractData(String fileName) throws Exception {
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