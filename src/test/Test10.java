package test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import util.JsonReadWriteUtils;
import util.ParseMessage;

/**
 * Тут я буду делать разбор сообщений с добавлением к сообщению информации о
 * шаблонах. Идея в том, чтобы просто добавить ещё один ключ ("template")в
 * JSON-объект записи.
 * 
 * @author aaovchinnikov
 *
 */
public class Test10 {

	public static void main(String[] args) {
		try {
			JsonReadWriteUtils.readJsonLogFile("D:\\my_all_messages_20160118\\messages_very_short.json");
			List<String> templates=Files.readAllLines(Paths.get("D:\\my_all_messages_20160118\\templates_4.txt"));
			parseMessagesAndAddTemplates(JsonReadWriteUtils.getJsonArray(), templates);
			JsonReadWriteUtils.saveJsonArrayToFile(JsonReadWriteUtils.getJsonArray(), "D:\\my_all_messages_20160118\\messages_very_short.json");
			
			System.out.println("Total messages processed: " + totalMessagesCount + "\nParsed messages: " + parsedMessagesCount + "\nUnparsed Messages: " + unparsedMessagesCount);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static int parsedMessagesCount;
	private static int unparsedMessagesCount;
	private static int totalMessagesCount;
	
	/**
	 * Метод производит разбор переданного списка сообщений (как JSON-объектов)
	 * с помощью переданных шаблонов. Если сообщение было разборано, то в его
	 * JSON-объект добавляется ключ "template" в со значением равным
	 * разобравшему шаблону. Если сообщение не разобрано, то ключ не добавляется.
	 * 
	 * @param messages
	 * @param templates
	 */
	private static void parseMessagesAndAddTemplates(JSONArray messages, List<String> templates) {
		boolean parsed;
		String msg;
		Map<String, String> placeholderValueMap;
		JSONObject message;
		
		parsedMessagesCount = 0;
		unparsedMessagesCount = 0;
		totalMessagesCount = messages.size();
		
		for (Object o : messages) {
			message = (JSONObject) o;
			parsed = false;
			msg = ((String)message.get("msg"));
			for (String template : templates) {
				placeholderValueMap = ParseMessage.parseMessageAgainstTemplate(msg, template);
				if (!placeholderValueMap.isEmpty()) {
					parsed = true;
					message.put("template", template);
					parsedMessagesCount++;
					break;
				}
			}
			if (!parsed) {
				unparsedMessagesCount++;
			}
		}
	}

}
