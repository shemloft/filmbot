package bot;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import dialog.Phrases;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;

import structures.Hint;
import structures.Messages;
import structures.Question;
import structures.User;

public class GameStateTest {
	
	User user;
	TestQuestionGenrator questionGenerator;
	GameState state;
	
	@Before
	public void setUp() {
		user = new User("name");
		questionGenerator = new TestQuestionGenrator();
		addQuestions();
		state = new GameState(user, questionGenerator);
	}
	
	public void addQuestions() {
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
	}
	
	
	@Test
	public void testFirstQuestion() {
		Messages firstGameMessages = state.getAnswer("input");
		assertEquals(Phrases.GAME_HELP, firstGameMessages.getIndexMessage(0).getText());
		assertEquals("question1", firstGameMessages.getIndexMessage(1).getText());
		assertArrayEquals(new String[] {"a11", "a12", "a13", "a14"}, firstGameMessages.getIndexMessage(1).getPossibleAnswers());	
	}
	
	@Test
	public void testHelp() {
		state.getAnswer("input");
		Messages helpMessages =  state.getAnswer("/help");
		assertEquals(Phrases.GAME_HELP, helpMessages.getIndexMessage(0).getText());
		assertArrayEquals(new String[] {"a11", "a12", "a13", "a14"}, helpMessages.getIndexMessage(0).getPossibleAnswers());	
	}
	
	
	@Test
	public void testCorrectAnswer() {
		state.getAnswer("input");
		Messages correctAnswerMessages = state.getAnswer("a11");
		assertThat(correctAnswerMessages.getIndexMessage(0).getText(), containsString(Phrases.CORRECT_ANSWER));
		assertEquals("question2", correctAnswerMessages.getIndexMessage(1).getText());
		assertEquals(GameState.noHintPoints, user.getPoints());
		assertArrayEquals(new String[] {"a21", "a22", "a23", "a24"}, correctAnswerMessages.getIndexMessage(1).getPossibleAnswers());
	}
	
	
	@Test
	public void testCorrectAnswerOneHint() {
		state.getAnswer("input");
		Messages incorrectAnswerMessages = state.getAnswer("a12");		
		
		assertThat(incorrectAnswerMessages.getIndexMessage(0).getText(), containsString(Phrases.INCORRECT_ANSWER));
		assertEquals("h11hint11", incorrectAnswerMessages.getIndexMessage(1).getText());
		assertEquals(0, user.getPoints());
		assertArrayEquals(new String[] {"a11", "a13", "a14"}, incorrectAnswerMessages.getIndexMessage(1).getPossibleAnswers());	
		
		Messages correctAnswerMessages = state.getAnswer("a11");
		
		assertThat(correctAnswerMessages.getIndexMessage(0).getText(), containsString(Phrases.CORRECT_ANSWER));
		assertEquals("question2", correctAnswerMessages.getIndexMessage(1).getText());
		assertEquals(GameState.oneHintPoints, user.getPoints());
		assertArrayEquals(new String[] {"a21", "a22", "a23", "a24"}, correctAnswerMessages.getIndexMessage(1).getPossibleAnswers());
	}
	
	
	@Test
	public void testCorrectAnswerTwoHints() {
		state.getAnswer("input");
		state.getAnswer("a12");
		Messages incorrectAnswerMessages = state.getAnswer("a13");
		
		assertThat(incorrectAnswerMessages.getIndexMessage(0).getText(), containsString(Phrases.INCORRECT_ANSWER));
		assertEquals("h12hint12", incorrectAnswerMessages.getIndexMessage(1).getText());
		assertEquals(0, user.getPoints());
		assertArrayEquals(new String[] {"a11", "a14"}, incorrectAnswerMessages.getIndexMessage(1).getPossibleAnswers());	
		
		Messages correctAnswerMessages = state.getAnswer("a11");
		
		assertThat(correctAnswerMessages.getIndexMessage(0).getText(), containsString(Phrases.CORRECT_ANSWER));
		assertEquals("question2", correctAnswerMessages.getIndexMessage(1).getText());
		assertEquals(GameState.twoHintPoints, user.getPoints());
		assertArrayEquals(new String[] {"a21", "a22", "a23", "a24"}, correctAnswerMessages.getIndexMessage(1).getPossibleAnswers());		
	}
	
	@Test
	public void testIncorrectAnswerWithTwoHints() {
		state.getAnswer("input");
		state.getAnswer("a12");		
		state.getAnswer("a13");
		
		Messages incorrectAnswerMessages = state.getAnswer("a14");
		
		assertThat(incorrectAnswerMessages.getIndexMessage(0).getText(), containsString(Phrases.INCORRECT_ANSWER));
		assertEquals("question2", incorrectAnswerMessages.getIndexMessage(1).getText());
		assertEquals(0, user.getPoints());
		assertArrayEquals(new String[] {"a21", "a22", "a23", "a24"}, incorrectAnswerMessages.getIndexMessage(1).getPossibleAnswers());		
	}

	@Test
	public void testNoMoreQuestions() {
		state.getAnswer("input");
		state.getAnswer("a11");		
		state.getAnswer("a22");
		
		Messages noMoreQuestionsMessages = state.getAnswer("a33");
		
		assertThat(noMoreQuestionsMessages.getIndexMessage(0).getText(), containsString(Phrases.CORRECT_ANSWER));
		assertEquals(Phrases.NO_MORE_QUESTIONS, noMoreQuestionsMessages.getIndexMessage(1).getText());
		assertEquals(GameState.noHintPoints * 3, user.getPoints());
		assertArrayEquals(new String[0], noMoreQuestionsMessages.getIndexMessage(1).getPossibleAnswers());	
		
		Messages noMoreQuestionsAgainMessages = state.getAnswer("input");
		
		assertEquals(Phrases.NO_MORE_QUESTIONS, noMoreQuestionsAgainMessages.getIndexMessage(0).getText());
		assertEquals(GameState.noHintPoints * 3, user.getPoints());
		assertArrayEquals(new String[0], noMoreQuestionsAgainMessages.getIndexMessage(0).getPossibleAnswers());	
	}



}
