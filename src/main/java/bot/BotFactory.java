package bot;

import dialog.DialogState;
import dialog.IFilmDatabase;
import duel.Duel;
import duel.DuelQuestionGenerator;
import duel.DuelState;
import game.GameState;
import game.IQuestionDatabase;
import game.RandomQuestionGenerator;
import storage.UserDatabase;
import structures.User;

public class BotFactory implements IBotFactory{
	
	private IFilmDatabase filmDatabase;
	private IQuestionDatabase database;
	private int duelQuestionCount;
	
	public BotFactory(IFilmDatabase filmDatabase, IQuestionDatabase database, int duelQuestionCount) {
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