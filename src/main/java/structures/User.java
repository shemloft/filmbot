package structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
	
	/* Здесь нужно заменить currentOptions с Map<Field, String> на Map<Field, List<String>>,
	 * поменять все методы в соответсвии, убрать currentField (т.е. то какое поле текущее будет проверяться
	 * по тому, есть ли ключ в словаре !если ключа нет возвращается null а не бросается исключение!
	 * 
	 *  Также нужно исправить в конструкторе добавление  fieldName,  fieldKey, т.к. теперь их несколько
	 *  Соответсвующие изменения в UserUtils и UserDataHandler */
	
	
	public String ID;
	public String name;
	public List<String> savedFilmsIDs;
	public boolean firstTime;
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
	
	public void changeCurrentOptions(Field field, String key) {
		
		currentField = field;
		currentOptions.put(currentField, key);		
	}
	
	public void clearCurrentField() {
		currentOptions.remove(currentField);
		currentField = null;
	}
	
	public String getCurrentKey() {
		if (currentField == null || currentOptions.get(currentField) == null)
			return null;
		return currentOptions.get(currentField);
	}

}