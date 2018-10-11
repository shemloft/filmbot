package storage;

import java.util.ArrayList;
import java.util.List;

import structures.Film;

public class FilmParser {
	
	DatabaseHelper databaseHelper;
	private List<Film> filmList;
	
	
	public FilmParser(DatabaseHelper databaseHelper) throws Exception {
		this.databaseHelper = databaseHelper;	
		filmList = new ArrayList<Film>();
		extractFilmList();
	}
	
	private void extractFilmList() throws Exception {
		List<String[]> Database = databaseHelper.extractData();
		for (String[] row : Database) {
			String name = row[0];
			String country = row[1];
			String year = row[2];
			Film film = new structures.Film(name, year, country);
			filmList.add(film);
		}
	}
	
	public List<Film> getFilmList() {
		return filmList;
	}

}
