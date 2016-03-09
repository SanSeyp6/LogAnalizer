package util;

import java.util.List;
import java.util.regex.Pattern;

public class Templates {
	private static final Pattern PLACEHOLDER_REGEX = Pattern.compile("^\\{\\w*\\}$");

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
						if ((isPlaceholder(sPlaceholder) || sPlaceholder.equals("{&}"))
								&& (isPlaceholder(lcsPlaceholder) || lcsPlaceholder.equals("{&}"))) {
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
	public static boolean isPlaceholder(String string) {
		return PLACEHOLDER_REGEX.matcher(string).matches();
	}

	// это старая версия метода. Оставлена тут, чтобы было легко откатиться и
	// сравнивать
	// TODO это тоже полный пиздец и надо рефакторить, как и #getTemplate
	public static String uniteTemplates(List<String> templates) {
		String unitedTemplate = templates.get(0);
		String tmp;
		int i = 0, j = 0;
		int placeholdersCount = 0;
		int curveBracePosition;
		String unitedemplatePlaceholder, sPlaceholder;

		for (String s : templates) {
			if (!unitedTemplate.equals(s)) {
				i = 0;
				j = 0;
				StringBuilder sb = new StringBuilder();
				while (i < unitedTemplate.length() && j < s.length()) {
					if (unitedTemplate.charAt(i) == s.charAt(j)) {
						if (unitedTemplate.charAt(i) == '{') {
							if ((unitedTemplate.indexOf('}', i) != -1) && (s.indexOf('}', j) != -1)) {
								unitedemplatePlaceholder = unitedTemplate.substring(i, unitedTemplate.indexOf('}', i) + 1);
								System.out.println("unitedemplatePlaceholder:" + unitedemplatePlaceholder);
	
								sPlaceholder = s.substring(j, s.indexOf('}', j) + 1);
								System.out.println("sPlaceholder:" + sPlaceholder);
	
								if ((isPlaceholder(unitedemplatePlaceholder) || unitedemplatePlaceholder.equals("{&}"))
										&& (isPlaceholder(sPlaceholder) || sPlaceholder.equals("{&}"))) {
									if (sPlaceholder.equals(unitedemplatePlaceholder)) {
										sb.append(sPlaceholder);
										System.out.println("sb: " + sb.toString());
										i += sPlaceholder.length();
										j += sPlaceholder.length();
									} else if(unitedemplatePlaceholder.equals("{&}")){
										sb.append(unitedemplatePlaceholder);
										System.out.println("sb: " + sb.toString());
										i += unitedemplatePlaceholder.length();
									} else if(sPlaceholder.equals("{&}")){
										sb.append(sPlaceholder);
										System.out.println("sb: " + sb.toString());
										j += sPlaceholder.length();
									} else {
										throwException(templates, unitedTemplate, s, i, j);
									}
								} else {
									sb.append(unitedTemplate.charAt(i));
									System.out.println("sb: " + sb.toString());
									i++;
									j++;
								}
							} else {
								sb.append(unitedTemplate.charAt(i));
								System.out.println("sb: " + sb.toString());
								i++;
								j++;
							}
						
						} else {
							
						}
						
						
						
						
						if (unitedTemplate.charAt(i) == '{') {
							curveBracePosition = unitedTemplate.indexOf('}', i);
							if(curveBracePosition != -1){
								tmp = unitedTemplate.substring(i, curveBracePosition + 1);
								if (isPlaceholder(tmp) || tmp.equals("{&}")	) {
									sb.append(tmp);
									i+=tmp.length();
								} else {
									throwException(templates, unitedTemplate, s, i, j);
								}
							} else {
								throwException(templates, unitedTemplate, s, i, j);
							}
						} else if(s.charAt(j) == '{') {
							curveBracePosition = s.indexOf('}', j);
							if(curveBracePosition != -1){
								tmp = s.substring(i, curveBracePosition + 1);
								if (isPlaceholder(tmp) || tmp.equals("{&}")	) {
									sb.append(tmp);
									j+=tmp.length();
								} else {
									throwException(templates, unitedTemplate, s, i, j);
								}
							} else {
								throwException(templates, unitedTemplate, s, i, j);
							}
						} else {
							sb.append(unitedTemplate.charAt(i));
							i++;
							j++;
						}

					} else {
					}
				}
				// если что-то длинней другого, добавляем плейсхолдер
				if ((i < unitedTemplate.length() && j == s.length())
						|| (i == unitedTemplate.length() && j < s.length())) {
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
	
	private static void throwException(List<String> templates, String unitedTemplate, String s, int i, int j){
		System.err.println("Templates:" + templates);
		System.err.println("UnitedTemplate:" + unitedTemplate);
		System.err.println("CurrentTemplate:" + s);
		System.err.println("i:" + i);
		System.err.println("j:" + j);
		throw new IllegalStateException("Unexpected chars in templates");
	}

}
