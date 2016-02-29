package util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import main.ParseMessage;
import main.SimilarMessagesComposite.TreeNode;

/**
 * Тут хранятся вспомогательные статические методы
 * @author aaovchinnikov
 *
 */
public class Util {
	/**
	 * Находит все именованные placeholder-ы и заносит их в дерево placeholder-ов
	 * @param templates
	 * @param placeholdersRoot
	 */
	public static void computePlaceholders(List<String> templates, TreeNode placeholdersRoot) {
		List<String> placeholders;
		for(String template: templates){
			placeholders = ParseMessage.getPlaceholders(template);
			for(String placeholder: placeholders){
				try{
					Integer.parseInt(placeholder);	
				} catch (NumberFormatException e){
					placeholdersRoot.addChild(new TreeNode(placeholder));	
				}
			}
		}
	}

	/**
	 * Преобразует вывод метода {@link Exception#getStackTrace()} в строку для дальнейшего использования
	 * @param ex исключение, вывод которого необходимо получить как строку
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
	 * @param list передаваемый список для удаления дублирующихся элементов
	 * @return список без дублирующихся элементов
	 */
	public static List<String> removeDuplicatesFromStringList(List<String> list) {
		Set<String> set = new LinkedHashSet<String>(list);
		return new ArrayList<String>(set);
	}

}
