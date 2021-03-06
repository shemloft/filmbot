package dialog;

public class Phrases {

	public static final String HELP = "\nЭтот бот кидает кино по вашим запросам.\n"
			+ "/help справка\n";

	public static final String UNKNOWN_COMMAND = "Неизвестная команда, загляни, пожалуйста, в справку";

	public static final String NEXT_WITHOUT_OPT = "Дружок, сначала выбери опцию, а потом проси фильм";

	public static final String NO_SUCH_FILM = "Нет фильмов с такими параметрами";

	public static final String NO_MORE_FILM = "Все фильмы с данными параметрами из базы были предоставлены";
	
	public static final String[] BASIC_ANSWERS = {"YEAR", "GENRE", "ACTOR", "NEXT"};
	
	public static final String[] MORE_OPTIONS_ANSWERS = {"ЕЩЕ ОПЦИЯ", "ПОЛУЧИТЬ ФИЛЬМ"};
	
	public static final String GENRE_HINT = "Подсказка. Жанр:\n";
	
	public static final String YEAR_HINT = "Подсказка. Дата релиза:\n";
	
	public static final String OVERVIEW_HINT = "Подсказка. Описание фильма:\n";
	
	public static final String EXIT = "Выйти";
	
	public static final String CORRECT_ANSWER = "Правильно! ";
	
	public static final String INCORRECT_ANSWER = "Неправильно! ";
	
	public static final String GAME_HELP = "\nЭтот бот задает вопросы по фильмам, отвечайте правильно и набирайте баллы.\n"
			+ "Угадано без подсказок: 7 баллов\n"
			+ "Угадано с одной подсказкой: 3 балла\n"
			+ "Угадано с двумя подсказками: 1 балл\n" 
			+ "/help справка\n";
	
	public static final String NO_MORE_QUESTIONS = "У нас не осталось вопросов для тебя.";
	
	public static final String RESULT_TABLE = "Рейтинг игроков";
	
	public static final String DEFAULT_QUESTION = "Из какого фильма данный кадр?";
	
	public static String earnedPointsText(int earnedPoints, int totalPoints) {
		return String.format("Заработанные очки: %s. Всего очков: %s", earnedPoints, totalPoints);
		
	}
	
	public static final String CHOOSE_OPTION = "Выберите опцию";

}
