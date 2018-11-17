package dialog;

public class Phrases {
	public static final String HELLO = "Назовите себя, пожалуйста";

	public static final String HELP = 
			"\nЭтот бот кидает кино по вашим запросам.\n" +	
	        "Формат ввода:\n" +
			"/y год\n" + 
	        "/c страна\n" + 
			"/g жанр\n" + 
	        "\n" + 
			"Может быть выбрана только одна опция\n" + 
	        "/next следующий фильм\n" + 
			"/help справка\n" + 
	        "/genres доступные жанры\n" + 
			"/countries доступные страны-создатели\n\n"	+ 
	        "И помните, если фильм был уже кинут по какому-либо из параметров, "
	        + "он не может быть кинут как по тому же самому или другому параметру ещё раз.";

	public static final String UNKNOWN_COMMAND = "Неизвестная команда, загляни, пожалуйста, в справку";

	public static final String SHORT_COMMAND = "Слишком короткая команда, не могу понять :с";

	public static final String YEAR_NAN = "Ну как так, год должен быть числом";

	public static final String NEXT_WITHOUT_OPT = "Дружок, сначала выбери опцию, а потом проси фильм";

	public static final String DATABASE_ERROR = "Ошибочка с базой данных, перепроверьте её";

	public static final String SAVE_USER_ERROR = "Ошибочка при сохранении пользователя";

	public static final String ADDING_FILM = "Фильм был успешно добавлен в базу";
	
	public static final String NO_SUCH_FILM = "Нет фильмов с такими параметрами";

//	public static List<String> GENRES;
//
//	public static List<String> YEARS;
//
//	public static List<String> COUNTRIES;

	public static final String[] GENRES = { "аниме", "биография", "боевик", "вестерн", "военный", "детектив", "драма",
			"комедия", "криминал", "мелодрама", "музыка", "мультфильм", "мюзикл", "приключения", "семейный", "спорт",
			"триллер", "фантастика", "фэнтэзи" };

	public static final String[] YEARS = { "1921", "1931", "1939", "1947", "1953", "1956", "1957", "1959", "1965",
			"1966", "1968", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1979", "1980", "1981", "1983",
			"1984", "1985", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1997", "1998", "1999",
			"2000", "2001", "2002", "2003", "2004", "2005", "2006", "2008", "2009", "2010", "2011", "2012", "2013",
			"2014", "2016", "2017" };

	public static final String[] COUNTRIES = { "Великобритания", "Германия", "Гонконг", "Индия", "Испания", "Италия",
			"Канада", "Новая Зеландия", "ОАЭ", "Польша", "СССР", "США", "Франция", "ФРГ", "Чехословакия", "Швейцария",
			"Япония" };

	public static final String AVAILAIBLE_GENRES = "Доступные жанры фильмов:\n" + String.join("\n", GENRES);

	public static final String AVAILAIBLE_COUNTRIES = "Страны-создатели фильмов, имеющиеся в базе:\n"
			+ String.join("\n", COUNTRIES);

	public static final String AVAILAIBLE_YEARS = "Доступные года создания фильмов:\n" + String.join("\n", YEARS);

	

}
