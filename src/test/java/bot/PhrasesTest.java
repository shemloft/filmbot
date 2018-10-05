package bot;

import junit.framework.Assert;
import junit.framework.TestCase;

import bot.Phrases;

public class PhrasesTest extends TestCase {
	public void testPhrasesConstructor() {
		Phrases phrases = new Phrases();
		Assert.assertEquals("В базе нет фильмов, снятых в этот год :с", phrases.yearPhrases.get("no_films"));
		Assert.assertEquals(phrases.countryPhrases.size(), 2);
		Assert.assertEquals(phrases.yearPhrases.size(), 2);
		Assert.assertTrue(phrases.yearPhrases.containsKey("all_films"));
	}

	public void testGetDictByKey() {
		Phrases phrases = new Phrases();
		Assert.assertEquals(2, phrases.getDictByKey("year").size());
		Assert.assertEquals(2, phrases.getDictByKey("country").size());
		Assert.assertEquals(null, phrases.getDictByKey("no key"));
	}

}
