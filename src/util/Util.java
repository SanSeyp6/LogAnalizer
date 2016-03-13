package util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import composites.SimilarMessagesComposite.TreeNode;

/**
 * Тут хранятся вспомогательные статические методы
 * 
 * @author aaovchinnikov
 *
 */
public class Util {
	/**
	 * Находит все именованные placeholder-ы и заносит их в дерево
	 * placeholder-ов
	 * 
	 * @param templates
	 * @param placeholdersRoot
	 */
	public static void computePlaceholders(List<String> templates, TreeNode placeholdersRoot) {
		List<String> placeholders;
		for (String template : templates) {
			placeholders = ParseMessage.getPlaceholders(template);
			for (String placeholder : placeholders) {
				try {
					Integer.parseInt(placeholder);
				} catch (NumberFormatException e) {
					placeholdersRoot.addChild(new TreeNode(placeholder));
				}
			}
		}
	}

	/**
	 * Преобразует вывод метода {@link Exception#getStackTrace()} в строку для
	 * дальнейшего использования
	 * 
	 * @param ex
	 *            исключение, вывод которого необходимо получить как строку
	 * @return строка, содержащая вывод метода {@link Exception#getStackTrace()}
	 */
	public static String getStackTrace(Exception ex) {
		StringWriter errors = new StringWriter();
		ex.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}

	/**
	 * Удаляет дублирующиеся элементы из переданного списка строк. Возвращает
	 * новый список строк без дублирующихся элементов. Исходный порядок
	 * элементов в возвращаемом списке сохраняется
	 * 
	 * @param list
	 *            передаваемый список для удаления дублирующихся элементов
	 * @return список без дублирующихся элементов
	 */
	public static List<String> removeDuplicatesFromStringList(List<String> list) {
		Set<String> set = new LinkedHashSet<String>(list);
		return new ArrayList<String>(set);
	}

	/**
	 * Сохраняет перечень плейсхолдеров и значений в JSON-файле с указанным
	 * именем
	 * 
	 * @param placeholdersRoot
	 * @param fileName
	 * @throws IOException
	 */
	public static void savePlaceholdersRootToFile(TreeNode placeholdersRoot, String fileName) throws IOException {
		JSONArray jsonArray = convertPlaceholdersRootToJsonArray(placeholdersRoot);

		JsonReadWriteUtils.saveJsonArrayToFile(jsonArray, fileName);
	}

	/**
	 * Преобразует placeholdersRoot в JSON-массив со структурой [{placeholder:
	 * "placeholdervalue", values: ["values"]}]
	 * 
	 * @param placeholdersRoot
	 * @return
	 */
	private static JSONArray convertPlaceholdersRootToJsonArray(TreeNode placeholdersRoot) {
		JSONArray jsonArray = new JSONArray();
		JSONArray valuesArray;
		JSONObject jsonObject;
		for (TreeNode placeholderNode : placeholdersRoot.getChildren()) {
			valuesArray = new JSONArray();
			for (TreeNode valueNode : placeholderNode.getChildren()) {
				valuesArray.add(valueNode.getText());
			}

			jsonObject = new JSONObject();
			jsonObject.put("placeholder", placeholderNode.getText());
			jsonObject.put("values", valuesArray);

			jsonArray.add(jsonObject);
		}

		return jsonArray;
	}

	public static void readPlaceholdersToRoot(TreeNode placeholdersRoot, String fileName)
			throws IOException, ParseException {
		JSONArray jsonArray = JsonReadWriteUtils.readJsonArrayFromFile(fileName);
		JSONArray valuesArray;
		JSONObject jsonObject;
		TreeNode placeholderNode, valueNode;

		for (Object o : jsonArray) {
			jsonObject = (JSONObject) o;
			placeholderNode = new TreeNode((String)jsonObject.get("placeholder"));
			valuesArray = (JSONArray) jsonObject.get("values");
			for(Object o2: valuesArray){
				valueNode = new TreeNode((String)o2);
				placeholderNode.addChild(valueNode);
			}
			placeholdersRoot.addChild(placeholderNode);
		}
	}
	
	/**
	 * Проверяет, является ли переданная строка целым числом. Спасибо Stackoverflow. <br/>
	 * http://stackoverflow.com/questions/5439529/determine-if-a-string-is-an-integer-in-java
	 * @param s
	 * @return
	 */
	public static boolean isInteger(String s) {
	    return isInteger(s,10);
	}

	/**
	 * Проверяет, является ли переданная строка целым числом в системе radix. Спасибо Stackoverflow. <br/>
	 * http://stackoverflow.com/questions/5439529/determine-if-a-string-is-an-integer-in-java
	 * @param s
	 * @return
	 */
	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}
}
