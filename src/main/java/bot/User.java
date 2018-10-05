package bot;

import java.util.ArrayList;

public class User {
	public String Name;
	public ArrayList<Film> savedFilms;

	public User(String name) {
		Name = name;
		savedFilms = new ArrayList<Film>();
	}

	public void addFilm(Film film) {
		savedFilms.add(film);
	}

}