package bot;


import storage.UserDatabase;

public interface IBotFactory {
	public IBot getInstance(Long id, String username, UserDatabase userDatabase);
}
