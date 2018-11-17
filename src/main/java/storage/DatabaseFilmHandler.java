package storage;

import java.util.List;

import structures.Field;
import structures.Film;

import utils.FilmUtils;
//по сути, это как раз и есть класс для базы, тк он используется у нас только в main для извлечения списка фильмов и в тесте
public class DatabaseFilmHandler implements FilmHandler {

	private IDatabase database;
	public List<Film> filmList;
	public Integer filmCount;
	private String[] header = { "ID", "Фильм", "Страна", "Год", "Жанр" };

	public DatabaseFilmHandler(IDatabase database) throws Exception {
		this.database = database;
		filmList = getFilmList();
		filmCount = filmList.size();
	}
	
	public void addFilmToDatabase(String title, String country, String year, String genre) throws Exception {
		Film film = FilmUtils.getFilm(filmCount.toString(), title, country, year, genre);
		if (filmList.contains(film))
			throw new Exception("Этот фильм уже есть в базе");
		String[] record = new String[] { film.ID, film.title, film.getField(Field.COUNTRY), film.getField(Field.YEAR),
				film.getField(Field.GENRE) };
		database.addData(record);
		filmList = getFilmList();
	}

	public List<Film> getFilmList() throws Exception {
		List<String[]> extractedList = database.extractData();
		extractedList.remove(0);
		return FilmUtils.stringListToFilmList(extractedList);
	}

// этот метод нигде не используется вроде, вообще, держу в курсе
	public void saveFilms(List<Film> filmList) throws Exception {
		List<String[]> rowList = FilmUtils.filmListToStringList(filmList);
		rowList.add(0, header);
		database.saveData(rowList);
	}

}
