package storage;

import java.util.List;
import java.util.Map;

import structures.Field;
import structures.Film;

public interface IFilmHandler {

	public List<Film> getFilmsByOptions(Map<Field, List<String>> options);

	public String[] getAvaliableFieldValues(Field field);

	public void addFilm(Film film) throws Exception;

	public Integer getFilmsCount();

}
