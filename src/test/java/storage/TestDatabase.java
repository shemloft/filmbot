//package storage;
//
//import java.util.List;
//
//import structures.Field;
//import structures.Film;
//import structures.User;
//
//public class TestDatabase implements IFilmDatabase{
//	
//	private Film film;
//	
//	public TestDatabase(Film film) {
//		this.film = film;
//	}
//	
//	public void setReturningFilm(Film film) {
//		this.film = film;
//	}
//
//	@Override
//	public Film getFilm(User user) {
//		return film;
//	}
//
//	@Override
//	public String[] getFieldValuesArray(Field field) {
//		List<String> fieldValues = film.getField(field);
//		String[] result = new String[fieldValues.size()];
//		return fieldValues.toArray(result);
//	}
//
//	@Override
//	public boolean requestExistInDatabase(Field field, String request) {
//		return film.getField(field).contains(request);
//	}
//
//}
