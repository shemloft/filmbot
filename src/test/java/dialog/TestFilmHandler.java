package dialog;

import java.util.List;
import java.util.Map;

import structures.Field;
import structures.Film;
import structures.Options;

public class TestFilmHandler implements IFilmHandler{
	
	private List<Film> films;
	private Map<Field, String[]> fields;
	
	public TestFilmHandler(List<Film> films, Map<Field, String[]> fields) {
		this.films = films;
		this.fields = fields;
	}

	@Override
	public String[] getAvaliableFieldValues(Field field) {
		return fields.get(field);
	}

	@Override
	public List<Film> getFilmsByOptions(Options options) {
		return films;
	}

}
