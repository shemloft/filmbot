package storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import dialog.Phrases;
import structures.Field;
import structures.Film;
import utils.FilmUtils;

public class FileFilmHandler implements IFilmHandler {

	private IFilmDatabaseFileHandler fileHandler;
	private List<Film> filmList;
	private Map<Field, Map<String, List<Film>>> filmMapsByField;
	private Map<Field, String[]> optionsValuesMap;

	public FileFilmHandler(IFilmDatabaseFileHandler fileHandler) throws Exception {
		this.fileHandler = fileHandler;
		initializeFields();
	}

	private void initializeFields() throws Exception {
		filmList = getFilmList();
		filmMapsByField = FilmUtils.getFilmMapsByField(filmList);
		optionsValuesMap = FilmUtils.getOptionValuesMap(filmMapsByField);
	}

	public Integer getFilmsCount() {
		return filmList.size();
	}

	private List<Film> getFilmList() throws Exception {
		List<String[]> extractedList = fileHandler.extractData();
		extractedList.remove(0);
		return FilmUtils.stringListToFilmList(extractedList);
	}

	public List<Film> getFilmsByOptions(Map<Field, List<String>> options) {
		List<Film> filmsByOptions = new ArrayList<Film>();
		outerloop: for (Film film : filmList) {
			for (Entry<Field, List<String>> entry : options.entrySet()) {
				Field field = entry.getKey();
				List<String> fieldOptions = entry.getValue();
				for (String option : fieldOptions) {
					if (!film.getField(field).contains(option))
						continue outerloop;
				}
			}
			filmsByOptions.add(film);
		}
		return filmsByOptions;
	}

	public String[] getAvaliableFieldValues(Field field) {
		return optionsValuesMap.get(field);
	}

	public void addFilm(Film film) throws Exception {
		if (filmList.contains(film))
			throw new Exception(Phrases.ADDING_FILM_ERROR);
		String[] record = FilmUtils.filmToStringArray(film);
		fileHandler.addData(record);
		initializeFields();
	}

}
