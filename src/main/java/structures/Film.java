package structures;

import java.util.List;

public class Film {
	private String title;
	private int ID;
	private String overview;
	private String image;
	private Options options;
	

	public Film(int ID, String title, Options options, String overview, String image) {
		this.title = title;
		this.options = options;
		this.ID = ID;
		this.overview = overview;
		this.image = image;
	}

	public Options getOptions() {
		return options;
	}
	
	public List<String> getField(Field field) {
		return options.getFieldValues(field);
	}
	
	public int getID() {
		return ID;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getOverview() {
		return overview;
	}
	
	public String getImage() {
		return image;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if (obj == null || obj.getClass() != this.getClass())
			return false;

		Film other = (Film) obj;

		return ID == other.ID;
	}

	@Override
	public int hashCode() {
		return ID;
	}
	
	public String toString() {
		return title;
	}
}
