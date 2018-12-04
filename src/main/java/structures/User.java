package structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import telegram.DialogState;

public class User {

	private String ID;
	private String name;
	public List<Integer> savedFilmsIDs;
	public Map<Field, List<String>> currentOptions;
	private Field currentField;
	private DialogState dialogState;
	
	public boolean requestComplete; 
	public boolean firstTime;

	public User(String chatID, String name) {
		this.ID = chatID;
		this.savedFilmsIDs = new ArrayList<Integer>();
		currentOptions = new HashMap<Field, List<String>>();
		dialogState = DialogState.BASIC;
		currentField = null;
		requestComplete = false;
		firstTime = true;
	}
	
	
	public String getName() {
		return name;
	}
	
	public void updateName(String newName) {
		name = newName;
	}

	public void addFilm(Film film) {
		savedFilmsIDs.add(film.ID);
	}
	
	public Field currentField() {
		return currentField;
	}
	
	public DialogState currentState() {
		return dialogState;
	}
	
	public void clearData() {
		currentOptions = null;
		dialogState = DialogState.BASIC;
		currentField = null;
		requestComplete = false;		
	}
	
	public void nowChosing(Field field) {
		currentField = field;
		dialogState = DialogState.CHOSING;
		if (currentOptions.get(field) == null)
			currentOptions.put(field, new ArrayList<String>());		
	}
	
	public void nowChosingMore() {		
		dialogState = DialogState.BASIC;
		currentField = null;
	}
	
	public void nowAdding(String input) {
		dialogState = DialogState.MORE_OPTIONS;
		currentOptions.get(currentField).add(input);
		currentField = null;
	}
	
	public void nowGettingFilm() {
		dialogState = DialogState.BASIC;
		currentField = null;
		requestComplete = true;
	}
}