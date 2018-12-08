package structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import telegram.DialogState;

public class User {

	private String name;
	public List<Integer> savedFilmsIDs;

	public User(String name) {
		this.savedFilmsIDs = new ArrayList<Integer>();
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
	
}