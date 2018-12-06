package dialog;

public class Phrases {

	public static final String HELP = "\nЭтот бот кидает кино по вашим запросам.\n"
			+ "/help справка\n" + "/genres доступные жанры\n" 
			+ "И помните, если фильм был уже кинут по какому-либо из параметров, "
			+ "он не может быть кинут как по тому же самому или другому параметру ещё раз.";

	public static final String UNKNOWN_COMMAND = "Неизвестная команда, загляни, пожалуйста, в справку";

	public static final String NEXT_WITHOUT_OPT = "Дружок, сначала выбери опцию, а потом проси фильм";

	public static final String NO_SUCH_FILM = "Нет фильмов с такими параметрами";

	public static final String NO_MORE_FILM = "Все фильмы с данными параметрами из базы были предоставлены";

}
