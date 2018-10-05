package bot;

import java.util.ArrayList;

public class Film {
	private String title;
	private String year;
	private String country;

	public Film(String sTitle, String sYear, String sCountry) {
		title = sTitle;
		year = sYear;
		country = sCountry;
	}

	public static ArrayList<String> getPossibleFields() {
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("country");
		fields.add("year");
		fields.add("title");
		return fields;
	}

	public String getField(String fieldName) {
		if (fieldName.equals("country"))
			return getCountry();
		if (fieldName.equals("year"))
			return getYear();
		if (fieldName.equals("title"))
			return getTitle();
		return null;

	}

	public String getYear() {
		return year;
	}

	public String getCountry() {
		return country;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if (obj == null || obj.getClass() != this.getClass())
			return false;

		Film other = (Film) obj;
		Boolean titleEquals = title == other.title || (title != null && title.equals(other.getTitle()));
		Boolean yearEquals = year == other.year || (year != null && year.equals(other.getYear()));
		Boolean countryEquals = country == other.country || (country != null && country.equals(other.getCountry()));

		return titleEquals && yearEquals && countryEquals;
	}

	@Override
	public int hashCode() {
		int result = 0;
		result ^= title.hashCode();
		result ^= year.hashCode();
		result ^= country.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "Название: " + title + ", год: " + year + ", страна: " + country;
	}
}
