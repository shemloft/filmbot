package bot;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.security.KeyException;
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
			String year = filmData[2];
			Film film = new bot.Film(name, year, country);
			filmList.add(film);		
		}		
	}
	
	
	public Map<String, ArrayList<Film>> getFilmsByYear() throws Exception 
	{
		return getDictionary("year");
	}

	public Map<String, ArrayList<Film>> getFilmsByCountry() throws Exception
	{
		return getDictionary("country");
	}
	
 	private Map<String, ArrayList<Film>> getDictionary(String keyName) throws KeyException
 	{
 		Map<String, ArrayList<Film>> filmsDictionary = new HashMap<String, ArrayList<Film>>();
		for (Film film: filmList) {			
			String key = film.getField(keyName);
			if (!filmsDictionary.containsKey(key)) {
				ArrayList<Film> filmListByKey = new ArrayList<Film>();
				filmListByKey.add(film);
				filmsDictionary.put(key, filmListByKey);
			} else {
				filmsDictionary.get(key).add(film);
			}
		}
		return filmsDictionary;
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