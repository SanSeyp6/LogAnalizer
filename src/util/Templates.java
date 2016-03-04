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

		System.out.println("message: " + s);
		System.out.println("lcs: " + lcs);
		System.out.println();

		while (i < s.length() && k < lcs.length()) {
			if (s.charAt(i) == lcs.charAt(k)) {
				// Хитрая логика, чтобы когда в lcs и message есть шаблоны, всё
				// нормально работало. Просто посимвольное сравнение оказалось
				// не катит
				if (s.charAt(i) == '{') {
					if ((s.indexOf('}', i) != -1) && (lcs.indexOf('}', k) != -1 ) ) {
						sPlaceholder = s.substring(i, s.indexOf('}', i) + 1);
						System.out.println("sPlaceholder:" + sPlaceholder);

						lcsPlaceholder = lcs.substring(k, lcs.indexOf('}', k) + 1);
						System.out.println("lcsPlaceholder:" + lcsPlaceholder);

						if ((isPlaceholder(sPlaceholder) || sPlaceholder.equals("{&}"))
								&& (isPlaceholder(lcsPlaceholder) || lcsPlaceholder.equals("{&}"))) {
							if (sPlaceholder.equals(lcsPlaceholder)) {
								sb.append(sPlaceholder);
								System.out.println("sb: " + sb.toString());
								i += sPlaceholder.length();
								k += sPlaceholder.length();
							} else {
								sb.append(sPlaceholder);
								i += sPlaceholder.length();
								System.out.println("sb: " + sb.toString());
							}
						} else {
							sb.append(lcs.charAt(k));
							System.out.println("sb: " + sb.toString());
							i++;
							k++;
						}
					} else {
						sb.append(lcs.charAt(k));
						System.out.println("sb: " + sb.toString());
						i++;
						k++;
					}
					// конец хитрой логики =)
				} else {
					sb.append(lcs.charAt(k));
					System.out.println("sb: " + sb.toString());
					i++;
					k++;
				}
			} else {
				sb.append("{&}");
				System.out.println("sb: " + sb.toString());
				while (s.charAt(i) != lcs.charAt(k)) {
					i++;
				}
			}
		}

		if (i < s.length()) {
			sb.append("{&}");
			System.out.println("sb: " + sb.toString());
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
		if (PLACEHOLDER_REGEX.matcher(string).matches()) {
			System.out.println("placeholder!");
		} else {
			System.out.println("ne placeholder!");
		}

		return PLACEHOLDER_REGEX.matcher(string).matches();
	}

	// это старая версия метода. Оставлена тут, чтобы было легко откатиться и
	// сравнивать
	/*
	 * public static String uniteTemplates(List<String> templates) { String
	 * unitedTemplate = templates.get(0); String tmp; int i = 0, j = 0; int
	 * placeholdersCount = 0;
	 * 
	 * 
	 * for (String s : templates) { if (!unitedTemplate.equals(s)) { i = 0; j =
	 * 0; StringBuilder sb = new StringBuilder(); while (i <
	 * unitedTemplate.length() && j < s.length()) { if (unitedTemplate.charAt(i)
	 * == s.charAt(j)) { sb.append(unitedTemplate.charAt(i)); i++; j++; } else {
	 * if (unitedTemplate.charAt(i) == '{' && unitedTemplate.charAt(i + 1) ==
	 * '&' && unitedTemplate.charAt(i + 2) == '}') { sb.append("{&}"); i += 3; }
	 * else if (s.charAt(j) == '{' && s.charAt(j + 1) == '&' && s.charAt(j + 2)
	 * == '}') { sb.append("{&}"); j += 3; } else {
	 * System.err.println("Templates:" + templates);
	 * System.err.println("UnitedTemplate:" + unitedTemplate);
	 * System.err.println("CurrentTemplate:" + s); System.err.println("i:" + i);
	 * System.err.println("j:" + j); throw new IllegalStateException(
	 * "Unexpected chars in templates"); } } } // если что-то длинней другого,
	 * добавляем плейсхолдер if ((i < unitedTemplate.length() && j ==
	 * s.length()) || (i == unitedTemplate.length() && j < s.length())) {
	 * sb.append("{&}"); } unitedTemplate = sb.toString(); } }
	 * 
	 * // now replace placehoders with numbers in united template do { tmp =
	 * unitedTemplate; unitedTemplate = tmp.replaceFirst("\\{&\\}", "{" +
	 * placeholdersCount + "}"); placeholdersCount++; } while
	 * (!tmp.equals(unitedTemplate));
	 * 
	 * return unitedTemplate; }
	 */

	
	// Блять! Похоже всё равно не получается. Когда 
	public static String uniteTemplates(List<String> templates) {
		String unitedTemplate = templates.get(0);
		String tmp;
		int placeholdersCount = 0;

		for (String template : templates) {
			unitedTemplate = Templates.getTemplate(template, StringComparison.computeLCS(unitedTemplate, template));
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

}
