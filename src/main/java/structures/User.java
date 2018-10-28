package structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
	public String ID;
	public String name;
	public List<String> savedFilmsIDs;
	public Boolean firstTime;
	public Map<Field, String> currentOptions;
	public Field currentField;

	public User(String name, String fileID, List<String> savedFilmsIDs, String fieldName, String fieldKey) {
		this.name = name;
		this.ID = fileID;
		firstTime = savedFilmsIDs == null;
		this.savedFilmsIDs = firstTime ? new ArrayList<String>() : savedFilmsIDs;
		
		currentOptions = new HashMap<Field, String>();
		for (Field field : Field.values())
			currentOptions.put(field, null);
		
		currentField = fieldName == null ? null : Field.valueOf(fieldName);
		if (fieldName != null)
			currentOptions.put(currentField, fieldKey);
	}	
	

	public void addFilm(Film film) {
		savedFilmsIDs.add(film.ID);
	}
	
	public void changeCurrentOption(Field field, String key) {
		currentOptions.put(currentField, null);
		currentField = field;
		currentOptions.put(currentField, key);		
	}
	
	public void clearCurrentField() {
		currentOptions.put(currentField, null);
		currentField = null;
	}
	
	public String getCurrentKey() {
		if (currentField == null || currentOptions.get(currentField) == null)
			return null;
		return currentOptions.get(currentField);
	}

}