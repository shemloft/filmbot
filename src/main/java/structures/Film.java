package structures;

import java.util.Map;

public class Film {
	public String title;
	public String ID;
	private Map<Field, String> filmData;

	public Film(String ID, String title, Map<Field, String> filmData) {
		this.title = title;
		this.filmData = filmData;
		this.ID = ID;
	}

	public String getField(Field field) {
		return filmData.get(field);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if (obj == null || obj.getClass() != this.getClass())
			return false;

		Film other = (Film) obj;
		
		return ID.equals(other.ID);
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
		return "Название: " + title + ", год: " + getField(Field.YEAR) + ", страна: " + getField(Field.COUNTRY);
	}
}
