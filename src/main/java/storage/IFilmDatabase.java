package storage;

import structures.Field;
import structures.Film;
import structures.User;

public interface IFilmDatabase {
	public Film getFilm(User user);
	
	public String[] getFieldValuesArray(Field field);
	
	public boolean requestExistInDatabase(Field field, String request);
}
