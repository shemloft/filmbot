package bot;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bot.ParserCSV;;

public class ParserTest extends TestCase {

	public void testExtractEmptyData() throws Exception {
//		try {
//			ParserCSV.extractData("");
//		} catch (FileNotFoundException e) {
//			Assert.assertEquals("Ошибочка с базой данных, перепроверьте её", e.getMessage());
//		}
	}

	public void testExtractWrongData() throws Exception {
//		try {
//			ParserCSV.extractData("Kek.csv");
//		} catch (FileNotFoundException e) {
//			Assert.assertEquals("Ошибочка с базой данных, перепроверьте её", e.getMessage());
//		}
	}

	public void testExtractRightData() throws Exception {
//		List<String[]> data = new ArrayList<String[]>();
//		try {
//			data = ParserCSV.extractData("Database.csv");
//		} catch (FileNotFoundException e) {
//			Assert.assertEquals("Ошибочка с базой данных, перепроверьте её", e.getMessage());
//		}
//		Assert.assertEquals(true, Arrays.asList(data.get(0)).contains("Побег из Шоушенка;США;1994"));
//		Assert.assertEquals(false, Arrays.asList(data.get(0)).contains("Фильм;Страна;Год"));
//		Assert.assertEquals(true, Arrays.asList(data.get(12)).contains("Крестный отец;США;1972"));
//		Assert.assertEquals(20, data.size());
	}
	
	public void testFilmsByYear() throws Exception{
//		Map<Integer, ArrayList<String>> filmsByYear = new HashMap<Integer, ArrayList<String>>();
//		filmsByYear = ParserCSV.getFilmsByYear("Database.csv");
//		ArrayList<String> filmList2006 = new ArrayList<String>();
//		filmList2006.add("Престиж");
//		Assert.assertEquals(filmList2006, filmsByYear.get(2006));
//		ArrayList<String> filmList1994 = new ArrayList<String>();
//		filmList1994.add("Побег из Шоушенка");
//		filmList1994.add("Форрест Гамп");
//		filmList1994.add("Леон");
//		filmList1994.add("Король Лев");
//		filmList1994.add("Криминальное чтиво");
//		Assert.assertEquals(filmList1994, filmsByYear.get(1994));
//		Assert.assertEquals(false, filmsByYear.containsKey(2019));
	}
	
	public void testFilmsByCountry() throws Exception{
//		Map<String, ArrayList<String>> filmsByCountry = new HashMap<String, ArrayList<String>>();
//		filmsByCountry = ParserCSV.getFilmsByCountry("Database.csv");
//		ArrayList<String> filmListUSSR = new ArrayList<String>();
//		filmListUSSR.add("Иван Васильевич меняет профессию");
//		filmListUSSR.add("Операция «Ы» и другие приключения Шурика");
//		Assert.assertEquals(filmListUSSR, filmsByCountry.get("СССР"));
//		Assert.assertEquals(false, filmsByCountry.containsKey("Россия"));
	}
}
