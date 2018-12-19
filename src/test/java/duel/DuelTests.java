package duel;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import game.Hint;
import game.Question;
import game.TestQuestionGenerator;
import structures.Messages;
import structures.Phrases;
import structures.User;

public class DuelTests {
	
	private Duel duel;
	private User user1;
	private User user2;
	
	@Before
	public void setUp() {
		TestQuestionGenerator generator = new TestQuestionGenerator();
		generator.addQuestion(new Question("q1", Arrays.asList("a1", "a2"), "a1", new ArrayList<Hint>(), null));
		generator.addQuestion(new Question("q2", Arrays.asList("a1", "a2"), "a2", new ArrayList<Hint>(), null));
		duel = new Duel(generator);
		user1 = new User("1", 0l);
		user2 = new User("2", 1l);
		user1.setOpponent(user2);
		user2.setOpponent(user1);
		user1.inDuel = true;
		user2.inDuel = true;
	}

	@Test
	public void testCorrectAnswerBoth() {
		duel.getFirstQuestion(user1);
		duel.processGameState("a1", user1);
		Messages messages = duel.processGameState("a1", user2);
		
		assertEquals(1, user1.getDuelPoints());
		assertEquals(0, user2.getDuelPoints());
		
		assertThat(messages.getIndexMessage(0).getText(), containsString(Phrases.OPPONENT_FAST));
		assertThat(messages.getIndexMessage(1).getText(), containsString(Phrases.YOU_FAST));
	}
	
	@Test
	public void testFirstIncorrectSecondCorrect() {
		duel.getFirstQuestion(user1);
		duel.processGameState("a2", user1);
		Messages messages = duel.processGameState("a1", user2);
		
		assertEquals(0, user1.getDuelPoints());
		assertEquals(1, user2.getDuelPoints());
		
		assertThat(messages.getIndexMessage(0).getText(), containsString(Phrases.YOU_CORRECT));
		assertThat(messages.getIndexMessage(1).getText(), containsString(Phrases.OPPONENT_CORRECT));
	}
	
	@Test
	public void testBothIncorrect() {
		duel.getFirstQuestion(user1);
		duel.processGameState("a2", user1);
		Messages messages = duel.processGameState("a2", user2);
		
		assertEquals(0, user1.getDuelPoints());
		assertEquals(0, user2.getDuelPoints());
		
		assertThat(messages.getIndexMessage(0).getText(), containsString(Phrases.BOTH_INCORRECT));
		assertThat(messages.getIndexMessage(1).getText(), containsString(Phrases.BOTH_INCORRECT));
	}
	
	@Test
	public void testOpponentLeave() {
		duel.getFirstQuestion(user1);
		user2.inDuel = false;
		Messages answer = duel.processGameState("a1", user1);

		assertThat(answer.getFirstMessage().getText(), containsString(Phrases.OPPONENT_LEAVE));
	}
	
	@Test
	public void testGameOver() {
		duel.getFirstQuestion(user1);
		duel.processGameState("a1", user1);
		duel.processGameState("a1", user2);
		duel.processGameState("a2", user1);
		Messages answer = duel.processGameState("a2", user2);
		
		assertThat(answer.getLastMessage().getText(), containsString(Phrases.GAME_OVER));
	}


}
