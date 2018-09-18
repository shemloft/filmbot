package Bot;


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
	
	static String help_text = "Ётот бот кидает кино по вашим запросам.\n"
			+ "‘ормат ввода:\n"
			+ "/y год\n"
			+ "/c страна\n"
			+ "\n"
			+ "ћожет быть выбрана только одна опци€\n"
			+ "/next дл€ следующего фильма\n"			
			+ "¬ызов справки: /help\n";
	
	private int iYear = 0;
	private String sCountry = null;
	
	private String sCurrentOpt = null;
	
	
	public String process_input(String input)
	{
		/*можно разбить на подметоды, это пока база*/
		
		if (input.equals("/help"))
			return help_text;
		if (input.equals("/next")) 
		{
			if (sCurrentOpt == null)
				return "ƒружок, сначала выбери опцию, а потом проси фильм";
			if (sCurrentOpt == "year")
				return "";
			if (sCurrentOpt == "country")
				return "";
			return ""; // ну вот тут как то из базы извлекаетс€ след. фильм	
			// и выдать сообщение если фильмы кончились
		}
		String option = input.substring(0, 3);
		if (option.equals("/y ")) 
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
		if (option.equals("/c "))
		{
			sCurrentOpt = "country";
			return "";
			// фильм по стране или сообщение что по стране фильмов в базе нет
		}
		return "Ќеизвестна€ команда, загл€ни, пожалуйста, в справку";
	}

}
