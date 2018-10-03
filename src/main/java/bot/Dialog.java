package bot;

import java.security.KeyException;
import java.util.ArrayList;
import java.util.Map;

public class Dialog 
{
	
	private String m_sYear = null;
	private String m_sCountry = null;
	private String sName = null;
	
	private String sCurrentOpt = null;
	
	private bot.ChatBot chatBot;
	private bot.User user;
	
	public Dialog (bot.ChatBot chatBot)
	{
		this.chatBot = chatBot;	
	}
	
	
	public String startDialog(String name)
	{
		if (name.equals(sName))
			return String.format("Давно не виделись, %s.", name);
		sName = name;
		user = new bot.User(sName);
		return String.format("Добро пожаловать, %s.\n%s", name, Phrases.HELP);		
	}
	
 	public String processInput(String input) throws KeyException
	{		
		if (input.equals("/help"))
			return Phrases.HELP;
		
		if (input.equals("/next")) 		
			return tryGetNextFilm();			
		
		if (input.length() < 3)
			return Phrases.SHORT_COMMAND;
		
		String option = input.substring(0, 3);
		String data = input.substring(3).trim();
		switch (option) {
		case "/y ":
			return getNextYear(data);
			
		case "/c ":
			return getNextCountry(data);
		
		default:	
			return Phrases.UNKNOWN_COMMAND;
		}
	}
 	
 	private String getNextFilm(
 			String key, 
 			String option, 
 			Map<String, String> phrases) throws KeyException
 	{
 		sCurrentOpt = option;
 		Map<String, ArrayList<Film>> filmsDict = 
 				chatBot.filmsStructure.getFilmsByKey(option);
 		
 		if (!filmsDict.containsKey(key))
 			return phrases.get("no_films");
 		
 		ArrayList<Film> films = filmsDict.get(key);
 		ArrayList<Film> savedFilms = user.savedFilms;
 		for (Film film : films)
 		{
 			if (savedFilms.contains(film))
 				continue;
 			user.addFilm(film);
 			return film.getTitle(); 			
 		}
 		return phrases.get("all_films");	
 	}
 	
 	private String getNextYear(String sYear) throws KeyException 
 	{
 		try	{
 			int iYear = Integer.parseInt(sYear);
 		} catch (NumberFormatException e) {
		     return Phrases.YEAR_NAN;
		}
 		
 		String option = "year";
 		m_sYear = chatBot.filmsStructure.getFilmsByKey(option).containsKey(sYear) ? 
 				sYear : null;
 		return getNextFilm(sYear, option, chatBot.phrases.yearPhrases);
		
 	}
 	
 	private String getNextCountry(String sCountry) throws KeyException
 	{
 		String option = "country";
 		m_sCountry = chatBot.filmsStructure.getFilmsByKey(option).containsKey(sCountry) ? 
 				sCountry : null;
 		return getNextFilm(sCountry, option, chatBot.phrases.countryPhrases);	
 	}	
 	
 	private String tryGetNextFilm() throws KeyException{
 		if (sCurrentOpt == null)
			return Phrases.NEXT_WITHOUT_OPT;
 		if (!Film.getPossibleFields().contains(sCurrentOpt))
 			return "";
 		String key = null;
 		if (sCurrentOpt == "year")
 			key = m_sYear;
 		if (sCurrentOpt == "country")
 			key = m_sCountry;

 		ArrayList<Film> films = 
 				chatBot.filmsStructure.getFilmsByKey(sCurrentOpt).get(key);
 		ArrayList<Film> savedFilms = user.savedFilms;

 		for (Film film : films)
 		{ 			
 			if (savedFilms.contains(film))
 				continue;
 			
			user.addFilm(film);
			return film.getTitle();			
 		}
 		return chatBot.phrases.getDictByKey(sCurrentOpt).get("all_films");
 		
 	}

}