package structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import telegram.DialogState;

public class Options {
	
	private Map<Field, List<String>> optionsMap;
	
	public Options() {
		optionsMap = new HashMap<Field, List<String>>();	
	}
	
	public void reset() {
		optionsMap = new HashMap<Field, List<String>>();
	}
	
	public void add(Field currentField, String input) {
		if (optionsMap.get(currentField) == null)
			optionsMap.put(currentField, new ArrayList<String>());
		optionsMap.get(currentField).add(input);	
	}
	
	public List<String> getFieldValues(Field field) {
		return optionsMap.get(field);
	}
	
	public boolean isEmpty() {
		return optionsMap.isEmpty();
	}
	
	public void print() {
		String printString = "";
		for (Map.Entry<Field, List<String>> entry : optionsMap.entrySet()) {
			printString += entry.getKey().name() + ": " + String.join(", ", entry.getValue()) + " ";
		}
		System.out.println(printString);
	}

}