package structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User {

	public String ID;
	public String name;
	public List<String> savedFilmsIDs;
	public boolean firstTime;
	public Map<Field, List<String>> currentOptions;

	// currentData вместо fieldName и fieldKey
	public User(String name, String fileID, List<String> savedFilmsIDs, Map<Field, List<String>> currentData) {
		this.name = name;
		this.ID = fileID;
		firstTime = savedFilmsIDs == null;
		this.savedFilmsIDs = firstTime ? new ArrayList<String>() : savedFilmsIDs;

		currentOptions = currentData;
//		for (Field field : Field.values())
//			currentOptions.put(field, new ArrayList<String>());
//		if (currentData != null) {
//			for (String fieldName : currentData.keySet())
//				for (Field field : currentOptions.keySet()) {
//					field = fieldName == null ? null : Field.valueOf(fieldName);
//					if (fieldName != null)
//						currentOptions.get(field).addAll(currentData.get(fieldName));
//				}
//		}
	}

	public void addFilm(Film film) {
		savedFilmsIDs.add(film.ID);
	}

	public void changeCurrentOptions(Map<Field, List<String>> data) {
		currentOptions = data;
	}

	public void clearCurrentOptions() {
		currentOptions = null;
	}

//	public String getCurrentKey() {
//		if (currentField == null || currentOptions.get(currentField) == null)
//			return null;
//		return currentOptions.get(currentField);
//	}

}