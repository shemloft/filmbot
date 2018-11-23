package structures;

import java.util.List;
import java.util.Map;

public class Film {
	public String title;
	public String ID;
	private Map<Field, List<String>> filmData;

	public Film(String ID, String title, Map<Field, List<String>> filmData) {
		this.title = title;
		this.filmData = filmData;
		this.ID = ID;
	}

	public List<String> getField(Field field) {
		return filmData.get(field);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if (obj == null || obj.getClass() != this.getClass())
			return false;

		Film other = (Film) obj;

		return ID.equals(other.ID) || title.equals(other.title) && checkFilmData(filmData, other.filmData);
	}

	private boolean checkFilmData(Map<Field, List<String>> selfFilmData, Map<Field, List<String>> otherFilmData) {
		for (Field field : Field.values())
			if (selfFilmData.get(field).size() != otherFilmData.get(field).size())
				return false;
		for (Field field : Field.values())
			for (String filmField : otherFilmData.get(field))
				if (!selfFilmData.get(field).contains(filmField))
					return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = title.hashCode();
		for (Field field : Field.values())
			result ^= getField(field).hashCode();
		return result;
	}

	@Override
	public String toString() {
		return String.format("Название: %s, год: %s, страна: %s, жанр: %s", title, String.join(", ", getField(Field.YEAR)),
				String.join(", ", getField(Field.COUNTRY)), String.join(", ", getField(Field.GENRE)));
	}
}
