package bot;

import java.util.ArrayList;

public class Film {
	private String m_sTitle;
	private String m_sYear;
	private String m_sCountry;

	public Film(String sTitle, String sYear, String sCountry) {
		m_sTitle = sTitle;
		m_sYear = sYear;
		m_sCountry = sCountry;
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
		return m_sYear;
	}

	public String getCountry() {
		return m_sCountry;
	}

	public String getTitle() {
		return m_sTitle;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if (obj == null || obj.getClass() != this.getClass())
			return false;

		Film other = (Film) obj;
		Boolean titleEquals = m_sTitle == other.m_sTitle || (m_sTitle != null && m_sTitle.equals(other.getTitle()));
		Boolean yearEquals = m_sYear == other.m_sYear || (m_sYear != null && m_sYear.equals(other.getYear()));
		Boolean countryEquals = m_sCountry == other.m_sCountry
				|| (m_sCountry != null && m_sCountry.equals(other.getCountry()));

		return titleEquals && yearEquals && countryEquals;
	}

	@Override
	public int hashCode() {
		int result = 0;
		result ^= m_sTitle.hashCode();
		result ^= m_sYear.hashCode();
		result ^= m_sCountry.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "Название: " + m_sTitle + ", год: " + m_sYear + ", страна: " + m_sCountry;
	}
}
