package telegram;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import storage.FilmDatabase;
import storage.TestFilmHandlerWithFields;
import structures.Field;
import structures.Film;

public class TelegramBotTest {	
	
	Message mockMessage(String text, Long chatId, String userFirstName) {
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
		
		Map<Field, List<String>> filmData = new HashMap<Field, List<String>>();		
		for (Field field : Field.values())
			filmData.put(field, new ArrayList<String>());		
		filmData.get(Field.GENRE).add("триллер");
		filmData.get(Field.GENRE).add("драма");
		filmData.get(Field.GENRE).add("криминал");
		filmData.get(Field.YEAR).add("1994");
		filmList.add(new Film(5, "Леон", filmData));
		
		filmData = new HashMap<Field, List<String>>();		
		for (Field field : Field.values())
			filmData.put(field, new ArrayList<String>());		
		filmData.get(Field.GENRE).add("триллер");
		filmData.get(Field.GENRE).add("комедия");
		filmData.get(Field.GENRE).add("криминал");
		filmData.get(Field.YEAR).add("1994");		
		filmList.add(new Film(13, "Криминальное чтиво", filmData));	
		
		String[] genres = { "триллер", "комедия", "криминал", "драма"};
		String[] years = { "1994"};
		Map<Field, String[]> fields = new HashMap<Field, String[]>();
		fields.put(Field.GENRE, genres);
		fields.put(Field.YEAR, years);
		
		FilmDatabase database = new  FilmDatabase(new TestFilmHandlerWithFields(filmList, fields));	
	
		return database;
	}
	
	@Test
	public void TwoUsersTestDifferentRequest() throws Exception {
		FilmDatabase database = getDatabase();
		TelegramBot bot = new TelegramBot(new UsersData(database), null, null);		
			
		bot.communicate(mockMessage("GENRE", (long) 1, "name1"));
		bot.communicate(mockMessage("комедия", (long) 1, "name1"));
		String ans1 = bot.communicate(mockMessage(
				"ПОЛУЧИТЬ ФИЛЬМ", (long) 1, "name1")).getText();
		
		bot.communicate(mockMessage("GENRE", (long) 2, "name2"));
		bot.communicate(mockMessage("драма", (long) 2, "name2"));
		String ans2 = bot.communicate(mockMessage(
				"ПОЛУЧИТЬ ФИЛЬМ", (long) 2, "name1")).getText();	
	
		assertEquals("Криминальное чтиво", ans1);
		assertEquals("Леон", ans2);		
	}
	
	@Test
	public void TwoUsersTestSameRequest() throws Exception {
		FilmDatabase database = getDatabase();
		TelegramBot bot = new TelegramBot(new UsersData(database), null, null);		
			
		bot.communicate(mockMessage("GENRE", (long) 1, "name1"));
		bot.communicate(mockMessage("комедия", (long) 1, "name1"));
		String ans1 = bot.communicate(mockMessage(
				"ПОЛУЧИТЬ ФИЛЬМ", (long) 1, "name1")).getText();
		
		bot.communicate(mockMessage("GENRE", (long) 2, "name2"));
		bot.communicate(mockMessage("комедия", (long) 2, "name2"));
		String ans2 = bot.communicate(mockMessage(
				"ПОЛУЧИТЬ ФИЛЬМ", (long) 2, "name1")).getText();	
	
		assertEquals("Криминальное чтиво", ans1);
		assertEquals("Криминальное чтиво", ans2);	
	}
	

 }

