package bot;

import storage.DuelQuestionGenerator;
import storage.IFilmDatabase;
import storage.QuestionDatabase;
import storage.RandomQuestionGenerator;
import storage.UserDatabase;
import structures.User;

public class BotFactory implements IBotFactory{
	
	private IFilmDatabase filmDatabase;
	private QuestionDatabase database;
	private int duelQuestionCount;
	
	public BotFactory(IFilmDatabase filmDatabase, QuestionDatabase database, int duelQuestionCount) {
		this.filmDatabase = filmDatabase;
		this.database = database;
		this.duelQuestionCount = duelQuestionCount;
	}

	@Override
	public IBot getInstance(Long id, String username, UserDatabase userDatabase) {
		User user = new User(username, id);
		return new Bot(user, new IState[] {
				new DialogState(user, filmDatabase),
				new GameState(user, new RandomQuestionGenerator(database)),
				new ResultTableState(userDatabase, user),
				new DuelState(user, userDatabase, new Duel(new DuelQuestionGenerator(database, duelQuestionCount)))});
	}

}