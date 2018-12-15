package bot;

import storage.IFilmDatabase;
import storage.IQuestionGenerator;
import storage.UserDatabase;
import structures.User;

public class BotFactory implements IBotFactory{
	
	private IFilmDatabase filmDatabase;
	private IQuestionGenerator gameGenerator;
	private IQuestionGenerator duelGenerator;
	
	public BotFactory(IFilmDatabase filmDatabase, IQuestionGenerator gameGenerator, IQuestionGenerator duelGenerator) {
		this.filmDatabase = filmDatabase;
		this.gameGenerator = gameGenerator;
		this.duelGenerator = duelGenerator;
	}

	@Override
	public IBot getInstance(Long id, String username, UserDatabase userDatabase) {
		User user = new User(username, id);
		return new Bot(user, new IState[] {
				new DialogState(user, filmDatabase),
				new GameState(user, gameGenerator),
				new ResultTableState(userDatabase, user),
				new DuelState(user, userDatabase, duelGenerator)});
	}

}
