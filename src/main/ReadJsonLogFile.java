package main;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadJsonLogFile {

	private static JSONArray jsonArray = new JSONArray();
	private static JSONParser jsonParser;
	private static boolean fileRead = false;

	/**
	 * Читает файл с записями Syslog-журнала в формате JSON. Этот метод должен
	 * быть вызван до использования других методов класса, так как они
	 * полагаются на него
	 * 
	 * @param path
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void readJsonLogFile(String path) throws IOException, ParseException {
		FileReader fr = new FileReader(path);
		jsonParser = new JSONParser();
		jsonArray = (JSONArray) jsonParser.parse(fr);
		fileRead = true;
	}

	/**
	 * Возвращает список строк из поля msg Syslog-сообщений из файла,
	 * прочитанного {@link ReadJsonLogFile#readJsonLogFile(String)}
	 * 
	 * @param file
	 * @param amount
	 * @return
	 */
	public static List<String> getMessages() {
		if (!fileRead) {
			System.err.println("Сначала используйте метод ReadJsonLogFile#readJsonLogFile, чтобы прочесть файл");
			return Collections.emptyList();
		}

		List<String> returnList = new ArrayList<String>(jsonArray.size());
		JSONObject jo;

		for (Object o : jsonArray) {
			jo = (JSONObject) o;
			returnList.add(jo.get("msg").toString());
		}

		return returnList;
	}

	/**
	 * Возвращает "сырой" JSONArray после чтения, или пустой JSONArray, если
	 * чтение не производилось. Таким образом, пустой JSONArray также может
	 * вернуться если прочитан пустой файл
	 * 
	 * @return
	 */
	public static JSONArray getJsonArray() {
		return jsonArray;
	}
}
