package storage;

import java.util.List;
import java.util.Map;

import structures.Field;
import structures.Film;
import structures.Options;

public interface IFilmHandler {

	public List<Film> getFilmsByOptions(Options options);
	// empty if none

	public String[] getAvaliableFieldValues(Field field);

}
