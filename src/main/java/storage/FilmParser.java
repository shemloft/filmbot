package storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


import structures.Film;
import structures.Field;


public class FilmParser {
	
	DatabaseHelper databaseHelper;	
	
	public FilmParser(DatabaseHelper databaseHelper) throws Exception {
		this.databaseHelper = databaseHelper;	
	}
	
	public List<Film> getFilmList() throws Exception {
		List<Film> filmList = new ArrayList<Film>();
		List<String[]> Database = databaseHelper.extractData();
		for (String[] row : Database) {
			String title = row[0];
			Map<Field, String> filmData = new HashMap<Field, String>();
			for (Field field : Field.values()) 
				filmData.put(field, row[field.ordinal() + 1]);
			Film film = new structures.Film(title, filmData);
			filmList.add(film);
		}
		return filmList;
	}
	
	
	public void saveFilms(List<Film> filmList) throws Exception {
		int rowLength = Field.values().length + 1;		
		List<String[]> rowList = new ArrayList<String[]>();
		for (Film film : filmList) {
			String[] filmRow = new String[rowLength];
			filmRow[0] = film.title;
			for (Field field : Field.values())
				filmRow[field.ordinal() + 1] = film.getField(field);
			rowList.add(filmRow);
		}
		databaseHelper.saveData(rowList);	
	}

}
