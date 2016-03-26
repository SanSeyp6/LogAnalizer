package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Templates {

	/**
	 * Метод строит обобщённый шаблон для группы строк. Так как вычисление lcSequence стоит дорого, 
	 * то его следует вычислять отдельно один раз и далее передавать в методы, использующие его.
	 * @param similarString
	 * @param lcSequence
	 * @return
	 */
	public static String getUnitedTemplate(List<String> similarStrings, String lcSequence){
		StringBuilder template = getInitialTemplateCandidate(similarStrings, lcSequence);
		template = removeUnneccessaryPlaceholders(template, similarStrings);
		return template.toString();
	}

	/**
	 * @param similarStrings
	 * @param lcSequence
	 * @return
	 */
	private static StringBuilder getInitialTemplateCandidate(List<String> similarStrings, String lcSequence) {
		StringBuilder sb = new StringBuilder();
		List<StringBuilder> sbList = getStringBuilders(similarStrings);
		int i = 0;
		char ch;
		boolean anyStringHasMoreSymbols = hasAnyStringMoreSymbols(sbList);
		
		while (i < lcSequence.length() && anyStringHasMoreSymbols) {
			ch = lcSequence.charAt(i);
			if(isSymbolFirst(ch, sbList)){
				sb.append(ch);
				i++;
				deleteFirstSymbol(sbList);
			} else {
				sb.append("{&}");
				deleteFirstDifferentSymbols(ch, sbList);
			}
			anyStringHasMoreSymbols = hasAnyStringMoreSymbols(sbList);
		}
		if(i==lcSequence.length() && anyStringHasMoreSymbols){
			sb.append("{&}");
		}
		return sb;
	}

	/**
	 * Возвращает true, если хоть у одной строки в списке есть ещё символы
	 * @param sbList
	 * @return
	 */
	private static boolean hasAnyStringMoreSymbols(List<StringBuilder> sbList) {
		for(StringBuilder sb: sbList){
			if(sb.length() != 0){
				return true;
			}
		}
		return false;
	}

	private static List<StringBuilder> getStringBuilders(List<String> similarStrings) {
		List<StringBuilder> sbList = new ArrayList<StringBuilder>();
		StringBuilder sb;
		
		for(String s: similarStrings){
			sb = new StringBuilder(s);
			sbList.add(sb);
		}
		
		return sbList;
	}

	/**
	 * Проверяет является ли переданный символ первым в переданных строках (StringBuilder-ах) 
	 * @param ch
	 * @param sbList
	 * @return
	 */
	private static boolean isSymbolFirst(char ch, List<StringBuilder> sbList) {
		for(StringBuilder sb: sbList){
			if(sb.charAt(0) != ch){
				return false;
			}
		}
		return true;
	}

	private static void deleteFirstSymbol(List<StringBuilder> sbList) {
		for(StringBuilder sb: sbList){
			sb.delete(0, 1);
		}
	}
	
	/**
	 * Удаляет символы от начала строки до символа ch в каждой строке списка sbList
	 * @param ch
	 * @param sbList
	 */
	private static void deleteFirstDifferentSymbols(char ch, List<StringBuilder> sbList) {
		int i;
		for(StringBuilder sb: sbList){
			i=0;
			while (ch != sb.charAt(i) && i < sb.length()) {
				i++;
			}
			sb.delete(0, i);
		}
	}

	/**
	 * 
	 * @param template
	 * @param similarStrings
	 */
	private static StringBuilder removeUnneccessaryPlaceholders(StringBuilder template, List<String> similarStrings) {
		int index=0;
		StringBuilder candidate;
		Pattern pattern;
		
		candidate=new StringBuilder(template);
		index = candidate.indexOf("{&}", index);
		while(index != -1){
			candidate.delete(index, index+3);
			pattern = ParseMessage.buildPatternWithUnnamedPlaceholders(candidate.toString());
			if(matchesAll(pattern, similarStrings)){
				template = candidate;
			} else {
				index++;
			}
			candidate=new StringBuilder(template);
			index = candidate.indexOf("{&}", index);
		}
		
		return template;
	}

	public static boolean matchesAll(Pattern pattern, List<String> similarStrings){
		for(String s: similarStrings){
			if(!pattern.matcher(s).matches()){
//				System.out.println("match fails at: "+s);
				return false;
			}
		}
		
		return true;
	}

}
