package structures;

public class Phrases {
	
	// General phrases
	public static final String RESULT_TABLE = "Рейтинг игроков";
	
	public static final String CHOOSE_OPTION = "Выберите опцию";
	
	public static final String DUEL = "Дуэль";
	
	public static final String DIALOG = "Получение фильмов";
	
	public static final String EXIT = "Выйти";
	
	public static final String[] CONTROL_TEXT = {"/help", "/start", EXIT};
	
	
	// Help phrases	
	public static final String HELP = "\nС помощью этого бота можно получить фильм по запросу,"
			+ "угадывать фильмы и получать баллы, или сразиться в дуэли с другим пользователем.\n"
			+ "/help справка\n";
	
	public static final String DIALOG_HELP = "\nЭтот бот кидает кино по вашим запросам.\n"
			+ "Выбирайте жанр, год выпуска или актеров и получайте фильм"
			+ "/help справка\n";	
	
	public static final String GAME_HELP = "\nЭтот бот задает вопросы по фильмам, отвечайте правильно и набирайте баллы.\n"
			+ "Угадано без подсказок: 7 баллов\n"
			+ "Угадано с одной подсказкой: 3 балла\n"
			+ "Угадано с двумя подсказками: 1 балл\n" 
			+ "/help справка\n";
	
	public static final String DUEL_HELP = "Добро пожаловать в дуэль!\n"
			+ "Вам и Вашему оппоненту предстоит соревноваться в угадывании фильмов на скорость.\n"
			+ "Победа в раунде присуждается тому, кто ответил первым.\n"
			+ "Для начала дуэли Ваш оппонент должен зайти в данную вкладку. Его имя должно появиться внизу, "
			+ "если его там нет нажмите кнопку обновить. Нажмите имя оппонента и начинайте игру!";
	

	// Dialog phrases
	public static final String UNKNOWN_COMMAND = "Неизвестная команда, загляни, пожалуйста, в справку";

	public static final String NEXT_WITHOUT_OPT = "Дружок, сначала выбери опцию, а потом проси фильм";

	public static final String NO_SUCH_FILM = "Нет фильмов с такими параметрами";

	public static final String NO_MORE_FILM = "Все фильмы с данными параметрами из базы были предоставлены";
	
	public static final String[] BASIC_ANSWERS = {"YEAR", "GENRE", "ACTOR", "NEXT"};
	
	public static final String[] MORE_OPTIONS_ANSWERS = {"ЕЩЕ ОПЦИЯ", "ПОЛУЧИТЬ ФИЛЬМ"};
	
	
	// Game phrases
	public static final String YEAR_HINT = "Подсказка. Дата релиза:\n";
	
	public static final String OVERVIEW_HINT = "Подсказка. Описание фильма:\n";	
	
	public static final String CORRECT_ANSWER = "Правильно! ";
	
	public static final String INCORRECT_ANSWER = "Неправильно! ";	
	
	public static final String NO_MORE_QUESTIONS = "У нас не осталось вопросов для тебя.";
	
	public static final String DEFAULT_QUESTION = "Из какого фильма данный кадр?";
	
	
	// Duel phrases
	public static final String REFRESH = "Обновить";	

	public static final String WIN = "Вы победили! ";
	
	public static final String FAIL = "Вы проиграли :с. ";
	
	public static final String DRAFT = "Ничья. ";
	
	public static final String GAME_OVER = "Игра закончена. ";
	
	public static final String WAITING_FOR_OPPONENT = "Ждём опонента";
	
	public static final String YOUR_OPPONENT = "Ваш опонент: ";
	
	public static final String OPPONENT_LEAVE = "Опонент вышел. ";
	
	public static final String YOU_FAST = "Вы ответили быстрее. ";
	
	public static final String OPPONENT_FAST = "Опонент ответил быстрее. ";
	
	public static final String YOU_CORRECT = "Вы ответили правильно. ";
	
	public static final String OPPONENT_CORRECT = "Опонент ответил правильно. ";
	
	public static final String BOTH_INCORRECT = "Вы оба ответили неправильно. ";
	
	// General phrases methods
	public static String userWelcome(boolean isFirstTime, String name) {
		return isFirstTime ? String.format("Добро пожаловать, %s.%s", name, HELP) 
				: String.format("Давно не виделись, %s.", name);
	}
	
	
	// Duel phrases methods	
	public static String earnedPointsText(int earnedPoints, int totalPoints) {
		return String.format("Заработанные очки: %s. Всего очков: %s", earnedPoints, totalPoints);		
	}
	
	public static String winOrLose(int yourPoints, int opponentPoints) {
		return GAME_OVER + (yourPoints >= opponentPoints ? (yourPoints == opponentPoints ? DRAFT : WIN) : FAIL) +
				comparePointsText(yourPoints, opponentPoints);					
	}
	
	public static String comparePointsText(int yourPoints, int opponentPoints) {
		return String.format("Ваши очки: %s. Очки опонента: %s", yourPoints, opponentPoints);	
	}
	
	public static String getDuelMessage(String message, int yourPoints, int totalPoints) {
		return message + earnedPointsText(yourPoints, totalPoints);
	}	

}
