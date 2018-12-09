package structures;

import java.util.List;
import java.util.Map;

public class Film {
	public String title;
	public int ID;
	private Map<Field, List<String>> filmData;

	public Film(int ID, String title, Map<Field, List<String>> filmData) {
		this.title = title;
		this.filmData = filmData;
		this.ID = ID;
	}

	public List<String> getField(Field field) {
		return filmData.get(field);
	}

//	@Override
//	public boolean equals(Object obj) {
//		if (obj == this)
//			return true;
//
//		if (obj == null || obj.getClass() != this.getClass())
//			return false;
//
//		Film other = (Film) obj;
//
//		return ID == other.ID;
//	}
//
//	@Override
//	public int hashCode() {
//		int result = title.hashCode();
//		for (Field field : Field.values())
//			result ^= getField(field).hashCode();
//		return result;
//	}
	
	public String toString() {
		return title;
	}
}
