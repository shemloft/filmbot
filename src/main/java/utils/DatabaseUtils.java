package utils;

import java.util.List;

import dialog.Phrases;
import storage.CSVHandler;
import storage.DatabaseFilmHandler;
import structures.Film;
import structures.User;

public class DatabaseUtils {

	public static List<Film> tryGetUserFilmList(String name) {
		List<Film> userFilms = null;
		CSVHandler helperCSV = new CSVHandler(name);
		DatabaseFilmHandler parser = new DatabaseFilmHandler(helperCSV);
		try {
			userFilms = parser.getFilmList();
		} catch (Exception e) {
			return null;
		}
		return userFilms;
	}

	public static void saveUser(User user) throws Exception {
		try {
			CSVHandler helperCSV = new CSVHandler(user.name);
			DatabaseFilmHandler parser = new DatabaseFilmHandler(helperCSV);
			parser.saveFilms(user.savedFilms);
		} catch (Exception e) {
			throw new Exception(Phrases.SAVE_USER_ERROR);
		}
	}

}
