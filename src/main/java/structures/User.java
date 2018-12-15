package structures;

import java.util.ArrayList;
import java.util.List;

public class User {

	private String name;
	public List<Integer> savedFilmsIDs;
	private boolean firstTime;
	private int points;

	public User(String name) {
		this.savedFilmsIDs = new ArrayList<Integer>();
		firstTime = true;
		this.name = name;
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
		savedFilmsIDs.add(film.getID());
	}
	
	public void addPoints(int earnedPoints) {
		points += earnedPoints;
	}
	
	public int getPoints() {
		return  points;
	}
	
}