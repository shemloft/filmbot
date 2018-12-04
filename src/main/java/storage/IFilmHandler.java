package storage;

import java.util.List;
import java.util.Map;

import structures.Field;
import structures.Film;

public interface IFilmHandler {

	public List<Film> getFilmsByOptions(Map<Field, List<String>> options);
	// empty if none

	public String[] getAvaliableFieldValues(Field field);

}
