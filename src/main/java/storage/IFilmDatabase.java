package storage;

import java.util.List;

import structures.Field;
import structures.Film;
import structures.Options;
import structures.User;

public interface IFilmDatabase {
	public Film getFilm(List<Integer> showedFilms, Options options);
	
	public String[] getFieldValuesArray(Field field);
	
	public boolean requestExistInDatabase(Field field, String request);
}
