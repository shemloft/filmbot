package bot;


public class Dialog 
{
	/* тут будет информация о конкретном диалоге для удобства ведения нескольких диалогов
	 * 
	 * метод связи с внешним миром, возвращения ответа
	 * 
	 * метод обработки введенных данных, проверка соответсвия формату
	 * 
	 * метод получения информации
	 * 
	 * */
	
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
	
	
	public String startDialog(String name)
	{//типа должна быть база, если имя в ней, то продолжать
		//прикол в том, что в базе помимо имени должны храниться списки фильмов, которые уже порекомендованы (совместно? по годам и по странам)
		if (name.equals(sName))
			return String.format("Давно не виделись, %s.", name);
		sName = name;
		return String.format("Добро пожаловать, %s.\n%s", name, HELP_TEXT);
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
			return getNextCountry();
		
		default:	
			return "Неизвестная команда, загляни, пожалуйста, в справку";
		}
	}
 	
 	private String getNextYear(String input) 
 	{
 		try {
			int year = Integer.parseInt(input.substring(3).trim());
		} catch (NumberFormatException e) {
		     return "Ну как так, год должен быть числом";
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
			return "Дружок, сначала выбери опцию, а потом проси фильм";
		if (sCurrentOpt == "year")
			return "";
		if (sCurrentOpt == "country")
			return "";
		return ""; // ну вот тут как то из базы извлекается след. фильм	
		// и выдать сообщение если фильмы кончились
 		
 	}

}