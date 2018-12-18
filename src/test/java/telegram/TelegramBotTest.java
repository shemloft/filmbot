package telegram;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import bot.BotFactory;
import dialog.FilmDatabase;
import dialog.TestFilmHandlerWithFields;
import game.Hint;
import game.Question;
import game.TestQuestionDatabase;
import structures.Field;
import structures.Film;
import structures.Options;

public class TelegramBotTest {	
	private TelegramBot bot; 
	
	private Message mockMessage(String text, Long chatId, String userFirstName) {
		Message message = mock(Message.class);
		User user = mock(User.class);
		when(user.getFirstName()).thenReturn(userFirstName);		
		when(message.getFrom()).thenReturn(user);
		when(message.getChatId()).thenReturn(chatId);
		when(message.getText()).thenReturn(text);
		
		return message;
	}
	
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
		TestQuestionDatabase questionDatabase = new TestQuestionDatabase();
		questionDatabase.addQuestion(new Question("q1", Arrays.asList("a1", "a2"), "a1", new ArrayList<Hint>(), null));
		questionDatabase.addQuestion(new Question("q2", Arrays.asList("a1", "a2"), "a2", new ArrayList<Hint>(), null));
		bot = new TelegramBot(
				new UsersData(new BotFactory(database, questionDatabase, 2)),
				null, null);	
	}

	
	@Test
	public void TestTwoUsersDifferentRequest() throws Exception {		
		bot.communicate(mockMessage("Получение фильмов", 1l, "name1"));
		bot.communicate(mockMessage("GENRE", 1l, "name1"));
		bot.communicate(mockMessage("комедия", 1l, "name1"));
		String ans1 = bot.communicate(mockMessage(
				"ПОЛУЧИТЬ ФИЛЬМ", 1l, "name1"))[0].getSendMessage().getText();
		
		bot.communicate(mockMessage("Получение фильмов", 2l, "name2"));
		bot.communicate(mockMessage("GENRE", 2l, "name2"));
		bot.communicate(mockMessage("драма", 2l, "name2"));
		String ans2 = bot.communicate(mockMessage(
				"ПОЛУЧИТЬ ФИЛЬМ", 2l, "name1"))[0].getSendMessage().getText();	
	
		assertEquals("Криминальное чтиво", ans1);
		assertEquals("Леон", ans2);		
	}
	
	@Test
	public void TestTwoUsersSameRequest() throws Exception {		
		bot.communicate(mockMessage("Получение фильмов", 1l, "name1"));
		bot.communicate(mockMessage("GENRE", 1l, "name1"));
		bot.communicate(mockMessage("комедия", 1l, "name1"));
		String ans1 = bot.communicate(mockMessage(
				"ПОЛУЧИТЬ ФИЛЬМ", 1l, "name1"))[0].getSendMessage().getText();
		
		bot.communicate(mockMessage("Получение фильмов", 2l, "name2"));
		bot.communicate(mockMessage("GENRE", 2l, "name2"));
		bot.communicate(mockMessage("комедия", 2l, "name2"));
		String ans2 = bot.communicate(mockMessage(
				"ПОЛУЧИТЬ ФИЛЬМ", 2l, "name1"))[0].getSendMessage().getText();	
	
		assertEquals("Криминальное чтиво", ans1);
		assertEquals("Криминальное чтиво", ans2);	
	}
	
	@Test
	public void TestThreeUsersDuel() throws Exception {		
		bot.communicate(mockMessage("Дуэль", 1l, "name1"));
		bot.communicate(mockMessage("Дуэль", 2l, "name2"));
		bot.communicate(mockMessage("name1", 2l, "name2"));
		bot.communicate(mockMessage("Да", 1l, "name1"));
		
		bot.communicate(mockMessage("Дуэль", 3l, "name3"));
		String text = bot.communicate(mockMessage("name1", 3l, "name3"))[0].getSendMessage().getText();
		
		assertThat(text, not(containsString("Присоединиться")));
		System.out.println(text);

	}
	
 }