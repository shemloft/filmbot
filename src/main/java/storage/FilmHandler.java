package storage;

import java.util.List;
import structures.Film;

public interface FilmHandler {
	public void addFilmToDatabase(String title, String country, String year, String genre) throws Exception;
	
	public List<Film> getFilmList() throws Exception;

	public void saveFilms(List<Film> filmList) throws Exception;
}
