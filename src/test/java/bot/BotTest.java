package bot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;
import org.junit.Before;
import org.junit.Test;

import dialog.Phrases;
import storage.FilmDatabase;
import storage.TestFilmHandlerWithFields;
import structures.Field;
import structures.Film;
import structures.Hint;
import structures.Messages;
import structures.Options;
import structures.Question;
import structures.User;

public class BotTest {
	Bot bot;
	DialogState dialogState;
	GameState gameState;
	
	private FilmDatabase getDatabase() throws Exception {
		List<Film> filmList = new ArrayList<Film>();
		
		Options options = new Options();
		options.addValues(Field.GENRE, Stream.of("триллер", "драма", "криминал").collect(Collectors.toList()));
		options.addValues(Field.YEAR, Stream.of("1994").collect(Collectors.toList()));
		filmList.add(new Film(5, "Леон", options, null, null));
	
		
		options = new Options();
		options.addValues(Field.GENRE, Stream.of("триллер", "комедия", "криминал").collect(Collectors.toList()));	
		options.addValues(Field.YEAR, Stream.of("1994").collect(Collectors.toList()));
		filmList.add(new Film(13, "Криминальное чтиво", options, null, null));	
		
		String[] genres = { "триллер", "комедия", "криминал", "драма"};
		String[] years = { "1994"};
		Map<Field, String[]> fields = new HashMap<Field, String[]>();
		fields.put(Field.GENRE, genres);
		fields.put(Field.YEAR, years);
		
		FilmDatabase database = new  FilmDatabase(new TestFilmHandlerWithFields(filmList, fields));	
	
		return database;
	}
	
	@Before
	public void setUp() throws Exception {
		FilmDatabase database = getDatabase();
		TestQuestionGenrator questionGenerator = new TestQuestionGenrator();
		User user = new User("name");
		dialogState = new DialogState(user, database);
		gameState = new GameState(user, questionGenerator); 
		bot = new Bot(user, new IState[] {dialogState, gameState});	
	}
	
	public TestQuestionGenrator getQuestionGenerator() {
		TestQuestionGenrator questionGenerator = new TestQuestionGenrator();
		questionGenerator.addQuestion(new Question("question1",
				Stream.of("a11", "a12", "a13", "a14").collect(Collectors.toList()),
				"a11", 
				Stream.of(new Hint("h11", "hint11"), new Hint("h12", "hint12")).collect(Collectors.toList()), 
				null));
		questionGenerator.addQuestion(new Question("question2",
				Stream.of("a21", "a22", "a23", "a24").collect(Collectors.toList()),
				"a22", 
				Stream.of(new Hint("h21", "hint21"), new Hint("h22", "hint22")).collect(Collectors.toList()), 
				null));
		questionGenerator.addQuestion(new Question("question3",
				Stream.of("a31", "a32", "a33", "a34").collect(Collectors.toList()),
				"a33", 
				Stream.of(new Hint("h31", "hint31"), new Hint("h32", "hint32")).collect(Collectors.toList()), 
				null));
		return questionGenerator;
	}
	
	@Test
	public void testFirstInput() {
		Messages answer = bot.getAnswer("lll");
		assertEquals("Выберите опцию", answer.getFirstMessage().getText());		
	}
	
	@Test
	public void testGetFilm() {
		bot.getAnswer("lll");
		Messages answer = bot.getAnswer(dialogState.getName());
		assertThat(answer.getFirstMessage().getText(), containsString(Phrases.HELP));		
	}

}
