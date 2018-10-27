package storage;

import java.util.List;
import structures.Film;

public interface FilmHandler {
	public List<Film> getFilmList() throws Exception;

	public void saveFilms(List<Film> filmList) throws Exception;
}
