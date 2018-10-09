package logic;

import java.util.ArrayList;

public class User {
	public String name;
	public ArrayList<Film> savedFilms;
	public Boolean firstTime;

	public User(String name, ArrayList<Film> savedFilms) {
		this.name = name;
		firstTime = savedFilms == null;
		this.savedFilms = firstTime ? new ArrayList<Film>() : savedFilms;
	}

	public void addFilm(Film film) {
		savedFilms.add(film);
	}

}