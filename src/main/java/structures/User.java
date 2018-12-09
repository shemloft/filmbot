package structures;

import java.util.ArrayList;
import java.util.List;

public class User {

	private String name;
	public List<Integer> savedFilmsIDs;
	private boolean firstTime;

	public User(String name) {
		this.savedFilmsIDs = new ArrayList<Integer>();
		firstTime = true;
	}
	
	public boolean isFirstTime() {
		if (!firstTime)
			return firstTime;
		firstTime = false;
		return true;			
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