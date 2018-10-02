package bot;

import java.util.ArrayList;
import java.util.Map.Entry;

public class Dialog 
{

	static String HELLO_TEXT = "Назовите себя, пожалуйста";
	
	static String HELP_TEXT = "Этот бот кидает кино по вашим запросам.\n"
			+ "Формат ввода:\n"
			+ "/y год\n"
			+ "/c страна\n"
			+ "\n"
			+ "Может быть выбрана только одна опция\n"
			+ "/next для следующего фильма\n"			
			+ "Вызов справки: /help\n"
			+ "Корректный выход из бота с сохранением: /exit\n";
	
	private int iYear = 0;
	private String sCountry = null;
	private String sName = null;
	
	private String sCurrentOpt = null;
	
	private bot.ChatBot chatBot;
	private bot.User user;
	
	public Dialog (bot.ChatBot chatBot)
	{
		this.chatBot = chatBot;	
	}
	
	
	public String startDialog(String name)
	{//типа должна быть база, если имя в ней, то продолжать
		//прикол в том, что в базе помимо имени должны храниться списки фильмов, которые уже порекомендованы (совместно? по годам и по странам)
		if (name.equals(sName))
			return String.format("Давно не виделись, %s.", name);
		sName = name;
		user = new bot.User(sName);
//		return String.format("Добро пожаловать, %s.\n%s", name, HELP_TEXT);
		return "meh";
	}
	
	
	
 	public String processInput(String input)
	{
		/*можно разбить на подметоды, это пока база*/
		
		if (input.equals("/help"))
			return HELP_TEXT;
		
		if (input.equals("/next")) 		
			return tryGetNextFilm();			
		
		if (input.length() < 3)
			return "Слишком короткая команда, не могу понять :с";
		
		String option = input.substring(0, 3);
		switch (option) {
		case "/y ":
			return getNextYear(input);
			
		case "/c ":
			return getNextCountry(input);
		
		default:	
			return "Неизвестная команда, загляни, пожалуйста, в справку";
		}
	}
 	
 	private String getNextYear(String input) 
 	{
 		int year;
 		try {
			year = Integer.parseInt(input.substring(3).trim());
		} catch (NumberFormatException e) {
		     return "Ну как так, год должен быть числом";
		}
		sCurrentOpt = "year";
		if (chatBot.filmsByYear.containsKey(year))
		{
			iYear = year;
			ArrayList<Film> films = chatBot.filmsByYear.get(year);
			ArrayList<Film> savedFilms = user.getSavedFilmsYear(year);			
			for (Film film : films)
			{
				if (savedFilms == null || !savedFilms.contains(film)) 
				{
					user.addFilmByYear(film);
					return film.getTitle();			
				}
			}
			return "Все фильмы этого года, имеющиеся в базе, были предоставлены";
		}					
		return "В базе нет фильмов, снятых в этот год :с";
		
 	}
 	
 	private String getNextCountry(String input)
 	{
 		sCurrentOpt = "country";
 		String country = input.substring(3).trim();
		if (chatBot.filmsByCountry.containsKey(country))
		{
			sCountry = country;
			ArrayList<Film> films = chatBot.filmsByCountry.get(country);
			ArrayList<Film> savedFilms = user.getSavedFilmsCountry(country);			
			for (Film film : films)
			{
				if (savedFilms == null || !savedFilms.contains(film)) 
				{
					user.addFilmByCountry(film);
					return film.getTitle();			
				}
			}
			return "Все фильмы этой страны, имеющиеся в базе, были предоставлены";
		}					
		return "В базе нет фильмов, снятых в этой стране :с";		
 	}
 	
 	private String tryGetNextFilm(){
 		if (sCurrentOpt == null)
			return "Дружок, сначала выбери опцию, а потом проси фильм";
		if (sCurrentOpt == "year") 
		{
			int year = iYear;
			ArrayList<Film> films = chatBot.filmsByYear.get(year);
			ArrayList<Film> savedFilms = user.getSavedFilmsYear(year);			
			for (Film film : films)
			{				
				if (savedFilms == null || !savedFilms.contains(film)) 
				{
					user.addFilmByYear(film);
					return film.getTitle();
				}
			}
			return "Все фильмы этого года, имеющиеся в базе, были предоставлены";
		}			
		if (sCurrentOpt == "country")
		{
			String country = sCountry;
			ArrayList<Film> films = chatBot.filmsByCountry.get(country);
			ArrayList<Film> savedFilms = user.getSavedFilmsCountry(country);			
			for (Film film : films)
			{
				if (savedFilms == null || !savedFilms.contains(film)) 
				{
					user.addFilmByCountry(film);
					return film.getTitle();			
				}
			}
			return "Все фильмы этой страны, имеющиеся в базе, были предоставлены";
		}
		return "";
 		
 	}

}