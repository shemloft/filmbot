package storage;

import java.util.List;

import structures.Film;

import utils.FilmUtils;

public class DatabaseFilmHandler implements FilmHandler {

	private Database database;
	private String[] header = { "ID", "Фильм", "Страна", "Год" };

	public DatabaseFilmHandler(Database database) {
		this.database = database;
	}

	public List<Film> getFilmList() throws Exception {	
		List<String[]> extractedList = database.extractData();
		extractedList.remove(0);
		return FilmUtils.stringListToFilmList(extractedList);
	}
	
	public void saveFilms(List<Film> filmList) throws Exception {
		List<String[]> rowList = FilmUtils.filmListToStringList(filmList);
		rowList.add(0, header);
		database.saveData(rowList);
	}

}
