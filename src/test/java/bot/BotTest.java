package bot;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import dialog.Phrases;
import structures.BotMessage;
import structures.Messages;
import structures.User;

public class BotTest {
	Bot bot;
	
	@Before
	public void setUp() throws Exception {
		User user = new User("name", 0l);
		IState state1 = new TestState(new Messages(new BotMessage(user.getID(), "answer1", new String[0])), "state1", StateType.DIALOG);
		IState state2 = new TestState(new Messages(new BotMessage(user.getID(), "answer2", new String[0])), "state2", StateType.ANSWER);
		bot = new Bot(user, new IState[] {state1, state2});	
	}
	
	@Test
	public void testFirstInput() {
		Messages answer = bot.getAnswer("lll");
		assertEquals(Phrases.CHOOSE_OPTION, answer.getFirstMessage().getText());		
	}
	
	@Test
	public void testChooseState() {
		bot.getAnswer("state1");
		Messages answer = bot.getAnswer("state1");
		assertEquals(answer.getFirstMessage().getText(), "answer1");		
	}
	
	@Test
	public void testChooseAnswerState() {
		Messages answer = bot.getAnswer("state2");
		assertEquals(answer.getFirstMessage().getText(), "answer2");
		
		answer = bot.getAnswer("lll");
		assertEquals(Phrases.CHOOSE_OPTION, answer.getFirstMessage().getText());
	}
	
	@Test
	public void testNoExitOption() {
		Messages answer = bot.getAnswer("lll");
		assertEquals(2, answer.getLastMessage().getPossibleAnswers().length);
		assertEquals("state1", answer.getLastMessage().getPossibleAnswers()[0]);
		assertEquals("state2", answer.getLastMessage().getPossibleAnswers()[1]);
	}
	
	@Test
	public void testAddExitOption() {
		Messages answer = bot.getAnswer("state1");
		assertEquals(1, answer.getLastMessage().getPossibleAnswers().length);
		assertEquals(Phrases.EXIT, answer.getLastMessage().getPossibleAnswers()[0]);
	}

}
