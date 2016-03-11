package util;

import java.util.List;
import java.util.regex.Pattern;

public class Templates {
	private static final Pattern PLACEHOLDER_REGEX = Pattern.compile("^\\{\\w*\\}$");
	private static final String UNNAMED_PLACEHOLDER = "{&}";

	/**
	 * Вариант, когда в LCS могут попасть {&}
	 * @param s
	 * @param lcs
	 * @return
	 */
	public static String getTemplate(String s, String lcs) {
		StringBuilder sb = new StringBuilder();
		int i = 0, k = 0;
		int lcsCurveBracePosition, sCurveBracePosition;
		String sPlaceholder, lcsPlaceholder;
		int sPlaceholderPosition;

		while (i < s.length() && k < lcs.length()) {
			
			if (lcs.charAt(k) == '{') {
			}
		}
	}
	
/*	
	public static String getTemplate(String s, String lcs) {
		StringBuilder sb = new StringBuilder();
		int i = 0, k = 0;
		int lcsCurveBracePosition, sCurveBracePosition;
		String sPlaceholder, lcsPlaceholder;
		int sPlaceholderPosition;

		while (i < s.length() && k < lcs.length()) {
			if (lcs.charAt(k) == '{') {
				lcsCurveBracePosition = lcs.indexOf('}', k);
				// перед нами плейсхолдер в LCS. тут дальше в строке могут быть:
				// 1) просто символы. Тогда надо все в строке до этого плейсхолдера заменить на {&}
				// ВОПРОС: может ли построиться LCS так, что из частей плейсхолдера получится плейсхолдер. 
				// Вроде нет, но если я ошибся, то тут могут быть баги
				// 2) просто {, без плейсхолдера - заменить в строке { и всё до этого плейсхолдера на {&} 
				// 3) другой плейсхолдер - - заменить в строке всё до нашего плейсхолдера на {&} вместе с найденным плейсхолдером
				// 4) этот плейсхолдер - круто, переносим его
				if (lcsCurveBracePosition != -1) {
					lcsPlaceholder = lcs.substring(k, lcsCurveBracePosition + 1);
					if(s.charAt(i)=='{'){
						sCurveBracePosition = s.indexOf('}', i);
						if(sCurveBracePosition != -1){
							sPlaceholder = s.substring(i, sCurveBracePosition + 1);
							if(isNamedPlaceholder(sPlaceholder)){ 
								if(lcsPlaceholder.equals(sPlaceholder)){ // случай 4)
									sb.append(lcsPlaceholder);
									i+=lcsPlaceholder.length();
									k+=lcsPlaceholder.length();
								} else { // случай 3)
									sb.append(UNNAMED_PLACEHOLDER);
									sPlaceholderPosition = s.indexOf(lcsPlaceholder, i);
									if(sPlaceholderPosition != -1){
										sb.append(lcsPlaceholder);
										i=sPlaceholderPosition + lcsPlaceholder.length();
										k+=lcsPlaceholder.length();
										continue;
									} else { // случай, когда плейсхолдера такого нет, но в LCS он почему-то появлся.
										// Этого случить не должно, но мы знаем как обычно бывает
										System.err.println(lcsPlaceholder);
										throw new IllegalStateException("during LCS-ing non-exising placeholder was built");
									}
								}
							} else { // sPlaceholder не именованный плейсхолдер, то случай 2)
								sb.append(UNNAMED_PLACEHOLDER);
								sPlaceholderPosition = s.indexOf(lcsPlaceholder, i);
								if(sPlaceholderPosition != -1){
									sb.append(lcsPlaceholder);
									i=sPlaceholderPosition + lcsPlaceholder.length();
									k+=lcsPlaceholder.length();
									continue;
								} else { // случай, когда плейсхолдера такого нет, но в LCS он почему-то появлся.
									// Этого случить не должно, но мы знаем как обычно бывает
									System.err.println(lcsPlaceholder);
									throw new IllegalStateException("during LCS-ing non-exising placeholder was built");
								}
							}
						} else { // это случай 2) описанный выше. не плейсхолдер, в строке нету '}'
							sb.append(UNNAMED_PLACEHOLDER);
							sPlaceholderPosition = s.indexOf(lcsPlaceholder, i);
							if(sPlaceholderPosition != -1){
								sb.append(lcsPlaceholder);
								i=sPlaceholderPosition + lcsPlaceholder.length();
								k+=lcsPlaceholder.length();
								continue;
							} else { // случай, когда плейсхолдера такого нет, но в LCS он почему-то появлся.
								// Этого случить не должно, но мы знаем как обычно бывает
								System.err.println(lcsPlaceholder);
								throw new IllegalStateException("during LCS-ing non-exising placeholder was built");
							}
						}
					} else { // это случай 1) описанный выше
						sb.append(UNNAMED_PLACEHOLDER);
						sPlaceholderPosition = s.indexOf(lcsPlaceholder, i);
						if(sPlaceholderPosition != -1){
							sb.append(lcsPlaceholder);
							i=sPlaceholderPosition + lcsPlaceholder.length();
							k+=lcsPlaceholder.length();
							continue;
						} else { // случай, когда плейсхолдера такого нет, но в LCS он почему-то появлся.
							// Этого случить не должно, но мы знаем как обычно бывает
							System.err.println(lcsPlaceholder);
							throw new IllegalStateException("during LCS-ing non-exising placeholder was built");
						}
					}
				} else { // это всё же не плейсхолдер в LCS
					if (s.charAt(i) == lcs.charAt(k)) {
						sb.append(lcs.charAt(k));
						System.out.println(sb);
						i++;
						k++;
					} else {
						sb.append(UNNAMED_PLACEHOLDER);
						System.out.println(sb);
						while (s.charAt(i) != lcs.charAt(k)) {
							i++;
						}
					}
				}
			} else {
//				System.out.println(s.charAt(i) +" "+ lcs.charAt(k));
				if (s.charAt(i) == lcs.charAt(k)) {
					sb.append(lcs.charAt(k));
					System.out.println(sb);
					i++;
					k++;
				} else {
					sb.append(UNNAMED_PLACEHOLDER);
					System.out.println(sb);
					while (s.charAt(i) != lcs.charAt(k)) {
						i++;
					}
				}
			}
		}

		if (i < s.length()) {
			sb.append(UNNAMED_PLACEHOLDER);
		}

		return sb.toString();
	}
*/
	
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
/*
	public static String uniteTemplates(List<String> templates) {
		String unitedTemplate = templates.get(0);
		String tmp;
		int i = 0, j = 0;
		int placeholdersCount = 0;
		
		int unitedTempalteCurveBracePosition, templateCurveBracePosition;
		String sPlaceholder, unitedTemplatePlaceholder;
		int sPlaceholderPosition;


		for (String s : templates) {
			i = 0;
			j = 0;
			StringBuilder sb = new StringBuilder();
			while (i < unitedTemplate.length() && j < s.length()) {
				if (unitedTemplate.charAt(i) == '{' || s.charAt(j)=='{') {
					if(unitedTemplate.charAt(i) == '{' && s.charAt(j)=='{'){
						if(isNamedPlaceholderAt(unitedTemplate, i) && isNamedPlaceholderAt(s,j)){
							unitedTemplatePlaceholder = getNamedPlaceholderAt(unitedTemplate, i);
							sPlaceholder = getNamedPlaceholderAt(s,j);
							if(unitedTemplatePlaceholder.equals(sPlaceholder)){
								sb.append(sPlaceholder);
								i+=sPlaceholder.length();
								j+=sPlaceholder.length();
							} else {
								throw new IllegalStateException("non equal placeholders");
							}
						} else {
							
						}
					} else {
						
					}
				} else {
					
				}
			}
			
			
			
			if (!unitedTemplate.equals(s)) { //TODO большой вопрос стоит ли так делать. Один хрен же посимвольно иду что при equals, что при своей обработке
				i = 0;
				j = 0;
				StringBuilder sb = new StringBuilder();
				while (i < unitedTemplate.length() && j < s.length()) {
					if (unitedTemplate.charAt(i) == '{' || s.charAt(j)=='{') {
						if(unitedTemplate.charAt(i) == '{' && s.charAt(j)=='{'){
							unitedTempalteCurveBracePosition = unitedTemplate.indexOf('}', i);
							templateCurveBracePosition = s.indexOf('}', j);
							if(unitedTempalteCurveBracePosition == -1 && templateCurveBracePosition == -1) { //случай на конце, когда концы одинаковы
								sb.append('{');
								i++;
								j++;
								continue;
							} else if(unitedTempalteCurveBracePosition == -1  || templateCurveBracePosition == -1){ //случай оба вместе рассмотрен выше и тут исключён
								
							} else {
								
							}
						} else {
							
						}
					} else {
						if (unitedTemplate.charAt(i) == s.charAt(j)) {
							sb.append(unitedTemplate.charAt(i));
							i++;
							j++;
						} else {
							// такого опять таки произойти не должно, но мы знаем как это бывает)
							throw new IllegalStateException("unequal characters");
						}
					}
						
						
						// либо просто {, то есть нет плейсхолдера
						// либо {&}
						// либо именованный плейсхолдер
						unitedTempalteCurveBracePosition = unitedTemplate.indexOf('}', i);
						if(unitedTempalteCurveBracePosition != -1){
							unitedTemplatePlaceholder = unitedTemplate.substring(i, unitedTempalteCurveBracePosition + 1);
							if(isNamedPlaceholder(unitedTemplatePlaceholder)){
								
							} else if (unitedTemplatePlaceholder.equals(UNNAMED_PLACEHOLDER)){
								
							} else {
								
							}
						} else {
							
						}
						
					} else {
						
					}
					
					if (unitedTemplate.charAt(i) == s.charAt(j)) {
						sb.append(unitedTemplate.charAt(i));
						i++;
						j++;
					} else {
						if (unitedTemplate.charAt(i) == '{' && unitedTemplate.charAt(i + 1) == '&' && unitedTemplate.charAt(i + 2) == '}') {
							sb.append("{&}");
							i += 3;
						} else if (s.charAt(j) == '{' && s.charAt(j + 1) == '&' && s.charAt(j + 2) == '}') {
							sb.append("{&}");
							j += 3;
						} else {
							System.err.println("Templates:" + templates);
							System.err.println("UnitedTemplate:" + unitedTemplate);
							System.err.println("CurrentTemplate:" + s);
							System.err.println("i:" + i);
							System.err.println("j:" + j);
							throw new IllegalStateException("Unexpected chars in templates");
						}
					}
				}
				// если что-то длинней другого, добавляем плейсхолдер
				if ((i < unitedTemplate.length() && j == s.length()) || (i == unitedTemplate.length() && j < s.length())) {
					sb.append("{&}");
				}
				unitedTemplate = sb.toString();
			}
		}

		// now replace placehoders with numbers in united template
		do {
			tmp = unitedTemplate;
			unitedTemplate = tmp.replaceFirst("\\{&\\}", "{" + placeholdersCount + "}");
			placeholdersCount++;
		} while (!tmp.equals(unitedTemplate));

		return unitedTemplate;
	}
*/
/*	
	public static String uniteTemplates(List<String> templates) {
		String unitedTemplate = templates.get(0);
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
						sb.append(unitedTemplate.charAt(i));
						i++;
						j++;
					} else {
						if (unitedTemplate.charAt(i) == '{' && unitedTemplate.charAt(i + 1) == '&' && unitedTemplate.charAt(i + 2) == '}') {
							sb.append("{&}");
							i += 3;
						} else if (s.charAt(j) == '{' && s.charAt(j + 1) == '&' && s.charAt(j + 2) == '}') {
							sb.append("{&}");
							j += 3;
						} else {
							System.err.println("Templates:" + templates);
							System.err.println("UnitedTemplate:" + unitedTemplate);
							System.err.println("CurrentTemplate:" + s);
							System.err.println("i:" + i);
							System.err.println("j:" + j);
							throw new IllegalStateException("Unexpected chars in templates");
						}
					}
				}
				// если что-то длинней другого, добавляем плейсхолдер
				if ((i < unitedTemplate.length() && j == s.length()) || (i == unitedTemplate.length() && j < s.length())) {
					sb.append("{&}");
				}
				unitedTemplate = sb.toString();
			}
		}

		// now replace placehoders with numbers in united template
		do {
			tmp = unitedTemplate;
			unitedTemplate = tmp.replaceFirst("\\{&\\}", "{" + placeholdersCount + "}");
			placeholdersCount++;
		} while (!tmp.equals(unitedTemplate));

		return unitedTemplate;
	}
*/

	private static boolean isNamedPlaceholderAt(String s, int index){
		int curveBracePosition;
		String tmp;
		
		if(s.charAt(index)=='{'){
			curveBracePosition = s.indexOf('}');
			if(curveBracePosition == -1) {
				return false;
			} else {
				tmp = s.substring(index, curveBracePosition+1);
				return isNamedPlaceholder(tmp); 
			}
		} else {
			return false;
		}
	}
	
	private static String getNamedPlaceholderAt(String s, int index){
		return s.substring(index, s.indexOf('}')+1);
	}
	
	private static boolean isUnnamedPlaceholderAt(String s, int index){
		if(index+UNNAMED_PLACEHOLDER.length() > s.length()){
			return false;
		}
		
		if(s.substring(index, UNNAMED_PLACEHOLDER.length()).equals(UNNAMED_PLACEHOLDER)){
			return true;
		} else {
			return false;
		}
	}
}
