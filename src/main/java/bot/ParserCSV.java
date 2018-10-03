package bot;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class ParserCSV {

	private String sFileName;
	public ArrayList<Film> filmList = new ArrayList<Film>();
	
	
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
	

	public static List<String[]> extractData(String fileName) throws Exception {
		List<String[]> allRows = new ArrayList<String[]>();
		try {
			CSVReader reader = new CSVReader(new FileReader(fileName), ',', '"', 1);
			allRows = reader.readAll();
			reader.close();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Ошибочка с базой данных, перепроверьте её");
		}
		return allRows;
	}
}