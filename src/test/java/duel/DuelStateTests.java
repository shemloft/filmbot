package duel;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import game.Hint;
import game.Question;
import game.TestQuestionGenerator;
import storage.TestUserDatabase;
import structures.Messages;
import structures.Phrases;
import structures.User;

public class DuelStateTests {
	
	private DuelState duelState1;
	private DuelState duelState2;
	private User user1;
	private User user2;
	private TestUserDatabase userDatabase;
	
	
	@Before
	public void setUp() {
		TestQuestionGenerator generator = new TestQuestionGenerator();
		generator.addQuestion(new Question("q1", Arrays.asList("a1", "a2"), "a1", new ArrayList<Hint>(), null));
		generator.addQuestion(new Question("q2", Arrays.asList("a1", "a2"), "a2", new ArrayList<Hint>(), null));	
		user1 = new User("1", 0l);
		user2 = new User("2", 1l);
		userDatabase = new TestUserDatabase();
		userDatabase.addUser(user1);
		userDatabase.addUser(user2);	
		duelState1 = new DuelState(user1, userDatabase, new Duel(generator));
		duelState2 = new DuelState(user2, userDatabase, new Duel(generator));
	}

	@Test
	public void testUserAppearsInAnswers() {
		Messages messages = duelState1.start();		
		assertArrayEquals(new String[] { Phrases.REFRESH }, messages.getFirstMessage().getPossibleAnswers());
		
		messages = duelState2.start();
		assertArrayEquals(new String[] { "1", Phrases.REFRESH }, messages.getFirstMessage().getPossibleAnswers());
	}
	
	@Test
	public void testRequestSend() {
		duelState1.start();	
		duelState2.start();
		duelState2.getAnswer("1");		
		
		assertTrue(user1.hasRequest());
	}
	
	@Test
	public void testAcceptRequestUser2() {
		duelState1.start();	
		duelState2.start();
		duelState2.getAnswer("1");
		duelState1.getAnswer("Да");
		
		assertEquals(user1.getOpponent(), user2);
		assertEquals(user2.getOpponent(), user1);
	}
	
	@Test
	public void testDeclineRequestUser2() {
		duelState1.start();	
		duelState2.start();
		duelState2.getAnswer("1");
		duelState1.getAnswer("Нет");
		
		assertEquals(null, user1.getOpponent());
		assertEquals(null, user2.getOpponent());
	}
	
	@Test
	public void testAcceptRequestUser1() {
		duelState1.start();	
		duelState2.start();
		duelState1.getAnswer(Phrases.REFRESH);
		duelState1.getAnswer("2");
		duelState2.getAnswer("Да");
		
		assertEquals(user1.getOpponent(), user2);
		assertEquals(user2.getOpponent(), user1);
	}
	
	@Test
	public void testDeclineRequestUser1() {
		duelState1.start();	
		duelState2.start();
		duelState1.getAnswer(Phrases.REFRESH);
		duelState1.getAnswer("2");
		duelState2.getAnswer("Нет");
		
		assertEquals(null, user1.getOpponent());
		assertEquals(null, user2.getOpponent());
	}
	
	@Test
	public void testAnswer() {
		duelState1.start();	
		duelState2.start();
		duelState2.getAnswer("1");
		duelState1.getAnswer("Да");
		duelState1.getAnswer("a1");
		duelState2.getAnswer("a1");
		
		assertEquals(user1.getDuelPoints(), 1);
		assertEquals(user2.getDuelPoints(), 0);
	}
	
	@Test
	public void testExit() {
		duelState1.start();	
		duelState2.start();
		duelState2.getAnswer("1");
		duelState1.getAnswer("Да");
		
		duelState1.processExit();
		
		assertFalse(user1.inDuel);
		assertFalse(user2.inDuel);
//		assertEquals(user2.getDuelPoints(), 0);
	}
		


}
