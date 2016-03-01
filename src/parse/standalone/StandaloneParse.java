package parse.standalone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.ReadJsonLogFile;
import old.ParseMessagesComposite;
import old.ParseMessagesComposite.Entry;
import util.ParseMessage;

import org.json.simple.parser.ParseException;

public class StandaloneParse {

	private int totalMessages;
	private int parsedMessagesCount;
	private int unparsedMessagesCount;
	private Set<String> unparsedMessages = new LinkedHashSet<String>();
	private List<Entry> parsedMessagesEntries;

	public List<Entry> parseMessages(List<String> messages, List<String> templates) {
		parsedMessagesEntries = new ArrayList<ParseMessagesComposite.Entry>();
		Map<String, String> map;
		Entry entry;
		boolean parsed;

		/*
		 * Запоминаем количество всех сообщений, разобранных и неразобранных
		 */
		totalMessages = messages.size();
		parsedMessagesCount = 0;
		unparsedMessagesCount = 0;

		for (String message : messages) {
			parsed = false;
			for (String template : templates) {
				map = ParseMessage.parseMessageAgainstTemplate(message, template);
				if (!map.isEmpty()) {
					parsed = true;
					entry = new Entry(message, template, map);
					parsedMessagesCount++;
					parsedMessagesEntries.add(entry);
					break;
				}
			}
			if (!parsed) {
				unparsedMessagesCount++;
				unparsedMessages.add(message);
			}
		}

		System.out.println("Total messages processed: " + totalMessages + "\nParsed messages: " + parsedMessagesCount + "\nUnparsed Messages: " + unparsedMessagesCount);

		return parsedMessagesEntries;
	}

	public Set<String> getUnparsedMessages() {
		return unparsedMessages;
	}

	public static void main(String[] args) {
		String messagesFileName="/home/sansey/Магистратура/Курс 2/ВКР/my_all_messages2.json";
		String templatesFileName="/home/sansey/Магистратура/Курс 2/ВКР/templates2.txt";
		String unparsedMessagesFileName="/home/sansey/Магистратура/Курс 2/ВКР/unparsed_messages.txt";
		List<String> messages;
		List<String> templates;
		Set<String> unparsedMessages;
		
		
		try {
			ReadJsonLogFile.readJsonLogFile(messagesFileName);
			messages = ReadJsonLogFile.getMessages();
			templates = Files.readAllLines(Paths.get(templatesFileName));
			
			StandaloneParse sp = new StandaloneParse();
			sp.parseMessages(messages, templates);
			unparsedMessages = sp.getUnparsedMessages();
			
			Files.write(Paths.get(unparsedMessagesFileName), unparsedMessages, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
