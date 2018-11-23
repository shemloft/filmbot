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

}