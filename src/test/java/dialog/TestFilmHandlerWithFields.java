package dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import structures.Field;
import structures.Film;
import structures.Options;

public class TestFilmHandlerWithFields implements IFilmHandler{
	
	private List<Film> films;
	private Map<Field, String[]> fields;
	private Map<Field, Map<String, List<Film>>> filmsByOptionsByField;
	
	public TestFilmHandlerWithFields(List<Film> films, Map<Field, String[]> fields) {
		this.films = films;
		filmsByOptionsByField = new HashMap<Field, Map<String, List<Film>>>(); 
		processFilms();
		this.fields = fields;
	}
	
	private void processFilms() {
		for (Film film : films) {
			System.out.println(films);
			Options options = film.getOptions();
			for (Field field : options.optionsFields()) {
				List<String> filmFieldValues = options.getFieldValues(field);
				if (filmsByOptionsByField.get(field) == null)
					filmsByOptionsByField.put(field, new HashMap<String, List<Film>>());
				for (String fieldValue : filmFieldValues) {
					if (filmsByOptionsByField.get(field).get(fieldValue) == null)
						filmsByOptionsByField.get(field).put(fieldValue, new ArrayList<Film>());
					filmsByOptionsByField.get(field).get(fieldValue).add(film);
				}
				
			}		
		}
	}

	@Override
	public List<Film> getFilmsByOptions(Options options) {
		List<Film> currentFilms = new ArrayList<Film>();
		
		for (Film film : films) {
			boolean allOptionsFound = true;
			for(Field field : options.optionsFields()) {
				List<String> fieldValues = options.getFieldValues(field);				
				for (String fieldValue : fieldValues) {
					if (!film.getOptions().getFieldValues(field).contains(fieldValue))
						allOptionsFound = false;
				}
			}
			if (allOptionsFound)
				currentFilms.add(film);
		}
		
		return currentFilms;
	}

	@Override
	public String[] getAvaliableFieldValues(Field field) {
		return fields.get(field);
	}

}
