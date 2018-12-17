package structures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Messages implements Iterable<BotMessage>{
	
	private List<BotMessage> messageList;
	
	public Messages() {
		messageList = new ArrayList<BotMessage>();		
	}
	
	public Messages(BotMessage message) {
		this();
		addMessage(message);		
	}
	
	public void addMessage(BotMessage message) {
		messageList.add(message);
	}
	
	public void addMessageInBegining(BotMessage message) {
		messageList.add(0, message);
	}
	
	public void addMessagesInBegining(Messages messages) {
		messageList = Stream.concat(messages.getMessages().stream(), messageList.stream())
                .collect(Collectors.toList());
	}
	
	public int count() {
		return messageList.size();
	}
	
	public BotMessage getLastMessage() {
		return messageList.get(count() - 1);
	}
	
	public BotMessage getFirstMessage() {
		return messageList.get(0);
	}
	
	public BotMessage getIndexMessage(int index) {
		return messageList.get(index);
	}

	@Override
	public Iterator<BotMessage> iterator() {
		return messageList.iterator();
	}
	
	public List<BotMessage> getMessages() {
		return messageList;
	}
	
	public void addMessages(Messages messages) {
		messageList.addAll(messages.getMessages());
	}

}
