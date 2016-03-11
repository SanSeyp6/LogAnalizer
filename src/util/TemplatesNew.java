package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class TemplatesNew {
	private static final Pattern PLACEHOLDER_REGEX = Pattern.compile("^\\{\\w*\\}$");
	public static final String UNNAMED_PLACEHOLDER = "{&}";
	public static final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";
	


	/**
	 * На основе переданного LCS из сообщения делает шаблон, подменяя места,
	 * которые в LCS отсутствуют на {&}. Можно также использовать на шаблонах с
	 * именоваными плейсхолдерами
	 * 
	 * @param s
	 * @param lcs
	 * @return
	 */
	// TODO очевидно надо рефакторить этот код :'(
	public static String getTemplate(String s, String lcs) {
		StringBuilder sb = new StringBuilder();
		int i = 0, k = 0;
		String sPlaceholder, lcsPlaceholder;

		while (i < s.length() && k < lcs.length()) {
			if (s.charAt(i) == lcs.charAt(k)) {
				// Хитрая логика, чтобы когда в lcs и message есть шаблоны, всё
				// нормально работало. Просто посимвольное сравнение оказалось
				// не катит
				if (s.charAt(i) == '{') {
					if ((s.indexOf('}', i) != -1) && (lcs.indexOf('}', k) != -1)) {
						sPlaceholder = s.substring(i, s.indexOf('}', i) + 1);
						lcsPlaceholder = lcs.substring(k, lcs.indexOf('}', k) + 1);
						if ((isNamedPlaceholder(sPlaceholder) || sPlaceholder.equals("{&}"))
								&& (isNamedPlaceholder(lcsPlaceholder) || lcsPlaceholder.equals("{&}"))) {
							if (sPlaceholder.equals(lcsPlaceholder)) {
								sb.append(sPlaceholder);
								i += sPlaceholder.length();
								k += sPlaceholder.length();
							} else {
								sb.append(sPlaceholder);
								i += sPlaceholder.length();
							}
						} else {
							sb.append(lcs.charAt(k));
							i++;
							k++;
						}
					} else {
						sb.append(lcs.charAt(k));
						i++;
						k++;
					}
					// конец хитрой логики =)
				} else {
					sb.append(lcs.charAt(k));
					i++;
					k++;
				}
			} else {
				sb.append("{&}");
				while (s.charAt(i) != lcs.charAt(k)) {
					i++;
				}
			}
		}

		if (i < s.length()) {
			sb.append("{&}");
		}

		return sb.toString();
	}

	/**
	 * Проверяет, является ли переданная строка плейсхолдером, т.е. имеет ли вид
	 * {placeholder_name}
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNamedPlaceholder(String string) {
		return PLACEHOLDER_REGEX.matcher(string).matches();
	}

	
	// это старая версия метода. Оставлена тут, чтобы было легко откатиться и
	// сравнивать

	 public static String uniteTemplates(List<String> templates) { 
		 String	unitedTemplate = templates.get(0); 
		 String tmp; 
		 int i = 0, j = 0; 
		 int placeholdersCount = 0;
	 
		 for (String s : templates) {
			 if (!unitedTemplate.equals(s)) { 
				 i = 0; 
				 j = 0;
				 StringBuilder sb = new StringBuilder(); 
				 while (i < unitedTemplate.length() && j < s.length()) { 
					if (unitedTemplate.charAt(i) == s.charAt(j)) { 
						 sb.append(unitedTemplate.charAt(i)); i++; j++; 
					} else {
						if (unitedTemplate.charAt(i) == '{' 
								&& unitedTemplate.charAt(i + 1) == '&' 
								&& unitedTemplate.charAt(i + 2) == '}') { 
							sb.append("{&}"); 
							i += 3; 
						} else if (s.charAt(j) == '{' && s.charAt(j + 1) == '&' && s.charAt(j + 2) == '}') {
							sb.append("{&}"); 
							j += 3; 
						} else {
							System.err.println("Templates:" + templates);
							System.err.println("UnitedTemplate:" + unitedTemplate);
							System.err.println("CurrentTemplate:" + s); System.err.println("i:" + i);
							System.err.println("j:" + j); 
							throw new IllegalStateException("Unexpected chars in templates");
						}
					}
				} // если что-то длинней другого, добавляем плейсхолдер
				if ((i < unitedTemplate.length() && j == s.length()) 
						|| (i == unitedTemplate.length() && j < s.length())) {
					sb.append("{&}");
				} unitedTemplate = sb.toString(); 
			}
		}
	 
	 // now replace placehoders with numbers in united template 
		do {
			 tmp = unitedTemplate;
			 unitedTemplate = tmp.replaceFirst("\\{&\\}", "{" + placeholdersCount + "}"); placeholdersCount++; 
		} while (!tmp.equals(unitedTemplate));
		return unitedTemplate; 
	 }
	

		public static String uniteTemplates2(List<String> templates) {
			String unitedTemplate = templates.get(0);
			String tmp;
			int placeholdersCount = 0;

			for (String template : templates) {
				tmp = StringComparison.computeLCS(unitedTemplate, template);
				System.out.println("lcs for:");
				System.out.println(unitedTemplate);
				System.out.println(template);
				System.out.println("is: "+ tmp);
				unitedTemplate = Templates.getTemplate(template, tmp);
				System.out.println("current united template: "+ unitedTemplate);
			}

			System.out.println("final unitedTemplate: "+unitedTemplate);

			// now replace placehoders with numbers in united template
			do {
				// TODO может стоит переделать на StringBuilder?
				tmp = unitedTemplate;
				unitedTemplate = tmp.replaceFirst("\\{&\\}", "{" + placeholdersCount + "}");
				placeholdersCount++;
			} while (!tmp.equals(unitedTemplate));

			return unitedTemplate;
		}
	 
	public static String uniteTemplates3(List<String> templates){
		if (templates == null || templates.isEmpty()){
			throw new IllegalArgumentException("templates list is null or empty!");
		}
		
		int i,j;
		List<String> unitedTemplateList = new LinkedList<String>(Arrays.asList(templates.get(0).split(String.format(WITH_DELIMITER, "\\{(\\w*|&)\\}"))));
		List<String> currentTemplateList;
		List<String> tmpTemplateList;
		
		for(String template: templates){
			currentTemplateList = new LinkedList<String>(Arrays.asList(template.split(String.format(WITH_DELIMITER, "\\{(\\w*|&)\\}"))));
			if (currentTemplateList.equals(unitedTemplateList)){
//				System.out.println("spiski odinakovye");
			} else {
				tmpTemplateList = new LinkedList<String>();
				while(true){
					i=getFirstNonPlaceholderIndex(unitedTemplateList);
					j=getFirstNonPlaceholderIndex(currentTemplateList);
					if(i==-1 || j==-1){
						//случай, когда простых токенов нет, остались лишь placeholders в конце
						if(i==-1 && j==-1) { 
							tmpTemplateList.addAll(TemplatesNew.unitePlaceholders(unitedTemplateList, currentTemplateList));
//							System.out.println("tmpTemplateList: "+tmpTemplateList);
							break;
						} else { // это значит не-плейсхолдер остался в одном, а в другом не осталось. Такого случиться не должно
							throw new IllegalStateException("No non-placeholder tokens in template");						
						}
					}
					
					if(!unitedTemplateList.get(i).equals(currentTemplateList.get(j))){
						// в шаблоне должны остаться лишь одинаковые не-плейсхолдер токены. Так что и этого случиться не должно.
						// хм. и всё же это случилось. На довольно непохожих строках.
						// TODO окей, давай заменим различия на ещё один {&} и посмотрим, что будет.
						
						
// old exception code						

						System.err.println("templates: "+templates);
						System.err.println("template: "+template);
						System.err.println("unitedTemplateList: "+unitedTemplateList);
						System.err.println("currentTemplateList: "+currentTemplateList);
						System.err.println(unitedTemplateList.get(i));
						System.err.println(currentTemplateList.get(j));
						throw new IllegalStateException("Not equal non-placeholder tokens in templates");
						
					}

					// Сюда должны передаться только плейсхолдеры.
					tmpTemplateList.addAll(TemplatesNew.unitePlaceholders(unitedTemplateList.subList(0, i), currentTemplateList.subList(0, j)));
					tmpTemplateList.add(unitedTemplateList.get(i));
					System.out.println("tmpTemplateList: "+tmpTemplateList);
					
					removeFirstTokens(unitedTemplateList, i);
					removeFirstTokens(currentTemplateList, j);
				}
				unitedTemplateList = tmpTemplateList;
			}
		}
		
		System.out.println("unitedTemplateList: "+unitedTemplateList);
		
		return buildUnitedTemplateFromTokensList(unitedTemplateList);
	}
	
	private static int getFirstNonPlaceholderIndex(List<String> templateTokensList){
		int i=0;
		for(String s: templateTokensList){
			if(TemplatesNew.isNamedPlaceholder(s) || s.equals(TemplatesNew.UNNAMED_PLACEHOLDER)){
				i++;
			} else {
				break;
			}
		}

		if(i==templateTokensList.size()){
			return -1;	
		} else {
			return i;
		}
	}
	
	/**
	 * Удаляет первые n+1 токенов из переданного списка 
	 * @param list
	 * @param n
	 */
	private static void removeFirstTokens(List<String> list, int n){
		for(int i=0; i<=n; i++){
			list.remove(0);
		}
	}
	
	/**
	 * Возвращает обощённый список плейсхолдеров
	 * @param pList1
	 * @param pList2
	 * @return
	 */
	private static List<String> unitePlaceholders(List<String> pList1, List<String> pList2){
		// если одинаковые
		if(pList1.equals(pList2)){
//			System.out.println("the same");
			return pList1;
		}

		// если pList1 содержит в себе подсписок pList2
		if(Collections.indexOfSubList(pList1, pList2)!=-1){
//			System.out.println("pList2 is sublist of pList1");
			return pList1;
		}

		// если pList2 содержит в себе подсписок pList1
		if(Collections.indexOfSubList(pList2, pList1)!=-1){
//			System.out.println("pList1 is sublist of pList2");
			return pList2;
		}

		// если pList1.appendWithoutIntersection(pList2)
		List<String> returnList = concatListsWithoutSublistIntersection(pList1, pList2);
		if(returnList.isEmpty()){
			returnList = concatListsWithoutSublistIntersection(pList2, pList1);
			if(returnList.isEmpty()){
				returnList = new ArrayList<String>();
				returnList.addAll(pList1);
				returnList.addAll(pList2);
//				System.out.println("pList1 and pList2 different");
			} else {
//				System.out.println("pList2 appends pList1");
			}
		} else {
//			System.out.println("pList1 appends pList2");
		}
		
		return returnList;
	}

	/**
	 * Возвращает конкатенированный список без пересечения или пустой список, если пересечения нет
	 * @param list1
	 * @param list2
	 * @return
	 */
	private static List<String> concatListsWithoutSublistIntersection(List<String> list1, List<String> list2){
		int i,j;
		for(i=list1.size()-1, j=0; i>=0 && j < list2.size(); i--, j++){
			if(!list1.get(i).equals(list2.get(j))){
				break;
			}
		}
		if(j!=0){
			List<String> returnList = new ArrayList<String>();
			returnList.addAll(list1.subList(0, i+1));
			returnList.addAll(list2);
			return returnList;
		} else { //нет пересечения
			return Collections.emptyList();
		}
	}
	
	private static String buildUnitedTemplateFromTokensList(List<String> list){
		StringBuilder sb=new StringBuilder();
		for(String s: list){
			sb.append(s);
		}
		String unitedTemplate = sb.toString();
		
		// now replace placehoders with numbers in united template
		String tmp;
		int placeholdersCount=0;
		do {
			tmp = unitedTemplate;
			unitedTemplate = tmp.replaceFirst("\\{&\\}", "{" + placeholdersCount + "}");
			placeholdersCount++;
		} while (!tmp.equals(unitedTemplate));

		return unitedTemplate;
	}
	
	private static void throwException(List<String> templates, String unitedTemplate, String s, int i, int j){
		System.err.println("Templates:" + templates);
		System.err.println("UnitedTemplate:" + unitedTemplate);
		System.err.println("CurrentTemplate:" + s);
		System.err.println("i:" + i);
		System.err.println("j:" + j);
		throw new IllegalStateException("Unexpected chars in templates");
	}

}
