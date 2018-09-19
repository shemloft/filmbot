package bot;


public class Dialog 
{
	/* тут будет информаци€ о конкретном диалоге дл€ удобства ведени€ нескольких диалогов
	 * 
	 * метод св€зи с внешним миром, возвращени€ ответа
	 * 
	 * метод обработки введенных данных, проверка соответсви€ формату
	 * 
	 * метод получени€ информации
	 * 
	 * */
	
	static String HELLO_TEXT = "Ќазовите себ€, пожалуйста";
	
	static String HELP_TEXT = "Ётот бот кидает кино по вашим запросам.\n"
			+ "‘ормат ввода:\n"
			+ "/y год\n"
			+ "/c страна\n"
			+ "\n"
			+ "ћожет быть выбрана только одна опци€\n"
			+ "/next дл€ следующего фильма\n"			
			+ "¬ызов справки: /help\n"
			+ " орректный выход из бота с сохранением: /exit\n";
	
	private int iYear = 0;
	private String sCountry = null;
	private String sName = null;
	
	private String sCurrentOpt = null;
	
	
	public String startDialog(String name)
	{
		if (name.equals(sName))
			return String.format("ƒавно не виделись, %s.", name);
		sName = name;
		return String.format("ƒобро пожаловать, %s.\n%s", name, HELP_TEXT);
	}
	
	
	
 	public String processInput(String input)
	{
		/*можно разбить на подметоды, это пока база*/
		
		if (input.equals("/help"))
			return HELP_TEXT;
		
		if (input.equals("/next")) 		
			return tryGetNextFilm();			
		
		if (input.length() < 3)
			return "—лишком коротка€ команда, не могу пон€ть :с";
		
		String option = input.substring(0, 3);
		switch (option) {
		case "/y ":
			return getNextYear(input);
			
		case "/c ":
			return getNextCountry();
		
		default:	
			return "Ќеизвестна€ команда, загл€ни, пожалуйста, в справку";
		}
	}
 	
 	private String getNextYear(String input) 
 	{
 		try {
			int year = Integer.parseInt(input.substring(3).trim());
		} catch (NumberFormatException e) {
		     return "Ќу как так, год должен быть числом";
		}
		sCurrentOpt = "year";
		return "";
		// фильм по году или сообщение что по этому году фильмов в базе нет
 	}
 	
 	private String getNextCountry()
 	{
 		sCurrentOpt = "country";
		return "";
		// фильм по стране или сообщение что по стране фильмов в базе нет
 	}
 	
 	private String tryGetNextFilm(){
 		if (sCurrentOpt == null)
			return "ƒружок, сначала выбери опцию, а потом проси фильм";
		if (sCurrentOpt == "year")
			return "";
		if (sCurrentOpt == "country")
			return "";
		return ""; // ну вот тут как то из базы извлекаетс€ след. фильм	
		// и выдать сообщение если фильмы кончились
 		
 	}

}
