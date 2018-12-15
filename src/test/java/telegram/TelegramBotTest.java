package telegram;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
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
import bot.TestQuestionGenrator;
import storage.FilmDatabase;
import storage.TestFilmHandlerWithFields;
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
		TestQuestionGenrator questionGenerator = new TestQuestionGenrator();		
		bot = new TelegramBot(new UsersData(new BotFactory(database, questionGenerator)), null, null);	
	}
	
	public TestQuestionGenrator getQuestionGenerator() {
		TestQuestionGenrator questionGenerator = new TestQuestionGenrator();
		return questionGenerator;
	}
	
	@Test
	public void TestTwoUsersDifferentRequest() throws Exception {		
		bot.communicate(mockMessage("Получение фильмов", (long) 1, "name1"));
		bot.communicate(mockMessage("GENRE", (long) 1, "name1"));
		bot.communicate(mockMessage("комедия", (long) 1, "name1"));
		String ans1 = bot.communicate(mockMessage(
				"ПОЛУЧИТЬ ФИЛЬМ", (long) 1, "name1"))[0].getSendMessage().getText();
		
		bot.communicate(mockMessage("Получение фильмов", (long) 2, "name2"));
		bot.communicate(mockMessage("GENRE", (long) 2, "name2"));
		bot.communicate(mockMessage("драма", (long) 2, "name2"));
		String ans2 = bot.communicate(mockMessage(
				"ПОЛУЧИТЬ ФИЛЬМ", (long) 2, "name1"))[0].getSendMessage().getText();	
	
		assertEquals("Криминальное чтиво", ans1);
		assertEquals("Леон", ans2);		
	}
	
	@Test
	public void TestTwoUsersSameRequest() throws Exception {		
		bot.communicate(mockMessage("Получение фильмов", (long) 1, "name1"));
		bot.communicate(mockMessage("GENRE", (long) 1, "name1"));
		bot.communicate(mockMessage("комедия", (long) 1, "name1"));
		String ans1 = bot.communicate(mockMessage(
				"ПОЛУЧИТЬ ФИЛЬМ", (long) 1, "name1"))[0].getSendMessage().getText();
		
		bot.communicate(mockMessage("Получение фильмов", (long) 2, "name2"));
		bot.communicate(mockMessage("GENRE", (long) 2, "name2"));
		bot.communicate(mockMessage("комедия", (long) 2, "name2"));
		String ans2 = bot.communicate(mockMessage(
				"ПОЛУЧИТЬ ФИЛЬМ", (long) 2, "name1"))[0].getSendMessage().getText();	
	
		assertEquals("Криминальное чтиво", ans1);
		assertEquals("Криминальное чтиво", ans2);	
	}
	
 }

