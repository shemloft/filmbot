package dialog;

public class Phrases {
	public static final String HELLO = "Назовите себя, пожалуйста";

	public static final String HELP = "\nЭтот бот кидает кино по вашим запросам.\n" + "Формат ввода:\n" + "/y год\n"
			+ "/c страна\n" + "/g жанр\n" + "\n" + "Может быть выбрана только одна опция\n" + "/next следующий фильм\n"
			+ "/help справка\n" + "/add добавление фильма в базу\n" + "/adding справка по добавлению фильма в базу\n"
			+ "/genres доступные жанры\n" + "/countries доступные страны-создатели\n\n"
			+ "И помните, если фильм был уже кинут по какому-либо из параметров, "
			+ "он не может быть кинут как по тому же самому или другому параметру ещё раз.";

	public static final String UNKNOWN_COMMAND = "Неизвестная команда, загляни, пожалуйста, в справку";

	public static final String SHORT_COMMAND = "Слишком короткая команда, не могу понять :с";

	public static final String YEAR_NAN = "Ну как так, год должен быть числом";

	public static final String NEXT_WITHOUT_OPT = "Дружок, сначала выбери опцию, а потом проси фильм";

	public static final String DATABASE_ERROR = "Ошибочка с базой данных, перепроверьте её";

	public static final String SAVE_USER_ERROR = "Ошибочка при сохранении пользователя";

	public static final String ADDING_FILM = "Фильм был успешно добавлен в базу";

	public static final String ADDING_FILM_ERROR = "Этот фильм уже есть в базе";

	public static final String NO_SUCH_FILM = "Нет фильмов с такими параметрами";

	public static final String NO_MORE_FILM = "Все фильмы с данными параметрами из базы были предоставлены";

	public static final String ADDING_PROCESS_ERROR = "Неправильный ввод данных, изучите справку по ключу /adding";

	public static final String ADDING_PROCESS = "После /add введите 4 параметра:\n/t - название фильма, "
			+ "\n/c - страна-создатель фильма (через запятую с пробелом, если их несколько)"
			+ "\n/y - год создания фильма" + "\n/g - жанр фильма (через запятую с пробелом, если их несколько)";

	public static final String AVAILAIBLE_GENRES = "Доступные жанры фильмов:\n";

	public static final String AVAILAIBLE_COUNTRIES = "Страны-создатели фильмов, имеющиеся в базе:\n";

	public static final String AVAILAIBLE_YEARS = "Доступные года создания фильмов:\n";

}
