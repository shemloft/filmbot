package bot;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import structures.Hint;
import structures.Question;
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
		duel.processGameState("a1", user2);
		
		assertEquals(1, user1.getDuelPoints());
		assertEquals(0, user2.getDuelPoints());
	}

}
