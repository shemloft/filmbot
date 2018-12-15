package structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Options {
	
	private Map<Field, List<String>> optionsMap;
	private String year;
	
	public Options() {
		optionsMap = new HashMap<Field, List<String>>();	
	}
	
	public void reset() {
		optionsMap = new HashMap<Field, List<String>>();
	}
	
	public void add(Field currentField, String input) {
		if (currentField == Field.YEAR) {
			year = input;
			return;
		}		
		if (optionsMap.get(currentField) == null)
			optionsMap.put(currentField, new ArrayList<String>());		
		optionsMap.get(currentField).add(input);	
	}
	
	public void addValues(Field currentField, Iterable<String> input) {
		if (currentField == Field.YEAR) {
			year = input.iterator().next();
			return;
		}	
		if (optionsMap.get(currentField) == null)
			optionsMap.put(currentField, new ArrayList<String>());
		for (String value : input)
			optionsMap.get(currentField).add(value);	
	}
	
	public List<Field> optionsFields() {
		List<Field> fields = new ArrayList<Field>(optionsMap.keySet());
		if (year != null)
			fields.add(Field.YEAR);
		return fields;
	}
	
	public List<String> getFieldValues(Field field) {
		switch(field) {
		case YEAR:
			return Stream.of(year).collect(Collectors.toList());
		default:
			return optionsMap.get(field);
		}		
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
