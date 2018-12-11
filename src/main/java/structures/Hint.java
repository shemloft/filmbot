package structures;

public class Hint {
	private String header;
	private String text;
	
	public Hint(String header, String text) {
		this.header = header;
		this.text = text;
	}
	
	public String getStringValue() {
		return header + text;
	}

}
