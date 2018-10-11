package structures;

import java.util.ArrayList;
import java.util.List;

public class User {
	public String name;
	public List<Film> savedFilms;
	public Boolean firstTime;

	public User(String name, List<Film> savedFilms) {
		this.name = name;
		firstTime = savedFilms == null;
		this.savedFilms = firstTime ? new ArrayList<Film>() : savedFilms;
	}

	public void addFilm(Film film) {
		savedFilms.add(film);
	}

}