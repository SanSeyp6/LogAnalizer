package util;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseMessage {
	public static final String SPECIAL_CHARS_STRING = "[{}()[].+*?^$\\|]";
	public static final Pattern SPECIAL_CHARS_REGEX = Pattern.compile("[{}()\\[\\].+*?^$\\\\|]");
	public static final Pattern PLACEHOLDERS_REGEX = Pattern.compile("\\{\\w*\\}");

	public static String escapeSpecialRegexChars(String str) {
		return SPECIAL_CHARS_REGEX.matcher(str).replaceAll("\\\\$0");
	}

	/**
	 * Пытается разобрать сообщение на основе переданного шаблона. Если в
	 * шаблоне нет полей (вида {filedName}) или сообщение не соответствует
	 * шаблону, то возвращает пустой Map<String, String>. <br/>
	 * Данная функция очень дорога в плане времени выполнения, поэтому следует
	 * вызывать её минимально возможное количество раз.
	 * 
	 * @param message
	 * @param template
	 * @return
	 */
	public static Map<String, String> parseMessageAgainstTemplate(String message, String template) {

/*
		String pString = StringComparison.computeDiff(template, template.replaceAll("\\{\\w*\\}", ""));
		if (pString.isEmpty()) {
			return Collections.emptyMap();
		}
		List<String> placeholders = convertPlaceholdersStringToList(pString);
		if (placeholders.isEmpty()) {
			return Collections.emptyMap();
		}
*/		
		List<String> placeholders=getPlaceholders(template);
		if(placeholders.isEmpty()){
			return Collections.emptyMap();
		}
		
		String regexp = escapeSpecialRegexChars(template);
		regexp = regexp.replaceAll("\\\\\\{\\w*\\\\\\}", "(.*)"); // FUCK! Hate regexp!
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(message);
		if (matcher.matches()) {
			int count = matcher.groupCount();
			Map<String, String> map = new HashMap<String, String>(count);
			// group 0 is full message by convention, skip it, start from 1
			for (int i = 1; i <= count; i++) {
				map.put(placeholders.get(i - 1), matcher.group(i));
			}
			return map;
		} else {
			return Collections.emptyMap();
		}
	}

	public static List<String> getPlaceholders(String template){
		List<String> returnList = null;
		Matcher matcher = PLACEHOLDERS_REGEX.matcher(template);
		String group;
		
		while(matcher.find()){
			if(returnList == null){
				returnList = new ArrayList<String>();
			}
			group=matcher.group();
			returnList.add(group.substring(1, group.length()-1)); //избавляемся от скобок
		}
		
		if(returnList == null){
			return Collections.emptyList();
		} else {
			return returnList;
		}
	}
	
}
